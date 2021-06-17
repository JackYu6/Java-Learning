package cn.edu.sicau.model;

/**
 * @authors 余承骏 严一鸣
 * @date 2021/6
 */

public class Passenger {

    private Integer ID;
    private String TICKET_ID;
    private String TXN_DATE;
    private String TXN_TIME;
    private String TICKET_TYPE;
    private Integer TRANS_CODE;
    private String TXN_STATION_ID;
    private Integer BEFORE_AMT;
    private String IN_TXN_STATION;
    private String IN_TXN_DATETIME;
    private String OUT_TXN_STATION;
    private String OUT_TXN_DATETIME;
    private Integer PASSENGER_FLOW;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTICKET_ID() {
        return TICKET_ID;
    }

    public void setTICKET_ID(String TICKET_ID) {
        this.TICKET_ID = TICKET_ID;
    }

    public String getTXN_DATE() {
        return TXN_DATE;
    }

    public void setTXN_DATE(String TXN_DATE) {
        this.TXN_DATE = TXN_DATE;
    }

    public String getTXN_TIME() {
        return TXN_TIME;
    }

    public void setTXN_TIME(String TXN_TIME) {
        this.TXN_TIME = TXN_TIME;
    }

    public String getTICKET_TYPE() {
        return TICKET_TYPE;
    }

    public void setTICKET_TYPE(String TICKET_TYPE) {
        this.TICKET_TYPE = TICKET_TYPE;
    }

    public Integer getTRANS_CODE() {
        return TRANS_CODE;
    }

    public void setTRANS_CODE(Integer TRANS_CODE) {
        this.TRANS_CODE = TRANS_CODE;
    }

    public String getTXN_STATION_ID() {
        return TXN_STATION_ID;
    }

    public void setTXN_STATION_ID(String TXN_STATION_ID) {
        this.TXN_STATION_ID = TXN_STATION_ID;
    }

    public Integer getBEFORE_AMT() {
        return BEFORE_AMT;
    }

    public void setBEFORE_AMT(Integer BEFORE_AMT) {
        this.BEFORE_AMT = BEFORE_AMT;
    }

    public String getIN_TXN_STATION() {
        return IN_TXN_STATION;
    }

    public void setIN_TXN_STATION(String IN_TXN_STATION) {
        this.IN_TXN_STATION = IN_TXN_STATION;
    }

    public String getIN_TXN_DATETIME() {
        return IN_TXN_DATETIME;
    }

    public void setIN_TXN_DATETIME(String IN_TXN_DATETIME) {
        this.IN_TXN_DATETIME = IN_TXN_DATETIME;
    }

    public String getOUT_TXN_STATION() {
        return OUT_TXN_STATION;
    }

    public void setOUT_TXN_STATION(String OUT_TXN_STATION) {
        this.OUT_TXN_STATION = OUT_TXN_STATION;
    }

    public String getOUT_TXN_DATETIME() {
        return OUT_TXN_DATETIME;
    }

    public void setOUT_TXN_DATETIME(String OUT_TXN_DATETIME) {
        this.OUT_TXN_DATETIME = OUT_TXN_DATETIME;
    }

    public Integer getPASSENGER_FLOW() {
        return PASSENGER_FLOW;
    }

    public void setPASSENGER_FLOW(Integer PASSENGER_FLOW) {
        this.PASSENGER_FLOW = PASSENGER_FLOW;
    }

    @Override
    public String toString() {
        return "record{" +
                "ID=" + ID +
                ", TICKET_ID:" + TICKET_ID +
                ", TXN_DATE:" + TXN_DATE +
                ", TXN_TIME:" + TXN_TIME +
                ", TICKET_TYPE:" + TICKET_TYPE +
                ", TRANS_CODE:" + TRANS_CODE +
                ", TXN_STATION_ID:" + TXN_STATION_ID +
                ", BEFORE_AMT:" + BEFORE_AMT +
                '}';
    }

    public String toString2() {
        return "record{" +
                "进站时间:" + IN_TXN_DATETIME +
                ", 进站车站名:" + IN_TXN_STATION +
                ", 出站时间:" + OUT_TXN_DATETIME +
                ", 出站车站名:" + OUT_TXN_STATION +
                ", 客流量:" + PASSENGER_FLOW +
                '}';
    }
}
