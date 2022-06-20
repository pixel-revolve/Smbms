package com.chy.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author pixel-revolve
 * @date 2022/5/24
 */
@Slf4j
public class CrossFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //此处ip地址为需要访问服务器的ip及端口号
//        response.setHeader("Access-Control-Allow-Origin", "http://10.15.120.205:9528");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers",
                "Access-Control-Allow-Headers," +
                        " Origin,Accept, X-Requested-With," +
                        " Content-Type, Access-Control-Request-Method," +
                        " Access-Control-Request-Headers, Authorization");
        log.debug("设置跨域请求");
        chain.doFilter(servletRequest, servletResponse);

    }


}
