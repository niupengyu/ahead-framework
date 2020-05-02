package com.github.niupengyu.web.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component("allowOrigin")
@ConfigurationProperties(prefix="news.allow-origin")
public class AllowOrigin {

    //private String allowOrigin="*";

    private Set<String> allowOrigins;

    private String allowMethods="*";

    private String maxAge="3600";

    private String allowHeaders="*";

    private String allowCredentials="false";


   /* public String getAllowOrigin() {
        return allowOrigin;
    }

    public void setAllowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }*/

    public String getAllowMethods() {
        return allowMethods;
    }

    public void setAllowMethods(String allowMethods) {
        this.allowMethods = allowMethods;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public String getAllowHeaders() {
        return allowHeaders;
    }

    public void setAllowHeaders(String allowHeaders) {
        this.allowHeaders = allowHeaders;
    }

    public String getAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(String allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public Set<String> getAllowOrigins() {
        return allowOrigins;
    }

    public void setAllowOrigins(Set<String> allowOrigins) {
        this.allowOrigins = allowOrigins;
    }

    @Override
    public String toString() {
        return "AllowOrigin{" +
                "allowOrigin='" + allowOrigins + '\'' +
                ", allowMethods='" + allowMethods + '\'' +
                ", maxAge='" + maxAge + '\'' +
                ", allowHeaders='" + allowHeaders + '\'' +
                ", allowCredentials='" + allowCredentials + '\'' +
                '}';
    }
}
