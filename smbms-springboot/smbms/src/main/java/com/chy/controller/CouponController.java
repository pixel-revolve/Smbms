package com.chy.controller;

import com.chy.model.Result;
import com.chy.pojo.Coupon;
import com.chy.pojo.SysUser;
import com.chy.service.CouponService;
import com.chy.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CouponController extends ApiBaseController{
    @Autowired
    private CouponService couponService;

    @PostMapping("/coupon/add")
    public Result<Coupon> add(@RequestBody Coupon coupon){
        int count = 0;
        try {
            count = couponService.add(coupon);
            if(count<=0) return Result.failure("add failure");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(coupon);
    }

    @DeleteMapping("/coupon/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        int count = 0;
        try {
            count = couponService.delete(id);
            if (count<=0) return Result.failure("delete failure");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(null);
    }

    @PutMapping("/coupon/update")
    public Result<Coupon> update(@RequestBody Coupon coupon){
        int count = 0;

        try {
            count = couponService.update(coupon);
            if(count<=0) return Result.failure("update failure");
            if(count<=0) return Result.failure("update failure");
            if(count<=0) return Result.failure("update failure");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(coupon);
    }

    @GetMapping("/coupon/get/{id}")
    public Result<Coupon> getCouponById(@PathVariable("id") Long id){
        Coupon coupon = new Coupon();
        try {
            coupon = couponService.getCouponById(id);
            if (coupon==null) return Result.failure("查询结果为空");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(coupon);
    }

    @GetMapping("/coupon/queryPage")
    public Result queryPage(@RequestParam(value = "price", required = false) Float price,
                            @RequestParam(value = "userId", required = false) Long userId,
                            @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "limit", required = false) Integer limit) {
        return couponService.queryPage(price,userId,page, limit);
    }
}
