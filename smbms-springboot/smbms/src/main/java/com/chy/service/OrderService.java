package com.chy.service;

import com.chy.model.Result;
import com.chy.pojo.SysOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderService {
    public int add(SysOrder sysOrder);

    public int delete(@Param("id") Long id);

    public int update(SysOrder sysOrder);

    public SysOrder getOrderById(@Param("id") Long id);

    Result queryPage(@Param("userId") Long userId,
                     @Param("goodsId") Long goodsId,
                     @Param("orderStartDate") String orderStartDate,
                     @Param("orderEndDate") String orderEndDate,
                     @Param("startDateNeeded") String startDateNeeded,
                     @Param("endDateNeeded") String endDateNeeded,
                     @Param("payMethod") String payMethod,
                     @Param("deliverMethod") String deliverMethod,
                     Integer page,
                     Integer limit);
}
