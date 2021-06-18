package com.github.langerpeng.service.impl;

import com.github.langerpeng.config.WxOpenConfigProperties;
import com.github.langerpeng.entity.AppBindRelationEntity;
import com.github.langerpeng.exception.WxManagementException;
import com.github.langerpeng.model.AppBindRelation;
import com.github.langerpeng.repo.WxAppBindRelationRepository;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenService;
import mockit.MockUp;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("微信应用管理")
class WxManagementServiceImplTest {

    @Mock
    private WxOpenService wxOpenService;

    private static Logger log;

    @BeforeAll
    public static void setUp() {
        log = mock(Logger.class);
        new MockUp<LoggerFactory>(LoggerFactory.class) {
            @mockit.Mock
            Logger getLogger(Class<?> clazz) {
                return log;
            }
        };
    }

    @Nested
    @DisplayName("获取应用授权地址")
    class AuthAddress {

        @Mock
        private WxOpenComponentService wxOpenComponentService;

        @Mock
        private WxOpenConfigProperties wxOpenConfigProperties;

        private static final String CALLBACK_ADDRESS = "https://webcenter-portal.langerpeng.github.com/auth-page";

        @Test
        @DisplayName("获取应用授权地址正常")
        void testGetAuthAddress() throws Exception {

            var callbackAddressAndParameter = CALLBACK_ADDRESS + "?realm=realm&applet_type=1";
            var authAddress = "https://wx.api.com/auth?callback=" +
                    URLEncoder.encode(callbackAddressAndParameter, StandardCharsets.UTF_8) + "&code=5566";

            Mockito.when(wxOpenService.getWxOpenComponentService())
                    .thenReturn(wxOpenComponentService);
            Mockito.when(wxOpenConfigProperties.getAuthRedirectUrl())
                    .thenReturn(CALLBACK_ADDRESS);
            Mockito.when(wxOpenComponentService
                    .getPreAuthUrl(any()))
                    .thenReturn(authAddress);

            WxManagementServiceImpl wxAppManagementService = new WxManagementServiceImpl(wxOpenService);
            var aClass = wxAppManagementService.getClass();
            var wxOpenPropertiesField = aClass.getDeclaredField("wxOpenConfigProperties");
            FieldSetter.setField(wxAppManagementService, wxOpenPropertiesField, wxOpenConfigProperties);

            var result = wxAppManagementService.getAuthAddress(1);

            assertThat(new URL(result))
                    .describedAs("授权地址不合法")
                    .hasHost("wx.api.com")
                    .hasParameter("code")
                    .hasParameter("callback");
            verify(wxOpenService, times(1)).getWxOpenComponentService();
            verify(wxOpenConfigProperties, times(2)).getAuthRedirectUrl();
            verify(wxOpenComponentService, times(1)).getPreAuthUrl(any());

        }

        @DisplayName("获取应用授权地址微信接口异常抛出")
        @Test
        void testGetAuthAddressWithWxErrorException() throws Exception {

            Mockito.when(wxOpenService.getWxOpenComponentService())
                    .thenReturn(wxOpenComponentService);
            Mockito.when(wxOpenConfigProperties.getAuthRedirectUrl())
                    .thenReturn(CALLBACK_ADDRESS);
            var exception = new WxErrorException("Get prepared auth address error!");
            Mockito.when(wxOpenComponentService
                    .getPreAuthUrl(any()))
                    .thenThrow(exception);

            var wxAppManagementService = new WxManagementServiceImpl(wxOpenService);
            var aClass = wxAppManagementService.getClass();

            var wxOpenPropertiesField = aClass.getDeclaredField("wxOpenConfigProperties");
            FieldSetter.setField(wxAppManagementService, wxOpenPropertiesField, wxOpenConfigProperties);

            String result = wxAppManagementService.getAuthAddress(1);

            assertThat(result).describedAs("微信异常情况下授权地址应为空").isEmpty();
            verify(wxOpenService, times(1)).getWxOpenComponentService();
            verify(wxOpenConfigProperties, times(2)).getAuthRedirectUrl();
            verify(wxOpenComponentService, times(1)).getPreAuthUrl(any());
            verify(log, times(1)).error(exception.getMessage());
        }

    }

    @Nested
    @DisplayName("应用之间绑定关系")
    class AppBindApp {

        @Mock
        private WxAppBindRelationRepository wxAppBindAppRepository;

        @Test
        @DisplayName("创建绑定关系正常")
        void testCreateAppBindRelations() throws NoSuchFieldException {

            Example<AppBindRelationEntity> of = Example.of(new AppBindRelationEntity()
                    .setWxAppFromId(1)
                    .setWxAppToId(2));

            Optional<AppBindRelationEntity> findResult = Optional.empty();

            Mockito.when(wxAppBindAppRepository.findOne(of))
                    .thenReturn(findResult);

            Mockito.when(wxAppBindAppRepository.save(any(AppBindRelationEntity.class)))
                    .thenReturn(new AppBindRelationEntity());

            var createAppBindApp = new AppBindRelation();
            createAppBindApp.setFromWxAppId(1);
            createAppBindApp.setToWxAppId(2);
            createAppBindApp.setUserId(10001L);

            var wxAppManagementService = new WxManagementServiceImpl(wxOpenService);
            var aClass = wxAppManagementService.getClass();
            var wxOpenPropertiesField = aClass.getDeclaredField("wxAppBindAppRepository");
            FieldSetter.setField(wxAppManagementService, wxOpenPropertiesField, wxAppBindAppRepository);
            var result = wxAppManagementService.createAppBindRelations(createAppBindApp);

            assertThat(result).describedAs("添加绑定关系失败").isTrue();
            verify(wxAppBindAppRepository, times(1)).findOne(of);
            verify(wxAppBindAppRepository, times(1)).save(any(AppBindRelationEntity.class));

        }

        @Test
        @DisplayName("创建绑定关系已经存在")
        void testCreateAppBindRelationsWithRepeatedException() throws NoSuchFieldException {

            Optional<AppBindRelationEntity> findResult = Optional.of(new AppBindRelationEntity());

            Mockito.when(wxAppBindAppRepository.findOne(any()))
                    .thenReturn(findResult);

            var wxAppManagementService = new WxManagementServiceImpl(wxOpenService);
            var aClass = wxAppManagementService.getClass();
            var wxOpenPropertiesField = aClass.getDeclaredField("wxAppBindAppRepository");
            FieldSetter.setField(wxAppManagementService, wxOpenPropertiesField, wxAppBindAppRepository);

            var createAppBindApp = new AppBindRelation();
            createAppBindApp.setFromWxAppId(1);
            createAppBindApp.setToWxAppId(2);
            createAppBindApp.setUserId(10001L);

            WxManagementException exception = Assertions.assertThrows(WxManagementException.class,
                    () -> wxAppManagementService.createAppBindRelations(createAppBindApp));
            assertThat(exception).describedAs("重复绑定异常信息错误").hasMessage("绑定关系已经存在");

            verify(wxAppBindAppRepository, times(1)).findOne(any());

        }

    }

}