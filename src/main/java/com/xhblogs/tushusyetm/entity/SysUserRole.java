package com.xhblogs.tushusyetm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user_role")
public class SysUserRole {
    private Long userRoleId;
    private Long userId;
    private Long roleId;
}
