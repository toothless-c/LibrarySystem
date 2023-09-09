package com.xhblogs.tushusyetm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhblogs.tushusyetm.entity.BkUser;
import com.xhblogs.tushusyetm.entity.SysUser;
import com.xhblogs.tushusyetm.parm.BkUserParm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BkUserService extends IService<BkUser> {
    IPage<BkUser> getList(BkUserParm bkUserParm);
    int editPasswordById(Integer id);
    int editCheckStatus( Integer id, String checkStatus);
    List<BkUser> queryByUsernameOrPassword(String username,String studentId);
    void addUser(BkUser user);
    BkUser  loadByUsername(String username);

}
