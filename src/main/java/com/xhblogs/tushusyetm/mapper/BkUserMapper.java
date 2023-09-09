package com.xhblogs.tushusyetm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhblogs.tushusyetm.entity.BkUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BkUserMapper extends BaseMapper<BkUser> {
    int editPasswordById(@Param("id") Integer id );
    int editCheckStatus(@Param("id") Integer id,@Param("checkStatus") String checkStatus);
    List<BkUser> queryByUsernameOrPassword(@Param("username") String username,@Param("studentId")String studentId);
}
