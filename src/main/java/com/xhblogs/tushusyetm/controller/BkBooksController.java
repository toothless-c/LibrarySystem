package com.xhblogs.tushusyetm.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhblogs.tushusyetm.entity.BkBooks;
import com.xhblogs.tushusyetm.parm.BookParm;
import com.xhblogs.tushusyetm.service.BkBooksService;
import com.xhblogs.tushusyetm.utlis.ResultUtils;
import com.xhblogs.tushusyetm.utlis.ResultVo;
import com.xhblogs.tushusyetm.utlis.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BkBooksController {
    @Autowired
    private BkBooksService bkBooksService;
    @DeleteMapping("/{bookId}")
    public ResultVo deleteBookClass(@PathVariable("bookId") Long bookId){
        boolean b = bkBooksService.removeById(bookId);
        return ResultUtils.success("删除成功", StatusCode.SUCCESS_CODE);
    }

    @PutMapping
    public ResultVo editBook(@RequestBody BkBooks bkBooks){
        bkBooks.setUpdateTime(new Date());
        boolean b = bkBooksService.updateById(bkBooks);
        if (b){
            return ResultUtils.success("编辑成功",StatusCode.SUCCESS_CODE);
        }
        return ResultUtils.error("编辑失败",StatusCode.SUCCESS_CODE);
    }

    @PostMapping
    public ResultVo addBooks(@RequestBody BkBooks bkBooks){
        bkBooks.setCreateTime(new Date());
        boolean save = bkBooksService.save(bkBooks);
        if (save){
            return ResultUtils.success("添加成功", StatusCode.SUCCESS_CODE);
        }
        return ResultUtils.success("添加失败",StatusCode.SUCCESS_CODE);
    }
    //角色列表
    @GetMapping("/list")
    public ResultVo getListBook(BookParm parm){
        IPage<BkBooks> list = bkBooksService.list(parm);
        if (list!=null){
            return ResultUtils.success("获取成功", StatusCode.SUCCESS_CODE,list);
        }
        return ResultUtils.success("获取失败", StatusCode.SUCCESS_CODE);
    }
    //查询分类下的
    @GetMapping("/{id}")
    public ResultVo queryByClassifyId(@PathVariable("id") int id){
        List<BkBooks> bkBooks = bkBooksService.queryByClassifyId(id);
        return ResultUtils.success("获取成功",StatusCode.SUCCESS_CODE,bkBooks);
    }
    @GetMapping("/query")
    public ResultVo queryByNameAndSm(BookParm parm){
        IPage<BkBooks> bkBooksIPage = bkBooksService.QueryAllByNameAndSsm(parm);
        if (bkBooksIPage!=null){
            return ResultUtils.success("获取成功",StatusCode.SUCCESS_CODE,bkBooksIPage);
        }
        return  ResultUtils.success("获取失败",StatusCode.SUCCESS_CODE);
    }
}
