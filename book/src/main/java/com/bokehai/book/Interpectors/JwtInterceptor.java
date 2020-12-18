package com.bokehai.book.Interpectors;

import com.bokehai.book.common.JwtUtil;
import com.bokehai.book.common.RedisUtil;
import com.bokehai.book.pojo.Admin;
import com.bokehai.book.pojo.ReaderCard;
import com.bokehai.book.pojo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class JwtInterceptor extends HandlerInterceptorAdapter {

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
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        System.out.println("bb");
        //获取请求路径
        String requestPath = request.getServletPath();
        //把对象转化为json格式
        ObjectMapper mapper = new ObjectMapper();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        result = new Result();
        //登录 注册 接口 验证码放行
        if (requestPath.contains("/loginAdmin") || requestPath.contains("/loginReader")
                || requestPath.contains("/saveReaderInfo")
                || requestPath.contains("/getValidateCode")
                || requestPath.contains("/checkLoginValidateCode")) {
            return true;
        }
        String token = request.getHeader("Authorization");
        String account = JwtUtil.getAccount(token);
        String role = "";
        String redisId = "";
        //获取存储在redis中的对象
        try {
            //不是admin对象会抛出异常
            Admin admin = mapper.readValue(account, Admin.class);
            role = admin.getDescrible();
            redisId = admin.getAdminId().toString();
        } catch (Exception e) {
            try {
                ReaderCard readerCard = mapper.readValue(account, ReaderCard.class);
                role = readerCard.getDescrible();
                redisId = readerCard.getReaderId().toString();
            } catch (Exception e1) {
                result.setResult("请携带token");
                result.setStatus(406);
                response.getWriter().write(mapper.writeValueAsString(result));
                return false;
            }
        }

        String redisToken = RedisUtil.getJedisFromPool().get(redisId);
        boolean isExpire = JwtUtil.checkToken(token, account);


        if (redisToken == null || !token.equals(redisToken) || !isExpire) {
            result.setResult("token已过期，请重新登录");
            result.setStatus(406);
            response.getWriter().write(mapper.writeValueAsString(result));
            return false;
        }

        // 根据权限访问限制接口访问
        if ("admin".equals(role)) {
            return true;
        } else if (requestPath.contains("admin")) {
            result.setResult("权限不足");
            result.setStatus(406);
            response.getWriter().write(mapper.writeValueAsString(result));
            return false;
        } else if (list.contains(requestPath)) {
            result.setResult("权限不足");
            result.setStatus(406);
            response.getWriter().write(mapper.writeValueAsString(result));
            return false;
        } else {
            return true;
        }
    }
}
