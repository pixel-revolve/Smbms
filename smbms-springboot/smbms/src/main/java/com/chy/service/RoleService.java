package com.chy.service;

import com.chy.model.Result;
import com.chy.pojo.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    public int add(SysRole sysRole);

    public int delete(@Param("id") Long id);

    public int update(SysRole sysRole);

    Result queryPage(@Param("roleName") String roleName, Integer page, Integer limit);

}
