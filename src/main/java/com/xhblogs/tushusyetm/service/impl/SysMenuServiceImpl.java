package com.xhblogs.tushusyetm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhblogs.tushusyetm.entity.SysMenu;
import com.xhblogs.tushusyetm.entity.Tree;
import com.xhblogs.tushusyetm.mapper.SysMenuMapper;
import com.xhblogs.tushusyetm.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Override
    public List<SysMenu> menuList() {
        //查询列表
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        //根据序号进行排序
        queryWrapper.lambda().orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> sysMenus = this.baseMapper.selectList(queryWrapper);
        Iterator<SysMenu> iterator = sysMenus.iterator();
        System.out.println("------这是查询的数据未封装--------");
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        //组装查询出来的数据
        List<SysMenu> list = Tree.makeTree(sysMenus,0L);
        return list;
    }

    @Override
    public List<SysMenu> parentList() {
        QueryWrapper<SysMenu> queryWrapper =  new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> list = this.baseMapper.selectList(queryWrapper);
        //构造父级菜单
        SysMenu sysMenu = new SysMenu();
        //默认0
        sysMenu.setMenuId(0L);
        //默认-1
        sysMenu.setParentId(-1L);
        sysMenu.setTitle("顶级菜单");
        list.add(sysMenu);
        List<SysMenu> lists = Tree.makeTree(list, -1L);
        return lists;
    }

    @Override
    public List<SysMenu> getMenuByUserId(Long userId) {
        return this.baseMapper.getMenuByUserId(userId);    }

    @Override
    public List<SysMenu> getMenuByRoleId(Long roleId) {
        return this.baseMapper.getMenuByRoleId(roleId);
    }

    @Override
    public List<SysMenu> getReaderMenuByUserId(Long readerId) {
        return this.baseMapper.getBkUserMenuByUserId(readerId);
    }
}
