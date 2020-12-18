package com.bokehai.book.mapper;

import com.bokehai.book.pojo.ReaderCard;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReaderCardMapper extends tk.mybatis.mapper.common.Mapper<ReaderCard> {
}
