package com.bokehai.book.service;

import com.bokehai.book.common.JwtUtil;
import com.bokehai.book.common.Md5Util;
import com.bokehai.book.common.RedisUtil;
import com.bokehai.book.mapper.ReaderCardMapper;
import com.bokehai.book.pojo.ReaderCard;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ReaderCardService {

    @Autowired
    private ReaderCardMapper readerCardMapper;


    /**
     * 获取所有借阅卡信息
     * @return
     */
    public List<ReaderCard> getAllReaderCard() {
        return readerCardMapper.selectAll();
    }

    /**
     * 用户注册借阅卡
     * @param readerCard
     * @return
     */
    @Transactional
    public boolean addReaderCard(ReaderCard readerCard){
        try {
            readerCard.setPassword(Md5Util.getMd5(readerCard.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return  readerCardMapper.insert(readerCard)>0;
    }

    /**
     * 重置借阅卡的密码
     * @param readerCard
     * @return
     */
    @Transactional
    public boolean updatePassword(ReaderCard readerCard){
        readerCard.setDescrible("normal");
        try {
            readerCard.setPassword(Md5Util.getMd5(readerCard.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return readerCardMapper.updateByPrimaryKey(readerCard) > 0;
    }

    /**
     * 删除借阅卡
     * @param readerId
     * @return
     */
    @Transactional
    public boolean deleteReaderCard(Integer readerId) {
        return readerCardMapper.deleteByPrimaryKey(readerId) > 0;
    }

    /**
     * 判断用户名和密码是否相等
     * @param username
     * @param password
     * @return
     */
    public ReaderCard hasMatchReader(String username, String password) {
        Example example = new Example(ReaderCard.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        try {
            criteria.andEqualTo("password",Md5Util.getMd5(password));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        List<ReaderCard> list = readerCardMapper.selectByExample(example);
        if(list.size() > 0) {
            //生成token存储在redis中
            Jedis jedis = RedisUtil.getJedisFromPool();
            //根据用户名创建token
            String token = null;
            try {
                token = JwtUtil.createToken(new ObjectMapper().writeValueAsString(list.get(0)));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
            //存储用户名和token在redis中
            jedis.set(list.get(0).getReaderId().toString(), token);
            return list.get(0);
        } else {
            return null;
        }
    }
}
