package com.xhblogs.tushusyetm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhblogs.tushusyetm.entity.SysRole;
import com.xhblogs.tushusyetm.parm.AssignParm;
import com.xhblogs.tushusyetm.parm.AssignVo;
import com.xhblogs.tushusyetm.parm.RoleParm;

public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> list(RoleParm parm);
    //角色权限的回显
    AssignVo getAssignShow(AssignParm parm);
}
