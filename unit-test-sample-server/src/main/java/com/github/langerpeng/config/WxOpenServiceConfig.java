package com.github.langerpeng.config;

import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author langer_peng
 */
@Configuration
public class WxOpenServiceConfig {

    @Resource
    private WxOpenConfigProperties wxOpenConfigProperties;

    @Bean
    public WxOpenService wxOpenService() {

        var wxOpenConfigStorage = new WxOpenInMemoryConfigStorage();

        wxOpenConfigStorage.setComponentAesKey(wxOpenConfigProperties.getAseKey());
        wxOpenConfigStorage.setComponentToken(wxOpenConfigProperties.getToken());
        wxOpenConfigStorage.setComponentAppId(wxOpenConfigProperties.getAppId());
        wxOpenConfigStorage.setComponentAppSecret(wxOpenConfigProperties.getAppSecret());
        WxOpenService wxOpenService = new WxOpenServiceImpl();
        wxOpenService.setWxOpenConfigStorage(wxOpenConfigStorage);

        return wxOpenService;
    }

}
