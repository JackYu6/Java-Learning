package cn.edu.sicau.view;

import cn.edu.sicau.action.PassengerAction;
import cn.edu.sicau.model.Passenger;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * @authors 余承骏 严一鸣
 * @date 2021/6
 */

public class View {
    //提示语
    private static final String CONTENT =
            "=========================================================\n" +
                    "系统功能列表：\n" +
                    "[GET/G]查看原表单条记录\n" +
                    "[ADD/A]添加原表记录\n" +
                    "[UPDATE/U]更新原表记录\n" +
                    "[DELETE/D]删除原表记录\n" +
                    "[QUERY/Q]查看原表全部记录的TICKET_ID和TICKET_TYPE\n" +
                    "[SELECT_INTEGRATE/S]筛选字段并整合日期和时间\n" +
                    "[OD/O]OD配对并添加进出站站名\n" +
                    "[POLYMERIZATION_TIME/P]聚合时间\n" +
                    "[CREATE_PASSENGER_FLOW/C]统计客流量\n" +
                    "[FINAL_QUERY/F]查询最终处理结果\n" +
                    "[EXIT/E]退出系统\n";

    //操作标记
    private static final String OPERATION_GET = "GET";
    private static final String OPERATION_ADD = "ADD";
    private static final String OPERATION_UPDATE = "UPDATE";
    private static final String OPERATION_DELETE = "DELETE";
    private static final String OPERATION_QUERY = "QUERY";
    private static final String OPERATION_SELECT_INTEGRATE = "SELECT_INTEGRATE";
    private static final String OPERATION_OD = "OD";
    private static final String OPERATION_POLYMERIZATION_TIME = "POLYMERIZATION_TIME";
    private static final String OPERATION_CRATE_PASSENGER_FLOW = "CREATE_PASSENGER_FLOW";
    private static final String OPERATION_FINAL_QUERY = "FINAL_QUERY";
    private static final String OPERATION_EXIT = "EXIT";


