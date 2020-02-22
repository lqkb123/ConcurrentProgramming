package com.liqiang.concurrent.chpater3;

/**
 * 双重检查锁定
 * 多线程下，会出现读取到未初始化完成的对象（重排序后，单线程可以保证结果一致，但是其他线程会看见未初始化完的对象）
 */
public class DoubleCheckedLocking {
    private static Instance instance;

    public static Instance getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedLocking.class) {
                if (instance == null) {
                    instance = new Instance();
                }
            }
        }
        return instance;
    }
}
