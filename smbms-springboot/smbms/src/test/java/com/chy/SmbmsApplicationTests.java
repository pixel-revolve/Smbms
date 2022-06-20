package com.chy;

import com.chy.mapper.UserMapper;
import com.chy.model.Result;
import com.chy.pojo.SysRole;
import com.chy.pojo.SysUser;
import com.chy.service.RoleService;
import com.chy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Set;

@SpringBootTest
class SmbmsApplicationTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

}
