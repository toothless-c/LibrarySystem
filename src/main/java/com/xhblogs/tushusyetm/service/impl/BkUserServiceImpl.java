package com.xhblogs.tushusyetm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhblogs.tushusyetm.entity.BkUser;
import com.xhblogs.tushusyetm.entity.BkUserRole;
import com.xhblogs.tushusyetm.mapper.BkUserMapper;
import com.xhblogs.tushusyetm.parm.BkUserParm;
import com.xhblogs.tushusyetm.service.BkUserRoleService;
import com.xhblogs.tushusyetm.service.BkUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BkUserServiceImpl extends ServiceImpl<BkUserMapper, BkUser> implements BkUserService {
    @Autowired
    private BkUserMapper bkUserMapper;
    @Autowired
    private BkUserRoleService bkUserRoleService;
    @Override
    public IPage<BkUser> getList(BkUserParm bkUserParm) {
        QueryWrapper<BkUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(bkUserParm.getId())){
            queryWrapper.lambda().like(BkUser::getId,bkUserParm.getId());
        }
        if (StringUtils.isNotEmpty(bkUserParm.getPhone())){
            queryWrapper.lambda().like(BkUser::getPhone,bkUserParm.getPhone());
        }
        if (StringUtils.isNotEmpty(bkUserParm.getUsername())){
            queryWrapper.lambda().like(BkUser::getUsername,bkUserParm.getUsername());
        }
        if (StringUtils.isNotEmpty(bkUserParm.getStudentId())) {
            queryWrapper.lambda().like(BkUser::getStudentId,bkUserParm.getStudentId());
        }
        IPage<BkUser> page = new Page<>();
        page.setCurrent(bkUserParm.getCurrentPage());
        page.setSize(bkUserParm.getPageSize());
        return this.baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int editPasswordById(Integer id) {
      return bkUserMapper.editPasswordById(id);
    }

    @Override
    public int editCheckStatus(Integer id, String checkStatus) {
        return bkUserMapper.editCheckStatus(id, checkStatus);
    }

    @Override
    public List<BkUser> queryByUsernameOrPassword(String username, String studentId) {
        return bkUserMapper.queryByUsernameOrPassword(username, studentId);
    }

    @Override
    @Transactional
    public void addUser(BkUser user) {
        int insert = this.bkUserMapper.insert(user);
        if (insert>0){
            BkUserRole bkUserRole = new BkUserRole();
            bkUserRole.setRoleId(12L);
            bkUserRole.setReaderId(user.getId());
            bkUserRoleService.save(bkUserRole);
        }
    }

    @Override
    public BkUser loadByUsername(String username) {
        QueryWrapper<BkUser> query = new QueryWrapper<>();
        query.lambda().eq(BkUser::getUsername,username);
        return this.baseMapper.selectOne(query);
    }
}
