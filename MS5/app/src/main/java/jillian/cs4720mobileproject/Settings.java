package jillian.cs4720mobileproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.*;
import android.widget.Button;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import java.io.OutputStreamWriter;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.simple.*;

/**
 * Created by monica on 11/21/14.
 */
public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        final Intent intent = new Intent();

        SeekBar correctness = (SeekBar)(findViewById(R.id.correctness));

        correctness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                intent.putExtra("correctness", String.valueOf(progress));
                System.out.println(String.valueOf(progress));
                setResult(Activity.RESULT_OK, intent);
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

    }


}
