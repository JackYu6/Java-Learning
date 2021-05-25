package com.manager.book;

import java.util.Date;

public class Book {
    private int id;
    private String name;
    private int state;//1：在库 0：被借
    private Date borrowDate;//被借日期   alt+enter 导包
    private int borrowCount;//借阅次数

    //鼠标右键->generate->Getter and Setter
    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
}
