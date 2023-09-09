package com.xhblogs.tushusyetm.parm;

import lombok.Data;

@Data
public class BookParm  {
    private String name; //图书名称
    private String sM;//图书编号
    private int id;//接受到的分类id
    private int currentPage;
    private int pageSize;
}
