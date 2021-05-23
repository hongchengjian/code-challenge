package com.hrs.security.domain;

public class JwtTokenDto {
    /**
     * token值
     */
    private String access_token;
    /**
     * token类型
     */
    private String token_type = "bearer";
    /**
     * 刷新token值
     */
    private String refresh_token;
    /**
     * 过期时间，具体到某个时间过期
     */
    private Long expires_in;
    /**
     * 作用范围,暂不使用
     */
    private String scope = "scope";
    /**
     * token的唯一Id
     */
    private String jti;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}
