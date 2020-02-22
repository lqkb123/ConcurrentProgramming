package com.liqiang.concurrent.chpater3;

/**
 * 基于类初始化的解决方案(类初始化锁，其他线程等待)
 */
public class InstanceFactory {
    public static class InstanceHolder {
        public static Instance instance = new Instance();
    }
    public static Instance getInstance(){
        return InstanceHolder.instance;
    }
}
