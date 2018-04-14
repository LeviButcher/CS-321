package edu.wscc.group2.guessinggameproject9group2.models;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Levi on 3/27/2018.
 */
public class NumberGeneratorTest {
    private final String TAG = "NumberGeneratorTest";
    private final int MAX = 100;

    public boolean testRandomGenerator(int low, int high)
    {
        int generated = NumberGenerator.generateNumber(low, high);
        System.out.println(TAG + "Random Number was " + generated + " For low:" + low + " and high:" + high);
        return generated >= low && generated <= high;
    }

    @Test
    public void test0To100(){
       assertTrue(testRandomGenerator(0, 100));
    }

    @Test
    public void test50To100(){
        assertTrue(testRandomGenerator(50,100));
    }

    @Test
    public void testSeriesOfNumbers(){

        for(int i = 0; i < MAX; i++)
        {
            assertTrue(testRandomGenerator(i, MAX));
        }
    }
}