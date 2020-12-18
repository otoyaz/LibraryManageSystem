package com.bokehai.book.controller;

import com.bokehai.book.common.GraphicHelper;
import com.bokehai.book.common.RedisUtil;
import com.bokehai.book.pojo.ReaderCard;
import com.bokehai.book.pojo.Result;
import com.bokehai.book.service.ReaderCardService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ReaderCardController {

    /**
     * 登录验证码SessionKey
     */
    public static final String LOGIN_VALIDATE_CODE = "login_validate_code";
    @Autowired
    private ReaderCardService readerCardService;
    private Result result;
    @Autowired
    private DefaultKaptcha captchaProducer;

    @GetMapping("/getValidateCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) {

        try {
            GraphicHelper.validateCode(request, response, captchaProducer, LOGIN_VALIDATE_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有借阅卡信息
     * @return
     */
    @GetMapping("/getAllReaderCard")
    public List<ReaderCard> getAllReaderCard() {
        return readerCardService.getAllReaderCard();
    }

    /**
     * 用户注册借阅卡
     * @param readerCard
     * @return
     */
    @PostMapping("/saveReaderCard")
    public Result addReaderCard(ReaderCard readerCard) {
        result = new Result();
        if (readerCard.getReaderId() == null) {
            result.setStatus(406);
            result.setResult("readerId不能为空");
            return result;
        } else if (StringUtils.isNullOrEmpty(readerCard.getUsername())) {
            result.setStatus(406);
            result.setResult("username不能为空");
            return result;
        } else if (StringUtils.isNullOrEmpty(readerCard.getPassword())) {
            result.setStatus(406);
            result.setResult("password不能为空");
            return result;
        }
        readerCard.setDescrible("normal");
        Boolean isAdd = readerCardService.addReaderCard(readerCard);
        if(isAdd) {
            result.setResult("添加成功");
            result.setStatus(200);
        } else {
            result.setResult("添加失败");
            result.setStatus(406);
        }
        return result;
    }

    /**
     * 重置借阅卡的密码
     * @param readerCard
     * @return
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody ReaderCard readerCard){
        result = new Result();
        if (readerCard.getReaderId() == null) {
            result.setStatus(406);
            result.setResult("readerId不能为空");
            return result;
        } else if (StringUtils.isNullOrEmpty(readerCard.getUsername())) {
            result.setStatus(406);
            result.setResult("username不能为空");
            return result;
        } else if (StringUtils.isNullOrEmpty(readerCard.getPassword())) {
            result.setStatus(406);
            result.setResult("password不能为空");
            return result;
        }
        Boolean isUpdate = readerCardService.updatePassword(readerCard);
        if(isUpdate) {
            result.setResult("更新成功");
            result.setStatus(200);
        } else {
            result.setResult("更新失败");
            result.setStatus(406);
        }
        return result;
    }

    /**
     * 删除借阅卡
     * @param readerId
     * @return
     */
    @DeleteMapping("/deleteReaderCard")
    public Result deleteReaderCard(Integer readerId) {
        result = new Result();
        if (readerId == null) {
            result.setStatus(406);
            result.setResult("readerId不能为空");
            return result;
        }
        Boolean isDel = readerCardService.deleteReaderCard(readerId);
        if(isDel) {
            result.setResult("删除成功");
            result.setStatus(200);
        } else {
            result.setResult("删除失败");
            result.setStatus(406);
        }
        return result;
    }


    /**
     * 判断用户名和密码是否相等
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/loginReader")
    public Result hasMatchReader(HttpServletRequest request, String username, String password, String validate) {
        result = new Result();
        if (StringUtils.isNullOrEmpty(username)) {
            result.setStatus(406);
            result.setResult("username不能为空");
            return result;
        } else if (StringUtils.isNullOrEmpty(password)) {
            result.setStatus(406);
            result.setResult("password不能为空");
            return result;
        } else if (StringUtils.isNullOrEmpty(validate)) {
            result.setStatus(406);
            result.setResult("validate不能为空");
            return result;
        }

        String loginValidateCode = request.getSession().getAttribute(LOGIN_VALIDATE_CODE).toString();
        System.out.println(validate);
        System.out.println(loginValidateCode);
        System.out.println(loginValidateCode.equals(validate));
        if (loginValidateCode == null || !loginValidateCode.equals(validate)) {
            result.setResult("验证码无效");
            result.setStatus(406);
            return result;
        }
        ReaderCard readerCard = readerCardService.hasMatchReader(username, password);
        if (readerCard != null) {
            result.setResult("登录成功");
            result.setStatus(200);
            result.setToken(RedisUtil.getJedisFromPool().get(readerCard.getReaderId().toString()));
            result.setAccountName(readerCard.getUsername());
            result.setRole(readerCard.getDescrible());
            result.setUserId(readerCard.getReaderId());
        } else {
            result.setResult("登录失败");
            result.setStatus(406);
        }
        return result;
    }

    /**
     * 登出用户
     *
     * @param readerId
     */
    @PostMapping("/logout")
    public void logout(String readerId) {
        System.out.println(readerId);
        System.out.println(readerId);
        if (StringUtils.isNullOrEmpty(readerId)) {
            return;
        }
        Jedis jedis = RedisUtil.getJedisFromPool();
        jedis.del(readerId);
    }
}
