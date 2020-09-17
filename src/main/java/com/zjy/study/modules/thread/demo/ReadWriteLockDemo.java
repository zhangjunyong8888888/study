package com.zjy.study.modules.thread.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
public class ReadWriteLockDemo {


    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    /**
     * 读锁
     */
    private static final Lock readLock = readWriteLock.readLock();

    /**
     * 写锁
     */
    private static final Lock writeLock = readWriteLock.writeLock();


      public static void main(String[] args) {
          readLock.lock();
          readLock.unlock();

          writeLock.lock();
          writeLock.unlock();
      }
}
