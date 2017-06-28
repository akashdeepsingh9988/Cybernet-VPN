package com.cybernetvpn.cybernetvpn;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class IpDetailsFragment extends Fragment {
    public String data, ip, city, state, country, isp;
    JSONObject jsonObject;
    String s;
    TextView txCity, txIp, txState, txCountry, txIsp;

    public IpDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static IpDetailsFragment newInstance(String param1, String param2) {
        IpDetailsFragment fragment = new IpDetailsFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ip_details, container, false);
        //textView = (TextView) view.findViewById(R.id.ipd);
        txCity = (TextView)view.findViewById(R.id.txcity);
        txIp = (TextView)view.findViewById(R.id.textView4);
        txState = (TextView)view.findViewById(R.id.txstate);
        txCountry = (TextView)view.findViewById(R.id.txcountry);
        txIsp = (TextView) view.findViewById(R.id.txisp);

        GetIp2 myAs=new GetIp2();
        myAs.execute();
        return view;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        //mListener = null;
    }

    class GetIp2 extends AsyncTask<String, Void, String>
    {
        //String s = null;

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            txIp.setText(ip);
            txCity.setText(city);
            txState.setText(state);
            txCountry.setText(country);
            txIsp.setText(isp);
            //textView.setText(ip+city+state+country);
            Toast.makeText(getContext(),s,Toast.LENGTH_SHORT);
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
                jsonObject=new JSONObject(s);

                city=jsonObject.getString("city");
                isp=jsonObject.getString("org");
                state=jsonObject.getString("regionName");
                country=jsonObject.getString("country");

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
}


