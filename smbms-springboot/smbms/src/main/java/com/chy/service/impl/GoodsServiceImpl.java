package com.chy.service.impl;

import com.chy.mapper.GoodsMapper;
import com.chy.model.Paging;
import com.chy.model.Result;
import com.chy.pojo.SysGoods;
import com.chy.pojo.SysRole;
import com.chy.pojo.SysUser;
import com.chy.service.GoodsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public int add(SysGoods sysGoods) {
        return goodsMapper.add(sysGoods);
    }

    @Override
    public int delete(Long id) {
        return goodsMapper.delete(id);
    }

    @Override
    public int update(SysGoods sysGoods) {
        return goodsMapper.update(sysGoods);
    }

    @Override
    public SysGoods getGoodsById(Long id) {
        return goodsMapper.getGoodsById(id);
    }

    @Override
    public Result queryPage(String name,String brand,Integer upperPrice,Integer lowerPrice,Integer page, Integer limit) {
        Page<SysUser> goodsPage = PageHelper.startPage(page, limit).doSelectPage(() -> goodsMapper.getGoodsList(name,brand,upperPrice,lowerPrice));
        System.out.println(goodsPage);
        Paging<SysUser> goodsPaging = new Paging<>(goodsPage);
        return Result.success(goodsPaging);
    }

}
