package com.zjy.study.modules.thread.threadpool.basic;

import com.zjy.study.modules.utils.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName : SubmitTask
 * @Description : 向线程池提交任务
 * @Author : zjy
 * @Date: 2020-05-17 13:11
 */
@Slf4j
public class SubmitTask {

    /**
     * submit -> 可以接受线程的返回值
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void submitTest() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> submit = executorService.submit(() -> {
            log.info("submit->执行任务....");
            ThreadUtil.sleep(5);
            return 2 * 100;
        });

        // Future.get() 方法是阻塞方法，直到线程有返回值
        log.info("submit->得到结果 {} ...",submit.get());
    }

    @Test
    public void executeTest(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(()->log.info("execute over ..."));
        ThreadUtil.sleep(5);
        log.info("main thread over ...");
    }
}
