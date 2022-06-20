package com.chy.intercepter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chy.pojo.SysUser;
import com.chy.property.JwtProperty;
import com.chy.service.UserService;
import com.chy.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pixel-revolve
 * @date 2022/5/24
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String,Object> map=new HashMap<>();

        // 获取请求头中JWT的Token
        String tokenHeader = request.getHeader(JwtProperty.tokenHeader);
        try {
            if (tokenHeader == null) {
                // 从cookie中取
                tokenHeader = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("jwt")).collect(Collectors.toList()).get(0).getValue();
            }
        } catch (Exception e) {
            log.debug("Token为空");
            map.put("msg","Token为空");
        }

        // 判断是否为空或是否以我们设定的前缀开头
        if (tokenHeader != null && tokenHeader.startsWith(JwtProperty.tokenPrefix)) {
            try {
                // 解析JWT
                Claims claims = JwtUtils.decryptAccessToken(tokenHeader);
                // 获取用户名
                String username = claims.getSubject();
                String userId = claims.getId();
                SysUser getUser = userService.getUserById(Long.parseLong(userId));
                if(getUser!=null){
                    return true;
                }else{
                    map.put("msg","Token无效");
                }
            } catch (ExpiredJwtException e) {
                log.error("Token过期");
                map.put("msg","Token过期");
            } catch (Exception e) {
                log.error("Token无效");
                map.put("msg","Token无效");
            }
        }

        // 设定响应内容是utf-8编码的json类型
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        // 序列化结果对象为JSON
        String resultJSON = JSON.toJSONString(map);

        response.getWriter().println(resultJSON);
        return false;
    }
}
