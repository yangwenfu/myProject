package com.xinyunlian.jinfu.spider.service;
import com.alibaba.fastjson.JSONArray;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.io.ByteStreams;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.spider.dao.EnterpriseInfoDao;
import com.xinyunlian.jinfu.spider.dto.resp.EnterpriseInfoDto;
import com.xinyunlian.jinfu.spider.dto.resp.EnterpriseInfoParseDto;
import com.xinyunlian.jinfu.spider.entity.EnterpriseInfoPo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.InputStream;
import java.net.URL;
import java.util.*;


/**
 * Created by lenovo on on 2017/7/25.
 */
@Service
public class EnterpriseInfoServiceImpl implements EnterpriseInfoService {

    @Autowired
    private EnterpriseInfoDao enterpriseInfoDao;

    @Value("${crawler_url}")
    private String CRAWLER_URL ;

    private static final Logger LOGGER = LoggerFactory.getLogger(EnterpriseInfoServiceImpl.class);

    private static void logStep(EnterpriseInfoDto ei){
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug(JsonUtil.toJson(ei));
        }
    }

    @Override
    @Transactional
    public void crawlerEnterpriseInfo(String enpName,String userId){
            String url = CRAWLER_URL+ UriEncoder.encode(enpName);
            LOGGER.info("first url is :{}",url);
            // 根据url请求数据
        try {
            // 第一层请求数据，解析拿到第二层linkHref；
            byte[] data = sendGetRequest(url);
            Document doc = Jsoup.parse(new String(data,"utf-8"));
            Elements s = doc.getElementsByAttributeValueContaining("href","https://www.tianyancha.com/company/");
            if(null == s || s.size() == 0) {
                LOGGER.info("search result is null!!!");
                return ;
            }
            Element e = null;
            for(Element em:s){
                if(em.text().contains(enpName)){
                    e = em;
                    break;
                }
            }
            if (null == e){
                e = s.get(0);
            }
            Elements links = e.getElementsByTag("a");
            String linkHref = links.get(0).attr("href");
            LOGGER.info("second url is :{}",linkHref);

            // 第二层请求数据，解析拿到公司基本信息；
            byte[] data1 = sendGetRequest(linkHref);
            Document  doc1 = Jsoup.parse(new String(data1,"utf-8"));
            EnterpriseInfoDto info = new EnterpriseInfoDto();
            // 构建enterpriseInfo
            info.setUserId(userId);
            this.buildEnterpriseInfo(doc1,info);
            LOGGER.info("build enterpriseinfo success !!!");
            // enterpriseInfo持久化到数据库
            this.saveEntripseInfo(info);
            LOGGER.info("insert enterpriseinfo success !!!");
        } catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }

    }

    /**ｕｒｌ
     * 构建EnterpriseInfo
     * @param doc
     * @param info
     */
    private void buildEnterpriseInfo(Document  doc, EnterpriseInfoDto info){
        // 解析table里面的字段值
        Elements css8 = doc.select("div.c8");
        for(Element el:css8){
            String name = el.text();
            String []  strs = name.trim().split("\\s+");
            switch (strs[0]){
                case "工商注册号：":
                    info.setBusniessRegNum(strs[1]);continue;
                case "组织机构代码：":
                    info.setOrgCode(strs[1]);continue;
                case "统一信用代码：":
                    info.setCreditCode(strs[1]);continue;
                case "企业类型：":
                    info.setEnterpriseType(strs[1]);continue;
                case "行业：":
                    info.setTrade(strs[1]);continue;
                case "营业期限：":
                    info.setBusniessTerm(strs[1]);continue;
                case "核准日期：":
                    info.setApprovalDate(strs[1]);continue;
                case "登记机关：":
                    info.setRegisterAuth(strs[1]);continue;
                case "注册地址：":
                    info.setRegisterAdr(strs[1]);continue;
                case "经营范围：":
                    info.setBuniessScope(strs[1]);continue;
                default:
                    continue;
            }
        }

        // 法人
        Element legal = doc.select("div.new-c3").select(".f18").first();
        if(null != legal){
            info.setLegalRep(legal.text());
        }

        // 注册成本及注册时间
        Elements rgsinfo = doc.select("div.new-border-bottom");
        for(Element rg: rgsinfo){
            String name = rg.child(0).text();
            String value = rg.child(1).text();
            if(name.contains("注册资本")){
                info.setRegisterCapital(value);
            }
            if(name.contains("注册时间")){
                info.setRegisterDate(value);
            }
        }

        // 企业状态
        Element status = doc.select("tr.f14").select("div.pt10").last();
        if(null != status){
            String name = status.child(0).text();
            String value = status.child(1).text();
            if(name.contains("企业状态")){
                info.setManageStatus(value);
            }
        }

        // 企业发展、司法风险、经营风险、经营状况、知识产权
        Elements  cpyEnables = doc.select("div.company-nav-item-enable");
        if(null == cpyEnables || cpyEnables.size() == 0) return ;
        this.buildEnterpriseParseInfo(cpyEnables, info);


    }

    // 中文title转换英文
    private void buildEnterpriseParseInfo(Elements es,EnterpriseInfoDto info){
        for(Element el:es) {
            String name = el.text();
            String []  strs = name.trim().split("\\s+");
            switch (strs[0]) {
                case "融资历史":
                    info.setCompanyRongzi(strs[1]);
                    continue;
                case "核心团队":
                    info.setCompanyTeammember(strs[1]);
                    continue;
                case "企业业务":
                    info.setCompanyProduct(strs[1]);
                    continue;
                case "投资事件":
                    info.setJigouTzanli(strs[1]);
                    continue;
                case "竞品信息":
                    info.setCompanyJingpin(strs[1]);
                    continue;
                case "法律诉讼":
                    info.setLawsuitCount(strs[1]);
                    continue;
                case "法院公告":
                    info.setCourtCount(strs[1]);
                    continue;
                case "失信人":
                    info.setDishonest(strs[1]);
                    continue;
                case "被执行人":
                    info.setZhixing(strs[1]);
                    continue;
                case "经营异常":
                    info.setAbnormalCount(strs[1]);
                    continue;
                case "行政处罚":
                    info.setPunishment(strs[1]);
                    continue;
                case "严重违法":
                    info.setIllegalCount(strs[1]);
                    continue;
                case "股权出质":
                    info.setEquityCount(strs[1]);
                    continue;
                case "动产抵押":
                    info.setMortgageCount(strs[1]);
                    continue;
                case "欠税公告":
                    info.setOwnTaxCount(strs[1]);
                    continue;
                case "招投标":
                    info.setBidCount(strs[1]);
                    continue;
                case "债券信息":
                    info.setBondCount(strs[1]);
                    continue;
                case "购地信息":
                    info.setGoudiCount(strs[1]);
                    continue;
                case "招聘":
                    info.setRecruitCount(strs[1]);
                    continue;
                case "税务评级":
                    info.setTaxCreditCount(strs[1]);
                    continue;
                case "抽查检查":
                    info.setCheckCount(strs[1]);
                    continue;
                case "产品信息":
                    info.setProductinfo(strs[1]);
                    continue;
                case "资质证书":
                    info.setQualification(strs[1]);
                    continue;
                case "商标信息":
                    info.setTmCount(strs[1]);
                    continue;
                case "专利":
                    info.setPatentCount(strs[1]);
                    continue;
                case "著作权":
                    info.setCpoyCount(strs[1]);
                    continue;
                default:
                    continue;
            }
        }
    }

    /**
     * Get请求
     * @param url
     * @return
     * @throws Exception
     */
    private byte[] sendGetRequest(String url) throws Exception{
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        WebRequest webRequest = new WebRequest(new URL(url));
        webRequest.setHttpMethod(HttpMethod.GET);
        return sendRequest(webClient,webRequest);
    }

    //底层请求
    private byte[] sendRequest(WebClient webClient,WebRequest webRequest) throws Exception{
        byte[] responseContent = null;
        Page page = webClient.getPage(webRequest);
        WebResponse webResponse = page.getWebResponse();
        int status = webResponse.getStatusCode();
        System.out.println("Charset : " + webResponse.getContentCharset());
        System.out.println("ContentType : " + webResponse.getContentType());
        // 读取数据内容
        if (status==200) {
            if (page.isHtmlPage()) {
                // 等待JS执行完成，包括远程JS文件请求，Dom处理
                webClient.waitForBackgroundJavaScript(10000);
                // 使用JS还原网页
                responseContent = ((HtmlPage) page).asXml().getBytes();
            } else {
                InputStream bodyStream = webResponse.getContentAsStream();
                responseContent = ByteStreams.toByteArray(bodyStream);
                bodyStream.close();
            }
        }
        // 关闭响应流
        webResponse.cleanUp();
        return responseContent;
    }

    protected void saveEntripseInfo(EnterpriseInfoDto enpInfo) {
        Assert.notNull(enpInfo);
        logStep(enpInfo);
        EnterpriseInfoPo po = enterpriseInfoDao.findByUserId(enpInfo.getUserId());
        if(Objects.isNull(po)){
            po = new EnterpriseInfoPo();
        }
        ConverterService.convert(enpInfo,po);
        enterpriseInfoDao.save(po);
    }
}
