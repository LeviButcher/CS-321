package edu.wscc.group2.guessinggameproject9group2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity {

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
    public void onConfigurationChanged(Configuration _config){
        super.onConfigurationChanged(_config);
        checkDeminsions(_config);
    }
    public void checkDeminsions(Configuration _config){
        if(_config.orientation == ORIENTATION_PORTRAIT){
            BuildGui(true);
        }else{
            BuildGui(false);
        }
    }
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
            int firstWidth = (int) (screenWidth * .55);
            topParams = relativeLayoutParamsWidth(firstWidth);
            botParams = relativeLayoutParamsHeight((screenWidth - firstWidth));
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

        fragmentTransaction.add(topRl.getId(), topFragment);
        fragmentTransaction.add(botRl.getId(), botFragment);
        fragmentTransaction.commit();
    }
    private static RelativeLayout.LayoutParams relativeLayoutParamsHeight(int height){
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, height);
    }
    private static RelativeLayout.LayoutParams relativeLayoutParamsWidth(int width){
        return new RelativeLayout.LayoutParams(width, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

}
