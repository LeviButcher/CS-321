package com.wvup.group2.numberguesser.models;

/**
 * Created by Levi on 3/26/2018.
 */

/**
 *  Generates random numbers and returns them
 */
public class NumberGenerator
{

    /**
     * Returns a Random number between the high and the low provided
     *
     * Based off of https://stackoverflow.com/questions/5887709/getting-random-numbers-in-java
     *
     * @param low lowest value possible
     * @param high highest value possible
     * @return A int somewhere equal to or in between the low and high
     */
    public static int generateNumber(int low, int high) {

        return (int)(Math.random() * high + low);
    }
}
