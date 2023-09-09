package com.xhblogs.tushusyetm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhblogs.tushusyetm.entity.BkClassify;
import com.xhblogs.tushusyetm.mapper.BkClassifyMapper;
import com.xhblogs.tushusyetm.parm.ClassifyParm;
import com.xhblogs.tushusyetm.service.BkClassifyService;
import org.springframework.stereotype.Service;

@Service
public class BkClassifyServiceImpl extends ServiceImpl<BkClassifyMapper, BkClassify> implements BkClassifyService {
    @Override
    public IPage<BkClassify> list(ClassifyParm parm) {
        Page<BkClassify> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        QueryWrapper<BkClassify> query = new QueryWrapper<>();
        return this.baseMapper.selectPage(page,query);
    }
}
