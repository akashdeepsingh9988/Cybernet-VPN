
package com.cybernetvpn.cybernetvpn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.squareup.picasso.Picasso;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import cn.pedant.SweetAlert.SweetAlertDialog;
//import me.drakeet.materialdialog.MaterialDialog;


public class PrivateMenuListFragment extends Fragment {

    private ImageView ivMenuUserProfilePhoto;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    EditText editText;
    WebView webView;
    FlowingDrawer flowingDrawer;
    String val;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.private_browsing_fragment_menu, container,
                false);

        ivMenuUserProfilePhoto = (ImageView) view.findViewById(R.id.ivMenuUserProfilePhoto);
        final NavigationView vNavigation = (NavigationView) view.findViewById(R.id.vNavigation);
        flowingDrawer = (FlowingDrawer) getActivity().findViewById(R.id.drawerlayout);
        webView =(WebView) getActivity().findViewById(R.id.webView);

        editText =(EditText) getActivity().findViewById(R.id.url2);
        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                int id = menuItem.getItemId();
                if(id==R.id.google)
                {
                    webView.loadUrl("http://www.google.com");
                    flowingDrawer.closeMenu();
                   // webView.clearHistory();
                }
                else if(id==R.id.facebook)
                {
                    webView.loadUrl("http://www.facebook.com");
                    flowingDrawer.closeMenu();
                }
                else if(id==R.id.save_url)
                {
                    webView.clearHistory();
                    //Snackbar.make(getActivity(),"Browsing data Cleared",Snackbar.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar
                            .make(getView(), "Browsing Data Deleted", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    flowingDrawer.closeMenu();
                }

                else if(id==R.id.about_private_browsing)
                {
                 //   TextView tx = new TextView(getActivity());
                    JustifiedTextView js = new JustifiedTextView(getActivity());
                    js.setText("Private browsing is a privacy feature in browsers to disable browsing history and the web cache." +
                            "This allows a person to browse the Web without storing local data that could be retrieved at a later date."+
                            "\n\nPrivacy mode will also disable the storage of data in cookies and Flash cookies." +
                            "This privacy protection is only on the local" +
                            " computing device as it is still possible to identify frequented websites by associating" +
                            " the IP address at the web server.");



                    new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText("PRIVATE BROWSING")
                            .setCustomImage(getResources().getDrawable(R.drawable.pt))
                            .setContentText(js.getText().toString())
                            .show();

                   // Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.my_ip)
                {
                    webView.loadUrl("https://www.google.com/search?q=my+ip");
                    flowingDrawer.closeMenu();
                    //Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.menu_upgrade)
                {
                    //Intent intent =new Intent(getActivity(),TabTestActivity.class);
                    //startActivity(intent);
                   Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.menu_settings)
                {
                    Intent intent =new Intent(getActivity(),SettingsActivity.class);
                    startActivity(intent);
                    //Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.menu_vpn_server)
                {
                    Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
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
        setupHeader();
        return  view ;
    }

    private void setupHeader() {
        int avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);
      //  String profilePhoto = getResources().getString(R.string.user_profile_photo);
        //Picasso.with(getActivity())
          //      .load(profilePhoto)
            //    .placeholder(R.drawable.img_circle_placeholder)
              //  .resize(avatarSize, avatarSize)
                //.centerCrop()
                //.transform(new CircleTransformation())
                //.into(ivMenuUserProfilePhoto);
    }

}
