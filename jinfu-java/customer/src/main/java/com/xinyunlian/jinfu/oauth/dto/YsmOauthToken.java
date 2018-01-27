package com.xinyunlian.jinfu.oauth.dto;

/**
 * Created by JL on 2016/11/4.
 */
public class YsmOauthToken extends OauthToken {

    private static final long serialVersionUID = 1L;

    private String flag;
    private String msg;
    private TokenData data;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TokenData getData() {
        return data;
    }

    public void setData(TokenData data) {
        this.data = data;
    }

    @Override
    public String getAccessToken() {
        return data.access_token;
    }


    public class TokenData {
        private String access_token;
        private String expires_in;
        private String refresh_token;
        private String scope;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }
    }
}
