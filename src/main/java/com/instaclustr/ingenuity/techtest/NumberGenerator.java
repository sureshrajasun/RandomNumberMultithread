package com.instaclustr.ingenuity.techtest;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * NumberGenerator.java -  The number generator class which will produce the numbers for the multiple thread calls.
 *                          and push the values into a blocking queue.
 * @author  Sureshraja
 * @version 1.0
 * @see {@link NumberGenerator } & {@link NumberProcessor}
 *
 *
 * Design Explanation:
 * =====================
 * Created the NumberGenerator as a Runnable to apply multithreading mechanisam so that the Runner class create multiple threads on this class to perfrom action.
 *
 * Created a constructor to assign the queue to the instance variable to have/share the copy of the queue instance from runner class. Which will be shared by the Processsor as well.
 *
 * Desiged this class a Producer class as of Producer consumer problem. Since the producer need to add the Random Numbers in a queue,
 * got the Queue instance from the Runner class as this queue will be used by the NumberProcessor as well.
 *
 * When the queue reached the maximum size of it's capacity, put wait on the queue instance so that other thread will wait until there is a space
 * to add a number in that queue, notify other threads to perform action otherwise.
 *  while (sharedQueue.size() == Constants.MAX_QUEUE_LEN) {
 *      sharedQueue.wait();
 *  }
 *  sharedQueue.notify();
 *
 */


public class NumberGenerator implements Runnable {

    private ArrayBlockingQueue<Byte> sharedQueue;

    public NumberGenerator(ArrayBlockingQueue<Byte> sharedQueue) {
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

        byte producedItem = (byte)RandomNumberGeneratorUtil
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