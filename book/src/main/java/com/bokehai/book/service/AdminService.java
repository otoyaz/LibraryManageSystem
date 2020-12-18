package com.bokehai.book.service;

import com.bokehai.book.common.JwtUtil;
import com.bokehai.book.common.Md5Util;
import com.bokehai.book.common.RedisUtil;
import com.bokehai.book.mapper.AdminMapper;
import com.bokehai.book.pojo.Admin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 获取管理员的名字
     * @param adminId
     * @return
     */
    public Admin getAdminUsername(long adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }


    /**
     * 判断用户名和密码是否相等
     * @param username
     * @param password
     * @return
     */
    public Admin hasMatchAdmin(String username, String password) {
        Example example = new Example(Admin.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        try {
            criteria.andEqualTo("password",Md5Util.getMd5(password));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        List<Admin> list = adminMapper.selectByExample(example);
        if(list.size() > 0) {
            //生成token存储在redis中
            Jedis jedis = RedisUtil.getJedisFromPool();
            //根据角色创建token
            String token = null;
            try {
                token = JwtUtil.createToken(new ObjectMapper().writeValueAsString(list.get(0)));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
            //存储用户名和token在redis中
            jedis.set(list.get(0).getAdminId().toString(), token);
            return list.get(0);
        } else {
            return null;
        }

    }

    /**
     * 重置管理员密码
     * @param adminId
     * @param newPassword
     * @return
     */
    @Transactional
    public boolean resetAdminPassword(Integer adminId, String newPassword,String username){
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setDescrible("admin");
        try {
            admin.setPassword(Md5Util.getMd5(newPassword));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        admin.setUsername(username);
        return adminMapper.updateByPrimaryKey(admin) > 0;
    }

    /**
     * 添加管理员账号
     * @param admin
     * @return
     */
    @Transactional
    public boolean addAdmin(Admin admin) {
        //对密码使用md5加密
        try {
            admin.setPassword(Md5Util.getMd5(admin.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return adminMapper.insert(admin) > 0;
    }
}
