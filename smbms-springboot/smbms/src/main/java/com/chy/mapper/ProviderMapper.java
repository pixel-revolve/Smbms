package com.chy.mapper;

import com.chy.pojo.Provider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProviderMapper {
    public int add(Provider provider);

    public int delete(@Param("id") Long id);

    public int update(Provider provider);

    public List<Provider> getProviderList(@Param("code")String code,
                                          @Param("name")String name,
                                          @Param("desc")String desc,
                                          @Param("phone")String phone,
                                          @Param("address")String address);

    public Provider getProviderById(@Param("id") Long id);
}
