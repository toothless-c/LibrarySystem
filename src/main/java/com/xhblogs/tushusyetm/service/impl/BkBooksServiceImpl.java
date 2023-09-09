package com.xhblogs.tushusyetm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhblogs.tushusyetm.entity.BkBooks;
import com.xhblogs.tushusyetm.mapper.BkBooksMapper;
import com.xhblogs.tushusyetm.parm.BookParm;
import com.xhblogs.tushusyetm.parm.ExceptionParm;
import com.xhblogs.tushusyetm.service.BkBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BkBooksServiceImpl extends ServiceImpl<BkBooksMapper, BkBooks> implements BkBooksService {
    @Autowired
    private BkBooksMapper bkBooksMapper;
    @Override
    public IPage<BkBooks> list(BookParm parm) {
        Page<BkBooks> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        QueryWrapper<BkBooks> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getName())){
            query.lambda().like(BkBooks::getName,parm.getName());
        }
        if (StringUtils.isNotEmpty(parm.getSM())){
            query.lambda().like(BkBooks::getSM,parm.getSM());
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    public List<BkBooks> queryByClassifyId(int id) {
        return bkBooksMapper.queryByClassifyId(id);
    }

    @Override
    public IPage<BkBooks> QueryAllByNameAndSsm(BookParm bookParm) {
        Page<BkBooks> page = new Page<>();
        page.setSize(bookParm.getPageSize());
        page.setCurrent(bookParm.getCurrentPage());
        return this.baseMapper.QueryAllByNameAndSsm(page,bookParm);
    }

    @Override
    public int subBook(Long bookId) {
        return this.baseMapper.subBook(bookId);
    }

    @Override
    public int addBook(Long booId) {
        return this.baseMapper.addBook(booId);
    }




}
