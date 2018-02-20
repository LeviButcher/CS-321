package edu.wscc.group4.group2assignment6;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";
    private TextView tipAmount, totalBill,tipPerGuest,totalCostPerGuest;
    private TextView guestAmount, baseBillAmount, baseTipAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void onStart()
    {
        super.onStart();
        Configuration config = getResources().getConfiguration();
        modifyLayout(config);
        updateGUI();
    }

    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        modifyLayout(newConfig);
        updateGUI();
    }

    public void modifyLayout(Configuration newConfig)
    {
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Log.w(TAG, "Horizontal Position");
            setContentView(R.layout.landscape_result);
            updateViewReferences();
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Log.w(TAG, "Portrait Position");
            setContentView(R.layout.portrait_result);
            updateViewReferences();
        }
        else
        {
            Log.w(TAG, "Wacky screen or something");
        }
    }

    public void updateViewReferences()
    {
        //Set up Results References
        tipAmount = findViewById(R.id.totalTip);
        totalBill = findViewById(R.id.TotalBill);
        tipPerGuest = findViewById(R.id.tipPerGuest);
        totalCostPerGuest = findViewById(R.id.TotalCostPerGuest);
        guestAmount = findViewById(R.id.guestAmount);
        baseBillAmount =findViewById(R.id.baseBill);
        baseTipAmount=findViewById(R.id.tipPercent);
    }

    public void updateGUI()
    {
        tipAmount.setText(MainActivity.tipCalc.formattedTotalTip());
        totalBill.setText(MainActivity.tipCalc.formattedTotalBill());
        tipPerGuest.setText(MainActivity.tipCalc.formattedTipPerGuest());
        totalCostPerGuest.setText(MainActivity.tipCalc.formattedTotalAmountPerGuest());
        guestAmount.setText(MainActivity.tipCalc.getGuests() + "");
        baseBillAmount.setText(MainActivity.tipCalc.getFormattedBill());
        baseTipAmount.setText(MainActivity.tipCalc.getFormattedTipPercentage());
    }

    /**
     * Up to you if you want to leave this method
     *
     * called by button for now in landscape and portrait result
     * @param v
     */
    public void goBack(View v)
    {
        this.finish();
    }
}
