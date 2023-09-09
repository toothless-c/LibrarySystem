package com.xhblogs.tushusyetm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhblogs.tushusyetm.entity.SysMenu;
import com.xhblogs.tushusyetm.entity.SysRole;
import com.xhblogs.tushusyetm.entity.SysUser;
import com.xhblogs.tushusyetm.entity.SysUserRole;
import com.xhblogs.tushusyetm.parm.UserParm;
import com.xhblogs.tushusyetm.mapper.SysUserMapper;
import com.xhblogs.tushusyetm.service.SysUserRoleService;
import com.xhblogs.tushusyetm.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUSerServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Override
    public IPage<SysUser> list(UserParm parm) {
        //构造分页对象
        IPage<SysUser> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        //构造查询条件
        if(StringUtils.isNotEmpty(parm.getNickName())){
            query.lambda().like(SysUser::getNickName,parm.getNickName());
        }
        if(StringUtils.isNotEmpty(parm.getPhone())){
            query.lambda().like(SysUser::getPhone,parm.getPhone());
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    @Transactional
    public void addUser(SysUser user) {
        //新增用户
        int insert = this.baseMapper.insert(user);
        //分配角色
        if(insert > 0){
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(11L);
            userRole.setUserId(user.getUserId());
            sysUserRoleService.save(userRole);
        }
    }

    @Override
    @Transactional
    public void editUser(SysUser user) {

    }

    @Override
    public SysUser loadByUsername(String username) {
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername,username);
        return this.baseMapper.selectOne(query);
    }
}

