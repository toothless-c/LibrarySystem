package com.xhblogs.tushusyetm.entity;

import lombok.Data;

/**
 * 返回前端的用户信息
 */
@Data
public class UserInfoVo {
    private String name;
    private String avatar;
    private String introduction;
    private Object[] roles;
}
