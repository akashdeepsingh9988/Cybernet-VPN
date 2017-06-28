package com.cybernetvpn.cybernetvpn;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactActivityFragment extends Fragment {
    ImageView phone,email,website;

    public ContactActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
       View view = inflater.inflate(R.layout.fragment_contact, container, false);
        phone = (ImageView)view.findViewById(R.id.imageView13);
        email = (ImageView)view.findViewById(R.id.imageView9);
        website = (ImageView)view.findViewById(R.id.imageView11);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),PrivateBrowsingActivity.class);
                intent.putExtra("mysiteurl","http://www.cybernetvpn.com");
                startActivity(intent);

            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] TO = {"akashthind007@gmail.com"};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:akashthind007@gmail.com"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                //emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                //emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail"));
                    // finish();
                    //Log.i("Finished", "mail sent");
                } catch (android.content.ActivityNotFoundException ex) {
                    // Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+919988104732"));

                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                startActivity(callIntent);
                //Log.d("phoneclick","phoneclicked");
            }
        });


        return view;
    }
}
