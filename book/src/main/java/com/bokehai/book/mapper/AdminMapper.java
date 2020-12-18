package com.bokehai.book.mapper;

import com.bokehai.book.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
//欺骗Spring容器，以为这个接口也是他管理的
@Repository
public interface AdminMapper extends tk.mybatis.mapper.common.Mapper<Admin> {

}
