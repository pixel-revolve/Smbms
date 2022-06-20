package com.chy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("SysUser")
public class SysUser implements Serializable {
    Long id;
    String username;
    String password;
    String nickname;
    String phone;
    String email;
    String avatar;

    Set<SysRole> roleSet;
    List<SysGoods> shoppingCart;
    List<Coupon> myCoupons;
}
