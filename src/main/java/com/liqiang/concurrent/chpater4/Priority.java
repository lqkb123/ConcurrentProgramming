package com.liqiang.concurrent.chpater4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 线程优先级
 * 线程优先级不能作为程序正确性的依赖，因为操作系统可以完全不用理会java线程对于优先级的设定
 */
public class Priority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) throws InterruptedException {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread" + i);
            thread.setPriority(priority);
            thread.start();
        }
        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;
        for (Job job : jobs) {
            System.out.println("Job Priority: " + job.priority + ", Count: " + job.jobCount);
        }
    }

    static class Job implements Runnable {
        private int priority;
        private long jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            //让其他的线程做
            while (notStart) {
                //Thread.yield()翻译成中文就是让步的意思，
                // 根据语义理解就是线程让出当前时间片给其他线程执行,使当前线程由执行状态，变成为就绪状态，让出cpu时间，重新与其他线程争夺CPU的调度权
                // 调用Thread.yield()后线程并不一定会立刻停止执行，
                // 从runnable状态切换到running状态是需要一些准备的，这个需要耗费一些时间。
                Thread.yield();
            }
            while (notEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }

}
