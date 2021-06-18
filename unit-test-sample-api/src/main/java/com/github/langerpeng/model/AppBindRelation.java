package com.github.langerpeng.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author langer_peng
 */
@ApiModel("创建应用之间的绑定关系")
@Data
public class AppBindRelation {

    @ApiModelProperty(value = "关联应用id", required = true)
    @NotNull(message = "关联应用id不能为空")
    private Integer fromWxAppId;

    @ApiModelProperty(value = "被关联应用id", required = true)
    @NotNull(message = "被关联应用id不能为空")
    private Integer toWxAppId;

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "创建用户Id不能为空")
    private Long userId;

}

