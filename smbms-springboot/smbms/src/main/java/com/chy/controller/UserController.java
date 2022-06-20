package com.chy.controller;

import com.chy.model.Result;
import com.chy.pojo.Coupon;
import com.chy.pojo.SysGoods;
import com.chy.pojo.SysRole;
import com.chy.pojo.SysUser;
import com.chy.property.JwtProperty;
import com.chy.service.UserService;
import com.chy.utils.JwtUtils;
import com.chy.utils.MapUtil;
import com.chy.utils.ServletUtil;
import org.nutz.mapl.Mapl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class UserController extends ApiBaseController{
    @Autowired
    private UserService userService;

    @GetMapping("/user/queryPage")
    public Result queryPage(@RequestParam(value = "username", required = false) String username,
                           @RequestParam(value = "role", required = false) String role,
                           @RequestParam(value = "nickname", required = false) String nickname,
                           @RequestParam(value = "phone", required = false) String phone,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "limit", required = false) Integer limit) {
        System.out.println("role"+role);
        return userService.queryPage(username,role,nickname,phone,email,page, limit);
    }


    @PostMapping("/user/add")
    public Result add(@RequestBody SysUser sysUser){
        return userService.add(sysUser);
    }

    @DeleteMapping("/user/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        int count = 0;
        try {
            count = userService.delete(id);
            if (count<=0) {
                return Result.failure("delete failure");
            }
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(null);
    }

    @PutMapping("/user/update")
    public Result update(@RequestBody SysUser sysUser){
        int count = 0;
        try {
            count = userService.update(sysUser);
            if (count<=0) {
                return Result.failure("update failure");
            }
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(sysUser);
    }

    @GetMapping("/user/get/{id}")
    public Result<SysUser> getUserById(@PathVariable("id") Long id){
        SysUser sysUser = new SysUser();
        try {
            sysUser = userService.getUserById(id);
            if(sysUser==null) {
                return Result.failure("查询不到用户");
            }
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(sysUser);
    }

    @GetMapping("/user/getUserByUsername/{username}")
    public Result<SysUser> getUserById(@PathVariable("username") String username){
        SysUser sysUser = new SysUser();
        try {
            sysUser = userService.getUserByUsername(username);
            if(sysUser==null) {
                return Result.failure("查询不到用户");
            }
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(sysUser);
    }

    @GetMapping("/user/getRole/{id}")
    public Result<Set<SysRole>> getRoleListById(@PathVariable("id") Long id){
        Set<SysRole> roleSet = new HashSet<>();
        try {
            roleSet = userService.getRoleListById(id);
            if (roleSet.isEmpty()) {
                return Result.failure("查询不到角色");
            }
        } catch (Exception e) {
            return Result.failure(e.toString());
        }
        return Result.success(roleSet);
    }

    @GetMapping("/user/getShoppingCart/{id}")
    public Result<List<SysGoods>> getShoppingCartListById(@PathVariable("id") Long id){
        List<SysGoods> sysGoodsList = new ArrayList<>();
        try {
            sysGoodsList = userService.getShoppingCartListById(id);
            if (sysGoodsList.isEmpty()) {
                return Result.failure("查询不到购物车");
            }
        } catch (Exception e) {
            return Result.failure(e.toString());
        }
        return Result.success(sysGoodsList);
    }

    @GetMapping("/user/getCoupon/{id}")
    public Result<List<Coupon>> getCouponListById(@PathVariable("id") Long id){
        List<Coupon> couponList = new ArrayList<>();
        try {
            couponList = userService.getCouponListById(id);
            if (couponList.isEmpty()) {
                return Result.failure("查询不到优惠券");
            }
        } catch (Exception e) {
            return Result.failure(e.toString());
        }
        return Result.success(couponList);
    }


    @PostMapping("/user/login")
    public Result login(@RequestParam String username,@RequestParam String password){

        SysUser user = userService.login(username, password).getData();
        if(user!=null) {
            if(user.getRoleSet()==null){
                return Result.failure("该用户未配置权限");
            }

            // 创建一token
            String token = JwtProperty.tokenPrefix + JwtUtils.createAccessToken(user);
            //封装返回体
            Map<String, Object> resData = new HashMap<>();
            resData.put("token", token);

            return Result.success(resData);
        }
        return Result.failure("用户名或密码错误");
    }

    /**
     * 根据token获取用户id，如果不存在则抛出异常
     *
     * @param request
     * @return
     */
    public Long getIdUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        Long idUser = JwtUtils.obtainUserId();
        if (idUser == null) {
            throw new RuntimeException("用户不存在");
        }
        return idUser;
    }


    @GetMapping("/user/account")
    public Result getAccount() {
        return userService.getUserAccount();
    }

    @PostMapping("/user/setRole")
    public Result setRole(@RequestParam Long userId, @RequestParam HashSet<Long> roleIds) {
        return userService.setRole(userId, roleIds);
    }


    /**
     * 获取通知列表
     */
    @GetMapping(value = "/notice/list")
    public Object list(String condition) {
        return Result.success();
    }


}
