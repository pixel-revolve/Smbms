package com.chy.service;

import com.chy.model.Result;
import com.chy.pojo.Menu;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * @Author pixel-revolve
 * @Description Menu业务接口
 * @Date: 2022/5/3 10:15
 */
@Service
public interface MenuService {

    /**
     * 返回所有Menu集合
     *
     * @return
     */
    Result<List<Menu>> queryAll();

    /**
     * 根据角色返回符合的Menu集合
     *
     * @param roleIds
     * @return
     */
    Result<List<Menu>> queryAllByRoleIds(HashSet<Long> roleIds);

}
