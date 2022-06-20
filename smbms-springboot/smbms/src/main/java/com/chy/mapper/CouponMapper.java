package com.chy.mapper;

import com.chy.pojo.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CouponMapper {
    public int add(Coupon coupon);

    public int delete(@Param("id") Long id);

    public int update(Coupon coupon);

    public List<Coupon> getCouponList(@Param("price")Float price,@Param("userId")Long userId);

    public Coupon getCouponById(@Param("id") Long id);
}
