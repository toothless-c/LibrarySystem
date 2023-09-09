package com.xhblogs.tushusyetm;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhblogs.tushusyetm.config.JwtUtils;
import com.xhblogs.tushusyetm.entity.BkBooks;
import com.xhblogs.tushusyetm.entity.BkUser;
import com.xhblogs.tushusyetm.entity.BoorowBook;
import com.xhblogs.tushusyetm.entity.SysMenu;
import com.xhblogs.tushusyetm.mapper.BkBooksMapper;
import com.xhblogs.tushusyetm.mapper.BkUserMapper;
import com.xhblogs.tushusyetm.mapper.BoorowBookMapper;
import com.xhblogs.tushusyetm.parm.*;
import com.xhblogs.tushusyetm.service.*;
import com.xhblogs.tushusyetm.service.impl.BkBooksServiceImpl;
import com.xhblogs.tushusyetm.utlis.PageCovertUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class TushusyetmApplicationTests {
    @Autowired
    private BkUserMapper bkUserMapper;
    @Autowired
    private BkBooksMapper bkBooksMapper;
    @Autowired
    private BkBooksService bkBooksService;
    @Autowired
    private BoorowBookService boorowBookService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private JwtUtils jwtUtils;
    @Test
    void contextLoads() {
        List<BkUser> list = bkUserMapper.queryByUsernameOrPassword(null, "20206102050098");
    }
    @Test
    void test1() throws ParseException {
        Long id = 11L;
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        list.add(4L);
        list.add(5L);
        list.add(6L);
        list.add(7L);
        list.add(8L);
        list.add(9L);
        list.add(10L);
        list.add(11L);
        list.add(12L);
        roleMenuService.assignSave(id,list);
    }
    @Test
    void test2(){
        String admin = jwtUtils.generateToken("admin", "1");
    }
}
