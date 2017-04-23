package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matan frank on 30/01/2017.
 */
public class GeneralBL {

    public static void setNameAndDisconnect(final Activity activity)
    {
        TextView disconnect = (TextView)activity.findViewById(R.id.disconnect);
        TextView connectedName = (TextView) activity.findViewById(R.id.connectedName);

        /*Four possible options:
        * 1- this is a guest and no user connected - GOOD
        * 2- this is a guest and we have user that's connected - BAD
        * 3- this is not a guest and no user connected - BAD
        * 4- this is not a guest and we have user that's connected - GOOD*/
        if(LoginActivity.isGuest && MainActivity.user == null)
        {
            disconnect.setVisibility(View.INVISIBLE);
            connectedName.setVisibility(View.INVISIBLE);
        }
        else if(LoginActivity.isGuest && MainActivity.user != null)
        {
            MainActivity.user=null; //TODO make possible to be connected and still go to guest mode. nowadays need to disconnect for guest.
            disconnect.setVisibility(View.INVISIBLE);
            connectedName.setVisibility(View.INVISIBLE);
        }
        else if(!LoginActivity.isGuest && MainActivity.user == null)
        {
            Intent intent = new Intent(activity.getApplicationContext(), SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
        else if(!LoginActivity.isGuest && MainActivity.user != null)
        {
            connectedName.setText(MainActivity.user.getFirstName() + " " + MainActivity.user.getLastName());
            disconnect.setVisibility(View.VISIBLE);
            connectedName.setVisibility(View.VISIBLE);
        }

        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect(activity);
            }
        });
    }



    public static void disconnect(Activity activity)
    {
        TashchezDAL tashchezDAL = new TashchezDAL(activity);

        SharedPreferences sharedpreferences = activity.getSharedPreferences("login", Context.MODE_PRIVATE);
        sharedpreferences.edit().clear().commit();



        Intent disconnectIntent = new Intent(activity.getApplicationContext(), LoginActivity.class);
        disconnectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(disconnectIntent);

        //do not able two device connected to same account. sign that the user is connected
//        Map<String,String> params = new HashMap<String, String>();
//        params.put("username", MainActivity.user.getUsername());
//        params.put("is_connected", "false");
//        tashchezDAL.getDataFrom("changeConnectedStatus", params, null, null, null, "post");
//        MainActivity.user.setIsConnected(true);//need to cheack if secceded first!!!!!!!!!!!!!!!!

        activity.finish();
    }

    public static void loadAd(final Activity activity)
    {
        PublisherAdView mPublisherAdView = (PublisherAdView) activity.findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);
    }

}
