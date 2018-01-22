package com.ylfin.wallet.portal.controller;

import com.ylfin.wallet.portal.controller.vo.BillInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Api("账单接口")
@RestController
@RequestMapping("/api/bill")
public class BillController {

    @ApiOperation("返回当前账户账单列表, 无限滚动接口")
    @GetMapping()
    public Page<BillInfo> list(@RequestParam(required = false, name = "lastBillId") Long lastBillId) {
        // TODO
        return new PageImpl<>(new ArrayList<>());
    }
}
