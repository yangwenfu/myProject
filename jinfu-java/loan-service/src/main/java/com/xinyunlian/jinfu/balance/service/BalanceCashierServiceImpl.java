package com.xinyunlian.jinfu.balance.service;

import com.xinyunlian.jinfu.balance.dao.BalanceCashierDao;
import com.xinyunlian.jinfu.balance.dao.BalanceDetailDao;
import com.xinyunlian.jinfu.balance.dao.BalanceOutlineDao;
import com.xinyunlian.jinfu.balance.entity.BalanceCashierPo;
import com.xinyunlian.jinfu.balance.entity.BalanceDetailPo;
import com.xinyunlian.jinfu.balance.entity.BalanceOutlinePo;
import com.xinyunlian.jinfu.balance.enums.EBalanceStatus;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.ftp.util.FtpUtil;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Willwang on 2017/5/19.
 */
@Component
public class BalanceCashierServiceImpl implements BalanceCashierService {

    @Autowired
    private BalanceCashierDao balanceCashierDao;

    @Autowired
    private BalanceOutlineDao balanceOutlineDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceCashierServiceImpl.class);

    @Override
    @Transactional
    public void refresh(Long outlineId) throws BizServiceException {
        List<BalanceCashierPo> cashierPos = balanceCashierDao.findByOutlineId(outlineId);

        if(cashierPos.size() > 0){
            throw new BizServiceException(EErrorCode.LOAN_BALANCE_CANT_UPDATE_CASHIER, "已存在收银台账单，无法更新");
        }

        BalanceOutlinePo balanceOutlinePo = balanceOutlineDao.findOne(outlineId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(balanceOutlinePo.getGenerateDate());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        this.updateFromFtp(outlineId, DateHelper.formatDate(calendar.getTime(), DateHelper.SIMPLE_DATE_YMD));
    }

    @Override
    public void autoRefresh() throws BizServiceException {
        BalanceOutlinePo balanceOutlinePo = balanceOutlineDao.findByGenerateDate(new Date());
        this.refresh(balanceOutlinePo.getId());
    }


    @Override
    @JmsListener(destination = DestinationDefine.PAY_BILL_NOTIFY)
    public void updateFromMQ(String json){
        try{
            //billDate
            Map<String, Object> params = JsonUtil.toObject(Map.class, json);
            Long billDate = (Long) params.get("billDate");

            Date date = new Date(billDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            BalanceOutlinePo balanceOutlinePo = balanceOutlineDao.findByGenerateDate(calendar.getTime());

            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("收银台对账单自动生成,{}", balanceOutlinePo);
            }

            if(balanceOutlinePo == null){
                throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "小贷对账概要不存在");
            }

            this.refresh(balanceOutlinePo.getId());
        }catch(Exception e){
            LOGGER.warn("balance updateFromMQ occur exception", e);
        }
    }

    /**
     * 自动补全路径后面的斜杠
     * @param path
     * @return
     */
    private String completeSuffixSprit(String path) {
        if (!path.endsWith("/")) {
            path = path + "/";
        }
        return path;
    }

    /**
     * 通过ftp更新对账单
     * @param date yyyyMMdd
     */
    private  void updateFromFtp(Long outlineId, String date) throws BizServiceException{
        if (StringUtils.isEmpty(date)) {
            date = DateHelper.formatDate(new Date(), DateHelper.SIMPLE_DATE_YMD);
        }

        String host = AppConfigUtil.getConfig("ftp.server.ip");
        String port = AppConfigUtil.getConfig("ftp.server.port");
        String username = AppConfigUtil.getConfig("ftp.cashier.username");
        String password = AppConfigUtil.getConfig("ftp.cashier.password");
        String path = AppConfigUtil.getConfig("ftp.cashier.path");
        String partnerId = AppConfigUtil.getConfig("cashier.pay.partnerId");
        StringBuilder sb = new StringBuilder();
        FTPClient ftpClient = FtpUtil.createFtpClient(host, Integer.parseInt(port), username, password);
        File file = FtpUtil.download(ftpClient, this.completeSuffixSprit(path), sb.append(partnerId).append("_").append(date).append(".txt").toString());
        int counter = 0;
        boolean skip = true;
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String str;
            while ((str = br.readLine()) != null) {
                if(skip){
                    skip = false;
                    continue;
                }
                String[] args = str.split("\\|");

                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("balance cashier read line,{}", str);
                }

                BalanceCashierPo balanceCashierPo = new BalanceCashierPo();
                balanceCashierPo.setBizId(args[1]);
                balanceCashierPo.setPayAmt(new BigDecimal(args[3]));
                balanceCashierPo.setPayDate(DateHelper.getDate(args[5], ApplicationConstant.TIMESTAMP_FORMAT));
                balanceCashierPo.setPayStatus("1".equals(args[6]) ? "成功":"失败");
                balanceCashierPo.setChannelName(args[7]);
                balanceCashierPo.setBalanceStatus(EBalanceStatus.NOT);
                balanceCashierPo.setOutlineId(outlineId);

                balanceCashierDao.save(balanceCashierPo);

                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("balance cashier parse object,{}", balanceCashierPo);
                }

                counter++;
            }

            br.close();
            reader.close();
        } catch (Exception e) {
            LOGGER.error("收银台对账文件解析发生异常", e);
        }


        if(counter == 0){
            throw new BizServiceException(EErrorCode.LOAN_BALANCE_DATA_NOT_ENOUGH, "对账单未生成");
        }
    }
}
