package com.chy.service.impl;

import com.chy.mapper.RoleMapper;
import com.chy.model.Paging;
import com.chy.model.Result;
import com.chy.pojo.SysRole;
import com.chy.pojo.SysUser;
import com.chy.service.RoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public int add(SysRole sysRole) {
        return roleMapper.add(sysRole);
    }

    @Override
    public int delete(Long id) {
        return roleMapper.delete(id);
    }

    @Override
    public int update(SysRole sysRole) {
        return roleMapper.update(sysRole);
    }

    @Override
    public Result queryPage(String roleName,Integer page, Integer limit) {
        Page<SysUser> rolePage = PageHelper.startPage(page, limit).doSelectPage(() -> roleMapper.getRoleList(roleName));
        System.out.println(rolePage);
        Paging<SysUser> rolePaging = new Paging<>(rolePage);
        return Result.success(rolePaging);
    }


}
