package com.bokehai.book.pojo;

import javax.persistence.Id;
import java.io.Serializable;

public class Admin implements Serializable {

    @Id
    private Integer admin_id;
    private String password;
    private String username;
    private String describle;

    public Integer getAdminId() {
        return admin_id;
    }

    public void setAdminId(Integer admin_id) {
        this.admin_id = admin_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

}
