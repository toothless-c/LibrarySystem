package com.xhblogs.tushusyetm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xhblogs.tushusyetm.entity.BoorowBook;
import com.xhblogs.tushusyetm.parm.*;
import com.xhblogs.tushusyetm.service.BoorowBookService;
import com.xhblogs.tushusyetm.utlis.ResultUtils;
import com.xhblogs.tushusyetm.utlis.ResultVo;
import com.xhblogs.tushusyetm.utlis.StatusCode;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowBookController {
    @Autowired
    private BoorowBookService boorowBookService;

    @GetMapping("/getBorrowList")
    public ResultVo getBorrowList(BoorowBookParm parm){
        IPage<BoorowBook> borrowList = boorowBookService.getBorrowList(parm);
        return ResultUtils.success("查询成功",borrowList);
    }
    @PostMapping("/{id}")
    public ResultVo returnBook(@PathVariable("id") Integer id){
       return null;
    }
    //借书
    @PostMapping
    public ResultVo borrow(@RequestBody BoorwParm borrowParm) throws ParseException {
        boorowBookService.borrow(borrowParm);
        return ResultUtils.success("借书成功",StatusCode.SUCCESS_CODE);
    }
    //借阅查看
    @GetMapping("/getLookBorrowList")
    public ResultVo getLookBorrowList(LookParm parm){
        IPage<BoorowBook> borrowList = boorowBookService.getLookBorrowList(parm);
        return ResultUtils.success("查询成功",borrowList);
    }
    //还书
    @PostMapping("/returnBook")
    public ResultVo returnBook(@RequestBody List<ReturnParm> parm){
        boorowBookService.returnBook(parm);
        return ResultUtils.success("还书成功", StatusCode.SUCCESS_CODE);
    }
    //异常还书
    @PostMapping("/exceptionBooks")
    public ResultVo exceptionBooks(@RequestBody ExceptionParm parm){
        boorowBookService.exceptionBook(parm);
        return ResultUtils.success("还书成功!");
    }

}
