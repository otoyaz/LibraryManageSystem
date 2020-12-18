package com.bokehai.book.service;

import com.bokehai.book.mapper.LendMapper;
import com.bokehai.book.pojo.Lend;
import com.bokehai.book.pojo.LendMessage;
import com.bokehai.book.pojo.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class LendService {

    @Autowired
    private LendMapper lendMapper;

    /**
     * 还书
     * @param bookId
     * @param readerId
     * @return
     */
    public boolean returnBook(Integer bookId,Integer readerId){

        return lendMapper.returnBook(bookId, readerId) > 0;
    }

    /**
     * 借书
     * @param bookId
     * @param readerId
     * @return
     */
    @Transactional
    public boolean lendBook(Integer bookId,Integer readerId){
        Lend lend = new Lend();
        lend.setBook_id(bookId);
        lend.setReader_id(readerId);
        Long time = new Date().getTime() + 259200000;

        lend.setLend_date(new Date());
        lend.setBack_date(new Date(time));
        return lendMapper.insert(lend) > 0;
    }

    /**
     * 查询所有借阅的书籍
     * @return
     */
    public List<LendMessage> getLendList() {
        return lendMapper.selectAllLend();
    }

    /**
     * 获得指定读者借阅的书籍
     * @param readerId
     * @return
     */
    public List<LendMessage> getMyLendList(long readerId) {
        return lendMapper.selectByReaderId(readerId);
    }

    /**
     * 删除书籍
     * @param serNum
     * @return
     */
    @Transactional
    public Boolean deleteLend(long serNum) {
        return lendMapper.deleteByPrimaryKey(serNum) > 0;
    }


    /**
     * 获取排行
     *
     * @return
     */
    @Transactional
    public List<Rank> getRank() {
        List<Rank> list = lendMapper.getRank();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setRanked((i + 1) + "");
        }
        return list;
    }
}
