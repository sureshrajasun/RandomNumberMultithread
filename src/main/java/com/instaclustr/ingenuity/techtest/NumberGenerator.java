package com.instaclustr.ingenuity.techtest;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * NumberGenerator.java -  The number generator class which will produce the numbers for the multiple thread calls.
 *                          and push the values into a blocking queue.
 * @author  Sureshraja
 * @version 1.0
 * @see {@link NumberGenerator } & {@link NumberProcessor}
 */


public class NumberGenerator implements Runnable {

    private ArrayBlockingQueue<Integer> sharedQueue;

    public NumberGenerator(ArrayBlockingQueue<Integer> sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void produce() throws InterruptedException {

        int producedItem = RandomNumberGeneratorUtil
                .getRandomNumber(Constants.MIN_VALUE, Constants.MAX_VALUE);
        synchronized (sharedQueue) {
            while (sharedQueue.size() == Constants.MAX_QUEUE_LEN) {
                sharedQueue.wait();
            }
            sharedQueue.add(producedItem);
            sharedQueue.notify();
        }

    }
}