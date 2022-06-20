package com.chy.service.impl;

import com.chy.mapper.MenuMapper;
import com.chy.mapper.UserMapper;
import com.chy.model.Paging;
import com.chy.model.Result;
import com.chy.pojo.*;
import com.chy.service.UserService;
import com.chy.utils.JwtUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Result add(SysUser sysUser) {
        if (sysUser.getUsername() == null || sysUser.getPassword() == null) {
            return Result.failure("登录账号或密码不可为空");
        }
        if (sysUser.getRoleSet() == null || sysUser.getRoleSet().size() == 0) {
            return Result.failure("请分配角色");
        }
        if(userMapper.getUserByUsername(sysUser.getUsername())!=null){
            return Result.failure("该用户名已存在");
        }
        // 实例化一对象用于接收
        SysUser myUser = new SysUser();
        if (sysUser.getNickname() != null && !"".equals(sysUser.getNickname())) {
            myUser.setNickname(sysUser.getNickname());
        }
        if (sysUser.getAvatar() != null && !"".equals(sysUser.getAvatar())) {
            myUser.setAvatar(sysUser.getAvatar());
        }
        myUser.setUsername(sysUser.getUsername());
        myUser.setPassword(sysUser.getPassword());
        myUser.setPhone(sysUser.getPhone());
        myUser.setEmail(sysUser.getEmail());
        if(userMapper.add(myUser)!=1){
            return Result.failure("插入用户失败");
        }
        myUser=userMapper.getUserByUsername(myUser.getUsername());
        try {
            Set<SysRole> sysRoles = sysUser.getRoleSet();
            for (SysRole sysRole : sysRoles) {
                userMapper.addRole(myUser.getId(), sysRole.getRoleId());
            }
        }catch (Exception e){
           log.error(e.toString());
            return Result.failure("分配角色失败");
        }

        return Result.success(sysUser);
    }

    @Override
    public int delete(Long id) {
        return userMapper.delete(id);
    }

    @Override
    public int update(SysUser sysUser) {
        return userMapper.update(sysUser);
    }

    @Override
    public Result queryPage(String username,String role,String nickname,String phone,String email,Integer page, Integer limit) {
        Page<SysUser> userPage = PageHelper.startPage(page, limit).doSelectPage(() -> userMapper.getUserList(username,role,nickname,phone,email));
        System.out.println(userPage);
        Paging<SysUser> userPaging = new Paging<>(userPage);
        return Result.success(userPaging);
    }


    @Override
    public SysUser getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public Set<SysRole> getRoleListById(Long id) {
        return userMapper.getRoleListById(id);
    }

    @Override
    public List<SysGoods> getShoppingCartListById(Long id) {
        return userMapper.getShoppingCartListById(id);
    }

    @Override
    public List<Coupon> getCouponListById(Long id) {
        return userMapper.getCouponListById(id);
    }

    @Override
    public Result setRole(Long userId, HashSet<Long> roleIds) {
        if(roleIds == null ||roleIds.isEmpty()) {
            return Result.failure("分配角色不可为空");
        }
        try {
            userMapper.deleteRole(userId);
            for (Long roleId : roleIds) {
                userMapper.addRole(userId, roleId);
            }
            return Result.success();
        } catch (Exception e) {
            log.error(e.toString());
            return Result.failure(e.toString());
        }
    }

    @Override
    public SysUser getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public Result<SysUser> login(String username, String password) {
        SysUser getSysUser=userMapper.getUserByUsername(username);
        if(getSysUser==null){
            return Result.failure("用户名错误");
        }
        if(!Objects.equals(getSysUser.getPassword(), password)){
            return Result.failure("密码错误");
        }
        return Result.success(getSysUser);
    }

    /**
     * 封装account
     *
     * @return
     */
    @Override
    public Result getUserAccount() {

        try {
            Long userId = JwtUtils.obtainUserId();
            SysUser sysUser = userMapper.getUserById(userId);
            HashSet<String> roles = new HashSet<>();
            HashSet<Long> roleIds = new HashSet<>();
            Map<String, Object> map = new HashMap<>();
            for (SysRole role : sysUser.getRoleSet()) {
                roles.add(role.getRoleName());
                roleIds.add(role.getRoleId());
            }
            map.put("roles", roles);
            List<Menu> menus = menuMapper.queryByRoleIds(roleIds);

            map.put("menus", menus);
            map.put("profile", sysUser);
            return Result.success(map);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.failure(e.toString());
        }

    }

}
