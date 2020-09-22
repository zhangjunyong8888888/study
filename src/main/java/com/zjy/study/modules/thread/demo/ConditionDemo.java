package com.zjy.study.modules.thread.demo;


import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition 实现消息队列
 *
 * <p>可以有多个线程等待队列
 */
public class ConditionDemo<T> {

    /**
     * 数据存储
     */
    private final LinkedList<T> dataLIst = new LinkedList<>();
    /**
     * 队列最大值
     */
    private static final Integer MAX_SIZE = 10;

    /**
     * 生产者数量
     */
    private static final Integer PRODUCER_COUNT = 10;
    /**
     * 消费者数量
     */
    private static final Integer CONSUMER_COUNT = 2;

    /**
     * CAS 锁
     */
    private final Lock lock = new ReentrantLock();

    /**
     * 消费者
     */
    private final Condition producer = lock.newCondition();
    /**
     * 消费者
     */
    private final Condition consumer = lock.newCondition();

    public static void main(String[] args) {
        ConditionDemo<Integer> container = new ConditionDemo<>();

        // 生产者线程
        for (int i = 0; i < 10; i++) {
          new Thread(()->{
              new Random().ints().limit(10).forEach(container::put);
          },String.format("P-%d",i)).start();
        }
        // 2 个消费者线程
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                while (true){
                    container.get();
                }
            },String.format("P-%d",i)).start();
        }
    }

    /**
     * 放入数据
     *
     * @param data 数据
     */
    public void put(T data) {
        String threadName = Thread.currentThread().getName();
        try {
            lock.lock();
            // 达到最大数量线程等待，wait时会释放锁，等待被消费者唤醒
            while (dataLIst.size() == MAX_SIZE) {
                System.out.println(String.format("生产者【%s】进入等待，队列已满...", threadName));
                producer.await();
                System.out.println(String.format("生产者【%s】已被唤醒...", threadName));
            }
            dataLIst.add(data);
            System.out.println(String.format("生产者【%s】成功生产消息【%s】...", threadName, data));
            // 通知消费者进行消费
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获得数据
     *
     * @return 数
     */
    public T get() {
        String threadName = Thread.currentThread().getName();
        T data = null;
        try {
            lock.lock();
            // 队列为空是线程等待 等待被是生产者唤醒
            while (dataLIst.isEmpty()) {
                System.out.println(String.format("消费者【%s】进入等待，队列为空...", threadName));
                consumer.await();
                System.out.println(String.format("消费者【%s】已被唤醒...", threadName));
            }
            data = dataLIst.removeFirst();
            System.out.println(String.format("消费者【%s】成功消费消息【%s】...", threadName, data));
            // 唤醒生产者
            producer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return data;
    }

}
