package com.instaclustr.ingenuity.techtest;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multisets;
import com.google.common.math.Stats;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * RandomNumberGeneratorUtil.java - a simple class to support with util methods for random number generator
 * @author  Sureshraja
 * @version 1.0
 * @see {@link NumberGenerator } & {@link NumberProcessor}
 *
 * Design Explanation:
 * =====================
 *
 * Created the Util class to have all the supporting functions of this application inside this class.
 *
 * In getRandomNumber(), Random calss from Util package is used to generate the Random numbers in the range.
 *
 * Multisets class from Google's Guava used to get the most common number used in the queue. Also the Stats class is used to compute the average.
 *
 */

public class RandomNumberGeneratorUtil {

    public static byte getRandomNumber(int min, int max) {
        Random random = new Random();
        return (byte)random.ints(min, (max + 1)).findFirst().getAsInt();
    }

    public  static int getMostCommonNumber(ArrayBlockingQueue<Byte> sharedQueue){
       return Multisets.copyHighestCountFirst(ImmutableMultiset.copyOf(sharedQueue))
                .iterator().next();
    }

    public static String LPad(String str, Integer length, char car) {
        return (str + String.format("%" + length + "s", "").replace(" ", String.valueOf(car))).substring(0, length);
    }


    public static String RPad(String str, Integer length, char car) {
        return (String.format("%" + length + "s", "").replace(" ", String.valueOf(car)) + str).substring(str.length(), length + str.length());
    }

    public static void printResults(ArrayBlockingQueue<Byte> sharedQueue, int minRandomValue,
                             int maxRandomValue, Integer mostCommon) {
        System.out.format("%s | Min = %s | Max = %s | Avg = %s | Most Common = %s \n",
                LPad(Arrays.toString(sharedQueue.toArray()), 100, ' '),
                RPad(String.valueOf(minRandomValue), 2, ' '),
                RPad(String.valueOf(maxRandomValue), 2, ' '),
                RPad(String.format("%.2f", Stats.meanOf(sharedQueue)), 4, ' '),
                RPad(String.valueOf(mostCommon), 2, ' '));
    }

}
