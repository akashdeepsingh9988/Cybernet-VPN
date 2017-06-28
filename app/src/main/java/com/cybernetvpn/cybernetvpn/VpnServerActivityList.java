package com.cybernetvpn.cybernetvpn;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VpnServerActivityList extends AppCompatActivity {

        String[] countries = new String[]{
                "United Stated",
                "United Kingdom",
                "Australia",
                "Spain",
                "Italy",
        };

        // Array of integers points to images stored in /res/drawable-ldpi/
        int[] flags = new int[]{
                R.drawable.us,
                R.drawable.uk,
                R.drawable.aus,
                R.drawable.spain,
                R.drawable.italy,
        };

        // Array of strings to store currencies
        String[] currency = new String[]{
                "Status : ON",
                "Status : OFF",
                "Status : OFF",
                "Status : OFF",
                "Status : OFF",

        };


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_vpn_server_list2);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

            for(int i=0;i<5;i++){
                HashMap<String, String> hm = new HashMap<String,String>();
                hm.put("txt", "Country : " + countries[i]);
                hm.put("cur","" + currency[i]);
                hm.put("flag", Integer.toString(flags[i]) );
                aList.add(hm);
            }

            // Keys used in Hashmap
            String[] from = { "flag","txt","cur" };

            // Ids of views in listview_layout
            int[] to = { R.id.flag,R.id.txt,R.id.cur};

            // Instantiating an adapter to store each items
            // R.layout.listview_layout defines the layout of each item
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.servers_listview, from, to);

            // Getting a reference to listview of main.xml layout file
            ListView listView = (ListView) findViewById(R.id.listview);

            // Setting the adapter to the listView
            listView.setAdapter(adapter);
        }
}


