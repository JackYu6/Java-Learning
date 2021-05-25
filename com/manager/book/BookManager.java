package com.manager.book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class BookManager {
    private Scanner input = new Scanner(System.in);
    private Book[] books;//声明对象数组

    //初始化方法
    public void init() {
        books = new Book[10];

        Book book0 = new Book();
        book0.setId(1000);
        book0.setName("java");
        book0.setState(1);//1：在库
//        book0.setBorrowDate(null);//默认是null
//        book0.setBorrowCount(0);//默认值是0

        Book book1 = new Book();//选中一个要批量改的名称，然后shift+F6
        book1.setId(1001);
        book1.setName("高并发");
        book1.setState(1);

        Book book2 = new Book();
        book2.setId(1002);
        book2.setName("分布式");
        book2.setState(1);

        books[0] = book0;
        books[1] = book1;
        books[2] = book2;

    }

    //菜单
    public void menu() {

        System.out.println("======欢迎进入图书管理系统======");
        System.out.println("请根据提示选择：");
        System.out.println("1.增加图书");
        System.out.println("2.删除图书");
        System.out.println("3.借阅图书");
        System.out.println("4.归还图书");
        System.out.println("5.图书风云榜");
        System.out.println("6.查看图书");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                addBook();
                break;
            case 2:
                deleteBook();
                break;
            case 3:
                borrowBook();
                break;
            case 4:
                returnBook();
                break;
            case 5:
                bookRank();
                break;
            case 6:
                showBooks();
                break;
            default:
        }
    }

    public void isContinueMenu() {
        System.out.println("是否回到主菜单（y/其他）");
        String isContinue = input.next();
        if ("y".equals(isContinue)) {
            menu();//间接递归调用
        }
    }

    private void addBook() {
        System.out.println("增加图书");

        Book book = new Book();
        System.out.println("请输入书的编号：");
        int id = input.nextInt();

        System.out.println("请输入书名：");
        String name = input.next();

        int state = 1;//新书入库，1表示在库
//        Date bDate = null;//默认是null
//        int bCount = 0;//默认是0

        book.setId(id);
        book.setName(name);
        book.setState(state);

        //寻找新书增加进入的位置--第一个null处
        int position = -1;
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) {
                position = i;
                break;
            }
        }
        books[position] = book;

        isContinueMenu();
    }

    private void deleteBook() {
        System.out.println("请输入要删除的图书的名字");
        String name = input.next();
        int position = -1;
        for (int i = 0; i < books.length; i++) {
            if (books[i].getName().equals(name)) {
                position = i;
                break;
            }
        }
        //删除的本质是数组元素的移动
        int start = position + 1;
        int firstNullPosition = -1;
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) {
                firstNullPosition = i;
                break;
            }
        }
        int end = firstNullPosition - 1;

        if (firstNullPosition == -1) {//当书架无null时
            end = books.length - 1;
        }
        for (int i = start; i <= end; i++) {
            books[i - 1] = books[i];
        }
        books[end] = null;

        isContinueMenu();
    }

    private void borrowBook() {
        System.out.println("借阅图书");
        System.out.println("请输入要借阅的图书的名字");
        String name = input.next();

        int position = -1;
        for (int i = 0; i < books.length; i++) {
            if (books[i].getName().equals(name)) {
                position = i;
                break;
            }
        }

        books[position].setState(0);
        Date date = new Date();
        books[position].setBorrowDate(date);
//        //Date <-> String
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        //Date -> String
//        String dateStr = sdf.format(date);

        isContinueMenu();
    }

    private void returnBook() {
        System.out.println("归还图书");
        System.out.println("请输入要归还的图书的名字");
        String name = input.next();

        int position = -1;
        for (int i = 0; i < books.length; i++) {
            if (books[i].getName().equals(name)) {
                position = i;
                break;
            }
        }
        books[position].setState(1);
        books[position].setBorrowDate(null);

        int count = books[position].getBorrowCount() + 1;
        books[position].setBorrowCount(count);

        isContinueMenu();
    }

    //图书风云榜 根据被借次数降序
    private void bookRank() {
        System.out.println("风云榜");//排序 一个临时操作，不是永久排序
        Book[] newBooks = new Book[books.length];
        for(int i=0;i<books.length;i++) {
            if (books[i] != null) {
                newBooks[i] = books[i];
            }
        }

        for (int i = 0; i < newBooks.length - 1; i++) {
            for (int j = 0; j < newBooks.length - 1 - i; j++) {
                if (newBooks[j] != null && newBooks[j+1] != null) {
                    if (newBooks[j].getBorrowCount() < newBooks[j + 1].getBorrowCount()) {
                        Book temp = newBooks[j];
                        newBooks[j] = newBooks[j + 1];
                        newBooks[j + 1] = temp;
                    }
                }
            }
        }
        System.out.println("编号\t\t\t书名\t\t\t状态\t\t被借日期\t\t被借次数");
        for (Book book : newBooks) {
            if (book != null) {
//                String borrowDateStr = book.getBorrowDate() == null ? "-" : book.getBorrowDate() + "";//任何类型+字符串转为字符串
                String borrowDateStr = "";
                if (book.getBorrowDate() == null) {
                    borrowDateStr = "-";
                } else {
//                    borrowDateStr = book.getBorrowDate() + "";
                    Date bDate = book.getBorrowDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    borrowDateStr = sdf.format(bDate);
                }
                String stateStr = book.getState() == 0 ? "被借" : "在库";

                System.out.println(book.getId() + "\t\t" + book.getName() + "\t\t" + stateStr + "\t\t" + borrowDateStr + "\t\t" + book.getBorrowCount());
            }
        }
        isContinueMenu();
    }

    //查看所有的书
    private void showBooks() {
        System.out.println("编号\t\t\t书名\t\t\t状态\t\t被借日期\t\t被借次数");
        for (Book book : books) {
            if (book != null) {
//                String borrowDateStr = book.getBorrowDate() == null ? "-" : book.getBorrowDate() + "";//任何类型+字符串转为字符串
                String borrowDateStr = "";
                if (book.getBorrowDate() == null) {
                    borrowDateStr = "-";
                } else {
//                    borrowDateStr = book.getBorrowDate() + "";
                    Date bDate = book.getBorrowDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    borrowDateStr = sdf.format(bDate);
                }
                String stateStr = book.getState() == 0 ? "被借" : "在库";

                System.out.println(book.getId() + "\t\t" + book.getName() + "\t\t" + stateStr + "\t\t" + borrowDateStr + "\t\t" + book.getBorrowCount());
            }
        }
        isContinueMenu();
    }
}
