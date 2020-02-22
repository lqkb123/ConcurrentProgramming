package com.liqiang.concurrent.chpater4;

import java.util.concurrent.TimeUnit;

/**
 * 中断线程
 *
 * 在Thread中，有一些耗时操作，比如sleep()、join()、wait()等，
 * 都会在执行的时候check interrupt的状态，一旦检测到为true，立刻抛出InterruptedException。
 */
public class Interrupted {
    public static void main(String[] args) throws InterruptedException {
        //sleepThread不停地尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "SleepRunnerThread");
        sleepThread.setDaemon(true);
        //busyThread不停地运行
        Thread busyThread = new Thread(new BusyRunner(), "BusyRunnerThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();
        //休眠5秒,让sleepThread和busyThread充分运行
        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("SleepRunnerThread interrupt is: " + sleepThread.isInterrupted());
        System.out.println("BusyRunnerThread interrupt is: " + busyThread.isInterrupted());
        //防止sleepThread 和 busyThread立刻退出
        SleepUtils.second(2);
    }

    static class SleepRunner implements Runnable {

        @Override
        public void run() {
            while (true) {
                SleepUtils.second(10);
            }
        }
    }

    static class BusyRunner implements Runnable {

        @Override
        public void run() {
            while (true) {

            }
        }
    }
}
