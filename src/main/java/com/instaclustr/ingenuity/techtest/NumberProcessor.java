package com.instaclustr.ingenuity.techtest;

import com.google.common.primitives.Ints;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * NumberProcessor.java -  The number processor is get the last 30 values from the blocking queue and it will compute the
 *                          Min, Max, Avg and Most Common number.
 * @author  Sureshraja
 * @version 1.0
 * @see {@link NumberGenerator } & {@link NumberProcessor}
 */


public class NumberProcessor implements Runnable {
    private ArrayBlockingQueue<Integer> sharedQueue;

    public NumberProcessor(ArrayBlockingQueue<Integer> sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void consume() throws InterruptedException {

        while (sharedQueue.size() == 30) {
            synchronized (sharedQueue) {
                int[] randomNums = Ints.toArray(sharedQueue);
                int minRandomValue = Ints.min(randomNums);
                int maxRandomValue = Ints.max(randomNums);
                Integer mostCommon = RandomNumberGeneratorUtil.getMostCommonNumber(sharedQueue);
                RandomNumberGeneratorUtil.printResults(sharedQueue, minRandomValue, maxRandomValue, mostCommon);
                sharedQueue.clear();
                sharedQueue.notify();
            }
        }
    }

}