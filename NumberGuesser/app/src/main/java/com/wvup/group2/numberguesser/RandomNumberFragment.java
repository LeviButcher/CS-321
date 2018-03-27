package com.wvup.group2.numberguesser;

import android.app.Fragment;

import com.wvup.group2.numberguesser.models.NumberGenerator;

/**
 * Generates a RandomNumber between the low and high provided
 *
 * Created by Levi on 3/26/2018.
 */
public class RandomNumberFragment extends Fragment {

    /**
     * Generates a random number equal to or between the numbers passed int
     * @param low lowest value
     * @param high highest value
     * @return somewhere equal to or in between low and high
     */
    public int generateRandomNumber(int low, int high){
        return NumberGenerator.generateNumber(low, high);
    }

}
