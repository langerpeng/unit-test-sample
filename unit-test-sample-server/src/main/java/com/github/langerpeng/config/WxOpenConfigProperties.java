package com.github.langerpeng.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author langer_peng
 */
@Data
@ConfigurationProperties("management.wx-open")
public class WxOpenConfigProperties {

    private String appId;
    private String appSecret;
    private String aseKey;
    private String token;
    private String authRedirectUrl;

}
