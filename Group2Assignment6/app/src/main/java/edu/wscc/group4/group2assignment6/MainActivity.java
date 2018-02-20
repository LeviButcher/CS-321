package edu.wscc.group4.group2assignment6;

import android.content.res.Configuration;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private final int BUTTON_HEIGHT = 300;
    private int buttonWidth;

    private boolean veriicalDimensionSet;
    private boolean horizontalDimenSet;



    private final String[] tipStrings = {"10%", "15%","18%","20%", "25%", "Custom Amount"};

    private EditText totalBill, numOfGuests;
    private RelativeLayout.LayoutParams totalBillParams, numOfGuestsParams, gridParams, buttonParams;
    private RelativeLayout rl;

    private GridLayout theButtonGrid;

    private int elementWidth;
    private int elementPadding;

    private ArrayList<Button> buttonList = new ArrayList<Button>();
    private Point size = new Point();
    private Button calculate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildGui();
        Configuration config = getResources().getConfiguration();
        checkDeminsions(config);
    }
    public void buildGui() {
        getWindowManager().getDefaultDisplay().getSize(size);
        elementWidth = (int) (size.x * .9);
        elementPadding = (int) (size.x * .05);
        ScrollView sv = new ScrollView(this);
        rl = new RelativeLayout(this);

        totalBill = new EditText(this);
        totalBill.setId(View.generateViewId());
        totalBill.setHint("Bill Total ($ XX.XX )");
        totalBill.addTextChangedListener(new NumberTextWatcher(totalBill));
        totalBill.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        numOfGuests = new EditText(this);
        numOfGuests.setId(View.generateViewId());

        totalBillParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        rl.addView(totalBill, totalBillParams);

        numOfGuestsParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        numOfGuests.setInputType(InputType.TYPE_CLASS_NUMBER);
        numOfGuests.setHint("Number of Guests (2 is Default)");
        rl.addView(numOfGuests, numOfGuestsParams);


        theButtonGrid = new GridLayout(this);
        theButtonGrid.setId(View.generateViewId());
        for(int i = 0; i < tipStrings.length; i++){
            buttonList.add(new Button(this));
            buttonList.get(i).setText(tipStrings[i]);
            buttonList.get(i).setHeight(BUTTON_HEIGHT);
        }

        gridParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gridParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        gridParams.addRule(RelativeLayout.BELOW, numOfGuests.getId());
        gridParams.topMargin = elementPadding;
        gridParams.leftMargin = elementPadding;
        gridParams.width = elementWidth;
        rl.addView(theButtonGrid, gridParams);

        calculate = new Button(this);
        calculate.setText("Calculate the Tip");

        calculate.setBackgroundColor(0xff0e8b0d);
        calculate.setTextColor(0xFFFFFFFF);
        buttonParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        buttonParams.addRule(RelativeLayout.BELOW, theButtonGrid.getId());

        rl.addView(calculate, buttonParams);
        sv.addView(rl);
        setContentView(sv);
    }
    public void onConfigurationChanged(Configuration _config){
        super.onConfigurationChanged(_config);
        checkDeminsions(_config);

    }
    public void checkDeminsions(Configuration _config){

        if(_config.orientation == Configuration.ORIENTATION_PORTRAIT){
            getWindowManager().getDefaultDisplay().getSize(size);
            elementWidth = (int) (size.x * .9);
            elementPadding = (int) (size.x * .05);

            totalBillParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            totalBillParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            totalBillParams.topMargin = elementPadding;
            totalBillParams.leftMargin = elementPadding;
            totalBillParams.width = elementWidth;

            numOfGuestsParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            numOfGuestsParams.addRule(RelativeLayout.BELOW, totalBill.getId());
            numOfGuestsParams.topMargin = elementPadding;
            numOfGuestsParams.leftMargin = elementPadding;
            numOfGuestsParams.width = elementWidth;

            gridParams.topMargin = elementPadding;
            gridParams.leftMargin = elementPadding;
            gridParams.width = elementWidth;

            theButtonGrid.removeAllViews();
            theButtonGrid.setColumnCount(2);
            theButtonGrid.setRowCount(3);
            buttonWidth = (int) (elementWidth / 2);
            buttonList = new ArrayList<Button>();
            for(int i = 0; i < tipStrings.length; i++){
                buttonList.add(new Button(this));
                buttonList.get(i).setText(tipStrings[i]);
                buttonList.get(i).setWidth(buttonWidth);
                buttonList.get(i).setHeight(BUTTON_HEIGHT);
                theButtonGrid.addView(buttonList.get(i));
            }

            calculate.setWidth(elementWidth);
            buttonParams.topMargin = elementPadding;
            buttonParams.leftMargin = elementPadding;

        }else if(_config.orientation == Configuration.ORIENTATION_LANDSCAPE){
            getWindowManager().getDefaultDisplay().getSize(size);
            elementWidth = (int) (size.x * .9);
            elementPadding = (int) (size.x * .05);

            totalBillParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            totalBillParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            totalBillParams.topMargin = elementPadding;
            totalBillParams.leftMargin = elementPadding;
            totalBillParams.width = (int)(elementWidth * .47);

            numOfGuestsParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            numOfGuestsParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            numOfGuestsParams.topMargin = elementPadding;
            numOfGuestsParams.leftMargin = elementPadding;
            numOfGuestsParams.rightMargin = elementPadding;
            numOfGuestsParams.width =  (int)(elementWidth * .47);

            gridParams.topMargin = elementPadding;
            gridParams.leftMargin = elementPadding;
            gridParams.width = elementWidth;

            theButtonGrid.removeAllViews();
            theButtonGrid.setColumnCount(3);
            theButtonGrid.setRowCount(2);
            buttonWidth = (int) (elementWidth / 3);
            buttonList = new ArrayList<Button>();
            for(int i = 0; i < tipStrings.length; i++){
                buttonList.add(new Button(this));
                buttonList.get(i).setText(tipStrings[i]);
                buttonList.get(i).setWidth(buttonWidth);
                buttonList.get(i).setHeight(BUTTON_HEIGHT);
                theButtonGrid.addView(buttonList.get(i));
            }

            calculate.setWidth(elementWidth);
            buttonParams.topMargin = elementPadding;
            buttonParams.leftMargin = elementPadding;
            buttonParams.bottomMargin = elementPadding;
        }
    }

    /**
     * https://stackoverflow.com/questions/28757931/how-to-format-the-input-of-edittext-when-typing-with-thousands-separators-in
     */
    public class NumberTextWatcher implements TextWatcher {
        private DecimalFormat df;
        private DecimalFormat dfnd;
        private boolean hasFractionalPart;

        private EditText et;

        public NumberTextWatcher(EditText et){
            df = new DecimalFormat("#,###.##");
            df.setDecimalSeparatorAlwaysShown(true);
            dfnd = new DecimalFormat("#,###");
            this.et = et;
            hasFractionalPart = false;
        }
        @SuppressWarnings("unused")
        private static final String TAG = "NumberTextWatcher";

        @Override
        public void afterTextChanged(Editable s){
            et.removeTextChangedListener(this);
            try{
                int inilen, endlen;
                inilen = et.getText().length();
                String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                Number n = df.parse(v);
                int cp = et.getSelectionStart();
                if(hasFractionalPart){
                    et.setText(df.format(n));
                }else{
                    et.setText(dfnd.format(n));
                }
                endlen = et.getText().length();
                int sel =(cp + (endlen - inilen));
                if(sel > 0 && sel <= et.getText().length()){
                    et.setSelection(sel);
                }else{
                    et.setSelection(et.getText().length() - 1);
                }
            }catch (NumberFormatException nfe){

            }catch(ParseException e){

            }
            et.addTextChangedListener(this);
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count){
            if(s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))){
                hasFractionalPart = true;
            }else{
                hasFractionalPart = false;
            }
        }
    }
}