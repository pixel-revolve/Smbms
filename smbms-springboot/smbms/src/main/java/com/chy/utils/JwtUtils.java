package com.chy.utils;

import com.alibaba.fastjson.JSON;
import com.chy.pojo.SysUser;
import com.chy.property.JwtProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * jwt工具类
 *
 * @author pixel-revolve
 * @date 2022/05/24
 */
public class JwtUtils {

    /**
     * 生成Jwt
     *
     * @param user
     * @return
     */
    public static String createAccessToken(SysUser user) {

        String token = null;

        // 登陆成功生成JWT
        token = Jwts.builder()
                // 放入用户ID
                .setId(String.valueOf(user.getId()))
                // 用户昵称
                .setSubject(user.getUsername())
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("dyh")
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + JwtProperty.expiration * 1000))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JwtProperty.secret)
                .compact();

        return token;
    }

    public static Claims decryptAccessToken(String tokenHeader) {

        // 截取JWT前缀
        String token = tokenHeader.replace(JwtProperty.tokenPrefix, "");
        // 解析JWT
        return Jwts.parser()
                .setSigningKey(JwtProperty.secret)
                .parseClaimsJws(token)
                .getBody();

    }

    public static Long obtainUserId() {

        String jwt = ServletUtil.getHeader("Authorization");
        Claims claims = JwtUtils.decryptAccessToken(jwt);
        return Long.valueOf(claims.getId());

    }


}



