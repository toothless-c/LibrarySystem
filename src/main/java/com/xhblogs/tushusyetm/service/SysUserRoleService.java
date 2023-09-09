package com.xhblogs.tushusyetm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhblogs.tushusyetm.entity.SysUser;
import com.xhblogs.tushusyetm.entity.SysUserRole;

public interface SysUserRoleService extends IService<SysUserRole> {

    void addUser(SysUser user);
    void editUser(SysUser user);
}
