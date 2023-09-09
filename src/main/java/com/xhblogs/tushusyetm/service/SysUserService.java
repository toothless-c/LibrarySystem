package com.xhblogs.tushusyetm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhblogs.tushusyetm.entity.SysUser;
import com.xhblogs.tushusyetm.parm.UserParm;

public interface SysUserService extends IService<SysUser> {
    IPage<SysUser> list(UserParm parm);
    void addUser(SysUser user);
    void editUser(SysUser user);
   SysUser loadByUsername(String username);
}
