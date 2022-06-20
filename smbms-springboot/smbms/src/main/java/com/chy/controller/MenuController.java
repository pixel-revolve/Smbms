package com.chy.controller;

import com.chy.model.Result;
import com.chy.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;


/**
 * @Author pixel-revolve
 * @Description
 * @Date: 2022/5/3 10:24
 */
@RequestMapping("/api/menu")
@RestController
@CrossOrigin
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/queryAll")
    public Result queryAll() {
        return menuService.queryAll();
    }

    @PostMapping("/queryAllByRoleIds")
    public Result queryAllByRoleIds(@RequestParam HashSet<Long> roleIds) {
        return menuService.queryAllByRoleIds(roleIds);
    }


}
