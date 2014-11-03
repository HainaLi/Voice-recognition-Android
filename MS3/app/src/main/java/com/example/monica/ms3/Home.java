package com.example.monica.ms3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.UnsupportedEncodingException;


public class Home extends Activity {

    String IPAdd = new String();
    String lights = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText ipAddress = (EditText) findViewById(R.id.IPAddress);
                if(ipAddress.getText() != null)
                {
                    IPAdd = ipAddress.getText().toString();
                }
                else
                {
                    System.out.println("No IP Address Entered!");
                }
                final EditText jsonLights = (EditText) findViewById(R.id.jsonLights);
                lights = jsonLights.getText().toString();
                try {
                    StringEntity lighters = new StringEntity(lights);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(ipAddress + "/rpi");
                    httpPost.setEntity(lighters);
                }
                catch (UnsupportedEncodingException e) {
                    System.out.println("Encoding Error!");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
