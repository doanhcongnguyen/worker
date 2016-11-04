package com.alpha;

import java.util.concurrent.ExecutorService;

/**
 * Calculate 10000 first number using multi worker
 */
public class Main {

    private static int sum = 0;

    public static void main(String[] args) {
        new Main().doJob();
        System.out.println("SUM: " + sum);
    }

    public void doJob() {
        ExecutorService executor = ConcurrentUtils.initExecutor();
        for (int i = 1; i <= 10; i++) {
            this.doCalculate(executor, 1000 * (i - 1) + 1, 1000 * i);
        }
        ConcurrentUtils.stop(executor);
    }

    public void doCalculate(ExecutorService executor, int startNumber, int endNumber) {
        executor.submit(() -> {
            doExecute(startNumber, endNumber);
            System.out.println(Thread.currentThread().getName() + ": calculate from {" + startNumber + " -> " + endNumber + "} done");
        });
    }

    private void doExecute(int startNumber, int endNumber) {
        int total = 0;
        for (int i = startNumber; i <= endNumber; i++) {
            total += i;
        }
        doAddTotalToSum(total);
    }

    private synchronized void doAddTotalToSum(int total) {
        sum += total;
    }
}
