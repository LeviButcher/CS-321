package com.wvup.group2.numberguesser.models;

/**
 * Created by Levi on 3/26/2018.
 */

public class NumberGuesser{

    private int _number;

    /**
     * Sets the hidden number field
     * @param n value to set hidden number field too
     */
    public void setNumberToGuess(int n) {
        _number = n;
    }

    /**
     * Compares the hidden number field to the number passed in
     *
     * x is the number field
     * y is the number passed in
     *
     * if x is less then y then return a value less then 0
     *
     * if x is greater then y then return a value greater then 0
     *
     * if x == y then return 0
     *
     * @param n Number to compare hidden number too
     * @return How much greater or smaller the hidden number is compared to n
     */
    public int compareTo(int n) {

        return Integer.compare(_number, n);
    }
}
