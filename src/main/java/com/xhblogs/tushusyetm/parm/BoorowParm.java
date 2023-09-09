package com.xhblogs.tushusyetm.parm;

import lombok.Data;

@Data
public class BoorowParm {
    private Long currentPage;
    private Long pageSize;
    private String username;
    private String borrowStatus;
}
