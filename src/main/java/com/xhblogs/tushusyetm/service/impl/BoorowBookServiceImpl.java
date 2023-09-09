package com.xhblogs.tushusyetm.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xhblogs.tushusyetm.entity.BkBooks;
import com.xhblogs.tushusyetm.entity.BoorowBook;
import com.xhblogs.tushusyetm.mapper.BkBooksMapper;
import com.xhblogs.tushusyetm.mapper.BoorowBookMapper;
import com.xhblogs.tushusyetm.parm.*;
import com.xhblogs.tushusyetm.service.BkBooksService;
import com.xhblogs.tushusyetm.service.BoorowBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoorowBookServiceImpl extends ServiceImpl<BoorowBookMapper, BoorowBook> implements BoorowBookService {
    @Autowired
    private BkBooksService bkBooksService;
    @Autowired
    private BkBooksMapper bkBooksMapper;
    @Override
    public void borrow(BoorwParm boorowParm) throws ParseException {
        //查询图书库存
        QueryWrapper<BkBooks> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(BkBooks::getId,boorowParm.getBookIds());
        List<BkBooks> list = bkBooksService.list(queryWrapper);
        //查询当前图书id的库存
//        List<BkBooks> collect = list.stream().filter(item -> item.getNumber() < 1L).collect(Collectors.toList());
//        if (collect.size()>0){
//            //查询哪一本的图书库存不足
//            List<String> stringList = collect.stream().map(BkBooks::getName).collect(Collectors.toList());
//            return ;
//        }
        //较少库存
        List<Long> bookIds = boorowParm.getBookIds();
        for (int i = 0; i < bookIds.size(); i++) {
            Long bookId = bookIds.get(i);
            int a = bkBooksService.subBook(bookId);
            if (a ==1){
                BoorowBook boorowBook = new BoorowBook();
                Date date = new Date();
                String strDateFormat = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                String newdatime = sdf.format(date);
                Date newTime = sdf.parse(newdatime);
                boorowBook.setBookId(bookId);
                boorowBook.setReaderId(boorowParm.getReaderId());
                boorowBook.setBorrowTime(newTime);
                boorowBook.setReturnTime(boorowParm.getReturnTime());
                boorowBook.setBorrowStatus("1");
                int insert = bkBooksMapper.insert(boorowBook);
            }
        }
    }

    @Override
    public IPage<BoorowBook> getBorrowList(BoorowBookParm parm) {
        Page<BoorowBook> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        return this.baseMapper.getBorrowList(page,parm);
    }

    @Override
    public IPage<BoorowBook> getLookBorrowList(LookParm parm) {
        //构造分页对象
        Page<BoorowBook> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        return this.baseMapper.getLookBorrowList(page,parm);
    }

    @Override
    @Transactional
    public void returnBook(List<ReturnParm> list) {
        for (int i = 0; i < list.size(); i++) {
            //更新图书库存
            int res = bkBooksService.addBook(list.get(i).getBookId());
            if (res == 1) {
                //更新状态为还书
                BoorowBook borrowBook = new BoorowBook();
                borrowBook.setBorrowStatus("2");
                borrowBook.setReturnStatus("1");
                borrowBook.setId(list.get(i).getBorrowId());
                this.baseMapper.updateById(borrowBook);
            }
        }
    }

    @Override
    public void exceptionBook(ExceptionParm parm) {
        // 0: 异常、破损  1：丢失 ：不能还库存
        String type = parm.getType();
        if(type.equals("0")){
            //加库存
            int res = bkBooksService.addBook(parm.getBookId());
            if(res == 1){
                //变更借书状态
                BoorowBook borrowBook = new BoorowBook();
                borrowBook.setId(parm.getBorrowId());
                borrowBook.setBorrowStatus("2"); //已还
                borrowBook.setReturnStatus("2"); //异常还书
                borrowBook.setExcepionText(parm.getExcepionText());
                this.baseMapper.updateById(borrowBook);
            }
        }else{ //丢失
            //变更借书状态
            BoorowBook borrowBook = new BoorowBook();
            borrowBook.setId(parm.getBorrowId());
            borrowBook.setBorrowStatus("2"); //已还
            borrowBook.setReturnStatus("3"); //丢失
            borrowBook.setExcepionText(parm.getExcepionText());
            this.baseMapper.updateById(borrowBook);
        }
    }
}
