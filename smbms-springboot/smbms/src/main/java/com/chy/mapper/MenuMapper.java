package com.chy.mapper;

import com.chy.pojo.Menu;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author pixel-revolve
 * @Description
 * @Date: 2022/5/3 9:39
 */
@Component
public class MenuMapper {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 寻找Menu集合下所有数据
     *
     * @return
     */
    public List<Menu> queryAll() {
        return mongoTemplate.findAll(Menu.class);
    }

    public List<Menu> queryByRoleIds(HashSet<Long> roleIds) {
        List<Menu> menus = this.queryAll();
        return this.traverseRoutes(menus, roleIds);

    }

    private List<Menu> traverseRoutes(List<Menu> menus, HashSet<Long> roleIds) {

        return menus.stream().map(menu -> {
            if (!menu.getRoleIds().containsAll(roleIds)) {
                menu.setHidden(false);
            }
            if (menu.getChildren() != null && menu.getChildren().size() != 0) {
                menu.setChildren(traverseRoutes(menu.getChildren(), roleIds));
            }
            return menu;
        }).collect(Collectors.toList());

    }


}
