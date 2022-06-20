package com.chy.controller;

import com.chy.model.Result;
import com.chy.pojo.SysGoods;
import com.chy.pojo.SysOrder;
import com.chy.pojo.SysRole;
import com.chy.service.OrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import com.chy.pojo.SysOrder;
import com.chy.service.OrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class OrderController extends ApiBaseController{
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/add")
    public Result add(@RequestBody SysOrder sysOrder){
        int count = 0;
        try {
            count = orderService.add(sysOrder);
            if(count<=0) return Result.failure("add failure");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(sysOrder);
    }

    @DeleteMapping("/order/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        int count = 0;
        try {
            count = orderService.delete(id);
            if(count<=0) return Result.failure("delete failure");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(null);
    }

    @PutMapping("/order/update")
    public Result update(@RequestBody SysOrder sysOrder){
        int count = 0;
        try {
            count = orderService.update(sysOrder);
            if(count<=0) return Result.failure("update failure");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(sysOrder);
    }

    @GetMapping("/order/get/{id}")
    public Result<SysOrder> getOrderById(@PathVariable("id") Long id) {
        SysOrder sysOrder = new SysOrder();
        try {
            sysOrder = orderService.getOrderById(id);
            if (sysOrder == null) return Result.failure("查询不到结果");
        } catch (Exception e) {
            Result.failure(e.toString());
        }
        return Result.success(sysOrder);
    }

    @GetMapping("/order/queryPage")
    public Result queryPage(@RequestParam(value = "userId", required = false) Long userId,
                            @RequestParam(value = "goodsId", required = false) Long goodsId,
                            @RequestParam(value = "orderStartDate", required = false) String orderStartDate,
                            @RequestParam(value = "orderEndDate", required = false) String orderEndDate,
                            @RequestParam(value = "startDateNeeded", required = false) String startDateNeeded,
                            @RequestParam(value = "endDateNeeded", required = false) String endDateNeeded,
                            @RequestParam(value = "payMethod", required = false) String payMethod,
                            @RequestParam(value = "deliverMethod", required = false) String deliverMethod,
                            @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "limit", required = false) Integer limit) {
        System.out.println("orderStartDate:"+orderStartDate);
        System.out.println("endDateNeeded:"+endDateNeeded);
        return orderService.queryPage(userId, goodsId, orderStartDate, orderEndDate, startDateNeeded, endDateNeeded, payMethod, deliverMethod, page, limit);
    }
}
