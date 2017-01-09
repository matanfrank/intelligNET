package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;

public class TashchezHigayonUI extends AppCompatActivity
{
public static final int NUM_COL=7;
public static final int NUM_ROW=7;
private ImageView coverImage;
private TextView connectedName;
private static TashchezHigayonAdapter adapter;
//private static newEditText editText;
private GridView tashchezGrid;
private  TypeTashchezGrid tashchez;
public static boolean solveMode = false;
private TashchezDAL tashchezDAL = new TashchezDAL(this);
private TashchezBL tashchezBL;
private Activity activity = this;
public android.os.Handler h;
public Runnable r1, r2;
public static RelativeLayout coverLayout;
private TextView definitionTextView;
    private FloatingActionButton chatHead;
    private FloatingActionButton eraseHead;
    private FloatingActionButton helpHead;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tashchez_higayon_ui);
    TextView disconnect = (TextView)findViewById(R.id.disconnect);
    TextView connectedName = (TextView) findViewById(R.id.connectedName);


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
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
            SharedPreferences sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            sharedpreferences.edit().clear().commit();

            Intent disconnectIntent = new Intent(getApplicationContext(), LoginActivity.class);
            disconnectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(disconnectIntent);
            finish();
        }
    });


        definitionTextView = (TextView)activity.findViewById(R.id.definitionTextView);
        coverLayout = (RelativeLayout)findViewById(R.id.coverLayout);
        coverImage = (ImageView) findViewById(R.id.symbolImage);
        tashchezGrid = (GridView)findViewById(R.id.menuGrid);
        tashchezGrid.setNumColumns(NUM_COL);


        h = new android.os.Handler();

        r1 = new Runnable() {
@Override
public void run() {


        try {
        tashchezBL = new TashchezBL(TashchezDAL.jsonArray.getJSONObject(0).getString("content"), activity, h, r2);//maybe should add TashchezDAL.jsonArray.getJSONObject(0).getString()
        } catch (JSONException e) {
        e.printStackTrace();
        }

        }
        };


        r2 = new Runnable() {
@Override
public void run() {


        for(int i=0 ; i<tashchezBL.getTashchezStruct().cellIndex.size() ; i++)
        Log.d("123456789", "" + tashchezBL.getTashchezStruct().cellType.get(i) + " " + tashchezBL.getTashchezStruct().cellIndex.get(i) + " " + tashchezBL.getTashchezStruct().content.get(i) + " " + tashchezBL.getTashchezStruct().numOfLetter.get(i));
        tashchez = new TypeTashchezGrid(NUM_ROW, NUM_COL, true, tashchezBL.getTashchezStruct(), false, getApplicationContext());


        adapter = new TashchezHigayonAdapter(activity, R.layout.cell_definition_tashchez, R.layout.cell_solve_tashchez, tashchez.board);// new TashchezPassEditText() {
//@Override
//public void setEditText(newEditText editText) {
//        TashchezHigayonUI.editText = editText;
//        }
//        });



        tashchezGrid.setAdapter(adapter);


        }
        };

        tashchezDAL.getDataFrom("tashchezGet", h, r1, null);









        startService(new Intent(this, ChatHeadService.class));

//    chatHead = (FloatingActionButton) findViewById(R.id.wallFB);
    eraseHead = (FloatingActionButton) findViewById(R.id.eraseFB);
//    helpHead = (FloatingActionButton) findViewById(R.id.saveFB);

//    chatHead.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.FB))));
    eraseHead.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.FB))));
//    helpHead.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.FB))));

//    chatHead.setRippleColor((getResources().getColor(R.color.FB)));//change color when click
    eraseHead.setRippleColor((getResources().getColor(R.color.FB)));
//    helpHead.setRippleColor((getResources().getColor(R.color.FB)));



    eraseHead.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            h.post(r2);
        }
    });

        chatHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TashchezHigayonUI.this, WallUI.class);
                if (getIntent().getExtras() != null)
                {
                    intent.putExtra("statusType", "תשחץ אי-גיון");
                    intent.putExtra("statusIndex", getIntent().getExtras().getInt("statusIndex"));
                    Log.d("122333", "TashchezHigayonUI: " + getIntent().getExtras().getInt("statusIndex"));
                }


            startActivity(intent);
        }
});
        chatHead.setOnTouchListener(new View.OnTouchListener() {
            private float initialX;
            private float initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = v.getX();
                        initialY = v.getY();
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                    case MotionEvent.ACTION_MOVE:
                        v.setX(initialX + (int) (event.getRawX() - initialTouchX));
                        v.setY(initialY + (int) (event.getRawY() - initialTouchY));
                }

                if (event.getRawX() - initialTouchX > 0.3 || event.getRawY() - initialTouchY > 0.3)
                    return true;
                else
                    return false;
            }
        });
        }



//get out of SOLVE_MODE and if is out of SOLVE_MODE exit the activity
@Override
public boolean dispatchKeyEvent(KeyEvent event)
        {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
        if (solveMode) {



        android.os.Handler h = new android.os.Handler();

        Runnable r = new Runnable() {
@Override
public void run() {
//                            coverImage.setVisibility(View.VISIBLE);
//                            connectedName.setVisibility(View.VISIBLE);


        coverLayout.setVisibility(View.VISIBLE);

        //TashchezHigayonUI.editText.clearFocus();
    solveMode = false;
        }
        };
            h.postDelayed(r, 300);

        definitionTextView.setVisibility(View.GONE);


        for (int i = 0; i < NUM_ROW * NUM_COL; i++)
        tashchez.board.get(i).onEdit = false;


        }
        else
        {
//        Intent intent = new Intent(this, TashchezHigayonMenu .class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
            finish();
        }
        }
            return super.dispatchKeyEvent(event);
        }


        }


