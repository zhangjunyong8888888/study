package com.zjy.study.modules.springboot.threadpool.service.impl;

import com.zjy.study.modules.springboot.threadpool.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService {

    Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        logger.info("start executeAsync");
        System.out.println("异步线程要做的事情");
        System.out.println("可以在这里执行批量插入等耗时的事情");
        logger.info("end executeAsync");

    }
}
