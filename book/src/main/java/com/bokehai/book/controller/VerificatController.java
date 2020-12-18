package com.bokehai.book.controller;

import com.bokehai.book.common.GraphicHelper;
import com.bokehai.book.pojo.Result;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class VerificatController {

    /**
     * 登录验证码SessionKey
     */
    public static final String LOGIN_VALIDATE_CODE = "login_validate_code";


    private Result result;

    @Autowired
    private DefaultKaptcha captchaProducer;

    //    @GetMapping("/getValidateCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) {

        try {
            GraphicHelper.validateCode(request,response,captchaProducer,LOGIN_VALIDATE_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查验证码是否正确
     */
//    @RequestMapping("/checkLoginValidateCode")
    public Result checkLoginValidateCode(HttpServletRequest request, @RequestParam("validateCode")String validateCode) {
        String loginValidateCode = request.getSession().getAttribute(LOGIN_VALIDATE_CODE).toString();
        result = new Result();
        HashMap<String,Object> map = new HashMap<String,Object>();
        if(loginValidateCode == null){
            result.setResult("验证码过期");
            result.setStatus(406);
        }else if(loginValidateCode.equals(validateCode)){
            result.setResult("验证码正确");
            result.setStatus(200);
        }else if(!loginValidateCode.equals(validateCode)){
            map.put("status",false);//验证码不正确
            result.setResult("验证码不正确");
            result.setStatus(406);
        }
        return result;
    }
}
