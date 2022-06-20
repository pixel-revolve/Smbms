package com.chy.service;

import com.chy.model.Result;
import com.chy.pojo.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponService {
    public int add(Coupon coupon);

    public int delete(@Param("id") Long id);

    public int update(Coupon coupon);

    public Coupon getCouponById(@Param("id") Long id);

    Result queryPage(@Param("price")Float price,@Param("userId")Long userId,Integer page, Integer limit);

}
