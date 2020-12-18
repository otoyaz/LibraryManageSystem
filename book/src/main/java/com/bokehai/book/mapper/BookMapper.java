package com.bokehai.book.mapper;

import com.bokehai.book.pojo.Book;
import com.bokehai.book.pojo.BookType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper extends tk.mybatis.mapper.common.Mapper<Book> {

    @Select("select class_id,class_name from class_info;")
    List<BookType> findAllType();

    @Select("select book_id, name, author, publish, ISBN, introduction, language, price, pub_date,number,book.class_id as class_id ,class_name  from book,class_info where book.class_id = class_info.class_id")
    List<Book> selectAllBooks();

    @Insert(
            "insert into book(name, author, publish, ISBN, introduction, language, price, pub_date, class_id, number)\n"
                    + "values(#{name}, #{author}, #{publish}, #{isbn}, #{introduction}, #{language}, #{price}, #{pub_date}, #{class_id}, #{number})")
    int insertBook(Book book);

    @Update(
            "update  book set name = #{name}, author = #{author}, publish =  #{publish}, ISBN = #{isbn}, \n"
                    + "introduction = #{introduction}, language = #{language}, price =  #{price}, pub_date = #{pub_date}, class_id = #{class_id}, \n"
                    + "number = #{number} where book_id = #{book_id}")
    int updateBook(Book book);

    @Select("select * from book where name like #{searchWord}")
    List<Book> searchBookByName(String searchWord);
}
