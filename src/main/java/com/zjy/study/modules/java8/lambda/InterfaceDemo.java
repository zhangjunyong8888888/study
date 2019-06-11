package com.zjy.study.modules.java8.lambda;

@FunctionalInterface
interface Interface{

    int getInt(int i);

    default int add(int a,int b){
        return a+b;
    }
}
public class InterfaceDemo {
    public static void main(String[] args) {
        Interface interface1 = (i)->i*2;
        Interface interface2 = i->i*2;
        Interface interface3 = (int i)->i*2;
        Interface interface4 = (i)->{
            System.out.println("----");
            return i*2;
        };

        System.out.println(interface1.getInt(5));
        System.out.println(interface1.add(5,5));


    }
}
