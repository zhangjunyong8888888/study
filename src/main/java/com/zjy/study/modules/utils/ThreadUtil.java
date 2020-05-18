package com.zjy.study.modules.utils;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName : ThreadUtil
 * @Description : 线程工具类
 * @Author : zjy
 * @Date: 2020-05-17 13:17
 */
public class ThreadUtil {

    /**
     * 线程休眠 n 秒
     * @param seconds 休眠的秒数
     */
    public static void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
