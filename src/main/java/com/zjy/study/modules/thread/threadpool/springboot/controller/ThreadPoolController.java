package com.zjy.study.modules.thread.threadpool.springboot.controller;

import com.zjy.study.modules.thread.threadpool.springboot.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadPoolController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("executeAsync")
    public String executeAsync(){
        asyncService.executeAsync();
        return "OK";
    }
}
