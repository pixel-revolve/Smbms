package com.chy.mapper;

import com.chy.pojo.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
    public int add(SysRole sysRole);

    public int delete(@Param("id") Long id);

    public int update(SysRole sysRole);

    public List<SysRole> getRoleList(@Param("roleName") String roleName);
}
