package FingerGuessing;

import java.util.Scanner;

public class Game {
    User user;//声明对象
    Computer computer;
    int count;//对战次数

    //初始化
    public void init() {
        System.out.println("请输入您的姓名：");
        Scanner input = new Scanner(System.in);
        user = new User();//调用对象的属性和方法前先实例化
        user.name = input.next();
        user.score = 0;

        System.out.println("请选择您的对手：\n1.张三\t2.李四\t3.王五");
        int choice = input.nextInt();
        computer = new Computer();//对象实例化
        computer.score = 0;
        switch (choice) {
            case 1:
                computer.name = "张三";
                break;
            case 2:
                computer.name = "李四";
                break;
            case 3:
                computer.name = "王五";
                break;
            default:
                System.out.println("输入有误！");
        }
        System.out.println("您选择了与" + computer.name + "对战");
    }

    public void start() {
        init();
        Scanner input = new Scanner(System.in);
        String isContinue;
        do {
            int userFist = user.showFist();
            int computerFist = computer.showFist();
            calcResult(userFist, computerFist);
            System.out.println("输入 y 继续，输入其他结束。");
            isContinue = input.next();
        } while ("y".equals(isContinue));
        showResult(user, computer);
    }

    //计算每轮的结果
    public void calcResult(int userFist, int computerFist) {
        if ((userFist == 1 && computerFist == 3) || (userFist == 2 && computerFist == 1) || (userFist == 3 && computerFist == 2)) {
            System.out.println("您赢了！");
            user.score++;
        } else if ((userFist == 3 && computerFist == 1) || (userFist == 1 && computerFist == 2) || (userFist == 2 && computerFist == 3)) {
            System.out.println("您输了");
            computer.score++;
        } else {
            System.out.println("平局。");
        }
    }

    //显示最终结果
    public void showResult(User user, Computer computer) {
        System.out.println(user.name + "最终得分为：" + user.score);
        System.out.println(computer.name + "最终得分为：" + computer.score);
        if (user.score > computer.score)
            System.out.println(user.name + "赢得了最终的胜利！");
        else if (user.score < computer.score)
            System.out.println(computer.name + "赢得了最终的胜利！");
        else
            System.out.println("最终结果为平局。");
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
