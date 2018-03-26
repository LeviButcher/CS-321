package edu.wscc.group4.group2assignment6;

import android.content.res.Configuration;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
import static android.view.View.generateViewId;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";
    private TextView tipLabel, tipAmount, totalBillLabel, totalBill, tipPerGuestLabel, tipPerGuest, totalCostPerGuestLabel, totalCostPerGuest, guestAmountLabel, guestAmount, baseBillAmountLabel, baseBillAmount, basetipAmountLabel, baseTipAmount;
    private ScrollView sv;
    private RelativeLayout rl, tips, totals, tpg, tcpg, ga, bba, bta;
    private RelativeLayout.LayoutParams LabelParams, ValueParams, tipsParams, totalsParams, tpgParams, tcpgParams, gaParams, bbaParams, btaParams, returnButtonParams;
    private Point size = new Point();
    private int elementWidth;
    private int elementPadding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void onStart()
    {
        super.onStart();
        Configuration config = getResources().getConfiguration();
        updateGUI();
        modifyLayout(config);

    }

    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        modifyLayout(newConfig);
    }

    public void modifyLayout(Configuration newConfig)
    {
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getScreenSizeSetup();
            buttonParamsSetup(returnButtonParams);
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getScreenSizeSetup();
            buttonParamsSetup(returnButtonParams);

        }
        else
        {
            Log.w(TAG, "Wacky screen or something");
        }
    }

    public void updateGUI(){
        getScreenSizeSetup();

        sv = new ScrollView(this);

        rl = new RelativeLayout(this);
        rl.setId(generateViewId());

        tips = new RelativeLayout(this);
        tips.setId(generateViewId());
        tips.setPadding(elementPadding, elementPadding, elementPadding, elementPadding);
        tipsParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tipsParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        tipsParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);


        totals = new RelativeLayout(this);
        totals.setId(generateViewId());
        totals.setPadding(elementPadding, elementPadding, elementPadding, elementPadding);
        totalsParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        totalsParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        totalsParams.addRule(RelativeLayout.BELOW, tips.getId());

        tpg = new RelativeLayout(this);
        tpg.setId(generateViewId());
        tpg.setPadding(elementPadding, elementPadding, elementPadding, elementPadding);
        tpgParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tpgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        tpgParams.addRule(RelativeLayout.BELOW, totals.getId());

        tcpg = new RelativeLayout(this);
        tcpg.setId(generateViewId());
        tcpg.setPadding(elementPadding, elementPadding, elementPadding, elementPadding);
        tcpgParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tcpgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        tcpgParams.addRule(RelativeLayout.BELOW, tpg.getId());

        ga = new RelativeLayout(this);
        ga.setId(generateViewId());
        ga.setPadding(elementPadding, elementPadding, elementPadding, elementPadding);
        gaParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gaParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        gaParams.addRule(RelativeLayout.BELOW, tcpg.getId());

        bba = new RelativeLayout(this);
        bba.setId(generateViewId());
        bba.setPadding(elementPadding, elementPadding, elementPadding, elementPadding);
        bbaParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bbaParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        bbaParams.addRule(RelativeLayout.BELOW, ga.getId());

        bta = new RelativeLayout(this);
        bta.setId(generateViewId());
        bta.setPadding(elementPadding, elementPadding, elementPadding, elementPadding);
        btaParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btaParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        btaParams.addRule(RelativeLayout.BELOW, bba.getId());


        rl.addView(tips, tipsParams);
        rl.addView(totals, totalsParams);
        rl.addView(tpg, tpgParams);
        rl.addView(tcpg, tcpgParams);
        rl.addView(ga, gaParams);
        rl.addView(bba, bbaParams);
        rl.addView(bta, btaParams);

        LabelParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LabelParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        LabelParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        LabelParams.width = (int) (elementWidth * .3);
        LabelParams.leftMargin = elementPadding;
        LabelParams.addRule(RelativeLayout.TEXT_ALIGNMENT_GRAVITY, RelativeLayout.ALIGN_LEFT);

        ValueParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ValueParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        ValueParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        ValueParams.addRule(RelativeLayout.TEXT_ALIGNMENT_GRAVITY, RelativeLayout.ALIGN_RIGHT); //Maybe this is it? /shrug
        ValueParams.width =(int) (elementWidth * .65);
        ValueParams.rightMargin = elementPadding;


        tipLabel = new TextView(this);
        tipLabel.setId(generateViewId());
        tips.addView(tipLabel, LabelParams);

        tipAmount = new TextView (this);
        tipAmount.setId(R.id.totalTip);
        tips.addView(tipAmount, ValueParams);

        totalBillLabel = new TextView(this);
        totalBillLabel.setId(generateViewId());
        totals.addView(totalBillLabel, LabelParams);

        totalBill = new TextView(this);
        totalBill.setId(R.id.TotalBill);
        totals.addView(totalBill, ValueParams);

        tipPerGuestLabel = new TextView(this);
        tipPerGuestLabel.setId(generateViewId());
        tpg.addView(tipPerGuestLabel, LabelParams);

        tipPerGuest = new TextView(this);
        tipPerGuest.setId(R.id.tipPerGuest);
        tpg.addView(tipPerGuest, ValueParams);

        totalCostPerGuestLabel = new TextView(this);
        totalCostPerGuestLabel.setId(generateViewId());
        tcpg.addView(totalCostPerGuestLabel, LabelParams);

        totalCostPerGuest = new TextView(this);
        totalCostPerGuest.setId(R.id.TotalCostPerGuest);
        tcpg.addView(totalCostPerGuest, ValueParams);

        guestAmountLabel = new TextView(this);
        guestAmountLabel.setId(generateViewId());
        ga.addView(guestAmountLabel, LabelParams);


        guestAmount = new TextView(this);
        guestAmount.setId(R.id.guestAmount);
        ga.addView(guestAmount, ValueParams);

        baseBillAmountLabel = new TextView(this);
        baseBillAmountLabel.setId(generateViewId());
        bba.addView(baseBillAmountLabel, LabelParams);

        baseBillAmount = new TextView(this);
        baseBillAmount.setId(R.id.baseBill);
        bba.addView(baseBillAmount, ValueParams);

        basetipAmountLabel = new TextView(this);
        basetipAmountLabel.setId(generateViewId());
        bta.addView(basetipAmountLabel, LabelParams);

        baseTipAmount = new TextView(this);
        baseTipAmount.setId(R.id.tipPercent);
        bta.addView(baseTipAmount, ValueParams);

        tipLabel.setText(getResources().getString(R.string.totalTip));
        formatTextView(tipLabel);
        tipAmount.setText(MainActivity.tipCalc.formattedTotalTip());
        formatTextView(tipAmount);

        totalBillLabel.setText(getResources().getString(R.string.totalBill));
        formatTextView(totalBillLabel);
        totalBill.setText(MainActivity.tipCalc.formattedTotalBill());
        formatTextView(totalBill);

        tipPerGuestLabel.setText(getResources().getString(R.string.tipPerGuest));
        formatTextView(tipPerGuestLabel);
        tipPerGuest.setText(MainActivity.tipCalc.formattedTipPerGuest());
        formatTextView(tipPerGuest);


        totalCostPerGuestLabel.setText(getResources().getString(R.string.totalCostPerGuest));
        formatTextView(totalCostPerGuestLabel);
        totalCostPerGuest.setText(MainActivity.tipCalc.formattedTotalAmountPerGuest());
        formatTextView(totalCostPerGuest);

        guestAmountLabel.setText(getResources().getString(R.string.guest_label));
        formatTextView(guestAmountLabel);
        guestAmount.setText(MainActivity.tipCalc.getGuests() + "");
        formatTextView(guestAmount);

        baseBillAmountLabel.setText(getResources().getString(R.string.bill_label));
        formatTextView(baseBillAmountLabel);
        baseBillAmount.setText(MainActivity.tipCalc.getFormattedBill());
        formatTextView(baseBillAmount);

        basetipAmountLabel.setText(getResources().getString(R.string.tip_label));
        formatTextView(basetipAmountLabel);
        baseTipAmount.setText(MainActivity.tipCalc.getFormattedTipPercentage());
        formatTextView(baseTipAmount);

        for(int i = 0; i < rl.getChildCount(); i++){
            if((i % 2) == 0){
                rl.getChildAt(i).setBackgroundColor(0xFFEEEEEE);
            }
        }

        Button returnButton = new Button (this);
        returnButtonParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); //thats my gues
        buttonParamsSetup(returnButtonParams);
        returnButton.setPadding(0, elementPadding, 0, elementPadding );
        returnButton.setBackgroundColor(0xFF8f2727);
        returnButton.setTextColor(0xFFFFFFFF);
        returnButton.setText("Return");
        rl.addView(returnButton, returnButtonParams);

        returnButton.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                goBack(v);
            }
        });
        sv.addView(rl);
        setContentView(sv);
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

    private void formatTextView(TextView _inTV){
        _inTV.setTextSize(18);
        _inTV.setGravity(RIGHT);
        _inTV.setTextColor(0xFF333333);
    }
    private void buttonParamsSetup(RelativeLayout.LayoutParams _inButtonParams){

        _inButtonParams.addRule(RelativeLayout.BELOW, bta.getId());
        _inButtonParams.topMargin = elementPadding;
        _inButtonParams.leftMargin = elementPadding;
        _inButtonParams.bottomMargin = elementPadding;
        _inButtonParams.width = elementWidth;
    }
    private void getScreenSizeSetup(){
        getWindowManager().getDefaultDisplay().getSize(size);
        elementWidth = (int) (size.x * .9);
        elementPadding = (int) (size.x * .05);
    }
}