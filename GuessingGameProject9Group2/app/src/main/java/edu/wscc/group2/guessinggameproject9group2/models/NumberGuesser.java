package edu.wscc.group2.guessinggameproject9group2.models;

/**
 * Encompasses hiding a value to be guessed then allowing
 * being able to guess the value that was hidden before<br/>
 *
 * A value should be hidden before guess the value or the value hidden will just
 * be 0<br/>
 *
 * For determine if the value is Guessed the compareTo Method is used. If you guess to high a
 * negative number will be returned, if a guess was to low a positive value about 0 will be returned.
 * If the guess was correct then 0 will be returned.
 *
 * @author Zach and Levi
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
     * x is the hidden number field
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
