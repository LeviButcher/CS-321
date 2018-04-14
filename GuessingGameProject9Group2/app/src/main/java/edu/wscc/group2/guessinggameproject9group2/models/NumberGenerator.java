package edu.wscc.group2.guessinggameproject9group2.models;


import java.util.Random;

/**
 *  Generates random numbers and returns them
 *  @author Zach and Levi
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

        Random rand = new Random();
        return rand.nextInt((high - low) + 1) + low;
    }
}