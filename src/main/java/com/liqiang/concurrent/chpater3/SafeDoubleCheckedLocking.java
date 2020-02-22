package com.liqiang.concurrent.chpater3;

/**
 * 基于volatile的解决方案（禁止重排序来保证线程安全的延迟初始化）
 */
public class SafeDoubleCheckedLocking {
    private volatile static Instance instance;

    public static Instance getInstance() {
        if (instance == null) {
            synchronized (SafeDoubleCheckedLocking.class) {
                if (instance == null) {
                    instance = new Instance();
                }
            }
        }
        return instance;
    }
}
