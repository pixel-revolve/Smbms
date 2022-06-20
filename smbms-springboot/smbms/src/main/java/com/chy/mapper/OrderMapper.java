package com.chy.mapper;

import com.chy.pojo.SysGoods;
import com.chy.pojo.SysOrder;
import com.chy.pojo.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {
    public int add(SysOrder sysOrder);

    public int delete(@Param("id") Long id);

    public int update(SysOrder sysOrder);

    public List<SysOrder> getOrderList(@Param("userId") Long userId,
                                       @Param("goodsId") Long goodsId,
                                       @Param("orderStartDate") String orderStartDate,
                                       @Param("orderEndDate") String orderEndDate,
                                       @Param("startDateNeeded") String startDateNeeded,
                                       @Param("endDateNeeded") String endDateNeeded,
                                       @Param("payMethod") String payMethod,
                                       @Param("deliverMethod") String deliverMethod);

    public SysOrder getOrderById(@Param("id") Long id);
}
