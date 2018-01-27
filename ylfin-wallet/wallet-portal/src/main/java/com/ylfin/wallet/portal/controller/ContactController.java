package com.ylfin.wallet.portal.controller;

import com.xinyunlian.jinfu.user.dto.UserLinkmanDto;
import com.xinyunlian.jinfu.user.enums.ERelationship;
import com.xinyunlian.jinfu.user.service.UserLinkmanService;
import com.ylfin.core.auth.AuthenticationAdapter;
import com.ylfin.core.exception.ServiceException;
import com.ylfin.wallet.portal.CurrentUser;
import com.ylfin.wallet.portal.controller.req.ContactInfoReq;
import com.ylfin.wallet.portal.util.EnumConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Api("联系人接口")
@RestController
@RequestMapping("/api/contract")
public class ContactController {

    @Autowired
    private UserLinkmanService userLinkmanService;

    @Autowired
    private AuthenticationAdapter<CurrentUser, String> authenticationAdapter;

    @ApiOperation("添加联系人信息")
    @PostMapping
    public void add(@RequestBody @Valid ContactInfoReq contactInfoReq) {
        UserLinkmanDto userLinkmanDto = new UserLinkmanDto();
        userLinkmanDto.setUserId(authenticationAdapter.getCurrentUserId());
        ERelationship eRelationship = EnumConverter.getERelationship(contactInfoReq.getRelationship());
        if (eRelationship == null){
            throw new ServiceException("无此关系");
        }
        userLinkmanDto.setRelationship(eRelationship);
        userLinkmanDto.setPhone(contactInfoReq.getContactMobile());
        userLinkmanDto.setName(contactInfoReq.getContactName());
        List<UserLinkmanDto> userLinkmanDtoList = new ArrayList<>();
        userLinkmanDtoList.add(userLinkmanDto);
        userLinkmanService.saveUserLinkman(userLinkmanDtoList);
    }


}
