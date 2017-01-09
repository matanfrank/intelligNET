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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;



public class TashchezUI extends AppCompatActivity
{
    public static final int NUM_COL=7;
    public static final int NUM_ROW=7;

    private static TashchezAdapter adapter;
    //private static newEditText editText;
    private  GridView tashchezGrid;
    private  TypeTashchezGrid tashchez;
    public static boolean solveMode = false;
    private TashchezDAL tashchezDAL = new TashchezDAL(this);
    private TashchezBL tashchezBL;
    private Activity activity = this;
    public android.os.Handler h;
    public Runnable r1, r2;
    public static RelativeLayout coverLayout;
    private TextView definitionTextView;
    //private FloatingActionButton chatHead;
    private FloatingActionButton eraseHead;
    //private FloatingActionButton saveHead;
    public static boolean clickOnErase = false;
    public static TashchezSaveDB db;
    public static String savedSolution = "";
    private ProgressBar progressBar;

    int helpForDay = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tashchez_ui);
        TextView disconnect = (TextView)findViewById(R.id.disconnect);
        TextView connectedName = (TextView) findViewById(R.id.connectedName);
        progressBar = (ProgressBar)findViewById(R.id.pb_tashchez_ui);

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


        //check if there's is "save" for this user

        db = new TashchezSaveDB(getApplicationContext());
        if(MainActivity.user != null && getIntent() != null && getIntent().getExtras() != null)
            savedSolution = db.getTashchez(MainActivity.user.getUsername(), TashchezSaveDB.TYPE, getIntent().getExtras().getInt("statusIndex"));

//        Log.d("12312121212", savedSolution);



        definitionTextView = (TextView)activity.findViewById(R.id.definitionTextView);
        coverLayout = (RelativeLayout)findViewById(R.id.coverLayout);
        tashchezGrid = (GridView)findViewById(R.id.menuGrid);
        tashchezGrid.setNumColumns(NUM_COL);
        tashchezGrid.setVerticalScrollBarEnabled(true);


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
                    Log.d("123456789", ""+tashchezBL.getTashchezStruct().cellType.get(i) + " " + tashchezBL.getTashchezStruct().cellIndex.get(i) + " " + tashchezBL.getTashchezStruct().content.get(i) + " " + tashchezBL.getTashchezStruct().numOfLetter.get(i));
                tashchez = new TypeTashchezGrid(NUM_ROW, NUM_COL, true, tashchezBL.getTashchezStruct(), true, getApplicationContext());

                //save the tashchez for next use without needing to go to DB
                //TODO add check if already exist
                MainActivity.savedbBoardsData.add(tashchez);
                MainActivity.savedbBoardsType.add("תשחץ");
                MainActivity.savedbBoardsIndex.add(getIntent().getExtras().getInt("statusIndex"));

                adapter = new TashchezAdapter(activity, R.layout.cell_definition_tashchez, R.layout.cell_solve_tashchez, savedSolution, tashchez.board, tashchezGrid);//, new TashchezPassEditText() {

                tashchezGrid.setAdapter(adapter);
            }
        };


        if(MainActivity.savedbBoardsIndex.size() != 0)
        {//check if this tashchez already saved in this session so dont need to go to DB
            for (int i = 0; i < MainActivity.savedbBoardsIndex.size(); i++)
            {
                if (MainActivity.savedbBoardsIndex.get(i) == getIntent().getExtras().getInt("statusIndex") && MainActivity.savedbBoardsType.get(i).equals("תשחץ")) {

                    tashchez = (TypeTashchezGrid)(MainActivity.savedbBoardsData.get(i));
                    adapter = new TashchezAdapter(activity, R.layout.cell_definition_tashchez, R.layout.cell_solve_tashchez, savedSolution, tashchez.board, tashchezGrid);//, new TashchezPassEditText() {
                    tashchezGrid.setAdapter(adapter);
                    clickOnErase = false;
                    break;

                } else if (i == MainActivity.savedbBoardsIndex.size()-1) {
                    Log.d("aaaaaaaa", "bbbbbbbbbbbb");
                    tashchezDAL.getDataFrom("tashchezGet", h, r1, progressBar);
                }
            }
        }
        else
        {
            Log.d("aaaaaaaa", "cccccccccc");
            tashchezDAL.getDataFrom("tashchezGet", h, r1, progressBar);
        }

        //startService(new Intent(this, ChatHeadService.class));

        //chatHead = (FloatingActionButton) findViewById(R.id.wallFB);
        eraseHead = (FloatingActionButton) findViewById(R.id.eraseFB);
        //saveHead = (FloatingActionButton) findViewById(R.id.saveFB);

        //chatHead.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.FB))));
        eraseHead.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.FB))));
        //saveHead.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.FB))));

        //chatHead.setRippleColor((getResources().getColor(R.color.FB)));//change color when click
        eraseHead.setRippleColor((getResources().getColor(R.color.FB)));
        //saveHead.setRippleColor((getResources().getColor(R.color.FB)));

