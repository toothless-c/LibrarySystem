package com.xhblogs.tushusyetm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhblogs.tushusyetm.entity.BoorowBook;
import com.xhblogs.tushusyetm.parm.*;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.util.List;


public interface BoorowBookService extends IService<BoorowBook> {
    //借书
    void borrow(BoorwParm boorowParm) throws ParseException;
    IPage<BoorowBook> getBorrowList(BoorowBookParm parm);
    //借阅查看
    IPage<BoorowBook> getLookBorrowList(LookParm parm);
    //还书
    void returnBook(List<ReturnParm> list);
    //异常还书
    void exceptionBook(ExceptionParm parm);
}
