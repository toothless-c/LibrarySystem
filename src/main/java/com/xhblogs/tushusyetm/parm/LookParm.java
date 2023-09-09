package com.xhblogs.tushusyetm.parm;

import lombok.Data;

/**
 * 借阅查看
 */
@Data
public class LookParm {
    //当前页
    private Long currentPage;
    //每页条数
    private Long pageSize;
    private Long id;
    //图书名称
    private String bookName;
}
