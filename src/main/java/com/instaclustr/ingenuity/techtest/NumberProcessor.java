package com.instaclustr.ingenuity.techtest;

import com.google.common.primitives.Ints;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * NumberProcessor.java -  The number processor is get the last 30 values from the blocking queue and it will compute the
 *                          Min, Max, Avg and Most Common number.
 * @author  Sureshraja
 * @version 1.0
 * @see {@link NumberGenerator } & {@link NumberProcessor}
 *
 * Design Explanation:
 * =====================
 * Created the NumberProcessor as a Runnable to apply multithreading mechanisam so that the Runner class create a thread to perform the action on the shared queue.
 *
 * Created a constructor with a queue instance as a param so that the Runner can pass the shared queue instance to this processor to make use of it.
 *
 * When the Queue is full and it will be locked from adding a new value and this prcessor will monitor the capacity and once the capacity reaches, it will start computing the values
 * and once the couputation done and the results are printed then it will clear all the values from queue and it will notify other threads to proceed further action on the shared queue.s
 *
 * while (sharedQueue.size() == Constants.MAX_QUEUE_LEN) {
 *     // perfom action
 *      sharedQueue.clear();
 *      sharedQueue.notify();
 * }
 *
 *  Ints from Google's Guava used to get the minimum and maximum value of the queue. It eases lot of effort for the computation.
 *
 */


public class NumberProcessor implements Runnable {
    private ArrayBlockingQueue<Byte> sharedQueue;

    public NumberProcessor(ArrayBlockingQueue<Byte> sharedQueue) {
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

        while (sharedQueue.size() == Constants.MAX_QUEUE_LEN) {
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