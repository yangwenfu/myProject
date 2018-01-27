package com.xinyunlian.jinfu;

import com.xinyunlian.jinfu.audit.dto.LoanAttDto;
import com.xinyunlian.jinfu.audit.service.LoanAuditService;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2017/2/20/0020.
 */
public class LoanAttServiceTest extends AbstractServiceTest {

    @Autowired
    private LoanAuditService loanAuditService;

    @Test
    public void getAttList(){
        List<LoanAttDto> list = loanAuditService.getAttList("P1001212");
        System.out.println(JsonUtil.toJson(list));
    }

    @Test
    public void saveAttachment(){
        LoanAttDto dto = new LoanAttDto();
        dto.setApplyId("P1001313");
        dto.setUploadDate(new Date());
        dto.setUploader("貂蝉");
        dto.setFileName("tiger.png");
        dto.setFilePath("/LOAN_ATT/201702/tiger.png");
        loanAuditService.saveAttachment(dto);
    }

    @Test
    public void delete(){
        loanAuditService.deleteAttachment(4l);
    }

}
