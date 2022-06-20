package com.chy.service;

import com.chy.model.Result;
import com.chy.pojo.Provider;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProviderService {
    public int add(Provider provider);

    public int delete(@Param("id") Long id);

    public int update(Provider provider);

    public Provider getProviderById(@Param("id") Long id);

    Result queryPage(@Param("code")String code,
                     @Param("name")String name,
                     @Param("desc")String desc,
                     @Param("phone")String phone,
                     @Param("address")String address,
                     Integer page, Integer limit);

}
