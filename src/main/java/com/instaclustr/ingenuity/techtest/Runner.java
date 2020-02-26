package com.instaclustr.ingenuity.techtest;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

/**
 * Runner.java -  The main class which will run the multiple number generator and the single number processor.
 * @author  Sureshraja
 * @version 1.0
 * @see {@link NumberGenerator } & {@link NumberProcessor}
 *
 *  Design Explanation:
 *  =====================
 *  Runner is the stating point of the exceution of this application.
 *
 *  Used the ArrayBlockingQueue as a queue instance to store the values of a specific type. Our case its Byte so that the retrival of the
 *  number values from the queue and performing computation on the number values are very efficient since we are dealing with numbers.
 *
 *  Reson for using Blocking queue is to have the other process blocked once the queue is full.
 *
 *  Created a beginProcess() to begin the operation to get the number of threads to create value from user to proceed the NumberGenerator task with the nummber of threads
 *
 *  As per the requirment, many threads are created in createGenerators() and only one Procesor is created in createProcessors()
 */

public class Runner {

    ArrayBlockingQueue<Byte> sharedQueue = new ArrayBlockingQueue<>(Constants.MAX_QUEUE_LEN); //Creating shared object

    public static void main(String args[]) {
        new Runner().beginProcess();
    }

    private void beginProcess() {
        System.out.println("Enter the number of threads to create :: ");
        Scanner scanner = new Scanner(System.in);
        int noOfThreads = scanner.nextInt();

        createGenerators(sharedQueue, noOfThreads);
        createProcessors(sharedQueue);
    }

    private void createGenerators(ArrayBlockingQueue<Byte> sharedQueue, int noOfThreads) {
        IntStream.rangeClosed(1, noOfThreads).forEach(i -> {
            NumberGenerator generator = new NumberGenerator(sharedQueue);
            Thread producerThread1 = new Thread(generator, "Generator_" + i);
            producerThread1.start();

        });
    }

    private void createProcessors(ArrayBlockingQueue<Byte> sharedQueue) {
        NumberProcessor resultProcessor = new NumberProcessor(sharedQueue);
        Thread consumerThread0 = new Thread(resultProcessor, "Processor");
        consumerThread0.start();
    }
}
