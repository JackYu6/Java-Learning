package FingerGuessing;

public class Computer {
    String name;
    int score;

    public int showFist() {
        int choice = (int) (Math.random() * 3) + 1;
        if (choice == 1)
            System.out.println(name + "出了剪刀");
        else if (choice == 2)
            System.out.println(name + "出了石头");
        else if (choice == 3)
            System.out.println(name + "出了布");
        else
            System.out.println("输入有误！");
        return choice;
    }
}
