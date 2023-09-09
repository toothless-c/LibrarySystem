package com.xhblogs.tushusyetm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhblogs.tushusyetm.entity.BkBooks;
import com.xhblogs.tushusyetm.parm.BookParm;
import com.xhblogs.tushusyetm.parm.ExceptionParm;

import java.util.List;

public interface BkBooksService extends IService<BkBooks> {
    IPage<BkBooks> list(BookParm parm) ;
    List<BkBooks> queryByClassifyId(int id);
    IPage<BkBooks> QueryAllByNameAndSsm(BookParm bookParm);
    //借书
    int subBook(Long bookId);
    //还书
    int addBook(Long booId);

}
