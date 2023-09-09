package com.xhblogs.tushusyetm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bk_user")
public class BkUser {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String name;
    private String password;
    private String sex;
    private String phone;
    private String studentId;
    private String type;
    private String className;
    private String checkStatus;
}
