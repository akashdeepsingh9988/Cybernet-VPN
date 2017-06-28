package com.cybernetvpn.cybernetvpn;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import static android.R.attr.button;


public class BowserFragment extends Fragment implements View.OnClickListener {
 EditText editText;
    WebView webView;
    String url;
    Button load;
    public BowserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_browser, container, false);
        editText=(EditText)view.findViewById(R.id.url);
        webView=(WebView)view.findViewById(R.id.webview);
        load=(Button)view.findViewById(R.id.buton);
        load.setOnClickListener(this);
        Web web=new Web();
        webView.setWebViewClient(web);
        editText.setText("https://www.google.com");
        webView.loadUrl("https://www.google.com");
        
        return view;

    }

    @Override
    public void onClick(View v)
    {
        url=editText.getText().toString();
        Web web=new Web();
        webView.setWebViewClient(web);
        webView.loadUrl(url);
        //editText.setText();
    }

    class Web extends WebViewClient
    {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

}
