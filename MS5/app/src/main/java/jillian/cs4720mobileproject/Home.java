package jillian.cs4720mobileproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.*;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import org.json.JSONObject;
import org.json.simple.*;
import android.os.AsyncTask;

import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.widget.Toast;



public class Home extends Activity {
    String api_key = "339d1eb48603267e05701008f1e029b01a666012b51950ae5";
    private int REQUEST_CODE = 1;
    private static int SETTINGS_REQUEST = 2;
    String IPAdd = new String();
    JSONObject obj = new JSONObject();
    JSONObject actual = new JSONObject();
    String word = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void gotoSettings(View view)
    {
        Intent intent = new Intent(this, Settings.class);
        startActivityForResult(intent, SETTINGS_REQUEST);
    }
    public void btnGetRandWord(View view) {
        //EditText txtLat = (EditText) findViewById(R.id.txtLat);
        //EditText txtLong = (EditText) findViewById(R.id.txtLong);

        new generateRandomWordTask().execute(
                "http://api.wordnik.com:80/v4/words.json/randomWord?hasDictionaryDef=false&minCorpusCount=0&maxCorpusCount=-1&" +
                        "minDictionaryCount=1&maxDictionaryCount=-1&minLength=4&maxLength=7&api_key="
                        + api_key);
    }
    public String generateRandomWord(String url) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }

    private class generateRandomWordTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return generateRandomWord(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                System.out.println(result);
                JSONObject jsonObject = new JSONObject(result);

                System.out.println(jsonObject.getString("word"));

                Toast.makeText(getBaseContext(),
                        jsonObject.getString("word").toString(),
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d("generateRandomWordTask", e.getLocalizedMessage());
            }
        }
    }

    public void btnGetWordOfDay(View view) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        System.out.println(currentDate);

        new generateWordOfDayTask().execute(
                "http://api.wordnik.com:80/v4/words.json/wordOfTheDay?date=" + currentDate + "&api_key="
                        + api_key);
    }
    public String generateWordOfDay(String url) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }

    private class generateWordOfDayTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return generateWordOfDay(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                System.out.println(result);
                JSONObject jsonObject = new JSONObject(result);

                System.out.println(jsonObject.getString("word"));

                Toast.makeText(getBaseContext(),
                        jsonObject.getString("word").toString(),
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d("generateWordOfDay", e.getLocalizedMessage());
            }
        }
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

    public void speechRecognizer(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                "com.domain.app");
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        reset();
        String correctness;
        int correct = 5;
        if(requestCode == SETTINGS_REQUEST && resultCode == Activity.RESULT_OK)
        {
            correctness = data.getStringExtra("correctness");
            correct = Integer.parseInt(correctness);
            //System.out.println(correct);
        }
        final EditText ipAddress = (EditText) findViewById(R.id.IPAddress);
        final EditText sayText = (EditText) findViewById(R.id.sayThis);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && ipAddress.getText() != null) {

            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //float[] scores = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);
            IPAdd = ipAddress.getText().toString();
            word = sayText.getText().toString();
            IPAdd = "http://cs4720.cs.virginia.edu/rpi/?username=hl3wb";
            System.out.println(matches);
            System.out.println("Match Result " + matches.get(0));
            System.out.println("Word: " + word);
            if(correct == 5) {
                if (matches.get(0).equalsIgnoreCase(word)) {

                    sendPostRequest(IPAdd, 0, 0, 255, 0, 1.0);
                } else if (matches.contains(word)) {
                    int index = matches.indexOf(word);
                    sendPostRequest(IPAdd, index, 0, 255, 0, 1.0);
                    System.out.println("Close but no cigar");
                } else {
                    sendPostRequest(IPAdd, 0, 255, 0, 0, 1.0);
                }
            }
            if(correct == 4 || correct ==3) {
                int index = -1;
                if(matches.contains(word)) {
                    index = matches.indexOf(word);
                }
                if(index < 3 && index > 0){
                    sendPostRequest(IPAdd, 0, 0, 255, 0, 1.0);
                }
                else if(index > 0 && index >= 3)
                {
                    sendPostRequest(IPAdd, index, 0, 255, 0, 1.0);
                }
                else {
                    sendPostRequest(IPAdd, 0, 255, 0, 0, 1.0);
                }
            }
        }
    }

    public void sendPostRequest(String ip, int lightID, int red, int green, int blue, double intensity) {
        JSONArray array = new JSONArray();
        try {
            obj.put("lightId", lightID);
            obj.put("red", red);
            obj.put("green", green);
            obj.put("blue", blue);
            obj.put("intensity", intensity);

            array.add(obj);
            actual.put("lights", array);
            actual.put("propagate", true);
        }
        catch (Exception e)
        {
            System.out.println("JSON Error Thing");
            e.printStackTrace();
        }
        final String url = "http://" + ip + "/rpi";

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
    public void reset()
    {
        sendPostRequest(IPAdd, 1, 0, 0, 0, 1.0);
    }
}
