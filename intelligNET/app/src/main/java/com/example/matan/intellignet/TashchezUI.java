package com.example.matan.intellignet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import org.json.JSONException;

public class TashchezUI extends AppCompatActivity
{
    public static final int NUM_COL=7;
    public static final int NUM_ROW=7;
    private  ImageView coverImage;
    private TextView connectedName;
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
    private FloatingActionButton chatHead;
    private FloatingActionButton eraseHead;
    private FloatingActionButton saveHead;
    public static boolean clickOnErase = false;
    public static TashchezSaveDB db;
    public static String savedSolution = "";

    int helpForDay = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tashchez_ui);
        TextView disconnect = (TextView)findViewById(R.id.disconnect);

        //if there's is user logged in
        if(!LoginActivity.guest)
        {
            if (MainActivity.user != null)
            {
                connectedName = (TextView) findViewById(R.id.connectedName);
                connectedName.setText(MainActivity.user.getFirstName() + " " + MainActivity.user.getLastName());
            }
            else
            {
                MainActivity.user = new TypeUser("משתמש","יקר", "","","","", 0, 0);
            }
        }
        else
            disconnect.setVisibility(View.INVISIBLE);

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
                    Log.d("123456789", ""+tashchezBL.getTashchezStruct().cellType.get(i) + " " + tashchezBL.getTashchezStruct().cellIndex.get(i) + " " + tashchezBL.getTashchezStruct().content.get(i) + " " + tashchezBL.getTashchezStruct().numOfLetter.get(i));
                tashchez = new TypeTashchezGrid(NUM_ROW, NUM_COL, true, tashchezBL.getTashchezStruct(), true, getApplicationContext());


                adapter = new TashchezAdapter(activity, R.layout.cell_definition_tashchez, R.layout.cell_solve_tashchez, savedSolution, tashchez.board);//, new TashchezPassEditText() {
                //adapter.createContentToSave(savedSolution);
                adapter.setShowAlertInterface(new ShowAlertDialogInterface() {
                    @Override
                    public void showAlertDialog(final TypeTashchezCell tashchezCell) {

                        TashchezUI.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showDialog();
                            }

                            private void showDialog() {
                                new AlertDialog.Builder(TashchezUI.this)
                                        .setTitle("עוזר אינטליג-NET-י")
                                        .setMessage(MainActivity.user.getFirstName() + " " + MainActivity.user.getLastName() + ", נשארו לך עוד "+ helpForDay +" עזרות להיום.\nהאם אתה בטוח שברצונך לחשוף את הפתרון עבור משבצת זו?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {


                                                int defIndex = tashchezCell.index;
                                                int letterCounter = 0; // to know which letter of the answer to put

                                                if ((defIndex + 1) < (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && tashchez.board.get(defIndex + 1).getCellType().contains("definition") &&
                                                        (defIndex - 1) >= 0 && tashchez.board.get(defIndex - 1).getCellType().contains("definition")) {
                                                    while (tashchez.board.get(defIndex).getCellType().contains("solve") && defIndex >= TashchezUI.NUM_COL)//go up till def cell or till te first cell in the column
                                                    {
                                                        Log.d("lettecounter1", ""+letterCounter);
                                                        defIndex -= TashchezUI.NUM_ROW;
                                                        letterCounter++;
                                                    }
                                                    Log.d("lettecounter2", ""+letterCounter);
                                                    //if we reached the first cell in the column and it is still "solve cell", the "def cell" can be: one left or one right
                                                    if (tashchez.board.get(defIndex).getCellType().contains("solve") && defIndex < TashchezUI.NUM_COL) {
                                                        if (tashchez.board.get(defIndex).getCellType().contains("solveRightDown"))
                                                            defIndex++;
                                                        else if (tashchez.board.get(defIndex).getCellType().contains("solveLeftDown"))
                                                            defIndex--;
                                                    }

                                                    //if we are in "def cell", the "def cell" can be:
                                                    //a - the cell we just reached
                                                    //b - one cell forward and one right
                                                    //c - one cell forward and one left
                                                    else if (tashchez.board.get(defIndex).getCellType().contains("definition")) {
                                                        {
                                                            letterCounter--;
                                                            //a - the cell we just reached
                                                            if (tashchez.board.get(defIndex).getCellType().contains("definitionDown"))
                                                                defIndex = defIndex; //do nothing

                                                                //b - one cell forward and one up
                                                                //c - one cell forward and one down
                                                            else {
                                                                defIndex += TashchezUI.NUM_COL;

                                                                if (tashchez.board.get(defIndex).getCellType().contains("solveRightDown"))
                                                                    defIndex++;
                                                                else if (tashchez.board.get(defIndex).getCellType().contains("solveLeftDown"))
                                                                    defIndex--;
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    while (tashchez.board.get(defIndex).getCellType().contains("solve") && defIndex % TashchezUI.NUM_ROW != 0)//go to the right till def cell or till the first cell in the line
                                                    {
                                                        defIndex--;
                                                        letterCounter++;
                                                    }

                                                    Log.d("letterCounter", letterCounter + "");

                                                    //if we reached the first cell in the line and it is still "solve cell", the "def cell" can be: one up or one down
                                                    if (tashchez.board.get(defIndex).getCellType().contains("solve") && defIndex % TashchezUI.NUM_ROW == 0) {
                                                        {
                                                            if (tashchez.board.get(defIndex).getCellType().contains("solveDownLeft"))
                                                                defIndex = defIndex - TashchezUI.NUM_COL;
                                                            else if (tashchez.board.get(defIndex).getCellType().contains("solveUpLeft"))
                                                                defIndex = defIndex + TashchezUI.NUM_COL;
                                                        }
                                                    }

                                                    //if we "def cell", the "def cell" can be:
                                                    //a - the cell we just reached
                                                    //b - one cell forward and one up
                                                    //c - one cell forward and one down
                                                    else if (tashchez.board.get(defIndex).getCellType().contains("definition")) {
                                                        {
                                                            letterCounter--;
                                                            //a - the cell we just reached
                                                            if (tashchez.board.get(defIndex).getCellType().contains("definitionLeft"))
                                                                defIndex = defIndex; //do nothing

                                                                //b - one cell forward and one up
                                                                //c - one cell forward and one down
                                                            else {
                                                                defIndex++;

                                                                if (tashchez.board.get(defIndex).getCellType().contains("solveDownLeft"))
                                                                    defIndex = defIndex - TashchezUI.NUM_COL;
                                                                else if (tashchez.board.get(defIndex).getCellType().contains("solveUpLeft"))
                                                                    defIndex = defIndex + TashchezUI.NUM_COL;
                                                            }
                                                        }
                                                    }
                                                }
                                                String solutionLetter= "";
                                                if(letterCounter < tashchez.board.get(defIndex).solution.length())
                                                    solutionLetter = tashchez.board.get(defIndex).solution.charAt(letterCounter) + "";
                                                Log.d("04.07", "solutionLetter: " + solutionLetter + "  letterCounter: " + letterCounter);
                                                tashchez.board.get(tashchezCell.getIndex()).editText.setText(solutionLetter);


                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        });
                    }
                });




//                    @Override
//                    public void setEditText(newEditText editText) {
//                        TashchezUI.editText = editText;
//                    }
//                });



                tashchezGrid.setAdapter(adapter);

                clickOnErase = false;
            }
        };

        tashchezDAL.getDataFrom("tashchezGet", h, r1);




//        ShowAlertDialogInterface AlertDialog = new ShowAlertDialogInterface() {
//            @Override
//            public void setAlertDialog( dialog) {
//                AlertDialog.Builder(getContext())
//                                                    .setTitle("Moustachify Link")
//                                                    .setMessage("Paste in the link of an image to moustachify!")
//                                                    .setView(txtUrl)
//                                                    .setPositiveButton("Moustachify", new DialogInterface.OnClickListener() {
//                                                        public void onClick(DialogInterface dialog, int whichButton) {
////                                                            {
//                int defIndex = tashchezCell.index;
//                int letterCounter = 0; // for THIRD_CLICK_SOLVE to know which letter of the answer to put
//
//                if (getItem(defIndex + 1).getCellType().contains("definition")) {
//                    while (getItem(defIndex).getCellType().contains("solve") && defIndex >= TashchezUI.NUM_COL)//go up till def cell or till te first cell in the column
//                    {
//                        defIndex -= TashchezUI.NUM_ROW;
//                        letterCounter++;
//                    }
//
//                    //if we reached the first cell in the column and it is still "solve cell", the "def cell" can be: one left or one right
//                    if (getItem(defIndex).getCellType().contains("solve") && defIndex < TashchezUI.NUM_COL) {
//                        if (getItem(defIndex).getCellType().contains("solveRightDown"))
//                            defIndex++;
//                        else if (getItem(defIndex).getCellType().contains("solveLeftDown"))
//                            defIndex--;
//                    }
//
//                    //if we are in "def cell", the "def cell" can be:
//                    //a - the cell we just reached
//                    //b - one cell forward and one right
//                    //c - one cell forward and one left
//                    else if (getItem(defIndex).getCellType().contains("definition")) {
//                        {
//                            letterCounter--;
//                            //a - the cell we just reached
//                            if (getItem(defIndex).getCellType().contains("definitionDown"))
//                                defIndex = defIndex; //do nothing
//
//                                //b - one cell forward and one up
//                                //c - one cell forward and one down
//                            else {
//                                defIndex += TashchezUI.NUM_COL;
//
//                                if (getItem(defIndex).getCellType().contains("solveRightDown"))
//                                    defIndex++;
//                                else if (getItem(defIndex).getCellType().contains("solveLeftDown"))
//                                    defIndex--;
//                            }
//                        }
//                    }
//                } else {
//                    while (getItem(defIndex).getCellType().contains("solve") && defIndex % TashchezUI.NUM_ROW != 0)//go to the right till def cell or till the first cell in the line
//                    {
//                        defIndex--;
//                        letterCounter++;
//                    }
//
//                    Log.d("letterCounter", letterCounter + "");
//
//                    //if we reached the first cell in the line and it is still "solve cell", the "def cell" can be: one up or one down
//                    if (getItem(defIndex).getCellType().contains("solve") && defIndex % TashchezUI.NUM_ROW == 0) {
//                        {
//                            if (getItem(defIndex).getCellType().contains("solveDownLeft"))
//                                defIndex = defIndex - TashchezUI.NUM_COL;
//                            else if (getItem(defIndex).getCellType().contains("solveUpLeft"))
//                                defIndex = defIndex + TashchezUI.NUM_COL;
//                        }
//                    }
//
//                    //if we "def cell", the "def cell" can be:
//                    //a - the cell we just reached
//                    //b - one cell forward and one up
//                    //c - one cell forward and one down
//                    else if (getItem(defIndex).getCellType().contains("definition")) {
//                        {
//                            letterCounter--;
//                            //a - the cell we just reached
//                            if (getItem(defIndex).getCellType().contains("definitionLeft"))
//                                defIndex = defIndex; //do nothing
//
//                                //b - one cell forward and one up
//                                //c - one cell forward and one down
//                            else {
//                                defIndex++;
//
//                                if (getItem(defIndex).getCellType().contains("solveDownLeft"))
//                                    defIndex = defIndex - TashchezUI.NUM_COL;
//                                else if (getItem(defIndex).getCellType().contains("solveUpLeft"))
//                                    defIndex = defIndex + TashchezUI.NUM_COL;
//                            }
//                        }
//                    }
//                }
//                String solutionLetter = getItem(defIndex).solution.charAt(letterCounter) + "";
//                getItem(tashchezCell.getIndex()).editText.setText(solutionLetter);
////                                                            }
////                                                        }
////                                                    })
////                                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
////                                                        public void onClick(DialogInterface dialog, int whichButton) {
////                                                        }
////                                                    })
////                                                    .show();
////                                        }
////                                    });
//            }
//        }




        startService(new Intent(this, ChatHeadService.class));

        chatHead = (FloatingActionButton) findViewById(R.id.wallFB);
        eraseHead = (FloatingActionButton) findViewById(R.id.eraseFB);
        saveHead = (FloatingActionButton) findViewById(R.id.saveFB);

        chatHead.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.FB))));
        eraseHead.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.FB))));
        saveHead.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.FB))));

        chatHead.setRippleColor((getResources().getColor(R.color.FB)));//change color when click
        eraseHead.setRippleColor((getResources().getColor(R.color.FB)));
        saveHead.setRippleColor((getResources().getColor(R.color.FB)));

        saveHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.user != null) {
                    db.add(MainActivity.user.getUsername(), TashchezSaveDB.TYPE, getIntent().getExtras().getInt("statusIndex"), adapter.createContentToSave());
                    Toast.makeText(getApplicationContext(), "תשחץ נשמר", Toast.LENGTH_SHORT).show();
                }
            }
        });


        eraseHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnErase = true;
                h.post(r2);

            }
        });


        chatHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TashchezUI.this, WallUI.class);
                if (getIntent().getExtras() != null) {
                    intent.putExtra("statusType", "תשחץ");
                    intent.putExtra("statusIndex", getIntent().getExtras().getInt("statusIndex"));
                }
                Log.d("122333", "TashchezUI: " + getIntent().getExtras().getInt("statusIndex"));
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
//                    Intent intent = new Intent(this, TashchezMenu .class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                finish();
            }
        }
        return super.dispatchKeyEvent(event);
    }


}


