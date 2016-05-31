package com.example.matan.intellignet;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


public class WallNewsFragment extends Fragment {

    public static int i;
    public static ArrayList<TypePost> typePostArr = new ArrayList<>();
    public static android.os.Handler hAdapter = new Handler();
    public static  Runnable rAdapter;
    public static String firstName="";
    public static String lastName="";
    public static String date="";
    public static String content="";
    public static String type="";
    public static String index ="";
    public static String usernameEncode = "";
    public static JSONArray statuses = new JSONArray();

    public static WallNewsFragment newInstance(String type, int index) {
        WallNewsFragment fragment = new WallNewsFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wall_news, container, false);

        String statusType = "";
        int statusIndex = 0;
        if (getArguments() != null) {
            statusType = getArguments().getString("statusType");
            statusIndex = getArguments().getInt("statusIndex");
        }

        Log.d("122333", "WallNewsFragment " + index + ", " + type);


        EditText typeEditText = (EditText)view.findViewById(R.id.statusType);
        typeEditText.setText(statusType);
        EditText indexEditText = (EditText)view.findViewById(R.id.statusIndex);
        indexEditText.setText(statusIndex+"");
//        addMenuItems();

        final ListView list = (ListView)view.findViewById(R.id.news_posts_list);
        // menuGrid.setVerticalScrollBarEnabled(false);
        rAdapter = new Runnable() {
    @Override
    public void run() {
        list.setAdapter(new AdapterPosts(getActivity(), R.layout.row_post, typePostArr));
    }
};


        LinearLayout writeStatus = (LinearLayout)view.findViewById(R.id.writeStatus);
        if(LoginActivity.guest)
            writeStatus.setVisibility(View.GONE);


        return view;
    }




    private void addMenuItems()
    {
        final android.os.Handler h = new Handler();
        Runnable r;

                     //   Log.d("wall", username[i] + "" + firstName + " " + lastName + "" + date + " " + content + "" + type + " " + index);
//                        SimpleDateFormat isoFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
//                        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//                        try {
//                            Date date1 = isoFormat.parse(date);
//
//                            Log.d("date1", date + "\n" + date1);
//                        } catch (ParseException e) {
//                            Log.d("date1", "1");
//                            e.printStackTrace();
//                        }





//                        Log.d("wallllll", typePostArr.get(0).getName() + "" + typePostArr.get(0).getDate() + " " + typePostArr.get(0).getContent() + "" + typePostArr.get(0).getType() + " " + typePostArr.get(0).getIndex());

    }




    private void getStatuses()
    {
        final TashchezDAL tashchezDAL = new TashchezDAL(getContext());
        final String[] username= new String[TashchezDAL.jsonArray.length()];


        final android.os.Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {

           statuses = TashchezDAL.jsonArray;

                try {

                    for(int i=0 ; i<statuses.length() ; i++)
                        username[i] = statuses.getJSONObject(i).getString("username");
                    date = statuses.getJSONObject(i).getString("datetime");
                    content = statuses.getJSONObject(i).getString("content");
                    type = statuses.getJSONObject(i).getString("type");
                    index = statuses.getJSONObject(i).getString("index");


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        };

        tashchezDAL.getDataFrom("statusGet", h, r);
    }



//    private void getFullName()
//    {
//        tashchezDAL.getDataFrom("userGetName?username=" + usernameEncode, h1, r1);
//    }
//        try {
//            usernameEncode = URLEncoder.encode(username[i], "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }








android.os.Handler h1 = new Handler();
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        try {
            firstName = TashchezDAL.jsonArray.getJSONObject(WallNewsFragment.i).getString("firstname");
            lastName = TashchezDAL.jsonArray.getJSONObject(WallNewsFragment.i).getString("lastname");

            TypePost typePost = new TypePost(firstName + " " + lastName, date, content, type, index);
            Log.d("wall111", typePost.getName() + "" + typePost.getDate() + " " + typePost.getContent() + "" + typePost.getType() + " " + typePost.getIndex());
            typePostArr.add(typePost);
            hAdapter.post(rAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
};



}