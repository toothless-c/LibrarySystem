package com.xhblogs.tushusyetm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhblogs.tushusyetm.entity.BkBooks;
import com.xhblogs.tushusyetm.entity.BoorowBook;
import com.xhblogs.tushusyetm.parm.BookParm;
import com.xhblogs.tushusyetm.parm.ExceptionParm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BkBooksMapper extends BaseMapper<BkBooks> {
    List<BkBooks> queryByClassifyId(@Param("id") int id);
    IPage<BkBooks> QueryAllByNameAndSsm(Page<BkBooks> page, @Param("bookparm") BookParm bookParm);
    //借书
    int subBook(@Param("bookId") Long bookId);
    //还书
    int addBook(@Param("bookId") Long bookId);
    int insert(@Param("parm")BoorowBook boorowBook);
    //异常还书
}
