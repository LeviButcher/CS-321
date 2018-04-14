package com.wvup.levi.tipcalculator;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.wvup.levi.tipcalculator.models.TipCalculator;

public class MainActivity extends AppCompatActivity
{
    private TipCalculator tipCalc;
    private static final String TAG = "MainActivity";
    private TextView tipAmount, totalBill,tipPerGuest,totalCostPerGuest;
    private EditText billView, tipView,guestView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        tipCalc = new TipCalculator();

    }

    protected void onStart()
    {
        super.onStart();
        Configuration config = getResources().getConfiguration();
        modifyLayout(config);
        updateViewReferences();

    }

    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        modifyLayout(newConfig);
        updateViewReferences();
    }

    public void modifyLayout(Configuration newConfig)
    {
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Log.w(TAG, "Horizontal Position");
            setContentView(R.layout.landscape_converter);
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Log.w(TAG, "Portrait Position");
            setContentView(R.layout.potrait_converter);
        }
        else
        {
            Log.w(TAG, "Wacky screen or something");
        }
    }

    public void updateViewReferences()
    {
        //Set up Results References
        tipAmount = findViewById(R.id.totalTipDisplay);
        totalBill = findViewById(R.id.totalBillDisplay);
        tipPerGuest = findViewById(R.id.tipPerGuestDisplay);
        totalCostPerGuest = findViewById(R.id.amountPerGuestDisplay);

        //Set up EditText References
        billView = findViewById(R.id.billUser);
        tipView = findViewById(R.id.tipUser);
        guestView = findViewById(R.id.guestUser);

        TextChangeHandler tch = new TextChangeHandler();
        billView.addTextChangedListener(tch);
        tipView.addTextChangedListener(tch);
        guestView.addTextChangedListener(tch);
    }

    //Called whenever user has inputted a value into a field
    public void updateGUI()
    {
        tipAmount = findViewById(R.id.totalTipDisplay);
        totalBill = findViewById(R.id.totalBillDisplay);
        tipPerGuest = findViewById(R.id.tipPerGuestDisplay);
        totalCostPerGuest = findViewById(R.id.amountPerGuestDisplay);

        tipAmount.setText(tipCalc.calculateTotalTip()+"");
        totalBill.setText(tipCalc.calculateTotalBill()+"");
        tipPerGuest.setText(tipCalc.calculateTipPerGuest()+"");
        totalCostPerGuest.setText(tipCalc.calculateTotalAmountPerGuest()+"");
    }




    private class TextChangeHandler implements TextWatcher
    {
        @Deprecated
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {

        }

        @Deprecated
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {

        }


        @Override
        public void afterTextChanged(Editable editable)
        {

            try
            {
                updateModel();
            }
            catch(NumberFormatException nfe)
            {
                //Alerts

            }
            finally
            {
                updateGUI();
            }

        }
    }

    public void updateModel()
    {
        billView = findViewById(R.id.billUser);
        tipView = findViewById(R.id.tipUser);
        guestView = findViewById(R.id.guestUser);
        double bill = Double.parseDouble(billView.getText().toString());
        double tip = Double.parseDouble(tipView.getText().toString());
        int guest = Integer.parseInt(guestView.getText().toString());

        tipCalc = new TipCalculator(bill, tip, guest);

        Log.w(TAG, "bill " + bill + " tip " + tip + " guest " + guest);

    }
}
