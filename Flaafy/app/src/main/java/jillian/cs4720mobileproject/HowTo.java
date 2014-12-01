package jillian.cs4720mobileproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by monica on 11/30/14.
 */
public class HowTo extends Fragment{

    public HowTo()
    {
        //Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootview = inflater.inflate(R.layout.howto, container, false);
        return rootview;
    }
}
