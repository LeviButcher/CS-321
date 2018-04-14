package edu.wscc.group2.guessinggameproject9group2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import edu.wscc.group2.guessinggameproject9group2.models.NumberGenerator;

/**
 * Generates a RandomNumber between or equal to the low and high provided
 *
 * Created by Zach & Levi on 3/26/2018.
 */
public class RandomNumberFragment extends Fragment {
    private final String TAG = "RandomNumberFragment";
    private int _low;
    private int _high;
    private int generatedNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _low = 0;
        _high = 0;
    }

    /**
     * Sets the Low value of the number to be generated
     *
     * @param low int value
     */
    public void setLow(int low){
        _low = low;
    }


    /**
     * Sets the High value of the number to be generated
     * @param high
     */
    public void setHigh(int high){
        _high = high;
    }

    /**
     * Generates a random number between the low and high stored within the fragment
     *
     * Precondition: Low and High have been set
     * Postcondition: None
     */
    public void generateRandomNumber(){
        try{
            generatedNumber = NumberGenerator.generateNumber(_low, _high);
        }
        catch(Exception e){
            Log.d(TAG, e.toString());
        }
    }

    /**
     * Returns the generated number that is generated when generateRandomNumber is called
     *
     * Precondition: generateRandomNumber has been called
     * Postcondition: None
     * @return The generated random number
     */
    public int getGeneratedNumber(){
        return generatedNumber;
    }

}
