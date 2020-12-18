package com.bokehai.book.service;

import com.bokehai.book.mapper.BookMapper;
import com.bokehai.book.pojo.Book;
import com.bokehai.book.pojo.BookType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    /**
     * 根据名字模糊查找书籍
     * @param searchWord
     * @return
     */
    public List<Book> queryBookByName(String searchWord) {
//        Example example = new Example(Book.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andLike("name","%" + searchWord + "%");
        return bookMapper.searchBookByName(searchWord);
    }

    /**
     * 查找所有书籍
     * @return
     */
    public List<Book> getAllBooks() {
        return bookMapper.selectAllBooks();
    }

    /**
     * 判断某书籍是否存在
     * @param searchWord
     * @return
     */
    public boolean matchBook(String searchWord) {
        Example example = new Example(Book.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", searchWord == null ? "" : searchWord);
        return CollectionUtils.isEmpty(bookMapper.selectByExample(example));
    }

    /**
     * 添加书籍
     * @param book
     * @return
     */
    @Transactional
    public boolean addBook(Book book) {
        return bookMapper.insertBook(book) > 0;
    }

    /**
     * 根据Id查找书籍
     * @param bookId
     * @return
     */
    public Book getBook(Long bookId) {
        return bookMapper.selectByPrimaryKey(bookId);
    }

    /**
     * 更新书籍
     * @param book
     * @return
     */
    @Transactional
    public boolean editBook(Book book) {
        return bookMapper.updateBook(book) > 0;
    }

    /**
     * 删除书籍
     * @param bookId
     * @return
     */
    @Transactional
    public boolean deleteBook(Integer bookId) {
        return bookMapper.deleteByPrimaryKey(bookId) > 0;
    }


    /**
     * 查找所有书籍的分类
     *
     * @return
     */
    public List<BookType> findAllType() {
        return bookMapper.findAllType();
    }
}
