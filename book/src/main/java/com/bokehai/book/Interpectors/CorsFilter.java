package com.bokehai.book.Interpectors;

import com.bokehai.book.pojo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsFilter implements Filter {

    private String allowOrigin;
    private String allowMethods;
    private String allowCredentials;
    private String allowHeaders;
    private String exposeHeaders;


    private Result result;

    private List<String> list = new ArrayList<String>() {{
        add("/getAllReaderCard");
        add("/saveReaderCard");
        add("/deleteReaderCard");
        add("/getAllreaderInfo");
        add("/deleteReaderInfo");
        add("/getReaderInfo");
        add("/getLendList");
        add("/deleteLend");
        add("/saveBook");
        add("/updateBook");
        add("/deleteBook");
    }};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowOrigin = "http://localhost:8080";
        allowMethods = "GET,POST,PUT,DELETE,OPTIONS";
        allowCredentials = "true";
        allowHeaders = "Authorization,content-type";
        exposeHeaders = "";
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("aa");
        if (!StringUtils.isEmpty(allowOrigin)) {
            if(allowOrigin.equals("*")){
                response.setHeader("Access-Control-Allow-Origin", allowOrigin);
            }else{
                List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));
                if (allowOriginList != null && allowOriginList.size() > 0) {
                    String currentOrigin = request.getHeader("Origin");
                    if (allowOriginList.contains(currentOrigin)) {
                        response.setHeader("Access-Control-Allow-Origin", currentOrigin);
                    }
                }
            }
        }
        System.out.println(888);
        if (!StringUtils.isEmpty(allowMethods)) {
            response.setHeader("Access-Control-Allow-Methods", allowMethods);
        }
        if (!StringUtils.isEmpty(allowCredentials)) {
            response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
        }
        if (!StringUtils.isEmpty(allowHeaders)) {
            response.setHeader("Access-Control-Allow-Headers", allowHeaders);
        }
        if (!StringUtils.isEmpty(exposeHeaders)) {
            response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
        }
        filterChain.doFilter(servletRequest, servletResponse);

//        //获取请求路径
//        String requestPath = request.getServletPath();
//        //把对象转化为json格式
//        ObjectMapper mapper = new ObjectMapper();
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/json; charset=utf-8");
//        result = new Result();
//        //登录 注册 接口 验证码放行
//        if (requestPath.contains("/loginAdmin") || requestPath.contains("/loginReader")
//                || requestPath.contains("/saveReaderInfo")
//                || requestPath.contains("/getValidateCode")
//                || requestPath.contains("/checkLoginValidateCode")) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//
//        String token = request.getHeader("Authorization");
//        if (com.mysql.cj.util.StringUtils.isNullOrEmpty(token)) {
//            System.out.println(444);
//            result.setResult("请携带token");
//            result.setStatus(406);
//            response.getWriter().write(mapper.writeValueAsString(result));
//        }
//
//        String account = JwtUtil.getAccount(token);
//        String role = "";
//        String redisId = "";
//        //获取存储在redis中的对象
//        try {
//            //不是admin对象会抛出异常
//            Admin admin = mapper.readValue(account, Admin.class);
//            role = admin.getDescrible();
//            redisId = admin.getAdminId().toString();
//        } catch (Exception e) {
//            try {
//                ReaderCard readerCard = mapper.readValue(account, ReaderCard.class);
//                role = readerCard.getDescrible();
//                redisId = readerCard.getReaderId().toString();
//            } catch (Exception e1) {
//                return;
//            }
//        }
//
//        String redisToken = RedisUtil.getJedisFromPool().get(redisId);
//        boolean isExpire = JwtUtil.checkToken(token, account);
//
//
//        if (redisToken == null || !token.equals(redisToken) || !isExpire) {
//            result.setResult("token已过期，请重新登录");
//            result.setStatus(406);
//            response.getWriter().write(mapper.writeValueAsString(result));
//            return;
//        }
//
//        // 根据权限访问限制接口访问
//        if ("admin".equals(role)) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else if (requestPath.contains("admin")) {
//            result.setResult("权限不足");
//            result.setStatus(406);
//            response.getWriter().write(mapper.writeValueAsString(result));
//            return;
//        } else if (list.contains(requestPath)) {
//            result.setResult("权限不足");
//            result.setStatus(406);
//            response.getWriter().write(mapper.writeValueAsString(result));
//            return;
//        } else {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }

    }

    @Override
    public void destroy() {

    }
}