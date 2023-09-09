package com.xhblogs.tushusyetm.parm;

import lombok.Data;

@Data
public class RoleParm {
    private Long currentPage;
    private Long pageSize;
    private String roleName;
}
