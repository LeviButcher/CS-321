package edu.wscc.group2.guessinggameproject9group2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int firstHeight = (int)(size.y * .75);
        System.out.println("75% height is " + firstHeight);


        FragmentManager fragmentManger = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManger.beginTransaction();

        TopFragment topFragment = new TopFragment();

        fragmentTransaction.add(R.id.top_fragment_container, topFragment);
        fragmentTransaction.commit();

    }
}
