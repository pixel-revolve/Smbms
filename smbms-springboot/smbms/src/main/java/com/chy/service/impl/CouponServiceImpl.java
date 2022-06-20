package com.chy.service.impl;

import com.chy.mapper.CouponMapper;
import com.chy.model.Paging;
import com.chy.model.Result;
import com.chy.pojo.Coupon;
import com.chy.pojo.SysUser;
import com.chy.service.CouponService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponMapper couponMapper;

    @Override
    public int add(Coupon coupon) {
        return couponMapper.add(coupon);
    }

    @Override
    public int delete(Long id) {
        return couponMapper.delete(id);
    }

    @Override
    public int update(Coupon coupon) {
        return couponMapper.update(coupon);
    }

    @Override
    public Coupon getCouponById(Long id) {
        return couponMapper.getCouponById(id);
    }

    @Override
    public Result queryPage(Float price,Long userId,Integer page, Integer limit) {
        Page<SysUser> couponPage = PageHelper.startPage(page, limit).doSelectPage(() -> couponMapper.getCouponList(price,userId));
        System.out.println(couponPage);
        Paging<SysUser> couponPaging = new Paging<>(couponPage);
        return Result.success(couponPaging);
    }

}
