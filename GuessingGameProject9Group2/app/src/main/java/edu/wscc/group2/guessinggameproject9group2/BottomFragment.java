package edu.wscc.group2.guessinggameproject9group2;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Represents the Bottom Fragment of the NumberGuesserApp
 *
 * Takes up 25 percent of the screen real estate and containes methods for retrieving its
 * Edit Text Field
 *
 * @author Zach & Levi
 */
public class BottomFragment extends Fragment {

    private EditText userGuessInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setUpFragmentGui(container);
        View v = inflater.inflate(R.layout.bottom_layout,container, false);

        return v;
    }

    /**
     * Creates the Input field and inserts it into the View
     * @param container ViewGroup to insert EditText into
     */
    private void setUpFragmentGui(ViewGroup container){

        if(userGuessInput == null){
            userGuessInput = new EditText(getActivity());
            userGuessInput.setGravity(Gravity.CENTER_VERTICAL);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            userGuessInput.setLayoutParams(lp);
            userGuessInput.setHint("Enter Guess");
            userGuessInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            userGuessInput.setTextSize(25);
            userGuessInput.setTextColor(Color.parseColor("#FFFFFF"));
            userGuessInput.setHintTextColor(Color.parseColor("#d7d7ed"));
            userGuessInput.setPadding(50, 40, 50, 40);
            userGuessInput.setGravity(Gravity.CENTER);
            container.addView(userGuessInput);
        }
    }

    /**
     * Returns the EditText
     * @return
     */
    public EditText getUserGuessInput(){
          return userGuessInput;
    }
}