package com.bokehai.book.pojo;

import java.util.Date;

public class LendMessage {

    private Integer bookId;

    private String bookName;

    private String readerName;

    private Date lend_date;

    private Date back_date;


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public Date getLend_date() {
        return lend_date;
    }

    public void setLend_date(Date lend_date) {
        this.lend_date = lend_date;
    }

    public Date getBack_date() {
        return back_date;
    }

    public void setBack_date(Date back_date) {
        this.back_date = back_date;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "LendMessage{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", readerName='" + readerName + '\'' +
                ", lend_date=" + lend_date +
                ", back_date=" + back_date +
                '}';
    }
}
