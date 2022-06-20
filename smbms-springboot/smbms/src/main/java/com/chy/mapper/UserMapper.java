package com.chy.mapper;

import com.chy.pojo.Coupon;
import com.chy.pojo.SysGoods;
import com.chy.pojo.SysRole;
import com.chy.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    int add(SysUser sysUser);

    int addRole(Long userId, Long roleId);

    public int delete(@Param("id") Long id);

    public int update(SysUser sysUser);

    public List<SysUser> getUserList(@Param("username") String username,
                                     @Param("role")String role,
                                     @Param("nickname")String nickname,
                                     @Param("phone")String phone,
                                     @Param("email")String email);

    public SysUser getUserById(@Param("id") Long id);

    public SysUser getUserByUsername(@Param("username") String username);

    public Set<SysRole> getRoleListById(@Param("id") Long id);

    public List<SysGoods> getShoppingCartListById(@Param("id") Long id);

    public List<Coupon> getCouponListById(@Param("id") Long id);

    int deleteRole(Long userId);

}
