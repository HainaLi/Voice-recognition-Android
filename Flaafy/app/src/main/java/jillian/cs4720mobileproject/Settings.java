package jillian.cs4720mobileproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by monica on 11/21/14.
 */
public class Settings extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.settings, container, false);
        final Intent intent = new Intent(getActivity(), Home.class);

        SeekBar correctness = (SeekBar)(rootview.findViewById(R.id.correctness));

        correctness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                intent.putExtra("correctness", String.valueOf(progress));
                System.out.println(String.valueOf(progress));
                //Activity.setResult(Activity.RESULT_OK, intent);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

        });
        return rootview;
    }


}
