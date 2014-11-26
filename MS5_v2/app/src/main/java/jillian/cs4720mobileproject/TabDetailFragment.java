package jillian.cs4720mobileproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


import jillian.cs4720mobileproject.dummy.DummyContent;

/**
 * A fragment representing a single Tab detail screen.
 * This fragment is either contained in a {@link TabListActivity}
 * in two-pane mode (on tablets) or a {@link TabDetailActivity}
 * on handsets.
 */
public class TabDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private DummyContent.DummyItem mItem;

    public TabDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_detail, container, false);


        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            if (mItem.id == "1") {
                rootView = inflater.inflate(R.layout.about, container, false);

            } else if (mItem.id == "2") {
                rootView = inflater.inflate(R.layout.howto, container, false);
            } else if (mItem.id == "3") {
                rootView = inflater.inflate(R.layout.settings, container, false);

            } else if (mItem.id == "4") {
                rootView = inflater.inflate(R.layout.activity_home, container, false);



            } else {
                ((TextView) rootView.findViewById(R.id.tab_detail)).setText(mItem.content);

            }
        }
        return rootView;
    }


}


