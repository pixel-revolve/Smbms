package com.chy.controller;

import com.chy.model.Result;
import com.chy.pojo.SysGoods;
import com.chy.pojo.SysRole;
import com.chy.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import com.chy.pojo.SysGoods;
import com.chy.pojo.SysRole;
import com.chy.service.GoodsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoodsController extends ApiBaseController{
    @Autowired
    private GoodsService goodsService;

    @PostMapping("/goods/add")
    public Result add(@RequestBody SysGoods sysGoods){
        int count = 0;
        try {
            count = goodsService.add(sysGoods);
            if(count<=0) {
                return Result.failure("add failure");
            }
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(sysGoods);
    }

    @DeleteMapping("/goods/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        int count = 0;
        try {
            count = goodsService.delete(id);
            if(count<=0) {
                return Result.failure("delete failure");
            }
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(null);
    }

    @PutMapping("/goods/update")
    public Result update(@RequestBody SysGoods sysGoods){
        int count = 0;
        try {
            count = goodsService.update(sysGoods);
            if(count<=0) {
                return Result.failure("update failure");
            }
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(sysGoods);
    }

    @GetMapping("/goods/get/{id}")
    public Result<SysGoods> getGoodsById(@PathVariable("id") Long id) {
        SysGoods sysGoods = new SysGoods();
        try {
            sysGoods = goodsService.getGoodsById(id);
            if (sysGoods == null) {
                return Result.failure("查询不到结果");
            }
        } catch (Exception e) {
            Result.failure(e.toString());
        }
        return Result.success(sysGoods);
    }

    @GetMapping("/goods/queryPage")
    public Result queryPage(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "brand", required = false) String brand,
                            @RequestParam(value = "upperPrice", required = false) Integer upperPrice,
                            @RequestParam(value = "lowerPrice", required = false) Integer lowerPrice,
                            @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "limit", required = false) Integer limit) {
        return goodsService.queryPage(name,brand,upperPrice,lowerPrice,page, limit);
    }
}
