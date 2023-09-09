package com.xhblogs.tushusyetm.parm;

import lombok.Data;

@Data
public class BkUserParm {
    private Long currentPage;
    private Long pageSize;
    private String username;
    private String id;
    private String phone;
    private String studentId;
}
