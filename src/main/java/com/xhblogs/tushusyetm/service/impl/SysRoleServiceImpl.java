package com.xhblogs.tushusyetm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhblogs.tushusyetm.entity.SysMenu;
import com.xhblogs.tushusyetm.entity.SysRole;
import com.xhblogs.tushusyetm.entity.SysUser;
import com.xhblogs.tushusyetm.entity.Tree;
import com.xhblogs.tushusyetm.mapper.SysRoleMapper;
import com.xhblogs.tushusyetm.parm.AssignParm;
import com.xhblogs.tushusyetm.parm.AssignVo;
import com.xhblogs.tushusyetm.parm.RoleParm;
import com.xhblogs.tushusyetm.service.SysMenuService;
import com.xhblogs.tushusyetm.service.SysRoleService;
import com.xhblogs.tushusyetm.service.SysUserRoleService;
import com.xhblogs.tushusyetm.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;
    @Override
    public IPage<SysRole> list(RoleParm parm) {
        //构造分页对象
        IPage<SysRole> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        //构造查询条件
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getRoleName())){
            query.lambda().like(SysRole::getRoleName,parm.getRoleName());
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    public AssignVo getAssignShow(AssignParm parm) {
        //查询当前用户的信息
        SysUser user = sysUserService.getById(parm.getUserId());
        //菜单数据
        List<SysMenu> list = null;
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        list = sysMenuService.list(query);
//        if(user.getIsAdmin().equals("1")){ //如果是超级管理员，拥有所有的权限
//            QueryWrapper<SysMenu> query = new QueryWrapper<>();
//            query.lambda().orderByAsc(SysMenu::getOrderNum);
//            list = sysMenuService.list(query);
//        }else{
//            list = sysMenuService.getMenuByUserId(user.getUserId());
//        }
        //组装树
        List<SysMenu> menuList = Tree.makeTree(list, 0L);
        //查询角色原来的菜单
        List<SysMenu> roleList = sysMenuService.getMenuByRoleId(parm.getRoleId());
        List<Long> ids = new ArrayList<>();
        Optional.ofNullable(roleList).orElse(new ArrayList<>()).stream().filter(item -> item != null).forEach(item ->{
            ids.add(item.getMenuId());
        });
        //组装数据
        AssignVo vo = new AssignVo();
        vo.setMenuList(menuList);
        vo.setCheckList(ids.toArray());
        return vo;

    }
}
