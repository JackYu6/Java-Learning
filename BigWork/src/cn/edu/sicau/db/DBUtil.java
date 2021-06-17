package cn.edu.sicau.db;

import java.sql.*;

/**
 * @authors 余承骏 严一鸣
 * @date 2021/6
 */

public class DBUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/bigwork6?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static Connection conn = null;//数据库连接对象

    static { //静态代码块块（执行类的静态方法时，static代码块也会执行，它最先执行且只执行一次）
        try {
            //加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //获得数据库的连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            /*增加ID字段并设置为主键自增*/
            //获取执行sql的对象
            Statement stmt = conn.createStatement();
            //执行sql
            stmt.execute("ALTER TABLE 20200101test ADD ID int NOT NULL PRIMARY KEY AUTO_INCREMENT FIRST");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {//返回数据库连接对象的静态方法
        return conn;
    }
}
