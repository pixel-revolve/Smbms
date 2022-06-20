package com.chy.controller;

import com.chy.model.Result;
import com.chy.pojo.Provider;
import com.chy.pojo.SysRole;
import com.chy.service.RoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleList;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RoleController extends ApiBaseController{
    @Autowired
    private RoleService roleService;

    @PostMapping("/role/add")
    public Result add(@RequestBody SysRole sysRole){
        int count = 0;
        try {
            count = roleService.add(sysRole);
            if(count<=0) return Result.failure("add failure");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(sysRole);
    }

    @DeleteMapping("/role/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        int count = 0;
        try {
            count = roleService.delete(id);
            if(count<=0) return Result.failure("delete failure");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(null);
    }

    @PutMapping("/role/update")
    public Result update(@RequestBody SysRole sysRole){
        int count = 0;
        try {
            count = roleService.update(sysRole);
            if(count<=0) return Result.failure("update failure");
        }catch (Exception e){
            return Result.failure(e.toString());
        }
        return Result.success(sysRole);
    }

    @GetMapping("/role/queryPage")
    public Result queryPage(@RequestParam(value = "roleName", required = false) String roleName,
                            @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "limit", required = false) Integer limit) {
        return roleService.queryPage(roleName,page, limit);
    }

}
