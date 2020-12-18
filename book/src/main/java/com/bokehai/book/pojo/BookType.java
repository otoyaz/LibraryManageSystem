package com.bokehai.book.pojo;

import javax.persistence.Id;

public class BookType {

    @Id
    private String class_id;

    private String class_name;

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @Override
    public String toString() {
        return "BookType{" +
                "class_id='" + class_id + '\'' +
                ", class_name='" + class_name + '\'' +
                '}';
    }
}
