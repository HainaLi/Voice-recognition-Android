package jillian.cs4720mobileproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

/**import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;**/

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.UnsupportedEncodingException;

import org.json.simple.*;


public class Home extends Activity {

    String IPAdd = new String();
    String lights = new String();
    JSONObject obj = new JSONObject();
    private CloseableHttpClient httpclient;
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
                /**final EditText jsonLights = (EditText) findViewById(R.id.jsonLights);
                lights = jsonLights.getText().toString();
                try {
                    StringEntity lighters = new StringEntity(lights);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(ipAddress + "/rpi");
                    httpPost.setEntity(lighters);
                }
                catch (UnsupportedEncodingException e) {
                    System.out.println("Encoding Error!");
                }**/

                JSONArray array = new JSONArray();

                JSONObject lightIdJSON = new JSONObject();
                JSONObject redJSON = new JSONObject();
                JSONObject greenJSON = new JSONObject();
                JSONObject blueJSON = new JSONObject();
                JSONObject intensityJSON = new JSONObject();

                int lightId = 1;
                int red = 255;
                int green = 0;
                int blue = 0;
                int intensity = 1;

                lightIdJSON.put("lightId", lightId);
                redJSON.put("red", red);
                greenJSON.put("green", green);
                blueJSON.put("blue", blue);
                intensityJSON.put("intensity", intensity);

                array.add(lightIdJSON);
                array.add(redJSON);
                array.add(greenJSON);
                array.add(blueJSON);
                array.add(intensityJSON);

                obj.put("lights", array);
                obj.put("propagate", "true");

                try {
                    StringEntity lighters = new StringEntity(obj.toString());
                    //HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://" + ipAddress + "/rpi");
                    httpPost.setEntity(lighters);

                    CloseableHttpResponse response = (CloseableHttpResponse) httpclient.execute(httpPost);
                    //httpPost.completed();
                    response.close();
                }
                catch (Exception e) {
                    System.out.println("Encoding Error!");
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
