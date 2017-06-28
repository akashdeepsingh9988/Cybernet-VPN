package com.cybernetvpn.cybernetvpn;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PrivateBrowsingActivity extends AppCompatActivity {

    private FlowingDrawer mDrawer;
    EditText editText;
    String url;
    WebView webView;
    ProgressBar progressBar;
    BottomNavigationView bottomNavigationView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.private_browsing_activity);
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        webView = (WebView) findViewById(R.id.webView);
        webView.setVerticalScrollBarEnabled(true);
        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        setupToolbar();
        setupMenu();
        editText = (EditText) findViewById(R.id.url2);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                  progressBar.setVisibility(ProgressBar.VISIBLE);
            }
          progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }

            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                editText.setText(url);
                return true;
            }
        });


        try
        {
            Intent intent = getIntent();
            String mysiteUrl = intent.getStringExtra("mysiteurl");
            if (mysiteUrl != null) {
                editText.setText(mysiteUrl);
                webView.loadUrl(mysiteUrl);
                Log.d("myurl",mysiteUrl);
            }

            else
            {
                webView.loadUrl("http://www.google.com");

            }
        }
        catch (Exception e)
        {

        }
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_GO) {
                    url = editText.getText().toString();
                    if (!url.contains("www") || !url.contains("http://") || !url.contains("https://")) {
                        webView.loadUrl("https://www.google.com/search?q=" + url);
                        editText.setText(webView.getUrl());
                    } else {
                        editText.setText(url);
                        webView.loadUrl(url);
                    }
                    return true;
                }
                return false;
            }

        });

        //download files
        webView.setDownloadListener(new DownloadListener() {
                                   @Override
                                   public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                                       DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                                       request.setMimeType(mimeType);
                                       //------------------------COOKIE!!------------------------
                                       String cookies = CookieManager.getInstance().getCookie(url);
                                       request.addRequestHeader("cookie", cookies);
                                       //------------------------COOKIE!!------------------------
                                       request.addRequestHeader("User-Agent", userAgent);
                                       request.setDescription("Downloading file...");
                                       request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                                       request.allowScanningByMediaScanner();
                                       request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                       request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                                       DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                       dm.enqueue(request);
                                       Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
                                   }
                               });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.back) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                        editText.setText(webView.getOriginalUrl());
                    }
                } else if (id == R.id.next) {
                    if (webView.canGoForward()) {
                        webView.goForward();
                        editText.setText(webView.getOriginalUrl());

                    }
                } else if (id == R.id.reload) {
                    webView.reload();
                    editText.setText(webView.getOriginalUrl());
                }
                return true;
            }
        });

        //swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        //swipeRefreshLayout.setOnRefreshListener(this);

    }
    //public void onRefresh()
    //{
      //  webView.reload();
        //swipeRefreshLayout.setRefreshing(false);
    //}

    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.toggleMenu();
            }
        });
    }

    private void setupMenu()
    {
        FragmentManager fm = getSupportFragmentManager();
        PrivateMenuListFragment mMenuFragment = (PrivateMenuListFragment) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new PrivateMenuListFragment();
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }

//        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
//            @Override
//            public void onDrawerStateChange(int oldState, int newState) {
//                if (newState == ElasticDrawer.STATE_CLOSED) {
//                    Log.i("MainActivity", "Drawer STATE_CLOSED");
//                }
//            }
//
//            @Override
//            public void onDrawerSlide(float openRatio, int offsetPixels) {
//                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}

