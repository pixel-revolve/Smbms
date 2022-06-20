package com.chy.service.impl;

import com.chy.mapper.ProviderMapper;
import com.chy.model.Paging;
import com.chy.model.Result;
import com.chy.pojo.Provider;
import com.chy.pojo.SysUser;
import com.chy.service.ProviderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public int add(Provider provider) {
        return providerMapper.add(provider);
    }

    @Override
    public int delete(Long id) {
        return providerMapper.delete(id);
    }

    @Override
    public int update(Provider provider) {
        return providerMapper.update(provider);
    }

    @Override
    public Provider getProviderById(Long id) {
        return providerMapper.getProviderById(id);
    }

    @Override
    public Result queryPage(String code,String name,String desc,String phone,String address, Integer page, Integer limit) {
        Page<SysUser> providerPage = PageHelper.startPage(page, limit).doSelectPage(() -> providerMapper.getProviderList(code,name,desc,phone,address));
        System.out.println(providerPage);
        Paging<SysUser> providerPaging = new Paging<>(providerPage);
        return Result.success(providerPaging);
    }

}