//        saveHead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (MainActivity.user != null) {
//                    db.add(MainActivity.user.getUsername(), TashchezSaveDB.TYPE, getIntent().getExtras().getInt("statusIndex"), adapter.createContentToSave());
//                    Toast.makeText(getApplicationContext(), "תשחץ נשמר", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        eraseHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tashchezGrid.getChildCount() == NUM_COL*NUM_ROW)//check if the gridview is ready to use (shown on the screen)
                {
                    EditText editText;
                    clickOnErase = true;
                    //h.post(r2);
                    LayoutInflater inflater;
                    for(int i=0 ; i < NUM_COL*NUM_ROW ; i++)
//                        if(tashchezGrid.getChildAt(i).getId() == getResources().getIdentifier(String.valueOf(R.layout.cell_solve_tashchez), "id", getPackageName()))
                    {
                        if (tashchezGrid.getChildAt(i).findViewById(R.id.tashchez_edit_text) != null)
                        {
                            editText = (EditText) tashchezGrid.getChildAt(i).findViewById(R.id.tashchez_edit_text);
                            editText.setText("");
                        }
                    }
                    //delete from sqlite DB
                    db.deleteTashchez(MainActivity.user.getUsername(), TashchezSaveDB.TYPE, getIntent().getExtras().getInt("statusIndex"));
                    clickOnErase = false;
                }
            }
        });


//        chatHead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TashchezUI.this, WallUI.class);
//                if (getIntent().getExtras() != null) {
//                    intent.putExtra("statusType", "תשחץ");
//                    intent.putExtra("statusIndex", getIntent().getExtras().getInt("statusIndex"));
//                }
//                Log.d("122333", "TashchezUI: " + getIntent().getExtras().getInt("statusIndex"));
//                startActivity(intent);
//            }
//        });
//        chatHead.setOnTouchListener(new View.OnTouchListener() {
//            private float initialX;
//            private float initialY;
//            private float initialTouchX;
//            private float initialTouchY;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        initialX = v.getX();
//                        initialY = v.getY();
//                        initialTouchX = event.getRawX();
//                        initialTouchY = event.getRawY();
//
//                    case MotionEvent.ACTION_MOVE:
//                        v.setX(initialX + (int) (event.getRawX() - initialTouchX));
//                        v.setY(initialY + (int) (event.getRawY() - initialTouchY));
//
//                }
//
//                if (event.getRawX() - initialTouchX > 0.3 || event.getRawY() - initialTouchY > 0.3)
//                    return true;
//                else
//                    return false;
//            }
//        });
    }



    //get out of SOLVE_MODE and if is out of SOLVE_MODE exit the activity
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {


        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            Log.d("solveMode", "solveMode: " + solveMode);
            if (solveMode) {



                android.os.Handler h = new android.os.Handler();

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
//                            coverImage.setVisibility(View.VISIBLE);
//                            connectedName.setVisibility(View.VISIBLE);


                        coverLayout.setVisibility(View.VISIBLE);

                        //  TashchezUI.editText.clearFocus();
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
                //save the solve of this user
                if (MainActivity.user != null) {
                    db.add(MainActivity.user.getUsername(), TashchezSaveDB.TYPE, getIntent().getExtras().getInt("statusIndex"), adapter.createContentToSave());
                    Toast.makeText(getApplicationContext(), "תשחץ נשמר", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
        return super.dispatchKeyEvent(event);
    }



    @Override
    protected void onStop() {
        super.onStop();

        //update helpForDay in db
        tashchezDAL.getDataFrom("userUpdateHelpForDay?username="+MainActivity.user.getUsername()+"&helpforday="+MainActivity.user.getHelpForDay(), null, null, null);

        SharedPreferences sharedpreferences = getSharedPreferences("helpForDayDate", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("lastUseDate", MainActivity.lastUseDate);
        editor.commit();
    }


}


