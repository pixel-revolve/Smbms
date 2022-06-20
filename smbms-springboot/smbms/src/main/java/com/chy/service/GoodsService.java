package com.chy.service;

import com.chy.model.Result;
import com.chy.pojo.SysGoods;
import com.chy.pojo.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsService {
    public int add(SysGoods sysGoods);

    public int delete(@Param("id") Long id);

    public int update(SysGoods sysGoods);

    public SysGoods getGoodsById(@Param("id") Long id);

    Result queryPage(@Param("name") String name ,
                     @Param("brand") String brand,
                     @Param("upperPrice") Integer upperPrice,
                     @Param("lowerPrice") Integer lowerPrice,
                     Integer page,
                     Integer limit);

}
