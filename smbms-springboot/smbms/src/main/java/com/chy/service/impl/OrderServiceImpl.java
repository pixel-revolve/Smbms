package com.chy.service.impl;

import com.chy.mapper.OrderMapper;
import com.chy.model.Paging;
import com.chy.model.Result;
import com.chy.pojo.SysOrder;
import com.chy.pojo.SysUser;
import com.chy.service.OrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int add(SysOrder sysOrder) {
        return orderMapper.add(sysOrder);
    }

    @Override
    public int delete(Long id) {
        return orderMapper.delete(id);
    }

    @Override
    public int update(SysOrder sysOrder) {
        return orderMapper.update(sysOrder);
    }

    @Override
    public SysOrder getOrderById(Long id) {
        return orderMapper.getOrderById(id);
    }

    @Override
    public Result queryPage(Long userId,Long goodsId,String orderStartDate,String orderEndDate,String startDateNeeded,String endDateNeeded,String payMethod,String deliverMethod,Integer page, Integer limit) {
        Page<SysUser> orderPage = PageHelper.startPage(page, limit).doSelectPage(() -> {
            orderMapper.getOrderList(userId, goodsId, orderStartDate, orderEndDate, startDateNeeded, endDateNeeded, payMethod, deliverMethod);
        });
        System.out.println(orderPage);
        Paging<SysUser> orderPaging = new Paging<>(orderPage);
        return Result.success(orderPaging);
/*
        SimpleDateFormat sf = null;
        try {
            sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date orderStartDate_date = sf.parse("1999-01-01 00:00:00");
            Date orderEndDate_date = sf.parse("2999-01-01 00:00:00");
            Date startDateNeeded_date = sf.parse("1999-01-01 00:00:00");
            Date endDateNeeded_date = sf.parse("2999-01-01 00:00:00");
            if (orderStartDate!=null&&orderStartDate!="") {
                orderStartDate_date = sf.parse(orderStartDate);
            }
            if (orderStartDate!=null&&orderStartDate!="") {
                orderEndDate_date = sf.parse(orderEndDate);
            }
            if (orderStartDate!=null&&orderStartDate!="") {
                startDateNeeded_date = sf.parse(startDateNeeded);
            }
            if (orderStartDate!=null&&orderStartDate!="") {
                endDateNeeded_date = sf.parse(endDateNeeded);
            }
            Date finalOrderStartDate_date = orderStartDate_date;
            Date finalOrderEndDate_date = orderEndDate_date;
            Date finalStartDateNeeded_date = startDateNeeded_date;
            Date finalEndDateNeeded_date = endDateNeeded_date;
            Page<SysUser> orderPage = PageHelper.startPage(page, limit).doSelectPage(() -> {
                orderMapper.getOrderList(userId, goodsId, finalOrderStartDate_date, finalOrderEndDate_date, finalStartDateNeeded_date, finalEndDateNeeded_date, payMethod, deliverMethod);
            });
            System.out.println(orderPage);
            Paging<SysUser> orderPaging = new Paging<>(orderPage);
            return Result.success(orderPaging);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.failure("error");
        */
    }

}
