
package com.cybernetvpn.cybernetvpn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.blinkt.openvpn.core.VpnStatus;


public class MenuListFragment extends Fragment {

    private ImageView ivMenuUserProfilePhoto;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String ip;
    JSONObject jsonObject;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    class MyAsn extends AsyncTask<String, Void, String> {

        //String s = null;


        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            if(ip==null)
            {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Your IP")
                        .setContentText("Check your internet connection and try again")
                        .show();
            }

            else
            {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Your IP")
                        .setContentText("Your IP Address is : "+ip)
                        .show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL whatismyip = new URL("http://checkip.amazonaws.com");
                BufferedReader input = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
                ip = input.readLine();
            } catch (Exception e) {
            }
            return ip;
        }
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);
        ivMenuUserProfilePhoto = (ImageView) view.findViewById(R.id.ivMenuUserProfilePhoto);
        NavigationView vNavigation = (NavigationView) view.findViewById(R.id.vNavigation);
        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                int id = menuItem.getItemId();
                if(id==R.id.menu_connect)
                {
                    Snackbar snackbar = Snackbar.make(getView(), "Click on connect button to start VPN connection", Snackbar.LENGTH_LONG);
                    snackbar.show();

                   // Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.menu_private_browsing)
                {
                    Intent intent =new Intent(getActivity(),PrivateBrowsingActivity.class);
                    startActivity(intent);
                }
                else if(id==R.id.menu_my_ip)
                {
                    MyAsn myAsn = new MyAsn();
                    myAsn.execute();
                }

                else if(id==R.id.menu_vpn_server)
                {
                    Intent intent =new Intent(getActivity(),VpnServerActivityList.class);
                    startActivity(intent);

                    // Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                }


                else if(id==R.id.privacy_policy)
                {
                   // Snackbar snackbar = Snackbar.make(getView(), "Sorry! This service not available now", Snackbar.LENGTH_LONG);
                    //snackbar.show();
                    Intent intent = new Intent(getActivity(),PrivacyPolicy.class);
                    startActivity(intent);
                    //Toast.makeText(getActivity(),,Toast.LENGTH_SHORT).show();
                }


                else if(id==R.id.menu_upgrade)
                {
                    //Intent intent =new Intent(getActivity(),TabTestActivity.class);
                    //startActivity(intent);
                   Toast.makeText(getActivity(),"This feature is not available now.",Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.menu_settings)
                {
                    Intent intent =new Intent(getActivity(),SettingsActivity.class);
                    startActivity(intent);
                    //Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.menu_vpn_server)
                {
                    Toast.makeText(getActivity(),"This feature is unavailable at this time",Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.menu_about)
                {
                    Intent intent =new Intent(getActivity(),AboutUsActivity.class);
                    startActivity(intent);
                    //Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.menu_contact)
                {
                    Intent intent =new Intent(getActivity(),ContactActivity.class);
                    startActivity(intent);
                 //   Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.menu_feedback)
                {
                    Intent intent =new Intent(getActivity(),FeedBackActivity.class);
                    startActivity(intent);
                   // Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        }) ;
       // setupHeader();
        return  view ;
    }

    private void setupHeader() {
        int avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);
        //String profilePhoto = getResources().getString(R.string.user_profile_photo);
        //Picasso.with(getActivity())
                //.load(profilePhoto)
               // .placeholder(R.drawable.img_circle_placeholder)
                //.resize(avatarSize, avatarSize)
                //.centerCrop()
                //.transform(new CircleTransformation())
               // .into(ivMenuUserProfilePhoto);
    }

}
