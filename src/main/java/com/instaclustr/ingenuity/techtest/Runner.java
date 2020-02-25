package com.instaclustr.ingenuity.techtest;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

/**
 * Runner.java -  The main class which will run the multiple number generator and the single number processor.
 * @author  Sureshraja
 * @version 1.0
 * @see {@link NumberGenerator } & {@link NumberProcessor}
 */

public class Runner {

    ArrayBlockingQueue<Integer> sharedQueue = new ArrayBlockingQueue<Integer>(Constants.MAX_QUEUE_LEN); //Creating shared object

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

    private void createGenerators(ArrayBlockingQueue<Integer> sharedQueue, int noOfThreads) {
        IntStream.rangeClosed(1, noOfThreads).forEach(i -> {
            NumberGenerator generator = new NumberGenerator(sharedQueue);
            Thread producerThread1 = new Thread(generator, "Generator_" + i);
            producerThread1.start();

        });
    }

    private void createProcessors(ArrayBlockingQueue<Integer> sharedQueue) {
        NumberProcessor resultProcessor = new NumberProcessor(sharedQueue);
        Thread consumerThread0 = new Thread(resultProcessor, "Processor");
        consumerThread0.start();
    }


}
