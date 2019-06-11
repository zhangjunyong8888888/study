package com.zjy.study.modules.java8.lambda;


import java.text.DecimalFormat;
import java.util.function.Function;


class Money{
    private final int money;

    public Money(int money){
        this.money=money;
    }

    public void printMoney(Function<Integer,String> format){
        System.out.println("金额为：" + format.apply(money));
    }

}

public class MoneyDemo {

    public static void main(String[] args) {
        Money money = new Money(9999);
        money.printMoney((i)->new DecimalFormat("#,###").format(i));
    }
}
