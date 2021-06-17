package cn.edu.sicau.action;

import cn.edu.sicau.dao.PassengerDao;
import cn.edu.sicau.model.Passenger;

import java.sql.SQLException;
import java.util.List;

/**
 * @authors 余承骏 严一鸣
 * @date 2021/6
 */

public class PassengerAction {

    public void add(Passenger passenger) throws Exception {
        PassengerDao dao = new PassengerDao();
        dao.addPassenger(passenger);
    }

    public void edit(Passenger passenger) throws Exception {
        PassengerDao dao = new PassengerDao();
        dao.updatePassenger(passenger);
    }

    public void del(Integer id) throws SQLException {
        PassengerDao dao = new PassengerDao();
        dao.delPassenger(id);
    }

    public List<Passenger> query() throws Exception {
        PassengerDao dao = new PassengerDao();
        return dao.query();
    }

    public List<Passenger> finalQuery(int timePower) throws Exception {
        PassengerDao dao = new PassengerDao();
        return dao.finalQuery(timePower);
    }

    public Passenger get(Integer id) throws SQLException {
        PassengerDao dao = new PassengerDao();
        return dao.get(id);
    }

    public void select_integrate() throws Exception {
        PassengerDao dao = new PassengerDao();
        dao.select_integrate();
    }

    public void OD() throws SQLException {
        PassengerDao dao = new PassengerDao();
        dao.OD();
    }

    public void polymerizationTime(int timePower) throws SQLException {
        PassengerDao dao = new PassengerDao();
        dao.polymerizationTime(timePower);
    }

    public void createPassengerFlow(int timePower) throws SQLException {
        PassengerDao dao = new PassengerDao();
        dao.createPassengerFlow(timePower);
    }
}