    public static void main(String[] args) {

        System.out.println(CONTENT);

        Scanner scan = new Scanner(System.in);
        Passenger passenger = new Passenger();
        PassengerAction action = new PassengerAction();
        String previous = null;//定义记忆变量
        int step = 1;//定义步骤标记
        //保持程序运行
        while (true) {
            String in = scan.next();
            if (OPERATION_EXIT.equals(in.toUpperCase())
                    || OPERATION_EXIT.substring(0, 1).equals(in.toUpperCase())) {
                System.out.println("您已成功退出系统。");
                break;
            } else if (OPERATION_QUERY.equals(in.toUpperCase())
                    || OPERATION_QUERY.substring(0, 1).equals(in.toUpperCase())) {
                try {
                    List<Passenger> list = action.query();
                    for (Passenger pa : list) {
                        System.out.println("TICKET_ID:" + pa.getTICKET_ID() + ", TICKET_TYPE:" + pa.getTICKET_TYPE());
                    }
                    System.out.println("查询成功。");
                    System.out.println(CONTENT);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("查询失败。");
                    System.out.println(CONTENT);
                }
            } else if (OPERATION_FINAL_QUERY.equals(in.toUpperCase())
                    || OPERATION_FINAL_QUERY.substring(0, 1).equals(in.toUpperCase())
                    || OPERATION_FINAL_QUERY.equals(previous)) {
                previous = OPERATION_FINAL_QUERY;

                if (1 == step) {
                    System.out.println("请输入待查询记录对应的时间间隔粒度（15/30/60）");
                } else if (2 == step) {
                    try {
                        List<Passenger> list = action.finalQuery(Integer.parseInt(in));

                        for (Passenger pa : list) {
                            System.out.println(pa.toString2());
                        }
                        System.out.println("查询成功。");
                        System.out.println(CONTENT);
                        previous = null;
                        step = 1;
                    } catch (SQLException throwable) {
                        throwable.printStackTrace();
                        System.out.println("查询失败。");
                        System.out.println(CONTENT);
                        previous = null;
                        step = 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (OPERATION_FINAL_QUERY.equals(previous)) {
                    step++;
                }
            } else if (OPERATION_GET.equals(in.toUpperCase())
                    || OPERATION_GET.substring(0, 1).equals(in.toUpperCase())
                    || OPERATION_GET.equals(previous)) {
                previous = OPERATION_GET;

                if (1 == step) {
                    System.out.println("请输入待查询记录的ID");
                } else if (2 == step) {
                    try {
                        passenger = action.get(Integer.valueOf(in));
                        System.out.println(passenger.toString());
                        System.out.println("查询成功。");
                        System.out.println(CONTENT);
                        previous = null;
                        step = 1;
                    } catch (SQLException throwable) {
                        throwable.printStackTrace();
                        System.out.println("查询失败。");
                        System.out.println(CONTENT);
                        previous = null;
                        step = 1;
                    }
                }
                if (OPERATION_GET.equals(previous)) {
                    step++;
                }

            } else if (OPERATION_ADD.equals(in.toUpperCase())
                    || OPERATION_ADD.substring(0, 1).equals(in.toUpperCase())
                    || OPERATION_ADD.equals(previous)) {
                previous = OPERATION_ADD;
                //新增记录

                if (1 == step) {
                    System.out.println("请输入TICKET_ID");
                } else if (2 == step) {
                    passenger.setTICKET_ID(in);
                    System.out.println("请输入TXN_DATE(8个数字)");
                } else if (3 == step) {
                    passenger.setTXN_DATE(in);
                    System.out.println("请输入TXN_TIME(6个数字)");
                } else if (4 == step) {
                    passenger.setTXN_TIME(in);
                    System.out.println("请输入TICKET_TYPE");
                } else if (5 == step) {
                    passenger.setTICKET_TYPE(in);
                    System.out.println("请输入TRANS_CODE");
                } else if (6 == step) {
                    passenger.setTRANS_CODE(Integer.valueOf(in));
                    System.out.println("请输入TXN_STATION_ID");
                } else if (7 == step) {
                    passenger.setTXN_STATION_ID(in);
                    System.out.println("请输入BEFORE_AMT");
                } else if (8 == step) {
                    passenger.setBEFORE_AMT(Integer.valueOf(in));

                    try {
                        action.add(passenger);
                        System.out.println("新增记录成功。");
                        System.out.println(CONTENT);
                        previous = null;
                        step = 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("新增记录失败。");
                        System.out.println(CONTENT);
                        previous = null;
                        step = 1;
                    }
                }
                if (OPERATION_ADD.equals(previous)) {
                    step++;
                }
            } else if (OPERATION_UPDATE.equals(in.toUpperCase())
                    || OPERATION_UPDATE.substring(0, 1).equals(in.toUpperCase())
                    || OPERATION_UPDATE.equals(previous)) {
                previous = OPERATION_UPDATE;
                //更新记录

                if (1 == step) {
                    System.out.println("请输入要更新的记录的ID");
                } else if (2 == step) {
                    passenger.setID(Integer.valueOf(in));
                    System.out.println("请输入TICKET_ID");
                } else if (3 == step) {
                    passenger.setTICKET_ID(in);
                    System.out.println("请输入TXN_DATE(8个数字)");
                } else if (4 == step) {
                    passenger.setTXN_DATE(in);
                    System.out.println("请输入TXN_TIME(6个数字)");
                } else if (5 == step) {
                    passenger.setTXN_TIME(in);
                    System.out.println("请输入TICKET_TYPE");
                } else if (6 == step) {
                    passenger.setTICKET_TYPE(in);
                    System.out.println("请输入TRANS_CODE");
                } else if (7 == step) {
                    passenger.setTRANS_CODE(Integer.valueOf(in));
                    System.out.println("请输入TXN_STATION_ID");
                } else if (8 == step) {
                    passenger.setTXN_STATION_ID(in);
                    System.out.println("请输入BEFORE_AMT");
                } else if (9 == step) {
                    passenger.setBEFORE_AMT(Integer.valueOf(in));

                    try {
                        action.edit(passenger);
                        System.out.println("更新记录成功。");
                        System.out.println(CONTENT);
                        previous = null;
                        step = 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("更新记录失败。");
                        System.out.println(CONTENT);
                        previous = null;
                        step = 1;
                    }
                }
                if (OPERATION_UPDATE.equals(previous)) {
                    step++;
                }
            } else if (OPERATION_DELETE.equals(in.toUpperCase())
                    || OPERATION_DELETE.substring(0, 1).equals(in.toUpperCase())
                    || OPERATION_DELETE.equals(previous)) {
                previous = OPERATION_DELETE;

                if (1 == step) {
                    System.out.println("请输入要删除的记录的ID");
                } else if (2 == step) {
                    try {
                        action.del(Integer.valueOf(in));
                        System.out.println("删除成功。");
                        System.out.println(CONTENT);
                        previous = null;
                        step = 1;
                    } catch (SQLException throwable) {
                        throwable.printStackTrace();
                        System.out.println("删除失败。");
                        System.out.println(CONTENT);
                        previous = null;
                        step = 1;
                    }
                }
                if (OPERATION_DELETE.equals(previous)) {
                    step++;
                }
            } else if (OPERATION_SELECT_INTEGRATE.equals(in.toUpperCase())
                    || OPERATION_SELECT_INTEGRATE.substring(0, 1).equals(in.toUpperCase())) {
                try {
                    System.out.println("开始筛选合并字段。");
                    action.select_integrate();
                    System.out.println("筛选合并字段成功。");
                    System.out.println(CONTENT);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("筛选合并字段失败。");
                    System.out.println(CONTENT);
                }
            } else if (OPERATION_OD.equals(in.toUpperCase())
                    || OPERATION_OD.substring(0, 1).equals(in.toUpperCase())) {
                try {
                    System.out.println("开始OD配对并添加进出站站名");
                    action.OD();
                    System.out.println("运行成功。");
                    System.out.println(CONTENT);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("运行失败。");
                    System.out.println(CONTENT);
                }
            } else if (OPERATION_POLYMERIZATION_TIME.equals(in.toUpperCase())
                    || OPERATION_POLYMERIZATION_TIME.substring(0, 1).equals(in.toUpperCase())) {
                try {
                    System.out.println("开始时间间隔粒度为15的聚合");
                    action.polymerizationTime(15);
                    System.out.println("开始时间间隔粒度为30的聚合");
                    action.polymerizationTime(30);
                    System.out.println("开始时间间隔粒度为60的聚合");
                    action.polymerizationTime(60);
                    System.out.println("聚合完成。");
                    System.out.println(CONTENT);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("聚合失败。");
                    System.out.println(CONTENT);
                }
            } else if (OPERATION_CRATE_PASSENGER_FLOW.equals(in.toUpperCase())
                    || OPERATION_CRATE_PASSENGER_FLOW.substring(0, 1).equals(in.toUpperCase())) {
                try {
                    System.out.println("开始统计时间间隔粒度为15的客流量");
                    action.createPassengerFlow(15);
                    System.out.println("开始统计时间间隔粒度为30的客流数");
                    action.createPassengerFlow(30);
                    System.out.println("开始统计时间间隔粒度为60的客流数");
                    action.createPassengerFlow(60);
                    System.out.println("统计完成。");
                    System.out.println(CONTENT);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("统计失败。");
                    System.out.println(CONTENT);
                }
            } else {
                System.out.println("您输入的值为：" + in);
            }
        }
    }
}
