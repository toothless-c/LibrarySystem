package com.xhblogs.tushusyetm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhblogs.tushusyetm.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    //查询所有的菜单
    List<SysMenu> menuList();
    //查询所有的上级菜单
    List<SysMenu> parentList();
    //根据用户id查询权限
    List<SysMenu> getMenuByUserId(Long userId);
    //根据角色id查询权限
    List<SysMenu> getMenuByRoleId(Long roleId);
    //根据读者id
    //根据读者id查询权限
    List<SysMenu> getReaderMenuByUserId(Long readerId);

}
