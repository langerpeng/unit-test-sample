package com.github.langerpeng.service.impl;

import com.github.langerpeng.client.WxManagementClient;
import com.github.langerpeng.config.WxOpenConfigProperties;
import com.github.langerpeng.constans.WxManagementExceptionType;
import com.github.langerpeng.entity.AppBindRelationEntity;
import com.github.langerpeng.repo.WxAppBindRelationRepository;
import com.github.langerpeng.exception.WxManagementException;
import com.github.langerpeng.model.*;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author langer_peng
 */
@RestController
@Slf4j
public class WxManagementServiceImpl implements WxManagementClient {

    @Resource
    private WxOpenConfigProperties wxOpenConfigProperties;

    @Resource
    private WxAppBindRelationRepository wxAppBindAppRepository;

    private final WxOpenService wxOpenService;

    public WxManagementServiceImpl(WxOpenService wxOpenService) {
        this.wxOpenService = wxOpenService;
    }

    @Override
    public String getAuthAddress(Integer appletType) {

        var authAddress = "";
        try {
            authAddress = wxOpenService.getWxOpenComponentService()
                    .getPreAuthUrl(wxOpenConfigProperties.getAuthRedirectUrl().contains("?") ?
                            wxOpenConfigProperties.getAuthRedirectUrl().concat("&applet_type=").concat(appletType.toString()) :
                            wxOpenConfigProperties.getAuthRedirectUrl().concat("?applet_type=").concat(appletType.toString()));
        } catch (WxErrorException e) {
            log.error(e.getMessage());
        }
        return authAddress;
    }

    @Override
    public Boolean createAppBindRelations(AppBindRelation createAppBindAppDto) {

        wxAppBindAppRepository.findOne(Example.of(new AppBindRelationEntity()
                .setWxAppFromId(createAppBindAppDto.getFromWxAppId())
                .setWxAppToId(createAppBindAppDto.getToWxAppId())))
                .ifPresent(bind -> {
                    throw new WxManagementException(WxManagementExceptionType.E10008);
                });

        wxAppBindAppRepository.save(new AppBindRelationEntity()
                .setCreateTime(LocalDateTime.now())
                .setCreateUserId(createAppBindAppDto.getUserId())
                .setWxAppFromId(createAppBindAppDto.getFromWxAppId())
                .setWxAppToId(createAppBindAppDto.getToWxAppId()));

        return true;
    }

}
