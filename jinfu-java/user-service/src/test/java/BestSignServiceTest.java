import com.xinyunlian.jinfu.common.dto.bscontract.RegUserWrap;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.RandomUtil;
import com.xinyunlian.jinfu.common.yitu.helper.FileHelper;
import com.xinyunlian.jinfu.contract.dto.BsCertApplReq;
import com.xinyunlian.jinfu.contract.dto.BsRegUserReq;
import com.xinyunlian.jinfu.contract.dto.BsUploadUserImageReq;
import com.xinyunlian.jinfu.contract.service.BestSignService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;

/**
 * Created by dongfangchao on 2017/3/3/0003.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:standard-code-dao-test.xml"})
public class BestSignServiceTest {

    @Autowired
    private BestSignService bestSignService;

    private String signId;

    @Before
    public void before(){
        signId = "1486788318186HHO92";
    }

    @Test
    public void contractInfo(){
        try {
            String json = bestSignService.contractInfo(signId);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void regUCompany(){
        //上上签注册用户
        BsRegUserReq bsRegUserReq = new BsRegUserReq();
        bsRegUserReq.setEmail("91330203MA2934G32Q");
        bsRegUserReq.setMobile("13738855770");
        bsRegUserReq.setName("宁波华青商务信息咨询有限公司");
        String regUserJson;
       try {
            regUserJson = bestSignService.regUser(bsRegUserReq);
            System.out.println(regUserJson);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.LOAN_BEST_SIGN_ERROR, "best sign user register error");
        }

        RegUserWrap regUserWrap = JsonUtil.toObject(RegUserWrap.class, regUserJson);


        String province, city, area, address;
        province = "浙江省";
        city = "宁波市";
        area = "江北区";
        address = "洪塘街道中策世纪博园";

        //上上签申请个人CA证书
        BsCertApplReq bsCertApplReq = new BsCertApplReq();
        bsCertApplReq.setName(bsRegUserReq.getName());
        bsCertApplReq.setPassword(RandomUtil.getNumberStr(10));
        bsCertApplReq.setMobile(bsRegUserReq.getMobile());
        String addressBuilder = province + city + area + address;
        bsCertApplReq.setAddress(addressBuilder);
        bsCertApplReq.setProvince(province);
        bsCertApplReq.setCity(city);
        bsCertApplReq.setLinkMan("刘建华");
        bsCertApplReq.setLinkIdCode("360102197312215812");
        bsCertApplReq.setOrgCode("91330203MA2934G32Q");
        bsCertApplReq.setEmial(bsRegUserReq.getEmail());
        String cerApplJson;
        try {
           cerApplJson = bestSignService.certificateCompanyApply(bsCertApplReq);
           System.out.println(cerApplJson);
        } catch (Exception e) {
          e.printStackTrace();
        }

        try {
            String image = FileHelper.getBase64FromInputStream(
                    new FileInputStream("D:\\项目\\小贷\\华青.png"));

            BsUploadUserImageReq bsUploadUserImageReq = new BsUploadUserImageReq();
            bsUploadUserImageReq.setImgName("华青公司公章");
            bsUploadUserImageReq.setImgType("png");
            bsUploadUserImageReq.setMobile(bsRegUserReq.getMobile());
            bsUploadUserImageReq.setName(bsRegUserReq.getName());
            bsUploadUserImageReq.setImage(image);
            bsUploadUserImageReq.setSealName("华青公司公章");
            bsUploadUserImageReq.setUseracount(bsRegUserReq.getEmail());

            bestSignService.uploaduserimage(bsUploadUserImageReq);
        } catch (Exception e) {
          e.printStackTrace();
        }
    }

}
