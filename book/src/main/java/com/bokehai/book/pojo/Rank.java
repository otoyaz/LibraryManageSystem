package com.bokehai.book.pojo;

public class Rank {

    private String count;

    private String readerName;

    private String ranked;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getRanked() {
        return ranked;
    }

    public void setRanked(String ranked) {
        this.ranked = ranked;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "count='" + count + '\'' +
                ", readerName='" + readerName + '\'' +
                ", ranked='" + ranked + '\'' +
                '}';
    }
}
