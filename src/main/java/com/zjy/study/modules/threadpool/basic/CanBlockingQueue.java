package com.zjy.study.modules.threadpool.basic;

import com.zjy.study.modules.utils.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @ClassName : CanBlockingQueue
 * @Description : 线程池可选择的阻塞队列
 * 1、无界队列
 * 2、有界队列
 * 3、同步移交队列
 * @Author : zjy
 * @Date: 2020-05-16 16:37
 */
@Slf4j
public class CanBlockingQueue {


    /**
     * 有界队列，初始容量为10-- 如果不传容量大小则为无界队列
     *
     * @throws InterruptedException
     */
    @Test
    public void haveCapacityBlockingQueue() throws InterruptedException {
        // 基于ArrayList的 有界队列
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        // 基于LinkedList的 有界队列
        //LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(10);
        for (int i = 0; i < 20; i++) {
            arrayBlockingQueue.put(i);
            log.info("向队列中成功插入:{}", i);
            if (i % 2 == 0) {
                Integer poll = arrayBlockingQueue.poll();
                log.info("从队列中成功取出:{}", poll);
            }
        }
    }

    /**
     * 同步移交阻塞队列
     */
    @Test
    public void synchronizedQueue() {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(() -> {
            try {
                synchronousQueue.put(1);
                log.info("插入移交队列成功...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.execute(() -> {
            ThreadUtil.sleep(5);
            Integer poll = synchronousQueue.poll();
            log.info("移交队列取出成功...-{}",poll);
        });

        ThreadUtil.sleep(20);
    }

    /**
     * 线程池
     */
    private static ThreadPoolExecutor executor =
            new ThreadPoolExecutor(
                    // 核心线程数和最大线程数
                    2, 3,
                    // 线程空闲后的存活时间
                    60L, TimeUnit.SECONDS,
                    // 有界阻塞队列
                    new LinkedBlockingQueue<Runnable>(5));

    public static void main(String[] args) {
        // 设置饱和策略为 终止策略
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.AbortPolicy());
    }



}
