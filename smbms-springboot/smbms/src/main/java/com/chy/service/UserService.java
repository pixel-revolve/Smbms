package com.chy.service;

import com.chy.model.Result;
import com.chy.pojo.Coupon;
import com.chy.pojo.SysGoods;
import com.chy.pojo.SysRole;
import com.chy.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface UserService {
    Result add(SysUser sysUser);

    public int delete(@Param("id") Long id);

    public int update(SysUser sysUser);

    public SysUser getUserById(@Param("id") Long id);

    public Set<SysRole> getRoleListById(@Param("id") Long id);

    public List<SysGoods> getShoppingCartListById(@Param("id") Long id);

    public List<Coupon> getCouponListById(@Param("id") Long id);

    /**
     * 分配角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    Result setRole(Long userId, HashSet<Long> roleIds);


    /**
     * 获取用户用户名
     *
     * @param username 用户名
     * @return {@link SysUser}
     */
    SysUser getUserByUsername(String username);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link SysUser}
     */
    Result<SysUser> login(String username, String password);

    Result getUserAccount();

    Result queryPage(@Param("username") String username,
                     @Param("role")String role,
                     @Param("nickname")String nickname,
                     @Param("phone")String phone,
                     @Param("email")String email,
                     Integer page,
                     Integer limit);


}
