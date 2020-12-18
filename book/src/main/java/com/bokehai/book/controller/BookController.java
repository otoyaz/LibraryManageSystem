package com.bokehai.book.controller;

import com.bokehai.book.pojo.Book;
import com.bokehai.book.pojo.BookType;
import com.bokehai.book.pojo.Result;
import com.bokehai.book.service.BookService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    //返回结果类
    private Result result;

    /**
     * 根据名字模糊查找书籍
     * @param searchWord
     * @return
     */
    @GetMapping("/selectBookByName")
    public List<Book> queryBookByName(String searchWord) {
        return bookService.queryBookByName("%" + searchWord + "%");
    }


    /**
     * 查找所有书籍
     * @return
     */
    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * 判断某书籍是否存在
     * @param searchWord
     * @return
     */
    @GetMapping("/isExistBook")
    public Result matchBook(String searchWord) {
        result = new Result();
        if (StringUtils.isNullOrEmpty(searchWord)) {
            result.setResult("searchWord不能为空");
            result.setStatus(406);
            return result;
        }
        Boolean isMatch = bookService.matchBook(searchWord);
        if(!isMatch) {
            result.setResult("查找到");
            result.setStatus(200);
        } else {
            result.setResult("查找不到");
            result.setStatus(406);
        }
        return result;
    }

    /**
     * 添加书籍
     * @param book
     * @return
     */
    @PostMapping("/saveBook")
    public Result addBook(@RequestBody Book book) {
        System.out.println(book);
        result = new Result();
        if (StringUtils.isNullOrEmpty(book.getName())) {
            result.setResult("name不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getAuthor())) {
            result.setResult("author不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getPublish())) {
            result.setResult("publish不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getIsbn())) {
            result.setResult("Isbn不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getIntroduction())) {
            result.setResult("introduction不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getLanguage())) {
            result.setResult("language不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getPrice().toString())) {
            result.setResult("price不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getPubdate().toString())) {
            result.setResult("pubdate不能为空");
            result.setStatus(406);
            return result;
        } else if (book.getClass_id() == null) {
            result.setResult("getClass_id不能为空");
            result.setStatus(406);
            return result;
        } else if (book.getNumber() == null) {
            result.setResult("number不能为空");
            result.setStatus(406);
            return result;
        }
        boolean isAdd = bookService.addBook(book);
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
     * 根据Id查找书籍
     * @param bookId
     * @return
     */
    @GetMapping("/getBook")
    public Book getBook(Long bookId) {
        return bookService.getBook(bookId);
    }


    /**
     * 更新书籍
     * @param book
     * @return
     */
    @PutMapping("/updateBook")
    public Result editBook(@RequestBody Book book) {
        System.out.println(book);
        result = new Result();
        if (book.getBookId() == null) {
            result.setResult("bookId不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getName())) {
            result.setResult("name不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getAuthor())) {
            result.setResult("author不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getPublish())) {
            result.setResult("publish不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getIsbn())) {
            result.setResult("Isbn不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getIntroduction())) {
            result.setResult("introduction不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getLanguage())) {
            result.setResult("language不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getPrice().toString())) {
            result.setResult("price不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(book.getPubdate().toString())) {
            result.setResult("pubdate不能为空");
            result.setStatus(406);
            return result;
        } else if (book.getClass_id() == null) {
            result.setResult("getClass_id不能为空");
            result.setStatus(406);
            return result;
        } else if (book.getNumber() == null) {
            result.setResult("number不能为空");
            result.setStatus(406);
            return result;
        }
        boolean isSuccess = bookService.editBook(book);
        if(isSuccess) {
            result.setResult("修改成功");
            result.setStatus(200);
        } else {
            result.setResult("修改失败");
            result.setStatus(406);
        }
        return result;
    }

    /**
     * 删除书籍
     * @param bookId
     * @return
     */
    @DeleteMapping("/deleteBook")
    public Result deleteBook(Integer bookId) {
        result = new Result();
        if (bookId == null) {
            result.setResult("bookId不能为空");
            result.setStatus(406);
            return result;
        }
        boolean isDel = bookService.deleteBook(bookId);
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
     * 查找所有书籍的分类
     *
     * @return
     */
    @GetMapping("/findAllType")
    public List<BookType> findAllType() {
        return bookService.findAllType();
    }
}
