package com.ylfin.wallet.portal.controller;

import java.util.List;

import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.IDCardDto;
import com.xinyunlian.jinfu.user.service.UserService;
import com.ylfin.core.auth.AuthenticationAdapter;
import com.ylfin.core.exception.ServiceException;
import com.ylfin.wallet.portal.CurrentUser;
import com.ylfin.wallet.portal.controller.vo.CertificationInfo;
import io.swagger.annotations.Api;
import com.ylfin.wallet.portal.service.ImageInfo;
import com.ylfin.wallet.portal.service.ImageService;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("实名认证接口")
@RestController
@RequestMapping("/api/certification")
public class CertificationController {

	@Autowired
    private AuthenticationAdapter<CurrentUser, String> authenticationAdapter;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PictureService pictureService;

    @ApiOperation("获取实名认证信息")
    @GetMapping
    public CertificationInfo getCertificationInfo() {
        String currentUserId = authenticationAdapter.getCurrentUserId();
        UserInfoDto userInfoDto = userService.findUserByUserId(currentUserId);
        if (userInfoDto == null){
            throw new ServiceException("当前用户不存在");
        }
        if (!userInfoDto.getIdentityAuth() || StringUtils.isBlank(userInfoDto.getUserName())
                || StringUtils.isBlank(userInfoDto.getIdCardNo())){
            throw new ServiceException("当前用户未实名认证");
        }
        CertificationInfo certificationInfo = new CertificationInfo();
        certificationInfo.setRealName(userInfoDto.getUserName());
        certificationInfo.setIdCardNo(userInfoDto.getIdCardNo());
        List<PictureDto> pictureDtoCardFront = pictureService.list(currentUserId, EPictureType.CARD_FRONT);
        if (pictureDtoCardFront != null){
            certificationInfo.setIdCardFrontPic(pictureDtoCardFront.get(0).getPictureFullPath());
        }
        List<PictureDto> pictureDtoCardBack = pictureService.list(currentUserId, EPictureType.CARD_BACK);
        if (!CollectionUtils.isEmpty(pictureDtoCardBack)){
            certificationInfo.setIdCardBackPic(pictureDtoCardBack.get(0).getPictureFullPath());
        }
        return certificationInfo;
    }

    @ApiOperation("提交实名认证信息")
    @PostMapping
    public void certification(@RequestBody CertificationInfo certificationInfo) {
        if (StringUtils.isEmpty(certificationInfo.getRealName()) || StringUtils.isEmpty(certificationInfo.getIdCardNo())) {
            throw new ServiceException("姓名和身份证号必填");
        }
        if (StringUtils.isEmpty(certificationInfo.getIdCardFrontPic()) || StringUtils.isEmpty(certificationInfo.getIdCardBackPic())) {
            throw new ServiceException("身份证照片必传");
        }
        String currentUserId = authenticationAdapter.getCurrentUserId();
        UserInfoDto userInfoDto = userService.findUserByUserId(currentUserId);
        if (userInfoDto == null) {
            throw new ServiceException("用户不存在");
        }
        if (userInfoDto.getIdentityAuth()) {
            throw new ServiceException("已经实名认证!");
        }
        IDCardDto idCardDto = new IDCardDto();
        idCardDto.setUserId(currentUserId);
        idCardDto.setIdCardNo(certificationInfo.getIdCardNo());
        idCardDto.setUserName(certificationInfo.getRealName());
        ImageInfo cardFront = imageService.saveIdCardFrontImg(certificationInfo.getIdCardFrontPic(), currentUserId);
        idCardDto.setCardFrontFilePath(cardFront.getImgPath());
        ImageInfo cardBack = imageService.saveIdCardBackImg(certificationInfo.getIdCardBackPic(), currentUserId);
        idCardDto.setCardBackFilePath(cardBack.getImgPath());
        if (StringUtils.isNotBlank(certificationInfo.getIdCardHeldPic())){
            imageService.saveIdCardHeldImg(certificationInfo.getIdCardHeldPic(),currentUserId);
        }
        userService.certifyWithCard(idCardDto);
    }


}
