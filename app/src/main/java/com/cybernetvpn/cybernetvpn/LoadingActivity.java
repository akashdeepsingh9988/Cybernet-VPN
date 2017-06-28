package com.cybernetvpn.cybernetvpn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class LoadingActivity extends AppCompatActivity
{
    ShimmerTextView shimmerTextView;
    Shimmer shimmer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        shimmerTextView = (ShimmerTextView)findViewById(R.id.textView3);
         shimmer= new Shimmer();
        shimmer.start(shimmerTextView);
        New n=new New();
        n.start();

    }
    public void finish()
    {
        super.finish();
        //overridePendingTransition(android.R.anim.fade_in,android.R.anim.bounce_interpolator);
    }
    public void onPause() {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        //overridePendingTransition(R.anim.modal_out, R.anim.modal_in);
        super.onPause();

    }

    class New extends Thread
    {
        public void run()
        {
            try
            {
                Thread.sleep(3000);
                Intent intent=new Intent(getApplicationContext(),NewMainActivity.class);
                finishAffinity();
                startActivity(intent);

            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }

    }


}


