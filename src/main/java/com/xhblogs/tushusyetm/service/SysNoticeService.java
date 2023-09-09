package com.xhblogs.tushusyetm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhblogs.tushusyetm.entity.SysNotice;
import com.xhblogs.tushusyetm.parm.NoticeParm;

public interface SysNoticeService extends IService<SysNotice> {
    IPage<SysNotice> getList(NoticeParm parm);
}
