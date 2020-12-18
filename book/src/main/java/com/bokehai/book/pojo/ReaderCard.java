package com.bokehai.book.pojo;

import javax.persistence.Id;
import java.io.Serializable;

public class ReaderCard implements Serializable {

    @Id
    private Integer reader_id;
    private String username;
    private String password;
    private String describle;

    public Integer getReaderId() {
        return reader_id;
    }

    public void setReaderId(Integer reader_id) {
        this.reader_id = reader_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }
}
