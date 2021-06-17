package cn.edu.sicau.dao;

import cn.edu.sicau.db.DBUtil;
import cn.edu.sicau.model.Passenger;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @authors 余承骏 严一鸣
 * @date 2021/6
 */

public class PassengerDao {

    public void addPassenger(Passenger p) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String sql = "" +
                " insert into 20200101test" +
                " (TICKET_ID,TXN_DATE,TXN_TIME,TICKET_TYPE," +
                " TRANS_CODE,TXN_STATION_ID,BEFORE_AMT)" +
                " value(" +
                " ?,?,?,?,?,?,?) ";
        PreparedStatement ptmt = conn.prepareStatement(sql);

        ptmt.setString(1, p.getTICKET_ID());
        ptmt.setString(2, p.getTXN_DATE());
        ptmt.setString(3, p.getTXN_TIME());
        ptmt.setString(4, p.getTICKET_TYPE());
        ptmt.setInt(5, p.getTRANS_CODE());
        ptmt.setString(6, p.getTXN_STATION_ID());
        ptmt.setInt(7, p.getBEFORE_AMT());

        ptmt.execute();
    }

    public void updatePassenger(Passenger p) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String sql = "" +
                " update 20200101test" +
                " set TICKET_ID=?,TXN_DATE=?,TXN_TIME=?,TICKET_TYPE=?," +
                " TRANS_CODE=?,TXN_STATION_ID=?,BEFORE_AMT=?" +
                " where ID=? ";//根据ID更新记录
        PreparedStatement ptmt = conn.prepareStatement(sql);

        ptmt.setString(1, p.getTICKET_ID());
        ptmt.setString(2, p.getTXN_DATE());
        ptmt.setString(3, p.getTXN_TIME());
        ptmt.setString(4, p.getTICKET_TYPE());
        ptmt.setInt(5, p.getTRANS_CODE());
        ptmt.setString(6, p.getTXN_STATION_ID());
        ptmt.setInt(7, p.getBEFORE_AMT());
        ptmt.setInt(8, p.getID());

        ptmt.execute();
    }

    public void delPassenger(Integer id) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String sql = "" +
                " delete from 20200101test" +
                " where ID=? ";
        PreparedStatement ptmt = conn.prepareStatement(sql);

        ptmt.setInt(1, id);

        ptmt.execute();
    }

    public List<Passenger> query() throws SQLException {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select TICKET_ID,TICKET_TYPE from 20200101test");

        List<Passenger> pg = new ArrayList<Passenger>();
        Passenger p = null;
        while (rs.next()) {
            p = new Passenger();
            p.setTICKET_ID(rs.getString("TICKET_ID"));
            p.setTICKET_TYPE(rs.getString("TICKET_TYPE"));

            pg.add(p);
        }
        return pg;
    }

    public List<Passenger> finalQuery(int timePower) throws SQLException {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs;
        if (timePower == 15) {
            rs = stmt.executeQuery("select * from test515");
        } else if (timePower == 30) {
            rs = stmt.executeQuery("select * from test530");
        } else {
            rs = stmt.executeQuery("select * from test560");
        }
        List<Passenger> pg = new ArrayList<Passenger>();
        Passenger p = null;

        while (rs.next()) {
            p = new Passenger();
            p.setIN_TXN_STATION(rs.getString("进站车站"));
            p.setIN_TXN_DATETIME(rs.getString("IN_TXN_DATETIME"));
            p.setOUT_TXN_STATION(rs.getString("出站车站"));
            p.setOUT_TXN_DATETIME(rs.getString("OUT_TXN_DATETIME"));
            p.setPASSENGER_FLOW(rs.getInt("PASSENGER_FLOW"));

            pg.add(p);
        }
        return pg;
    }

    public Passenger get(Integer id) throws SQLException {
        Passenger p = null;
        Connection conn = DBUtil.getConnection();
        String sql = "" +
                " select * from 20200101test" +
                " where ID=? ";
        PreparedStatement ptmt = conn.prepareStatement(sql);

        ptmt.setInt(1, id);

        ResultSet rs = ptmt.executeQuery();
        while (rs.next()) {
            p = new Passenger();
            p.setID(rs.getInt("ID"));
            p.setTICKET_ID(rs.getString("TICKET_ID"));
            p.setTXN_DATE(rs.getString("TXN_DATE"));
            p.setTXN_TIME(rs.getString("TXN_TIME"));
            p.setTICKET_TYPE(rs.getString("TICKET_TYPE"));
            p.setTRANS_CODE(rs.getInt("TRANS_CODE"));
            p.setTXN_STATION_ID(rs.getString("TXN_STATION_ID"));
            p.setBEFORE_AMT(rs.getInt("BEFORE_AMT"));
        }
        return p;
    }

    //筛选合并字段
    public void select_integrate() throws SQLException {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "CREATE TABLE test_select_integrate(TICKET_ID VARCHAR(50),TXN_DATETIME datetime,TICKET_TYPE varchar(20),TRANS_CODE int,TXN_STATION_ID varchar(20),BEFORE_AMT int);";
        stmt.execute(sql);
        sql = "INSERT INTO test_select_integrate SELECT TICKET_ID,CONCAT(TXN_DATE,TXN_TIME),TICKET_TYPE,TRANS_CODE,TXN_STATION_ID,BEFORE_AMT FROM 20200101test;";
        stmt.execute(sql);
    }

    //OD配对并添加进出站站名
    public void OD() throws SQLException {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE test000(TICKET_ID VARCHAR(50),IN_TXN_DATETIME datetime,TICKET_TYPE varchar(20),IN_TXN_STATION_ID varchar(20),BEFORE_AMT int,OUT_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),进站车站 varchar(20),出站车站 varchar(20));";
        stmt.execute(sql);
        sql = "CREATE TABLE test001(TICKET_ID VARCHAR(50),IN_TXN_DATETIME datetime,TICKET_TYPE varchar(20),IN_TXN_STATION_ID varchar(20),BEFORE_AMT int,OUT_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20));";
        stmt.execute(sql);
        sql = "CREATE TEMPORARY TABLE test211(TICKET_ID VARCHAR(50),TXN_DATETIME datetime,TICKET_TYPE varchar(20),TRANS_CODE int,TXN_STATION_ID varchar(20),BEFORE_AMT int,flag int);";
        stmt.execute(sql);
        sql = "INSERT INTO test211 SELECT TICKET_ID,CONCAT(TXN_DATE,TXN_TIME),TICKET_TYPE,TRANS_CODE,TXN_STATION_ID,BEFORE_AMT,0 FROM 20200101test where TRANS_CODE=21;";
        stmt.execute(sql);
        sql = "CREATE TEMPORARY TABLE test221(TICKET_ID VARCHAR(50),TXN_DATETIME datetime,TICKET_TYPE varchar(20),TRANS_CODE int,TXN_STATION_ID varchar(20),BEFORE_AMT int,flag int);";
        stmt.execute(sql);
        sql = "INSERT INTO test221 SELECT TICKET_ID,CONCAT(TXN_DATE,TXN_TIME),TICKET_TYPE,TRANS_CODE,TXN_STATION_ID,BEFORE_AMT,0 FROM 20200101test where TRANS_CODE=22;";
        stmt.execute(sql);
        sql = "insert into test001 (select ins.TICKET_ID,ins.TXN_DATETIME,ins.TICKET_TYPE,ins.TXN_STATION_ID,ins.BEFORE_AMT,outs.TXN_DATETIME,outs.TXN_STATION_ID from (select TICKET_ID,TXN_DATETIME,TICKET_TYPE,TRANS_CODE,TXN_STATION_ID,BEFORE_AMT from test211) ins join (select TICKET_ID,TXN_DATETIME,TXN_STATION_ID,BEFORE_AMT from test221) outs on ins.TICKET_ID = outs.TICKET_ID where ins.BEFORE_AMT = outs.BEFORE_AMT and ins.TXN_DATETIME<outs.TXN_DATETIME order by ins.TXN_DATETIME,ins.TICKET_ID,outs.TXN_DATETIME);";
        stmt.execute(sql);
        stmt.execute("alter table test001 add ID int not null primary key auto_increment first;");
        sql = "CREATE TABLE test002(ID int primary key,TICKET_ID VARCHAR(50),IN_TXN_DATETIME datetime,TICKET_TYPE varchar(20),IN_TXN_STATION_ID varchar(20),BEFORE_AMT int,OUT_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),flag int);";
        stmt.execute(sql);
        sql = "insert into test002 SELECT YB.ID,YB.TICKET_ID,YB.IN_TXN_DATETIME,YB.TICKET_TYPE,YB.IN_TXN_STATION_ID,YB.BEFORE_AMT,YB.OUT_TXN_DATETIME,YB.OUT_TXN_STATION_ID,inCF.cc FROM(SELECT TICKET_ID,IN_TXN_DATETIME, COUNT(*) AS cc FROM test001 GROUP BY TICKET_ID,IN_TXN_DATETIME) inCF JOIN (SELECT * FROM test001) YB ON inCF.TICKET_ID=YB.TICKET_ID AND inCF.IN_TXN_DATETIME=YB.IN_TXN_DATETIME WHERE inCF.cc>1 order by YB.IN_TXN_DATETIME ASC,YB.TICKET_ID,YB.OUT_TXN_DATETIME;";
        stmt.execute(sql);
        Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = st.executeQuery("select ID,flag from test002");
        while (rs.next()) {
            int n = rs.getInt(2);
            rs.deleteRow();
            rs.relative(n - 1);
        }
        sql = "DELETE FROM test001 WHERE ID IN (SELECT ID FROM test002);";
        stmt.execute(sql);
        sql = "CREATE TABLE test003(ID int,TICKET_ID VARCHAR(50),IN_TXN_DATETIME datetime,TICKET_TYPE varchar(20),IN_TXN_STATION_ID varchar(20),BEFORE_AMT int,OUT_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),flag int);";
        stmt.execute(sql);
        sql = "insert into test003 SELECT YB.ID,YB.TICKET_ID,YB.IN_TXN_DATETIME,YB.TICKET_TYPE,YB.IN_TXN_STATION_ID,YB.BEFORE_AMT,YB.OUT_TXN_DATETIME,YB.OUT_TXN_STATION_ID,inCF.cc FROM(SELECT TICKET_ID,OUT_TXN_DATETIME, COUNT(*) AS cc FROM test001 GROUP BY TICKET_ID,OUT_TXN_DATETIME) inCF JOIN (SELECT * FROM test001) YB ON inCF.TICKET_ID=YB.TICKET_ID AND inCF.OUT_TXN_DATETIME=YB.OUT_TXN_DATETIME WHERE inCF.cc>1 order by YB.OUT_TXN_DATETIME ASC,YB.TICKET_ID,YB.IN_TXN_DATETIME DESC;";
        stmt.execute(sql);
        stmt.execute("alter table test003 add ID2 int not null primary key auto_increment first;");
        rs = st.executeQuery("select ID2,flag from test003");
        while (rs.next()) {
            int n = rs.getInt(2);
            rs.deleteRow();
            rs.relative(n - 1);
        }
        sql = "DELETE FROM test001 WHERE ID IN (SELECT ID FROM test003);";
        stmt.execute(sql);
        stmt.execute("drop table test002,test003;");
        sql = "CREATE TABLE test003(TICKET_ID VARCHAR(50),IN_TXN_DATETIME datetime,TICKET_TYPE varchar(20),IN_TXN_STATION_ID varchar(20),BEFORE_AMT int,OUT_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),进站车站 varchar(20));";
        stmt.execute(sql);
        sql = "insert into test003 (select f.TICKET_ID,f.IN_TXN_DATETIME,f.TICKET_TYPE,f.IN_TXN_STATION_ID,f.BEFORE_AMT,f.OUT_TXN_DATETIME,f.OUT_TXN_STATION_ID,o1.车站中文名 from (select * from test001)f join (select 车站ID,车站中文名 from sqlresult)o1 on f.IN_TXN_STATION_ID=o1.车站ID);";
        stmt.execute(sql);
        sql = "insert into test000 (select f.TICKET_ID,f.IN_TXN_DATETIME,f.TICKET_TYPE,f.IN_TXN_STATION_ID,f.BEFORE_AMT,f.OUT_TXN_DATETIME,f.OUT_TXN_STATION_ID,f.进站车站,o2.车站中文名 from (select * from test003)f join (select 车站ID,车站中文名 from sqlresult)o2 on f.OUT_TXN_STATION_ID=o2.车站ID);";
        stmt.execute(sql);
        stmt.execute("drop table test003;");
    }

    //聚合时间
    public void polymerizationTime(int timePower) throws SQLException {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "";
        if (timePower == 15) {
            sql = "create table time15(time211 datetime,time212 datetime,time221 datetime,time222 datetime);";
        } else if (timePower == 30) {
            sql = "create table time30(time211 datetime,time212 datetime,time221 datetime,time222 datetime);";
        } else if (timePower == 60) {
            sql = "create table time60(time211 datetime,time212 datetime,time221 datetime,time222 datetime);";
        }
        try {
            conn.setAutoCommit(false);
            stmt.execute(sql);
            int n = 0;
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date time211, time212, time221, time222 = new Date();
            time211 = dateFormat1.parse("2020-01-01 06:00:00");
            time212 = dateFormat1.parse("2020-01-01 06:00:00");
            time221 = dateFormat1.parse("2020-01-01 06:00:01");
            time222 = dateFormat1.parse("2020-01-01 06:00:00");
            while (timePower * n < 17 * 60) {
                time212.setTime(time211.getTime() + timePower * 60 * 1000 - 1);
                time222.setTime(time221.getTime() + timePower * 60 * 1000 - 1);
                if (timePower == 15) {
                    sql = "insert into time15 values('" + dateFormat1.format(time211) + "','" + dateFormat1.format(time212) + "','" + dateFormat1.format(time221) + "','" + dateFormat1.format(time222) + "');";
                } else if (timePower == 30) {
                    sql = "insert into time30 values('" + dateFormat1.format(time211) + "','" + dateFormat1.format(time212) + "','" + dateFormat1.format(time221) + "','" + dateFormat1.format(time222) + "');";
                } else if (timePower == 60) {
                    sql = "insert into time60 values('" + dateFormat1.format(time211) + "','" + dateFormat1.format(time212) + "','" + dateFormat1.format(time221) + "','" + dateFormat1.format(time222) + "');";
                }
                stmt.addBatch(sql);
                time211.setTime(time211.getTime() + timePower * 60 * 1000);
                time221.setTime(time221.getTime() + timePower * 60 * 1000);
                n++;
            }
            n = 0;
            time211 = dateFormat1.parse("2020-01-02 06:00:00");
            time212 = dateFormat1.parse("2020-01-02 06:00:00");
            time221 = dateFormat1.parse("2020-01-02 06:00:01");
            time222 = dateFormat1.parse("2020-01-02 06:00:00");
            while (timePower * n < 17 * 60) {
                time212.setTime(time211.getTime() + timePower * 60 * 1000 - 1);
                time222.setTime(time221.getTime() + timePower * 60 * 1000 - 1);
                if (timePower == 15) {
                    sql = "insert into time15 values('" + dateFormat1.format(time211) + "','" + dateFormat1.format(time212) + "','" + dateFormat1.format(time221) + "','" + dateFormat1.format(time222) + "');";
                } else if (timePower == 30) {
                    sql = "insert into time30 values('" + dateFormat1.format(time211) + "','" + dateFormat1.format(time212) + "','" + dateFormat1.format(time221) + "','" + dateFormat1.format(time222) + "');";
                } else if (timePower == 60) {
                    sql = "insert into time60 values('" + dateFormat1.format(time211) + "','" + dateFormat1.format(time212) + "','" + dateFormat1.format(time221) + "','" + dateFormat1.format(time222) + "');";
                }
                stmt.addBatch(sql);
                time211.setTime(time211.getTime() + timePower * 60 * 1000);
                time221.setTime(time221.getTime() + timePower * 60 * 1000);
                n++;
            }
            stmt.executeBatch();
            stmt.clearBatch();
            conn.setAutoCommit(true);
            if (timePower == 15) {
                sql = "CREATE TEMPORARY TABLE test151(TICKET_ID VARCHAR(50),IN_TXN_DATETIME datetime,TICKET_TYPE varchar(20),IN_TXN_STATION_ID varchar(20),BEFORE_AMT int,OUT_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),进站车站 varchar(20),出站车站 varchar(20));";
                stmt.execute(sql);
                sql = "CREATE TABLE test15(TICKET_ID VARCHAR(50),IN_TXN_DATETIME datetime,TICKET_TYPE varchar(20),IN_TXN_STATION_ID varchar(20),BEFORE_AMT int,OUT_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),进站车站 varchar(20),出站车站 varchar(20));";
                stmt.execute(sql);
                sql = "insert into test151 (select F.TICKET_ID,T.time211,F.TICKET_TYPE,F.IN_TXN_STATION_ID,F.BEFORE_AMT,F.OUT_TXN_DATETIME,F.OUT_TXN_STATION_ID,F.进站车站,F.出站车站 from (select * from test000)F join (select * from time15)T on F.IN_TXN_DATETIME>=T.time211 and F.IN_TXN_DATETIME<=T.time212)";
                stmt.execute(sql);
                sql = "insert into test15 (select F.TICKET_ID,F.IN_TXN_DATETIME,F.TICKET_TYPE,F.IN_TXN_STATION_ID,F.BEFORE_AMT,T.time222,F.OUT_TXN_STATION_ID,F.进站车站,F.出站车站 from (select * from test151)F join (select * from time15)T on F.OUT_TXN_DATETIME>=T.time221 and F.OUT_TXN_DATETIME<=T.time222)";
                stmt.execute(sql);
            } else if (timePower == 30) {
                sql = "CREATE TEMPORARY TABLE test301(TICKET_ID VARCHAR(50),IN_TXN_DATETIME datetime,TICKET_TYPE varchar(20),IN_TXN_STATION_ID varchar(20),BEFORE_AMT int,OUT_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),进站车站 varchar(20),出站车站 varchar(20));";
                stmt.execute(sql);
                sql = "CREATE TABLE test30(TICKET_ID VARCHAR(50),IN_TXN_DATETIME datetime,TICKET_TYPE varchar(20),IN_TXN_STATION_ID varchar(20),BEFORE_AMT int,OUT_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),进站车站 varchar(20),出站车站 varchar(20));";
                stmt.execute(sql);
                sql = "insert into test301 (select F.TICKET_ID,T.time211,F.TICKET_TYPE,F.IN_TXN_STATION_ID,F.BEFORE_AMT,F.OUT_TXN_DATETIME,F.OUT_TXN_STATION_ID,F.进站车站,F.出站车站 from (select * from test000)F join (select * from time30)T on F.IN_TXN_DATETIME>=T.time211 and F.IN_TXN_DATETIME<=T.time212)";
                stmt.execute(sql);
                sql = "insert into test30 (select F.TICKET_ID,F.IN_TXN_DATETIME,F.TICKET_TYPE,F.IN_TXN_STATION_ID,F.BEFORE_AMT,T.time222,F.OUT_TXN_STATION_ID,F.进站车站,F.出站车站 from (select * from test301)F join (select * from time30)T on F.OUT_TXN_DATETIME>=T.time221 and F.OUT_TXN_DATETIME<=T.time222)";
                stmt.execute(sql);
            } else if (timePower == 60) {
                sql = "CREATE TEMPORARY TABLE test601(TICKET_ID VARCHAR(50),IN_TXN_DATETIME datetime,TICKET_TYPE varchar(20),IN_TXN_STATION_ID varchar(20),BEFORE_AMT int,OUT_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),进站车站 varchar(20),出站车站 varchar(20));";
                stmt.execute(sql);
                sql = "CREATE TABLE test60(TICKET_ID VARCHAR(50),IN_TXN_DATETIME datetime,TICKET_TYPE varchar(20),IN_TXN_STATION_ID varchar(20),BEFORE_AMT int,OUT_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),进站车站 varchar(20),出站车站 varchar(20));";
                stmt.execute(sql);
                sql = "insert into test601 (select F.TICKET_ID,T.time211,F.TICKET_TYPE,F.IN_TXN_STATION_ID,F.BEFORE_AMT,F.OUT_TXN_DATETIME,F.OUT_TXN_STATION_ID,F.进站车站,F.出站车站 from (select * from test000)F join (select * from time60)T on F.IN_TXN_DATETIME>=T.time211 and F.IN_TXN_DATETIME<=T.time212)";
                stmt.execute(sql);
                sql = "insert into test60 (select F.TICKET_ID,F.IN_TXN_DATETIME,F.TICKET_TYPE,F.IN_TXN_STATION_ID,F.BEFORE_AMT,T.time222,F.OUT_TXN_STATION_ID,F.进站车站,F.出站车站 from (select * from test601)F join (select * from time60)T on F.OUT_TXN_DATETIME>=T.time221 and F.OUT_TXN_DATETIME<=T.time222)";
                stmt.execute(sql);
                stmt.execute("drop table time15,time30,time60;");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //统计客流量
    public void createPassengerFlow(int timePower) throws SQLException {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        String sql1 = "", sql2 = "";
        if (timePower == 15) {
            sql1 = "create table test515(IN_TXN_STATION_ID varchar(20),进站车站 varchar(20),IN_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),出站车站 varchar(20),OUT_TXN_DATETIME datetime,PASSENGER_FLOW int);";
            sql2 = "insert into test515(SELECT IN_TXN_STATION_ID,进站车站,IN_TXN_DATETIME,OUT_TXN_STATION_ID,出站车站,OUT_TXN_DATETIME,COUNT(*) AS PASSENGER_FLOW FROM test15 GROUP BY IN_TXN_STATION_ID,进站车站,IN_TXN_DATETIME,OUT_TXN_STATION_ID,出站车站,OUT_TXN_DATETIME ORDER BY IN_TXN_STATION_ID,IN_TXN_DATETIME,OUT_TXN_STATION_ID,OUT_TXN_DATETIME);";
        } else if (timePower == 30) {
            sql1 = "create table test530(IN_TXN_STATION_ID varchar(20),进站车站 varchar(20),IN_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),出站车站 varchar(20),OUT_TXN_DATETIME datetime,PASSENGER_FLOW int);";
            sql2 = "insert into test530(SELECT IN_TXN_STATION_ID,进站车站,IN_TXN_DATETIME,OUT_TXN_STATION_ID,出站车站,OUT_TXN_DATETIME,COUNT(*) AS PASSENGER_FLOW FROM test30 GROUP BY IN_TXN_STATION_ID,进站车站,IN_TXN_DATETIME,OUT_TXN_STATION_ID,出站车站,OUT_TXN_DATETIME ORDER BY IN_TXN_STATION_ID,IN_TXN_DATETIME,OUT_TXN_STATION_ID,OUT_TXN_DATETIME);";
        } else if (timePower == 60) {
            sql1 = "create table test560(IN_TXN_STATION_ID varchar(20),进站车站 varchar(20),IN_TXN_DATETIME datetime,OUT_TXN_STATION_ID varchar(20),出站车站 varchar(20),OUT_TXN_DATETIME datetime,PASSENGER_FLOW int);";
            sql2 = "insert into test560(SELECT IN_TXN_STATION_ID,进站车站,IN_TXN_DATETIME,OUT_TXN_STATION_ID,出站车站,OUT_TXN_DATETIME,COUNT(*) AS PASSENGER_FLOW FROM test60 GROUP BY IN_TXN_STATION_ID,进站车站,IN_TXN_DATETIME,OUT_TXN_STATION_ID,出站车站,OUT_TXN_DATETIME ORDER BY IN_TXN_STATION_ID,IN_TXN_DATETIME,OUT_TXN_STATION_ID,OUT_TXN_DATETIME);";
        }
        try {
            stmt.execute(sql1);
            stmt.execute(sql2);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
