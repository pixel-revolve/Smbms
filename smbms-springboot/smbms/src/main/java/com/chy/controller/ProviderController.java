package com.chy.controller;

import com.chy.model.Result;
import com.chy.pojo.Provider;
import com.chy.service.ProviderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProviderController extends ApiBaseController{
    @Autowired
    private ProviderService providerService;

    @PostMapping("/provider/add")
    public Result add(@RequestBody Provider provider){
        int count = 0;
        try {
            count = providerService.add(provider);
            if(count<=0) return Result.failure("add failure");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(provider);
    }

    @DeleteMapping("/provider/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        int count = 0;
        try {
            count = providerService.delete(id);
            if(count<=0) return Result.failure("delete failure");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(null);
    }

    @PutMapping("/provider/update")
    public Result update(@RequestBody Provider provider){
        int count = 0;
        try {
            count = providerService.update(provider);
            if(count<=0) return Result.failure("update failure");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(provider);
    }

    @GetMapping("/provider/get/{id}")
    public Result<Provider> getProviderById(@PathVariable("id") Long id){
        Provider provider = new Provider();
        try {
            provider = providerService.getProviderById(id);
            if (provider==null) return Result.failure("查询不到结果");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(provider);
    }

    @GetMapping("/provider/queryPage")
    public Result queryPage(@RequestParam(value = "code", required = false) String code,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "desc", required = false) String desc,
                            @RequestParam(value = "phone", required = false) String phone,
                            @RequestParam(value = "address", required = false) String address,
                            @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "limit", required = false) Integer limit) {
        return providerService.queryPage(code,name,desc,phone,address,page, limit);
    }
}
