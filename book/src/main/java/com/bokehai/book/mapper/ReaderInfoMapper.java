package com.bokehai.book.mapper;

import com.bokehai.book.pojo.ReaderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReaderInfoMapper extends tk.mybatis.mapper.common.Mapper<ReaderInfo> {

    /**
     * 重写insert接口方法获取自增id
     *
     * @param readerInfo
     * @return
     */
    @Insert("insert into reader_info(name,account_name,sex,birth,address,phone) values(#{name},#{accountName},#{sex},#{birth},#{address},#{phone})")
    @Options(useGeneratedKeys = true, keyProperty = "reader_id", keyColumn = "reader_id")
    int insert(ReaderInfo readerInfo);
}
