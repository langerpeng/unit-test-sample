package com.github.langerpeng.client;

import com.github.langerpeng.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author langer_peng
 */
@FeignClient(name = "demo", contextId = "wxManagementClient")
@Api(value = "WxManagementClient", tags = "微信应用管理")
@RequestMapping("/api")
public interface WxManagementClient {

    /**
     * 获取应用授权地址
     *
     * @param appletType appletType
     * @return 授权地址
     */
    @ApiOperation("获取应用授权地址")
    @GetMapping("/v1.1/auth-address")
    String getAuthAddress(@ApiParam(name = "小程序类型") @RequestParam(name = "appletType", required = false) Integer appletType);


    /**
     * 添加应用之间的绑定关系
     *
     * @param appBindRelation 绑定关系
     * @return 结果
     */
    @ApiOperation("添加小程序之间的绑定关系")
    @PostMapping("/v1.1/app-bind-relations")
    Boolean createAppBindRelations(@RequestBody @Validated AppBindRelation appBindRelation);
}
