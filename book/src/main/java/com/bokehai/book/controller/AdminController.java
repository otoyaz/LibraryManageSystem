package com.bokehai.book.controller;


import com.bokehai.book.common.RedisUtil;
import com.bokehai.book.pojo.Admin;
import com.bokehai.book.pojo.Result;
import com.bokehai.book.service.AdminService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    private Result result;

    /**
     * 获取管理员的名字
     * @param adminId
     * @return
     */
    @GetMapping("/getAdminUsername")
    public Result getAdminUsername(Integer adminId) {
        result = new Result();
        if (adminId == null) {
            result.setResult("adminId不能为空");
            result.setStatus(406);
            return result;
        }
        Admin admin = adminService.getAdminUsername(adminId);
        if(admin == null) {
            result.setResult("不存在管理员");
            result.setStatus(406);
        } else {
            result.setResult(admin.getUsername());
            result.setStatus(200);
        }
        return result;
    }

    /**
     * 判断用户名和密码是否相等
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/loginAdmin")
    public Result hasMatchAdmin(String username, String password) {
        result = new Result();
        if (StringUtils.isNullOrEmpty(username)) {
            result.setResult("username不能为空");
            result.setStatus(406);
            return result;

        } else if (StringUtils.isNullOrEmpty(password)) {
            result.setResult("password不能为空");
            result.setStatus(406);
            return result;
        }
        Admin admin = adminService.hasMatchAdmin(username, password);
        if (admin != null) {
            result.setResult("登录成功");
            result.setToken(RedisUtil.getJedisFromPool().get(admin.getAdminId().toString()));
            result.setAccountName(admin.getUsername());
            result.setRole("admin");
            result.setStatus(200);
            result.setUserId(admin.getAdminId());
        } else {
            result.setResult("登录失败");
            result.setStatus(406);

        }
        return result;
    }

    /**
     * 重置管理员密码
     * @param adminId
     * @param newPassword
     * @return
     */
    @PutMapping("/updateAdminPassword")
    public Result resetAdminPassword(Integer adminId, String newPassword, String username) {
        result = new Result();
        if (null == adminId) {
            result.setResult("adminId不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(newPassword)) {
            result.setResult("newPassword不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(username)) {
            result.setResult("username不能为空");
            result.setStatus(406);
            return result;
        }
        Boolean isUpdate = adminService.resetAdminPassword(adminId,newPassword,username);
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
     * 添加管理员账号
     * @param admin
     * @return
     */

    @PostMapping("/saveAdmin")
    public Result addAdmin(@RequestBody Admin admin) {
        result = new Result();
        if (StringUtils.isNullOrEmpty(admin.getUsername())) {
            result.setResult("username不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(admin.getPassword())) {
            result.setResult("password不能为空");
            result.setStatus(406);
            return result;
        }
        admin.setDescrible("admin");
        Boolean isAdd = adminService.addAdmin(admin);
        if(isAdd) {
            result.setResult("添加成功");
            result.setStatus(200);
        } else {
            result.setResult("添加失败");
            result.setStatus(406);
        }
        return result;
    }
}
