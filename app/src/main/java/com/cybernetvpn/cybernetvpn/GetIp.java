package com.cybernetvpn.cybernetvpn;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Akashdeep on 02-Apr-17.
 */


class GetIp extends AsyncTask<String, Void, String>
{
    String s = null;
    String ip;

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        //mapView.animate();
    }

    @Override
    protected String doInBackground(String... params) {

        try
        {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader input = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            ip = input.readLine();

            HttpURLConnection connection = (HttpURLConnection) new URL("http://ip-api.com/json/" + ip).openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            InputStream in = new java.io.BufferedInputStream(connection.getInputStream());
            //s=readStream(in,10000);
            s=convertStreamToString(in);

            Log.d("MainActivity", "Call reached here");

            if (in != null) {
                // Converts Stream to String with max length of 500.
                Log.d("MainActivity call 2", s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}
