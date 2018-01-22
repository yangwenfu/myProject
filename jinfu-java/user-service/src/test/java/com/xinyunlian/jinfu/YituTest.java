package com.xinyunlian.jinfu;

import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.yitu.YituService;
import com.xinyunlian.jinfu.common.yitu.dto.req.VerifyReqDto;
import com.xinyunlian.jinfu.common.yitu.dto.resp.CertificationDto;
import com.xinyunlian.jinfu.common.yitu.helper.FileHelper;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class YituTest {

    @Autowired
    private YituService yituService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private FileStoreService fileStoreService;

    @Test
    public void yituTest() throws Exception {
       /* String base64 =  FileHelper.getBase64FromInputStream(new FileInputStream("d:/id5_fanpai.jpg"));

        yituService.ocr(base64);*/

        String cardFrontFilePath = "/USER_INFO_IMG/201702/39245301-4204-458d-8d7b-9102a4722d3d9148908676581193534.jpg";
        ///USER_INFO_IMG/201702/c27b24b1-68d0-42df-8456-eddfba3a536d6707506505666489472.jpg
        try {
            String backFilePath = cardFrontFilePath;
            File file = fileStoreService.download(backFilePath.substring(0,backFilePath.lastIndexOf("/")),
                    backFilePath.substring(backFilePath.lastIndexOf("/"),backFilePath.length()));
            String cardBackBase64 = FileHelper.getBase64FromInputStream(new FileInputStream(file));
            yituService.ocr(cardBackBase64,2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void yituTest2() throws Exception {

        String packageContent = FileHelper.readFile("d:/yitu.txt");
        String base64 = FileHelper.readFile("d:/base64.txt");

        VerifyReqDto verifyReqDto = new VerifyReqDto();
        verifyReqDto.setImagePacket(packageContent);
        verifyReqDto.setName("欧阳红芳");
        verifyReqDto.setIdCard("610322197608093322");
        CertificationDto certificationDto = yituService.verify(verifyReqDto);
        System.exit(1);
    }

    @Test
    public void yituTest3() throws Exception {
        String cardBackBase64 = FileHelper.getBase64FromInputStream(
                new FileInputStream("C:\\Users\\King\\AppData\\Local\\Temp\\39245301-4204-458d-8d7b-9102a4722d3d9148908676581193534.jpg"));
        CertificationDto certificationDto = yituService.certification("欧阳红芳", "610322197608093322",cardBackBase64);
    }

    @Test
    public void test4(){
        PictureDto pictureDto = pictureService.get("UC0000000402", EPictureType.LINE);
        System.exit(1);
    }

    @Test
    public void test6(){
        String a = "你好";
        String b = "adsad";
        System.exit(1);
    }


}
