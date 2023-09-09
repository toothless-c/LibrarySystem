package com.xhblogs.tushusyetm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bk_user_role")
public class BkUserRole {
    @TableId(type = IdType.AUTO)
    private Long readerRoleId;
    private Long readerId;
    private Long roleId;
}
