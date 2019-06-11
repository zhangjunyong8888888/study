package com.zjy.study.modules.java8.lambda;


import java.util.function.*;

public class MethodReferenceDemo {
    public static void main(String[] args) {
        //方法引用
        Consumer<String> consumer = System.out::println;
        consumer.accept("字符串");

        // 静态方法引用
        Consumer<Dog> dogConsumer = Dog::printName;
        Dog dog = new Dog("哮天犬",10);
        dogConsumer.accept(dog);

        //非静态方法引用
        //Function<Integer,Integer> dogFunction = dog::eat;
        //UnaryOperator<Integer> dogFunction= dog::eat;
        IntUnaryOperator dogFunction= dog::eat;
        System.out.println("还剩" + dogFunction.applyAsInt(5) + "斤");

        // 使用类名调用非静态方法
        BiFunction<Dog,Integer,Integer> dogBiFunction = Dog::eat;
        System.out.println("还剩" + dogBiFunction.apply(dog,1) + "斤");

        //无参构造函数引用
        Supplier<Dog> dogSupplier = Dog::new;
        System.out.println(dogSupplier.get());

        //有参构造函数引用
        BiFunction<String,Integer,Dog> dogBiFunction1 = Dog::new;
        System.out.println(dogBiFunction1.apply("二郎神",100));

    }
}

class Dog{

    private String name = "默认名称";
    private Integer food;

    public Dog(String name,Integer food) {
        this.name = name;
        this.food = food;
    }

    public Dog() {
    }

    public static void printName(Dog dog){
        System.out.println(dog);
    }

    /**
     * JDK默认会把当前实例传入到非静态方法，参数名为this，位置第一个
     */
    public Integer eat(Dog this,Integer num){
        System.out.println("吃了" + num +"斤狗粮");
        this.food-=num;
        return this.food;
    }

    @Override
    public String toString() {
        return this.name;
    }
}