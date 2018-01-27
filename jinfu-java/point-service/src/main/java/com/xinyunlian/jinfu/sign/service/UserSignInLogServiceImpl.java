package com.xinyunlian.jinfu.sign.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.sign.dao.UserSignInLogDao;
import com.xinyunlian.jinfu.sign.entity.UserSignInLogPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
@Service
public class UserSignInLogServiceImpl implements UserSignInLogService {

    @Autowired
    private UserSignInLogDao userSignInLogDao;

    @Override
    public Boolean checkSignIn(String userId, Date signInDate) throws BizServiceException {

        if (!StringUtils.isEmpty(userId) && signInDate != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            UserSignInLogPo po = userSignInLogDao.findByUserIdAndSignInDate(userId, sdf.format(signInDate));
            if (po != null){
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void signIn(String userId, Date signInDate) throws BizServiceException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        UserSignInLogPo ytdPo = userSignInLogDao.findByUserIdAndSignInDate(userId, sdf.format(DateHelper.add(signInDate, Calendar.DATE, -1)));
        Long conDays = 0l;
        if (ytdPo != null && ytdPo.getConDays() != null){
            conDays = ytdPo.getConDays();
        }
        UserSignInLogPo po = new UserSignInLogPo();
        po.setUserId(userId);
        po.setSignInDate(signInDate);
        po.setConDays(conDays + 1);
        userSignInLogDao.save(po);
    }
}
