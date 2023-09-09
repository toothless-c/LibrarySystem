package com.xhblogs.tushusyetm.parm;

import lombok.Data;

@Data
public class LoginVo {
    private Long userId;
    private String token;
    private String userType;
}
