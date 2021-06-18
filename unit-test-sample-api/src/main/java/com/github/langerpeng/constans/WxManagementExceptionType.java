package com.github.langerpeng.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author langer_peng
 */
@AllArgsConstructor
public enum WxManagementExceptionType {

    /**
     * 异常类型枚举
     */
    E10008(10008, "绑定关系已经存在");

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

}
