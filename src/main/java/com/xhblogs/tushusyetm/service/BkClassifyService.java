package com.xhblogs.tushusyetm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhblogs.tushusyetm.entity.BkClassify;
import com.xhblogs.tushusyetm.parm.ClassifyParm;


public interface BkClassifyService extends IService<BkClassify> {
    IPage<BkClassify> list(ClassifyParm parm);
}

