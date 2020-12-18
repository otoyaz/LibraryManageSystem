package com.bokehai.book.controller;

import com.bokehai.book.pojo.LendMessage;
import com.bokehai.book.pojo.Rank;
import com.bokehai.book.pojo.Result;
import com.bokehai.book.service.LendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LendController {

  @Autowired private LendService lendService;

  private Result result;


    /**
     * 还书
     * @param bookId
     * @param readerId
     * @return
     */
    @PutMapping("/returnBook")
    public Result returnBook(Integer bookId, Integer readerId) {
        result = new Result();
        if (bookId == null) {
            result.setResult("bookId不能为空");
            result.setStatus(406);
            return result;
        } else if (readerId == null) {
            result.setResult("readerId不能为空");
            result.setStatus(406);
            return result;
        }
        Boolean isReturn = lendService.returnBook(bookId, readerId);
        if(isReturn) {
            result.setResult("还书成功");
            result.setStatus(200);
        } else {
            result.setResult("还书失败");
            result.setStatus(406);
        }
        return result;
    }

    /**
     * 借书
     * @param bookId
     * @param readerId
     * @return
     */
    @PostMapping("/lendBook")
    public Result lendBook(Integer bookId, Integer readerId) {
        result = new Result();
        if (bookId == null) {
            result.setResult("bookId不能为空");
            result.setStatus(406);
            return result;
        } else if (readerId == null) {
            result.setResult("readerId不能为空");
            result.setStatus(406);
            return result;
        }
        Boolean isLend = lendService.lendBook(bookId, readerId);
        if(isLend) {
            result.setResult("借书成功");
            result.setStatus(200);
        } else {
            result.setResult("借书失败");
            result.setStatus(406);
        }
        return result;
    }


  /**
   * 查询所有借阅的书籍
   *
   * @return
   */
  @GetMapping("/getLendList")
  public List<LendMessage> getLendList() {
    return lendService.getLendList();
  }

  /**
   * 获得指定读者借阅的书籍
   *
   * @param readerId
   * @return
   */
  @GetMapping("/getMyLendList")
  public List<LendMessage> getMyLendList(long readerId) {
    return lendService.getMyLendList(readerId);
  }

  /**
   * 删除书籍
   *
   * @param serNum
   * @return
   */
  @DeleteMapping("/deleteLend")
  public Result deleteLend(Integer serNum) {
      result = new Result();
      if (serNum == null) {
          result.setResult("serNum不能为空");
          result.setStatus(406);
          return result;
      }
      Boolean isDel = lendService.deleteLend(serNum);
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
     * 获取排行
     *
     * @return
     */
    @GetMapping("/getRank")
    public List<Rank> getRank() {
        return lendService.getRank();
    }
}
