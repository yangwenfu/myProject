package com.xinyunlian.jinfu.user.dto;

/**
 * Class Name: SignInDto
 *
 * @author SC
 */
public class SignInDto extends OperationLogDto<RiskControlDto> {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String password;

    private String verifyCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
