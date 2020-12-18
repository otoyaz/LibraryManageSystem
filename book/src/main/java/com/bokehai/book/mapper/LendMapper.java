package com.bokehai.book.mapper;

import com.bokehai.book.pojo.Lend;
import com.bokehai.book.pojo.LendMessage;
import com.bokehai.book.pojo.Rank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LendMapper extends tk.mybatis.mapper.common.Mapper<Lend>{

    @Select(
            "select book.book_id as bookId, book.name as bookName, reader_info.name as readerName,lend_date,back_date from lend,reader_info,book where lend.reader_id=reader_info.reader_id \n"
                    + "and lend.book_id=book.book_id and lend.reader_id = #{readerId} and is_return = 0;")
    List<LendMessage> selectByReaderId(long readerId);


    @Select(
            "select book.name as bookName, reader_info.name as readerName,lend_date,back_date from lend,reader_info,book where lend.reader_id=reader_info.reader_id \n"
                    + "and lend.book_id=book.book_id and is_return = 0;")
    List<LendMessage> selectAllLend();

    @Select(
            "select count(reader_info.reader_id) as count ,reader_info.reader_id, reader_info.name as readerName from lend,reader_info,book where lend.reader_id=reader_info.reader_id \n"
                    + "and lend.book_id=book.book_id group by reader_id;")
    List<Rank> getRank();

    @Update("update lend set is_return = 1 where book_id = #{bookId} and reader_id = #{readerId};")
    int returnBook(Integer bookId, Integer readerId);
}
