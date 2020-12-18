package com.bokehai.book.controller;

import com.bokehai.book.pojo.ReaderInfo;
import com.bokehai.book.pojo.Result;
import com.bokehai.book.service.ReaderInfoService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReaderInfoController {

    @Autowired
    private ReaderInfoService readerInfoService;

    private Result result;

    /**
     * 获得所有读者的信息
     * @return
     */
    @GetMapping("/getAllreaderInfo")
    public List<ReaderInfo> getAllreaderInfo() {
        return readerInfoService.getAllreaderInfo();
    }

    /**
     * 根据id删除读者信息
     * @param readerId
     * @return
     */
    @DeleteMapping("/deleteReaderInfo")
    public Result deleteReaderInfo(Integer readerId) {
        result = new Result();
        if (readerId == null) {
            result.setResult("readerId不能为空");
            result.setStatus(406);
            return result;
        }
        Boolean isDel = readerInfoService.deleteReaderInfo(readerId);
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
     * 根据id获取读者的信息
     * @param readerId
     * @return
     */
    @GetMapping("/getReaderInfo")
    public List<ReaderInfo> getReaderInfo(Integer readerId) {
        List<ReaderInfo> list = new ArrayList<>();
        ReaderInfo readerInfo = readerInfoService.getReaderInfo(readerId);
        if (readerId != null) {
            list.add(readerInfo);
        }
        return list;
    }

    /**
     * 更新读者的信息
     * @param readerInfo
     * @return
     */
    @PutMapping("/updateReaderInfo")
    public Result editReaderInfo(@RequestBody ReaderInfo readerInfo) {
        result = new Result();
        if (readerInfo.getReaderId() == null) {
            result.setResult("readerId不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(readerInfo.getName())) {
            result.setResult("name不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(readerInfo.getAccountName())) {
            result.setResult("accountName不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(readerInfo.getSex())) {
            result.setResult("sex不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(readerInfo.getBirth().toString())) {
            result.setResult("birth不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(readerInfo.getAddress())) {
            result.setResult("address不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(readerInfo.getPhone())) {
            result.setResult("phone不能为空");
            result.setStatus(406);
            return result;
        }
        Boolean isUpdate = readerInfoService.editReaderInfo(readerInfo);
        if(isUpdate) {
            result.setResult("更新成功");
            result.setStatus(200);
        } else {
            result.setResult("更新失败");
            result.setStatus(406);
        }
        return result;
    }


    /**
     * 添加读者信息 同时也是注册接口
     * @param readerInfo
     * @return
     */
    @PostMapping("/saveReaderInfo")
    public Result addReaderInfo(@RequestBody ReaderInfo readerInfo) {
        System.out.println(readerInfo);
        result = new Result();
        if (StringUtils.isNullOrEmpty(readerInfo.getName())) {
            result.setResult("name不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(readerInfo.getAccountName())) {
            result.setResult("accountName不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(readerInfo.getSex())) {
            result.setResult("sex不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(readerInfo.getBirth().toString())) {
            result.setResult("birth不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(readerInfo.getAddress())) {
            result.setResult("address不能为空");
            result.setStatus(406);
            return result;
        } else if (StringUtils.isNullOrEmpty(readerInfo.getPhone())) {
            result.setResult("phone不能为空");
            result.setStatus(406);
            return result;
        }
        Boolean isAdd = readerInfoService.addReaderInfo(readerInfo);
        if(isAdd) {
            result.setResult("添加成功");
            result.setStatus(200);
        } else {
            result.setResult("添加失败");
            result.setStatus(406);
        }
        return result;
    }
}
