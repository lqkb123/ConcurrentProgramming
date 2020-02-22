package com.liqiang.concurrent.chpater1;

/**
 * 测试串行和并行执行速度
 *
 * 实际测试结果:在该机子情况，只有count值只有达到一亿数值才有明显效果，在一亿之前并行的执行速度比串行的执行速度还慢，原因是线程有创建和上下文切换的开销
 */
public class ConcurrencyTest {
    private static final long count = 1000000000;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    private static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int a = 0;
                for (long i = 0; i < count; i++) {
                    a += 5;
                }
            }
        });
        thread.start();

        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        //thread线程没有执行完一直会阻塞在join这里，join方法的本质调用的是Object中的wait方法实现线程的阻塞
        thread.join();
        long time = System.currentTimeMillis() - start;
        System.out.println("Concurrency :" + time + "ms,b= " + b);
    }

    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("Serial :" + time + "ms,b= " + b);
    }
}
