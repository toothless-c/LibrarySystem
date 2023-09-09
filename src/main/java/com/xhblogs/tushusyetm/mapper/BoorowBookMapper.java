package com.xhblogs.tushusyetm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhblogs.tushusyetm.entity.BkBooks;
import com.xhblogs.tushusyetm.entity.BoorowBook;
import com.xhblogs.tushusyetm.parm.BoorowBookParm;
import com.xhblogs.tushusyetm.parm.LookParm;
import org.apache.ibatis.annotations.Param;

public interface BoorowBookMapper extends BaseMapper<BoorowBook> {
    IPage<BoorowBook> getBorrowList(Page<BoorowBook> page, @Param("parm") BoorowBookParm parm);
    //借阅查看接口
    IPage<BoorowBook> getLookBorrowList(Page<BoorowBook> page, @Param("parms") LookParm parms);
}
