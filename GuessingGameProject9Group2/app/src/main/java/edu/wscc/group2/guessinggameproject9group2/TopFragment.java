package edu.wscc.group2.guessinggameproject9group2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;


/**
 * Represents the top Fragment in the NumberGuesser App<br/>
 *
 * contains two fields for inserting the low and high values of random number generated<br/>
 *
 * and contains buzzers containing the text TooHigh, TooLow, Correct. Methods are available for setting the background
 * for each and resetting the background back to defaults<br/>
 */
public class TopFragment extends Fragment {

    private final String TAG = "TopFragment";
    private final String NUMBER_GENERATOR = "Number Generator";

    private TextView tooLow;
    private TextView tooHigh;
    private TextView justRight;
    private int unHighlighted = Color.DKGRAY;
    private int toggleColor = Color.GREEN;
    private int incorrectColor = Color.parseColor("#f6b307");
    private int correctColor = Color.parseColor("#369a2c");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Number generator is only used by this fragment
        FragmentManager fragmentManger = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManger.beginTransaction();
        RandomNumberFragment rnf = new RandomNumberFragment();
        fragmentTransaction.add(rnf, NUMBER_GENERATOR);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        Activity _this = getActivity();
        View v = inflater.inflate(R.layout.top_fragment, container, false);
        RelativeLayout theMainRelative = v.findViewById(R.id.top_relative_layout);

        LinearLayout editTextLl = new LinearLayout(_this);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        editTextLl.setLayoutParams(linearParams);
        editTextLl.setPadding(30,30,30,30);

