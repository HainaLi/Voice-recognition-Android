package jillian.cs4720mobileproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;

/**
 * Created by monica on 11/30/14.
 */
public class Welcome extends Fragment {
    public Welcome()
    {
        //Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootview = inflater.inflate(R.layout.welcome, container, false);
        return rootview;
    }
}
