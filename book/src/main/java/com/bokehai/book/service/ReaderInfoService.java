package com.bokehai.book.service;

import com.bokehai.book.mapper.ReaderInfoMapper;
import com.bokehai.book.pojo.ReaderCard;
import com.bokehai.book.pojo.ReaderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReaderInfoService {

    @Autowired
    private ReaderInfoMapper readerInfoMapper;

    @Autowired
    private ReaderCardService readerCardService;


    /**
     * 获得所有读者的信息
     * @return
     */
    public List<ReaderInfo> getAllreaderInfo() {
        return readerInfoMapper.selectAll();
    }

    /**
     * 根据id删除读者信息
     * @param readerId
     * @return
     */
    @Transactional
    public boolean deleteReaderInfo(Integer readerId) {
        return readerInfoMapper.deleteByPrimaryKey(readerId) > 0;
    }

    /**
     * 根据id获取读者的信息
     * @param readerId
     * @return
     */
    public ReaderInfo getReaderInfo(Integer readerId) {
        return readerInfoMapper.selectByPrimaryKey(readerId);
    }

    /**
     * 更新读者的信息
     * @param readerInfo
     * @return
     */
    @Transactional
    public boolean editReaderInfo(ReaderInfo readerInfo) {
        return readerInfoMapper.updateByPrimaryKey(readerInfo) > 0;
    }


    /**
     * 添加读者信息
     * @param readerInfo
     * @return
     */
    @Transactional
    public boolean addReaderInfo(ReaderInfo readerInfo) {
        Boolean i = readerInfoMapper.insert(readerInfo) > 0;
        ReaderCard readerCard = new ReaderCard();
        readerCard.setReaderId(readerInfo.getReaderId());
        readerCard.setUsername(readerInfo.getAccountName());
        readerCard.setPassword("123456");
        readerCard.setDescrible("normal");
        Boolean j = readerCardService.addReaderCard(readerCard);
        return i && j;
    }
}
