package com.bokehai.book.pojo;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class ReaderInfo implements Serializable {

    @Id
    private Integer reader_id;
    private String name;
    private String accountName;
    private String sex;
    private Date birth;
    private String address;
    private String phone;

    public Integer getReaderId() {
        return reader_id;
    }

    public void setReaderId(Integer reader_id) {
        this.reader_id = reader_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public String toString() {
        return "ReaderInfo{" +
                "reader_id=" + reader_id +
                ", name='" + name + '\'' +
                ", accountName='" + accountName + '\'' +
                ", sex='" + sex + '\'' +
                ", birth=" + birth +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
