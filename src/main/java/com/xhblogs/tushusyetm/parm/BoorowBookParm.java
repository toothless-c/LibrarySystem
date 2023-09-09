package com.xhblogs.tushusyetm.parm;

import lombok.Data;

@Data
public class BoorowBookParm {
    private String studentId;
    private String borrowStatus;
    private Long currentPage;
    private Long pageSize;
}
