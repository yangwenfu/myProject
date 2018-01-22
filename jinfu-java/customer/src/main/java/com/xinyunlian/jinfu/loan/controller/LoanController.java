package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.app.dto.AppVersionControlDto;
import com.xinyunlian.jinfu.app.dto.DataVersionControlDto;
import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EDataType;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;
import com.xinyunlian.jinfu.app.service.AppVersionControlService;
import com.xinyunlian.jinfu.app.service.DataVersionControlService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.feedback.dto.FeedbackDto;
import com.xinyunlian.jinfu.feedback.service.LoanFeedbackService;
import com.xinyunlian.jinfu.loan.dto.LoanApplUserDto;
import com.xinyunlian.jinfu.loan.dto.feedback.FeedbackAddReqDto;
import com.xinyunlian.jinfu.loan.dto.loan.LoanAgreeDto;
import com.xinyunlian.jinfu.loan.dto.loan.LogContentDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanApplySearchDto;
import com.xinyunlian.jinfu.loan.service.LoanApplQueryService;
import com.xinyunlian.jinfu.loan.service.LoanApplUserService;
import com.xinyunlian.jinfu.loan.service.PrivateLoanService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author willwang
 */

@RestController
@RequestMapping(value = "loan")
public class LoanController {

    @Autowired
    private AppVersionControlService appVersionControlService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoanFeedbackService loanFeedbackService;

    @Autowired
    private PrivateLoanService privateLoanService;

    @Autowired
    private DataVersionControlService dataVersionControlService;

    @Autowired
    private LoanApplQueryService loanApplQueryService;

    @Autowired
    private LoanApplUserService loanApplUserService;

    private static final String RET_CODE = "100000";

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);

    @RequestMapping(value = "agree", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto agree(@RequestBody LoanAgreeDto dto) {
        //同意使用贷款
        privateLoanService.agree(dto.getApplId(), dto.getCardId());

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 放款异步回调
     * @param request
     * @return
     */
    @RequestMapping(value="callback")
    @ResponseBody
    public String callback(HttpServletRequest request){
        return privateLoanService.callback(request, false);
    }

    /**
     * 初始化接口
     *
     * @return
     */
    @GetMapping(value = "/init")
    public ResultDto getAppVersion(@RequestParam EOperatingSystem m) {
        AppVersionControlDto appVersionControlDto = appVersionControlService.getLatestAppInfo(EAppSource.YLZG, m);
        return ResultDtoFactory.toAckData(appVersionControlDto);
    }

    /**
     * 获取数据版本号
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/areaVersion", method = RequestMethod.GET)
    public ResultDto<DataVersionControlDto> getAreaVersion(@RequestParam EOperatingSystem m) {
        DataVersionControlDto dataVersionControlDto = dataVersionControlService.getLatestDataInfo(EAppSource.YLZG, m, EDataType.AREA);
        return ResultDtoFactory.toAck("获取成功", dataVersionControlDto);
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public Object feedback(@RequestBody @Valid FeedbackAddReqDto data, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        String userId = SecurityContext.getCurrentUserId();
        UserInfoDto user = userService.findUserByUserId(userId);

        FeedbackDto feedbackAddDto = ConverterService.convert(data, FeedbackDto.class);
        feedbackAddDto.setUserId(userId);
        feedbackAddDto.setUserName(user.getUserName());
        feedbackAddDto.setMobile(user.getMobile());

        try {
            loanFeedbackService.add(feedbackAddDto);

            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
        } catch (Exception e) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("loan.feedback.add.error"));
        }

    }

    /**
     * 更新小贷签约状态
     * @param code
     * @param signID
     * @param applId
     * @return
     */
    @RequestMapping(value = "signedContract", method = RequestMethod.GET)
    public ResultDto<Object> updateLoanApplSigned(String code, String signID, String usertype, String applId){
        if (!RET_CODE.equals(code)){
            return ResultDtoFactory.toNack("合同签名失败");
        }

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("updateLoanApplSigned, signID:{}, userType:{}, applId:{}", signID, usertype, applId);
        }

        privateLoanService.updateSigned(applId);
        return ResultDtoFactory.toAck("成功");
    }

    /**
     * 刷新数据拷贝
     * @param code
     * @param applyId
     * @return
     */
    @RequestMapping(value = "refreshApplUser", method = RequestMethod.GET)
    public ResultDto<Object> refreshApplUser(@RequestParam String code, @RequestParam String applyId){
        if (!RET_CODE.equals(code)){
            return ResultDtoFactory.toNack(MessageUtil.getMessage("common.operate.fail"));
        }
        privateLoanService.saveLoanApplUser(applyId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }


    /**
     * 刷新数据快照
     * @param code
     * @return
     */
    @RequestMapping(value = "refreshApplUsers", method = RequestMethod.GET)
    public ResultDto<Object> refreshApplUser(@RequestParam String code){
        if (!RET_CODE.equals(code)){
            return ResultDtoFactory.toNack(MessageUtil.getMessage("common.operate.fail"));
        }

        LoanApplySearchDto search = new LoanApplySearchDto();
        int currentPage = 1;
        search.setPageSize(100);
        LoanApplySearchDto rs;

        do{
            search.setCurrentPage(currentPage++);
            rs = loanApplQueryService.listLoanAppl(search);
            if(!rs.getList().isEmpty()){
                rs.getList().forEach(loanApplyListEachDto -> {
                    LoanApplUserDto loanApplUserDto = loanApplUserService.findByApplId(loanApplyListEachDto.getApplId());
                    if(loanApplUserDto == null){
                        try{
                            privateLoanService.saveLoanApplUser(loanApplyListEachDto.getApplId());
                        }catch(Exception e){
                            LOGGER.warn("appl_id:" + loanApplyListEachDto.getApplId() +" refresh appl user occur exception:", e);
                        }
                    }
                });
            }
        }while(!rs.getList().isEmpty());

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }


    @RequestMapping(value = "log", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> log(@RequestBody LogContentDto data){
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("android log:{}", data.getContent());
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

}
