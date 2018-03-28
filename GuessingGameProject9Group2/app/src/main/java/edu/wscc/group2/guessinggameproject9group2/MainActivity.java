package edu.wscc.group2.guessinggameproject9group2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.RelativeLayout;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * Sets up the GuessingGame App
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(this.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT){
            BuildGui(true);
        }else{
            BuildGui(false);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpGame();
    }

    public void onConfigurationChanged(Configuration _config){
        super.onConfigurationChanged(_config);
        checkDimensions(_config);
    }
    public void checkDimensions(Configuration _config){
        if(_config.orientation == ORIENTATION_PORTRAIT){
            BuildGui(true);
        }else{
            BuildGui(false);
        }
    }

    /**
     * Configures the view passed on whether the device is in horizontal or vertical orientation
     * if vertical then pass in true, if not pass in false
     *
     * @param _orientation true if vertical false if not
     */
    public void BuildGui(Boolean _orientation){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        RelativeLayout.LayoutParams topParams, botParams;
        RelativeLayout topRl = findViewById(R.id.top_fragment_container);
        RelativeLayout botRl = findViewById(R.id.bottom_fragment_container);
        if(_orientation){
            int firstHeight = (int)(screenHeight * .65);
            topParams = relativeLayoutParamsHeight(firstHeight);
            botParams = relativeLayoutParamsHeight((screenHeight - firstHeight));
            botParams.addRule(RelativeLayout.BELOW, topRl.getId());
        }else {
            int firstWidth = (int) (screenWidth * .65);
            topParams = relativeLayoutParamsWidth(firstWidth);
            botParams = relativeLayoutParamsWidth((screenWidth - firstWidth));
            botParams.addRule(RelativeLayout.RIGHT_OF, topRl.getId());
        }
        topRl.setLayoutParams(topParams);
        botRl.setLayoutParams(botParams);
        topRl.setBackgroundColor(Color.parseColor("#d7d7ed"));
        botRl.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


        // create a new fragmentManager
        FragmentManager fragmentManger = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManger.beginTransaction();

        TopFragment topFragment = new TopFragment();
        BottomFragment botFragment = new BottomFragment();
        NumberGuesserFragment guesserFragment = new NumberGuesserFragment();

        Log.d(TAG, "Id is" + topRl.getId());
        fragmentTransaction.add(topRl.getId(), topFragment, "Top Fragment");
        fragmentTransaction.add(botRl.getId(), botFragment, "Bot Fragment");
        fragmentTransaction.add(guesserFragment, "Guess Fragment");
        fragmentTransaction.commit();

    }

    private static RelativeLayout.LayoutParams relativeLayoutParamsHeight(int height){
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
    }

    private static RelativeLayout.LayoutParams relativeLayoutParamsWidth(int width){
        return new RelativeLayout.LayoutParams(width, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }


    private void setUpGame()
    {
        Log.d(TAG, "Id of R.layout.bottomlayout" + R.layout.bottom_layout);
        BottomFragment bottomFragment = (BottomFragment) getFragmentManager().findFragmentByTag("Bot Fragment");

        bottomFragment.getUserGuessInput().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.d(TAG, editable.toString());
                NumberGuesserFragment guesserFragment = (NumberGuesserFragment) getFragmentManager().findFragmentByTag("Guess Fragment");
                TopFragment topFragment =(TopFragment) getFragmentManager().findFragmentByTag("Top Fragment");
                topFragment.resetBuzzers();
                Log.d(TAG, "Random Number is" + topFragment.getRandomNumber());
                int numberEntered;
                try
                {
                     numberEntered = Integer.parseInt(editable.toString());
                }
                catch(Exception e)
                {
                    Log.d(TAG, e.toString());
                    return;
                }

                guesserFragment.setNumberToGuess(topFragment.getRandomNumber());
                int result = guesserFragment.guessNumber(numberEntered);

                if(result == 0){
                    topFragment.toggleJustRight();
                }
                else if(result < 0){
                    topFragment.toggleTooHigh();
                }
                else{
                    topFragment.toggleTooLow();
                }
            }
        });
    }
}
