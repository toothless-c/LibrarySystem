package com.xhblogs.tushusyetm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xhblogs.tushusyetm.entity.BkUser;
import com.xhblogs.tushusyetm.parm.BkUserParm;
import com.xhblogs.tushusyetm.service.BkUserService;
import com.xhblogs.tushusyetm.service.SysUserRoleService;
import com.xhblogs.tushusyetm.utlis.ResultUtils;
import com.xhblogs.tushusyetm.utlis.ResultVo;
import com.xhblogs.tushusyetm.utlis.StatusCode;
import org.apache.logging.log4j.status.StatusData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bkuser")
public class BkUserController {
    @Autowired
    private BkUserService bkUserService;
    @Autowired
      private SysUserRoleService sysUserRoleService;
    @PostMapping
    public  ResultVo addBkUser(@RequestBody BkUser bkUser){
        bkUser.setType("1");
        bkUser.setPassword("f379eaf3c831b04de153469d1bec345e");
        bkUser.setCheckStatus("0");
        //密码加密
        if(StringUtils.isNotEmpty(bkUser.getPassword())) {
            bkUser.setPassword(DigestUtils.md5DigestAsHex(bkUser.getPassword().getBytes()));
        }
        bkUserService.addUser(bkUser);
        return ResultUtils.success("添加成功",StatusCode.SUCCESS_CODE);
//        boolean save = bkUserService.save(bkUser);
//
//        if (save){
//            return ResultUtils.success("添加成功",StatusCode.SUCCESS_CODE);
//        }else {
//            return ResultUtils.success("添加失败",StatusCode.SUCCESS_CODE);
//        }
    }
    @PutMapping
    public ResultVo editReader(@RequestBody BkUser bkUser){
        boolean save = bkUserService.updateById(bkUser);
        if(save){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败！");
    }
    @DeleteMapping("/{id}")
    public ResultVo deleteReader(@PathVariable("id") Long id){
        boolean remove = bkUserService.removeById(id);
        if(remove){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败！");
    }

    @GetMapping("/list")
    public ResultVo getList(BkUserParm bkuserparm){
        IPage<BkUser> list = bkUserService.getList(bkuserparm);
        list.getRecords().stream().forEach(item->{
            item.setPassword("");
        });
        return ResultUtils.success("查询成功",list);
    }
    @PostMapping("/password/{id}")
    public ResultVo editPasswordById(@PathVariable("id") Integer id){
        int i = bkUserService.editPasswordById(id);
        if (i>0){
            return ResultUtils.success("重置密码成功,密码为666666",StatusCode.SUCCESS_CODE);
        }
        return ResultUtils.success("系统错误联系管理员",StatusCode.SUCCESS_CODE);
    }
    @PostMapping("/editsheckstatus/{id}/{checkStatus}")
    public ResultVo editScheckStaus(@PathVariable("id") Integer id ,@PathVariable("checkStatus") String checkStatus){
        int i = bkUserService.editCheckStatus(id, checkStatus);
        if (i>0){
            return ResultUtils.success("审核通过",StatusCode.SUCCESS_CODE,null);
        }else {
            return ResultUtils.success("审核失败",StatusCode.SUCCESS_CODE,null);
        }
    }
    //根据学号or名字查询
    @GetMapping("/getByName")
    public ResultVo getReader(BkUser bkUser){
        String username = bkUser.getUsername();
        String studentId = bkUser.getStudentId();
        List<BkUser> list = bkUserService.queryByUsernameOrPassword(username, studentId);
        return ResultUtils.success("查询成功",StatusCode.SUCCESS_CODE,list);
    }
}
