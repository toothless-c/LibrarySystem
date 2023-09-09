package com.xhblogs.tushusyetm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhblogs.tushusyetm.entity.BkClassify;
import com.xhblogs.tushusyetm.parm.ClassifyParm;
import com.xhblogs.tushusyetm.service.BkClassifyService;
import com.xhblogs.tushusyetm.utlis.ResultUtils;
import com.xhblogs.tushusyetm.utlis.ResultVo;
import com.xhblogs.tushusyetm.utlis.StatusCode;
import com.xhblogs.tushusyetm.utlis.TreePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bkclassify")
public class BkClassifyController   {
    @Autowired
    private BkClassifyService bkClassifyService;
//    @Override
//    public IPage<BkBooks> list(BookParm parm) {
//        Page<BkBooks> page = new Page<>();
//        page.setSize(parm.getPageSize());
//        page.setCurrent(parm.getCurrentPage());
//        QueryWrapper<BkBooks> query = new QueryWrapper<>();
//        if (StringUtils.isNotEmpty(parm.getName())){
//            query.lambda().like(BkBooks::getName,parm.getName());
//        }
//        if (StringUtils.isNotEmpty(parm.getSM())){
//            query.lambda().like(BkBooks::getSM,parm.getSM());
//        }
//        return this.baseMapper.selectPage(page,query);
//    }

    /**
     * 获取分类树
     * @return
     */
    @GetMapping("/tree")
    public ResultVo getAllTree(){
        List<Map<String,Object>> list = new ArrayList<>();
        List<BkClassify> bkClassifyList = bkClassifyService.list();
        for(BkClassify a:bkClassifyList){
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("label",a.getClassifyName());
            objectObjectHashMap.put("value",a.getClassifyId());
            list.add(objectObjectHashMap);
        }
        TreePojo treePojo = new TreePojo();
        treePojo.setLabel("图书分类");
        treePojo.setChildren(list);
        return ResultUtils.success("获取成功", StatusCode.SUCCESS_CODE,list);
    }

    /**
     * 获取所有分类
     * @param parm
     * @return
     */
    @GetMapping("/list")
    public ResultVo  getList(ClassifyParm parm){
        IPage<BkClassify> list = bkClassifyService.list(parm);
        if (list!=null){
            return ResultUtils.success("获取成功", StatusCode.SUCCESS_CODE,list);
        }else {
            return ResultUtils.success("获取失败",StatusCode.SUCCESS_CODE,list);
        }
    }

    /**
     * 新增分类
     * @param classify
     * @return
     */
    @PostMapping
    public ResultVo addClassify(@RequestBody BkClassify classify){
        boolean save = bkClassifyService.save(classify);
        if (save){
            return ResultUtils.success("添加成功",StatusCode.SUCCESS_CODE);
        }else {
            return ResultUtils.success("添加失败",StatusCode.SUCCESS_CODE);
        }
    }

    /**
     * 编辑分类
     * @param classify
     * @return
     */
    @PutMapping
    public ResultVo editClassify(@RequestBody BkClassify classify){
        boolean b = bkClassifyService.updateById(classify);
        if (b){
            return ResultUtils.success("编辑成功",StatusCode.SUCCESS_CODE);
        }else {
            return ResultUtils.success("编辑失败",StatusCode.SUCCESS_CODE);
        }
    }

    /**
     * 根据id删除分类
     * @param classifyId
     * @return
     */
    @DeleteMapping("/{classifyId}")
    public ResultVo deleteByid(@PathVariable("classifyId")int classifyId){
        boolean b = bkClassifyService.removeById(classifyId);
        if (b){
            return ResultUtils.success("删除成功",StatusCode.SUCCESS_CODE);
        }else {
            return ResultUtils.success("删除失败",StatusCode.SUCCESS_CODE);
        }
    }
}
