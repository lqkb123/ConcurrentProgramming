package com.liqiang.concurrent.chpater3;

/**
 * 非线程安全的延迟初始化对象
 * 多线程情况下会出现线程看到未初始化完成的情况(因为重排序)
 */
public class UnsafeLazyInitialization {
    private static Instance instance;

    public static Instance getInstance() {
        if (instance == null) {
            instance = new Instance();
        }
        return instance;
    }
}
