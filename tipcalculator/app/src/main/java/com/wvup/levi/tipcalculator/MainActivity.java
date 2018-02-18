package com.wvup.levi.tipcalculator;

import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wvup.levi.tipcalculator.models.TipCalculator;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    private TipCalculator tipCalc;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        tipCalc = new TipCalculator();
        setContentView(R.layout.activity_main);
    }

    protected void onStart()
    {
        super.onStart();
        setUpGui();
    }

    /**
     * Called when the program first start up or when the user has changed
     * the phones orientation
     */
    public void setUpGui()
    {
        //Parent Layout
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(LinearLayout.VERTICAL);

        //Child layout
        LinearLayout billLayout = new LinearLayout(this);
        billLayout.setOrientation(LinearLayout.HORIZONTAL);
        billLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Child layout
        LinearLayout tipLayout = new LinearLayout(this);
        tipLayout.setOrientation(LinearLayout.HORIZONTAL);
        tipLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Child layout
        LinearLayout guestLayout = new LinearLayout(this);
        guestLayout.setOrientation(LinearLayout.HORIZONTAL);
        guestLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Text and EditTexts
        EditText billAmount, tipAmount, guestAmount;
        TextView billAmountLabel, tipAmountLabel, guestAmountLabel, newsFeed;
        //ResultViews and Label


        //Init
        billAmountLabel = new TextView(this);
        tipAmountLabel = new TextView(this);
        guestAmountLabel = new TextView(this);
        newsFeed = new TextView(this);

        //Init
        billAmount = new EditText(this);
        tipAmount = new EditText(this);
        guestAmount = new EditText(this);

        //Set Text
        newsFeed.setText(R.string.newsFeed);
        billAmountLabel.setText(R.string.bill_label);
        tipAmountLabel.setText(R.string.tip_label);
        guestAmountLabel.setText(R.string.guest_label);

        billAmount.setText(Double.toString(tipCalc.getBill()));
        tipAmount.setText(Double.toString(tipCalc.getTipPercentage()));
        guestAmount.setText(String.format("%d", tipCalc.getGuests()));

        billAmount.setId(R.id.userBill);
        tipAmount.setId(R.id.userTip);
        guestAmount.setId(R.id.userGuest);

        TextChangeHandler tch = new TextChangeHandler();

        billAmount.setInputType(InputType.TYPE_CLASS_NUMBER |  InputType.TYPE_NUMBER_FLAG_DECIMAL);
        tipAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
        guestAmount.setInputType(InputType.TYPE_CLASS_NUMBER);

        billAmount.addTextChangedListener(tch);
        tipAmount.addTextChangedListener(tch);
        guestAmount.addTextChangedListener(tch);

        //Add to layouts
        billLayout.addView(billAmountLabel);
        billLayout.addView(billAmount);

        tipLayout.addView(tipAmountLabel);
        tipLayout.addView(tipAmount);

        guestLayout.addView(guestAmountLabel);
        guestLayout.addView(guestAmount);

        linearLayout.addView(newsFeed);
        linearLayout.addView(billLayout);
        linearLayout.addView(tipLayout);
        linearLayout.addView(guestLayout);

        addResultsView(linearLayout);
        //Set GUI
        setContentView(linearLayout);
    }

    private void addResultsView(LinearLayout linearLayout)
    {
        LinearLayout [] resultsLayouts = new LinearLayout[4];

        for(int i = 0; i < resultsLayouts.length; i++)
        {
            resultsLayouts[i] = new LinearLayout(this);
            resultsLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
            resultsLayouts[i].setGravity(LinearLayout.HORIZONTAL);
        }

        TextView tipLabel, totalBillLabel, tipPerGuestLabel, totalCostPerGuestLabel;
        TextView tip, totalBill, tipPerGuest, totalCostPerGuest;

        tipLabel = new TextView(this);
        totalBillLabel = new TextView(this);
        tipPerGuestLabel = new TextView(this);
        totalCostPerGuestLabel = new TextView(this);

        tip = new TextView(this);
        totalBill = new TextView(this);
        tipPerGuest = new TextView(this);
        totalCostPerGuest = new TextView(this);

        tip.setId(R.id.tipAmount);
        totalBill.setId(R.id.TotalBill);
        tipPerGuest.setId(R.id.tipPerGuest);
        totalCostPerGuest.setId(R.id.TotalCostPerGuest);

        tipLabel.setText(R.string.totalTip);
        totalBillLabel.setText(R.string.totalBill);
        tipPerGuestLabel.setText(R.string.tipPerGuest);
        totalCostPerGuestLabel.setText(R.string.totalCostPerGuest);

        resultsLayouts[0].addView(tipLabel);
        resultsLayouts[0].addView(tip);

        resultsLayouts[1].addView(totalBillLabel);
        resultsLayouts[1].addView(totalBill);

        resultsLayouts[2].addView(tipPerGuestLabel);
        resultsLayouts[2].addView(tipPerGuest);

        resultsLayouts[3].addView(totalCostPerGuestLabel);
        resultsLayouts[3].addView(totalCostPerGuest);

        for(int i = 0; i < resultsLayouts.length; i++)
        {
            linearLayout.addView(resultsLayouts[i]);
        }

        return;
    }

    //Called whenever user has inputted a value into a field
    public void updateGUI()
    {
        TextView tipAmount = findViewById(R.id.tipAmount);
        TextView totalBill = findViewById(R.id.TotalBill);
        TextView tipPerGuest = findViewById(R.id.tipPerGuest);
        TextView totalCostPerGuest = findViewById(R.id.TotalCostPerGuest);

        tipAmount.setText(tipCalc.calculateTotalTip()+"");
        totalBill.setText(tipCalc.calculateTotalBill()+"");
        tipPerGuest.setText(tipCalc.calculateTipPerGuest()+"");
        totalCostPerGuest.setText(tipCalc.calculateTipPerGuest()+"");
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
        EditText billView = findViewById(R.id.userBill);
        EditText tipView = findViewById(R.id.userTip);
        EditText guestView = findViewById(R.id.userGuest);
        double bill = Double.parseDouble(billView.getText().toString());
        double tip = Double.parseDouble(tipView.getText().toString());
        int guest = Integer.parseInt(guestView.getText().toString());

        tipCalc = new TipCalculator(bill, tip, guest);

        Log.w(TAG, "bill " + bill + " tip " + tip + " guest " + guest);

    }
}
