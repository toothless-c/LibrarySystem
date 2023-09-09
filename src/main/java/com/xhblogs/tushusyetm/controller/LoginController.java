package com.xhblogs.tushusyetm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xhblogs.tushusyetm.config.JwtUtils;
import com.xhblogs.tushusyetm.entity.*;
import com.xhblogs.tushusyetm.parm.LoginParm;
import com.xhblogs.tushusyetm.parm.LoginVo;
import com.xhblogs.tushusyetm.parm.RouterVO;
import com.xhblogs.tushusyetm.service.BkUserService;
import com.xhblogs.tushusyetm.service.SysMenuService;
import com.xhblogs.tushusyetm.service.SysUserService;
import com.xhblogs.tushusyetm.utlis.ResultUtils;
import com.xhblogs.tushusyetm.utlis.ResultVo;
import com.xhblogs.tushusyetm.utlis.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BkUserService bkUserService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private SysMenuService sysMenuService;
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm loginParm){
        if (StringUtils.isEmpty(loginParm.getUsername()) ||StringUtils.isEmpty(loginParm.getPassword()) ||StringUtils.isEmpty(loginParm.getUserType())){
            return ResultUtils.error("用户名、或者密码不能为空");
        }
        //读者
        if (loginParm.getUserType().equals("0")){
            QueryWrapper<BkUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BkUser::getUsername,loginParm.getUsername())
                    .eq(BkUser::getPassword, DigestUtils.md5DigestAsHex(loginParm.getPassword().getBytes()));
            BkUser one = bkUserService.getOne(queryWrapper);
            if (one == null){
                return ResultUtils.error("用户名或密码错误");
            }
            LoginVo loginVo = new LoginVo();
            loginVo.setUserId(one.getId());
            loginVo.setToken(jwtUtils.generateToken(one.getUsername(), loginParm.getUserType()));
            loginVo.setUserType(loginParm.getUserType());
            return ResultUtils.success("登录成功", StatusCode.SUCCESS_CODE,loginVo);
        }else if (loginParm.getUserType().equals("1")){//管理
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysUser::getUsername,loginParm.getUsername())
                    .eq(SysUser::getPassword,DigestUtils.md5DigestAsHex(loginParm.getPassword().getBytes()));
            SysUser one = sysUserService.getOne(queryWrapper);
            if (one == null){
                return ResultUtils.error("用户名或密码错误");
            }
            LoginVo loginVo = new LoginVo();
            loginVo.setUserId(one.getUserId());
            loginVo.setUserType(loginParm.getUserType());
            loginVo.setToken(jwtUtils.generateToken(one.getUsername(), loginParm.getUserType()));
            return ResultUtils.success("登录成功", StatusCode.SUCCESS_CODE,loginVo);
        }else {
            return ResultUtils.error("用户不存在");
        }
    }
    //获取用户权限字段
    @GetMapping("/getinfo")
    public ResultVo getInfo(Long userId, HttpServletRequest request){
        //从请求的头部获取token
        String token = request.getHeader("token");
        //从token里面解析用户的类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        //定义用户信息类
        UserInfoVo userInfo = new UserInfoVo();
        if(userType.equals("0")){ //读者
            //根据id查询读者
            BkUser bkUser = bkUserService.getById(userId);
            userInfo.setIntroduction(bkUser.getSex());
            userInfo.setName(bkUser.getName());
            userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            //查询权限
            List<SysMenu> readerMenuByUserId = sysMenuService.getReaderMenuByUserId(userId);
            List<String> collect = readerMenuByUserId.stream().filter(item -> item != null && item.getCode() != null).map(item -> item.getCode()).collect(Collectors.toList());
            if(collect.size() ==0){
                return ResultUtils.error("暂无登录权限，请联系管理员!");
            }
            //转成数组
            String[] strings = collect.toArray(new String[collect.size()]);
            userInfo.setRoles(strings);
            return ResultUtils.success("查询成功",userInfo);
        }
        if(userType.equals("1")){ //管理员
            SysUser sysUser = new SysUser();
            userInfo.setIntroduction(sysUser.getNickName());
            userInfo.setName(sysUser.getNickName());
            userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            List<SysMenu> menuByUserId = sysMenuService.getMenuByUserId(userId);
            List<String> collect = menuByUserId.stream().filter(item -> item != null && item.getCode() != null).map(item -> item.getCode()).collect(Collectors.toList());
            if(collect.size() ==0){
                return ResultUtils.error("暂无登录权限，请联系管理员!");
            }
            //转成数组
            String[] strings = collect.toArray(new String[collect.size()]);
            userInfo.setRoles(strings);
            return ResultUtils.success("查询成功",userInfo);
        }
        return ResultUtils.success("用户类型不存在",userInfo);
    }
    @GetMapping("/getMenuList")
    public ResultVo getMenuList(HttpServletRequest request){
        //获取token
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            return ResultUtils.success("token过期",600);
        }
        //获取用户名和类型
        String username = jwtUtils.getUsernameFromToken(token);
        //用户类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        if (userType.equals("0")){
            //获取用户信息
            BkUser reader = bkUserService.loadByUsername(username);
            //获取权限信息
            List<SysMenu> menuList = sysMenuService.getReaderMenuByUserId(reader.getId());
            List<SysMenu> collect = menuList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            if(collect.size() == 0){
                return ResultUtils.error("暂无登录权限，请联系管理员!");
            }
            List<RouterVO> routerVOS = Tree.makeRouter(collect, 0L);
            return ResultUtils.success("查询成功",routerVOS);
        }
        if (userType.equals("1")){
        //获取用户信息
            SysUser sysUser = sysUserService.loadByUsername(username);
            //获取权限信息
            List<SysMenu> menuList = sysMenuService.getMenuByUserId(sysUser.getUserId());
            List<SysMenu> collect = menuList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            if(collect.size() == 0){
                return ResultUtils.error("暂无登录权限，请联系管理员!");
            }
            List<RouterVO> routerVOS = Tree.makeRouter(collect, 0L);
            return ResultUtils.success("查询成功",routerVOS);
        }
        return ResultUtils.error("用户类型不存");
    }
}
