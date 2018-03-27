package edu.wscc.group2.guessinggameproject9group2;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

/**
 * Created by rober on 3/26/2018.
 */

public class TopFragment extends Fragment {
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

        minValue.addTextChangedListener(new minTextWatcher(minValue));
        maxValue.addTextChangedListener(new maxTextWatcher(maxValue));



        return v;
    }
    public static void editTextStyle(EditText _theField){
        View theParent = (View)_theField.getParent();
        _theField.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, .40f));
        _theField.setTextColor(Color.parseColor("#333333"));
        _theField.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        _theField.setTextSize(18);
    }




    private class minTextWatcher implements TextWatcher {
        private int minValue;
        private EditText ChangedText;
        public minTextWatcher(EditText inEditText){
            try{
                String theInput = inEditText.toString();
                minValue = Integer.parseInt(theInput);
            }catch(NumberFormatException nfe){
                System.out.println(nfe);
            }
        }
        @Override
        public void afterTextChanged(Editable e){
            // the Class to update the minimum value;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){};
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }
    private class maxTextWatcher implements TextWatcher {
        private int maxValue;
        private EditText ChangedText;
        public maxTextWatcher(EditText inEditText){
            try{
                String theInput = inEditText.toString();
                maxValue = Integer.parseInt(theInput);
            }catch(NumberFormatException nfe){
                System.out.println(nfe);
            }
        }
        @Override
        public void afterTextChanged(Editable e){
            // Update the Maximum value;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){};
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }
}