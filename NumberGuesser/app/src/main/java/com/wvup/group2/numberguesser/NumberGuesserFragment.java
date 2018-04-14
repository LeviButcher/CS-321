package com.wvup.group2.numberguesser;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wvup.group2.numberguesser.models.NumberGuesser;

/**
 * Created by Levi on 3/26/2018.
 */

public class NumberGuesserFragment extends Fragment {

    private NumberGuesser _numberGuesser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _numberGuesser = new NumberGuesser();
    }

    public NumberGuesser getNumberGuesser() {
        return _numberGuesser;
    }

    public void setNumberToGuess(int number){
        _numberGuesser.setNumberToGuess(number);
        return;
    }

    public int guessNumber(int guess){
        return _numberGuesser.compareTo(guess);
    }
}
