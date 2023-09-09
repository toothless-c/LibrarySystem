package com.xhblogs.tushusyetm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhblogs.tushusyetm.entity.SysUser;
import com.xhblogs.tushusyetm.entity.SysUserRole;
import com.xhblogs.tushusyetm.mapper.SysUserRoleMapper;
import com.xhblogs.tushusyetm.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Action;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Override
    @Transactional
    public void addUser(SysUser user) {
    
    }

    @Override
    @Transactional
    public void editUser(SysUser user) {

    }
}