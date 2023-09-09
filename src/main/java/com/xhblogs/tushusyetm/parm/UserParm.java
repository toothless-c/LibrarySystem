package com.xhblogs.tushusyetm.parm;

import lombok.Data;

@Data
public class UserParm {
    private Long currentPage;
    private Long pageSize;
    private String phone;
    private String nickName;
}
