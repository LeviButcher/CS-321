package edu.wscc.group2.guessinggameproject9group2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by rober on 3/26/2018.
 */

public class TopFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.top_fragment, container, false);
        TextView testTextView = new TextView(getActivity());
        testTextView.setText("Hello World!!");
        RelativeLayout theMainRelative = v.findViewById(R.id.test_relative_layout);
        theMainRelative.addView(testTextView);

        ((TextView) v.findViewById(R.id.tvFragText)).setText("Whats Going on??");
        return v;
    }
}