        EditText minValue = new EditText(_this);
        EditText maxValue = new EditText(_this);
        Space spacer = new Space(_this);
        spacer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, .20f));
        editTextLl.addView(minValue);
        editTextLl.addView(spacer);
        editTextLl.addView(maxValue);
        editTextStyle(minValue);
        editTextStyle(maxValue);
        minValue.setHint("Min. Value (default 1)");
        maxValue.setHint("Max Value (default 1)");
        theMainRelative.addView(editTextLl);
        RelativeLayout.LayoutParams forBuzzers = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        forBuzzers.addRule(RelativeLayout.CENTER_VERTICAL);
        forBuzzers.addRule(RelativeLayout.CENTER_HORIZONTAL);
        theMainRelative.addView(getBuzzers(), forBuzzers);

        minValue.addTextChangedListener(new minTextWatcher(minValue));
        maxValue.addTextChangedListener(new maxTextWatcher(maxValue));



        return v;
    }

    private View getBuzzers()
    {
        Activity _this = getActivity();
        LinearLayout ll = new LinearLayout(_this);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(llParams);
        llParams.setMargins(40,40,40,40);


        LinearLayout.LayoutParams spacerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, .05f);
        Space spacer1 = new Space(_this);
        Space spacer2 = new Space(_this);
        spacer1.setLayoutParams(spacerParams);
        spacer2.setLayoutParams(spacerParams);

        tooLow = new TextView(_this);
        tooHigh = new TextView(_this);
        justRight = new TextView(_this);

        ll.addView(tooLow);
        ll.addView(spacer1);
        ll.addView(justRight);
        ll.addView(spacer2);
        ll.addView(tooHigh);


        tooLow.setText("Too Low");

        tooHigh.setText("Too High");

        justRight.setText("Correct");

        defaultBuzzerStyle(tooHigh);
        defaultBuzzerStyle(tooLow);
        defaultBuzzerStyle(justRight);

        return ll;
    }

    /**
     * Change the TooLow TextView to have it's toggle Color
     *
     * Precondition: View is rendered
     * Postcondition: TooLow EditText has a different Background Color
     */
    public void toggleTooLow(){
        tooLow.setTextColor(Color.parseColor("#FFFFFF"));
        tooLow.setBackgroundColor(incorrectColor);
    }


    /**
     * Change the TooHigh TextView to have it's toggle Color
     *
     * Precondition: View is rendered
     * Postcondition: EditText has a different Background Color
     */
    public void toggleTooHigh(){
        tooHigh.setTextColor(Color.parseColor("#FFFFFF"));
        tooHigh.setBackgroundColor(incorrectColor);
    }

    /**
     * Change the Correct TextView to have it's toggle Color
     *
     * Precondition: View is rendered
     * Postcondition: EditText has a different Background Color
     */
    public void toggleJustRight(){
        justRight.setBackgroundColor(correctColor);
        justRight.setTextColor(Color.parseColor("#FFFFFF"));
    }

    /**
     * Reset all the buzzers to the there original background color
     *
     * Precondition: View is rendered
     * Postcondition: All Buzzers are back to there original background
     */
    public void resetBuzzers(){
        tooLow.setBackgroundColor(unHighlighted);
        tooHigh.setBackgroundColor(unHighlighted);
        justRight.setBackgroundColor(unHighlighted);
    }

    public static void defaultBuzzerStyle(TextView _inTextView){
        View theParent = (View)_inTextView.getParent();
        _inTextView.setBackgroundColor(Color.parseColor("#d8b7ca"));
        _inTextView.setTextColor(Color.parseColor("#d7d7ed"));
        _inTextView.setTextSize(20);
        _inTextView.setPadding(30, 10,30,10);
        _inTextView.setGravity(Gravity.CENTER);
        _inTextView.setAllCaps(true);

        _inTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, .30f));
    }



    public static void editTextStyle(EditText _theField){
        View theParent = (View)_theField.getParent();
        _theField.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, .40f));
        _theField.setTextColor(Color.parseColor("#333333"));
        _theField.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        _theField.setTextSize(18);
    }


    /**
     * Retrieves the random number that has been generated
     *
     * @return Random Number
     */
    public int getRandomNumber(){
        RandomNumberFragment ngf = (RandomNumberFragment) getFragmentManager().findFragmentByTag(NUMBER_GENERATOR);
        return ngf.getGeneratedNumber();
    }



    private class minTextWatcher implements TextWatcher {
        private int minValue = 1;
        private EditText ChangedText;

        public minTextWatcher(EditText inEditText){
            try
            {
                String theInput = inEditText.toString();
                minValue = Integer.parseInt(theInput);
            }
            catch(NumberFormatException nfe){
                System.out.println(nfe);
            }
        }
        @Override
        public void afterTextChanged(Editable e){
            // the Class to update the minimum value;
            RandomNumberFragment ngf = (RandomNumberFragment) getFragmentManager().findFragmentByTag(NUMBER_GENERATOR);
            try{
                minValue = Integer.parseInt(e.toString());
                ngf.setLow(minValue);
                Log.d(TAG, "Min Value is" + minValue);
                ngf.generateRandomNumber();
            }
            catch(NumberFormatException nfe)
            {
                Log.d(TAG, nfe.toString());
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){};
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }


    private class maxTextWatcher implements TextWatcher {
        private int maxValue = 1;
        private EditText ChangedText;

        public maxTextWatcher(EditText inEditText){
            try
            {
                //Constructor is only ever called apon instantiation, really no point to it as far as I can tell
                String theInput = inEditText.toString();
                maxValue = Integer.parseInt(theInput);
                Log.d(TAG, "Construct maxValue is " + maxValue);
            }
            catch(NumberFormatException nfe){
                System.out.println(nfe);
                Log.d(TAG, nfe.toString());
            }
        }
        @Override
        public void afterTextChanged(Editable e){
            // Update the Maximum value;
            RandomNumberFragment ngf = (RandomNumberFragment) getFragmentManager().findFragmentByTag(NUMBER_GENERATOR);
            Log.d(TAG, "Editable is" + e.toString());
            try{
                maxValue = Integer.parseInt(e.toString());
                ngf.setHigh(maxValue);
                Log.d(TAG, "Max Value is" + maxValue);
                ngf.generateRandomNumber();
            }
            catch(NumberFormatException nfe)
            {
                Log.d(TAG, nfe.toString());
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){};
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }
}