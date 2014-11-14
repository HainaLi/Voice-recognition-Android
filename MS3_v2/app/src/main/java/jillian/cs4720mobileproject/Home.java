package jillian.cs4720mobileproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import java.io.OutputStreamWriter;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.simple.*;


public class Home extends Activity {

    String IPAdd = new String();
    JSONObject obj = new JSONObject();
    JSONObject actual = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Button turnLightsOnButton = (Button) findViewById(R.id.button);
        final Button startSoundDetection = (Button) findViewById(R.id.startSoundDetection);
        final Button stopSoundDetection = (Button) findViewById(R.id.stopSoundDetection);
        final EditText ipAddress = (EditText) findViewById(R.id.IPAddress);
        final TextView soundAlert = (TextView) findViewById(R.id.soundDetected);
        final SoundMeter sm = new SoundMeter();

        startSoundDetection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sm.start();
            }

        });
        stopSoundDetection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sm.stop();
                if (sm.getAmplitude() > 0) {
                    soundAlert.setText("Sound Detected!");
                }
            }

        });

        turnLightsOnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //listener for the button that turns light on
                if(ipAddress.getText() != null)
                {
                    IPAdd = ipAddress.getText().toString();
                }
                else
                {
                    System.out.println("No IP Address Entered!");
                }

                JSONArray array = new JSONArray();

                int lightId = 1;
                int red = 0;
                int green = 0;
                int blue = 255;
                double intensity = 1.0;


                obj.put("lightId", lightId);
                obj.put("red", red);
                obj.put("green", green);
                obj.put("blue", blue);
                obj.put("intensity", intensity);


                array.add(obj);
                actual.put("lights", array);
                actual.put("propagate", true);
                final String url = "http://" + IPAdd + "/rpi";

                try {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                StringBuilder sb = new StringBuilder();
                                URL connect = new URL(url);
                                System.out.println(connect);
                                HttpURLConnection urlConnection = (HttpURLConnection) connect.openConnection();
                                urlConnection.setDoOutput(true);
                                urlConnection.setRequestMethod("POST");
                                urlConnection.setUseCaches(false);
                                urlConnection.setConnectTimeout(10000);
                                urlConnection.setReadTimeout(10000);
                                urlConnection.setRequestProperty("Content-Type", "application/json");
                                urlConnection.connect();
                                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                                out.write(actual.toString());
                                out.close();

                                int HttpResult =urlConnection.getResponseCode();
                                if(HttpResult ==HttpURLConnection.HTTP_OK){
                                    BufferedReader br = new BufferedReader(new InputStreamReader(
                                            urlConnection.getInputStream(),"utf-8"));
                                    String line = null;
                                    while ((line = br.readLine()) != null) {
                                        sb.append(line + "\n");
                                    }
                                    br.close();

                                    System.out.println(""+sb.toString());

                                }else{
                                    System.out.println(urlConnection.getResponseMessage());
                                }



                                System.out.println("Done!");
                            }
                            catch (Exception e)
                            {
                                System.out.println("Internet Error Thing");
                                e.printStackTrace();
                            }
                        }
                    }.start();

                }
                catch (Exception e) {
                    Log.d("App", obj.toString());
                    e.printStackTrace();
             }
        }
        });
        Log.d("App", obj.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
