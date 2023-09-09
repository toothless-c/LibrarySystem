package com.xhblogs.tushusyetm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xhblogs.tushusyetm.parm.LoginParm;
import com.xhblogs.tushusyetm.entity.SysUser;
import com.xhblogs.tushusyetm.parm.UserParm;
import com.xhblogs.tushusyetm.service.SysUserService;
import com.xhblogs.tushusyetm.utlis.ResultUtils;
import com.xhblogs.tushusyetm.utlis.ResultVo;
import com.xhblogs.tushusyetm.utlis.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 系统用户模块
 */
@RestController
@RequestMapping("/api/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping
    public ResultVo addUser(@RequestBody SysUser sysUser){
        //判断账号是否存在
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getUsername,sysUser.getUsername());
        SysUser one = sysUserService.getOne(queryWrapper);
        if (one !=null){
            return ResultUtils.success("账号已存在",StatusCode.SUCCESS_CODE);
        }
        //将密码进行加密
        if (StringUtils.isNotEmpty(sysUser.getPassword())){
            sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        }
           sysUser.setCreateTime(new Date());
           sysUserService.addUser(sysUser);
            return ResultUtils.error("添加成功",StatusCode.SUCCESS_CODE);
    }
    //编辑员工
    @PutMapping
    public ResultVo editUser(@RequestBody SysUser sysUser){
        //判断用户名是否存在
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername,sysUser.getUsername());
        SysUser one = sysUserService.getOne(query);
        if(one != null && one.getUserId() != sysUser.getUserId()){
            return ResultUtils.error("账户已经被占用",StatusCode.SUCCESS_CODE);
        }
        //密码加密
        if(StringUtils.isNotEmpty(sysUser.getPassword())) {
            sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        }
        sysUser.setUpdateTime(new Date());
        //存入数据库
        boolean save = sysUserService.updateById(sysUser);
        if(save){
            return ResultUtils.success("编辑成功",StatusCode.SUCCESS_CODE);
        }
        return  ResultUtils.error("编辑失败",StatusCode.SUCCESS_CODE);
    }
    //删除用户
    @DeleteMapping("/{userId}")
    public ResultVo deleteUser(@PathVariable("userId") Long userId){
        boolean remove = sysUserService.removeById(userId);
        if(remove){
            return ResultUtils.success("删除成功",StatusCode.SUCCESS_CODE);
        }
        return ResultUtils.error("删除失败",StatusCode.SUCCESS_CODE);
    }
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm loginParm){
        String username = loginParm.getUsername();
        String password = loginParm.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return ResultUtils.success("用户名密码不能为空",StatusCode.SUCCESS_CODE);
        }
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername,loginParm.getUsername())
                .eq(SysUser::getPassword,DigestUtils.md5DigestAsHex(loginParm.getPassword().getBytes()));
        return null;
    }
    @GetMapping("/list")
    public ResultVo getList(UserParm parm){
        IPage<SysUser> list = sysUserService.list(parm);
        list.getRecords().stream().forEach(item->{
            item.setPassword("");
        });
        return ResultUtils.success("获取成功",StatusCode.SUCCESS_CODE,list);
    }

}
