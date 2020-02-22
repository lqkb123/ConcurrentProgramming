package com.liqiang.concurrent.chpater3;

/**
 * 线程安全的延迟初始化
 * 在多线程频繁调用下性能会下降。
 * 如果不会被多线程频繁调用，那这个延迟初始化将能提供令人满意的性能
 */
public class SafeLazyInitialization {
    private static Instance instance;

    public synchronized static Instance getInstance() {
        if (instance == null) {
            instance = new Instance();
        }
        return instance;
    }
}
