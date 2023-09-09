package com.xhblogs.tushusyetm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhblogs.tushusyetm.entity.SysNotice;
import com.xhblogs.tushusyetm.mapper.SysNoticeMapper;
import com.xhblogs.tushusyetm.parm.NoticeParm;
import com.xhblogs.tushusyetm.service.SysNoticeService;
import org.springframework.stereotype.Service;

@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
    @Override
    public IPage<SysNotice> getList(NoticeParm parm) {
        //构造分页对象
        Page<SysNotice> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        //构造查询条件
        QueryWrapper<SysNotice> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getNoticeTitle())){
            query.lambda().like(SysNotice::getNoticeTitle,parm.getNoticeTitle());
        }
        query.lambda().orderByDesc(SysNotice::getCreateTime);
        return this.baseMapper.selectPage(page,query);
    }
}
