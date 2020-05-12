package com.zjy.study.modules.java8.stream;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class DealStream {

    public static void main(String[] args) {

        String msg = "my name is bob";

        // 获取到每个单词长度
        Stream.of(msg.split(" ")).filter(s-> s.length()>2).map(String::length).forEach(System.out::println);

        //flatMap A->B属性（是个集合），最终得到所有A元素里面的所有B属性的集合
        // intStream/longStream 并不是Stream的子类，所以需要进行装箱 boxed()
        Stream.of(msg.split(" ")).flatMap(s ->s.chars().boxed()).forEach( i -> System.out.println((char)i.intValue()));

        // peek 可以中间消费
        Stream.of(msg.split(" ")).peek(System.out::println).forEach(System.out::println);

        new Random().ints().filter( i -> i >1000 && i < 10000).limit(10).forEach(System.out::println);


        //---------中断操作----------
        // 使用并行流
        msg.chars().parallel().forEach(s -> System.out.print((char)s));
        System.out.println("\n------------");
        // 使用并行流 forEachOrdered 保证顺序
        msg.chars().parallel().forEachOrdered(s -> System.out.print((char)s));


        List<String> list = Stream.of(msg.split(" ")).collect(Collectors.toList());

        Map<Boolean, List<String>> collect = list.stream().collect(Collectors.partitioningBy(s -> s.equals("")));
        // reduce 拼接字符串
        Optional<String> reduce = Stream.of(msg.split(" ")).reduce((s1, s2) -> {
            System.out.println(String.format("%s:%s", s1, s2));
            return s1 + "|" + s2;
        });
        System.out.println(reduce.orElse(""));


        // --------并行流---------

        // .sequential() 为串流，不能与并行流同时使用，否则以后者为准
        // 并行流使用的线程池为 ForkJoinPool.commonPool
        // 默认的线程池个数为当前CPU的核数
        // 使用下面属性修改默认线程数--必须得在commonPool初始化之前注入进去,否则无法生效,此处因为上边代码已经初始化，所以在这不生效
        //System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");
        //IntStream.range(1,100).parallel().peek(DealStream::out).count();

        //使用自己的线程池，不使用默认的线程池，防止线程阻塞
        // 线程名称-ForkJoinPool-1
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        forkJoinPool.execute(()->IntStream.range(1,15).parallel().peek(DealStream::out).count());
        forkJoinPool.shutdown();

        // 防止主线程退出，forkJoinPool 线程也退出
        synchronized (forkJoinPool){
            try {
                forkJoinPool.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public static void out(int i){
        System.out.println(Thread.currentThread().getName() + " out"  + i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
