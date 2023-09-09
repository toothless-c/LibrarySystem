package com.xhblogs.tushusyetm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhblogs.tushusyetm.entity.RoleMenu;

import java.util.List;

public interface RoleMenuService extends IService<RoleMenu> {
    //保存角色的权限
    void assignSave(Long roleId, List<Long> menuList);
}
