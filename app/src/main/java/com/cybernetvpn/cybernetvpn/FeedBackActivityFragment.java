package com.cybernetvpn.cybernetvpn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A placeholder fragment containing a simple view.
 */
public class FeedBackActivityFragment extends Fragment implements View.OnClickListener {

    EditText name, email, phone, message;
    String nameValue, emailvalue, phoneValue, messageValue;
    AppCompatButton btn;
    public FeedBackActivityFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_feed_back, container, false);
        email = (EditText) view.findViewById(R.id.input_email);
        name = (EditText) view.findViewById(R.id.input_name);
        phone = (EditText) view.findViewById(R.id.input_phone);
        message = (EditText) view.findViewById(R.id.input_message);

        btn = (AppCompatButton)view.findViewById(R.id.btn_signup);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        nameValue = name.getText().toString();
        emailvalue = email.getText().toString();
        phoneValue = phone.getText().toString();
        messageValue = message.getText().toString();

        Log.d("mylog",nameValue+messageValue);


        int flagName=0, flagEmail=0, flagMsg=0, flagPhone=0;

        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Pattern phonePattern = Patterns.PHONE;

        boolean emailVal= emailPattern.matcher(emailvalue).matches();
        boolean phoneVal =phonePattern.matcher(phoneValue).matches();
        if(emailVal!=true)
        {
            email.setError("Invalid Email");
            flagEmail=0;
        }
        else
        {
            flagEmail=1;
        }

        if(phoneVal==false)
        {
            phone.setError("Please enter your phone correctly ");
            flagPhone=0;
        }
        else
        {
            flagPhone=1;
        }
        if(nameValue==null || nameValue.equals(" ") || nameValue.equals(""))
        {
            name.setError("Please enter your name");
            flagName=0;
        }
        else
        {
            flagName=1;
        }
        if(messageValue.isEmpty())
        {
            message.setError("Please enter the message");
            flagMsg=0;
        }
        else
        {
            flagMsg=1;
        }

        if (flagEmail==1 && flagPhone==1 && flagMsg==1 && flagName==1)
        {
            String s= String.valueOf(flagMsg+flagEmail+flagName+flagPhone);
            //Log.d("hello",s);

            FeedBackSend feedBackSend=new FeedBackSend();
            feedBackSend.execute();
        }

    }

    class FeedBackSend extends AsyncTask<String, Void, String> {
        String s = "";
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Please Wait....");
            //pd.setProgressDrawable(getResources().getDrawable(R.drawable.lc2));
            pd.show();
        }

        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            if (pd.isShowing())
            {
                pd.dismiss();
               // email.setText(s);
            }

            if (s.isEmpty() || s.equals("") || s.equals(" "))
            {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText("Check your internet connection and try again")
                        .show();
            }
            else if(s.contains("Successfuly")) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Submitted")
                        .setContentText("Thank you for submitting you valueable feedback")
                        .show();
                email.setText("");
                name.setText("");
                phone.setText("");
                message.setText("");
            }
            }
        @Override
        protected String doInBackground(String... params)
        {

            try
            {
                String p1 = URLEncoder.encode(nameValue,"utf-8");
                String p2 = URLEncoder.encode(messageValue,"utf-8");
                String ur = "http://akash-deep.in/contact-process.jsp?" +
                        "Name="+p1+"&Email="+emailvalue+"&phone="+phoneValue+"&Message="+p2+"";
                HttpURLConnection connection = (HttpURLConnection) new URL(ur).openConnection();
             //   Toast.makeText(getActivity(),ur,Toast.LENGTH_SHORT).show();
                Log.d("myurla",ur);
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                InputStream in = new java.io.BufferedInputStream(connection.getInputStream());
                s = convertStreamToString(in);
                //pd.dismiss();
            }
            catch (Exception e) {
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
