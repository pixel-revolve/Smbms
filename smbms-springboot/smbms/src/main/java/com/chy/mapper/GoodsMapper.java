package com.chy.mapper;

import com.chy.pojo.SysGoods;
import com.chy.pojo.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper {
    public int add(SysGoods sysGoods);

    public int delete(@Param("id") Long id);

    public int update(SysGoods sysGoods);

    public List<SysRole> getGoodsList(@Param("name") String name,
                                      @Param("brand") String brand,
                                      @Param("upperPrice") Integer upperPrice,
                                      @Param("lowerPrice") Integer lowerPrice);

    public SysGoods getGoodsById(@Param("id") Long id);
}
