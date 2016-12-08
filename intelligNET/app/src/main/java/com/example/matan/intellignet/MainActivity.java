package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity
{
    public static TypeUser user;
    public static ArrayList<ArrayList<?>> savedbBoardsData = new ArrayList<ArrayList<?>>();
    public static ArrayList<String> savedbBoardsType = new ArrayList<String>();
    public static ArrayList<Integer> savedbBoardsIndex = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView disconnect = (TextView)findViewById(R.id.disconnect);
        TextView connectedName = (TextView) findViewById(R.id.connectedName);


        /*Four possible options:
        * 1- this is a guest and no user connected - GOOD
        * 2- this is a guest and we have user that's connected - BAD
        * 3- this is not a guest and no user connected - BAD
        * 4- this is not a guest and we have user that's connected - GOOD*/
        if(LoginActivity.isGuest && user == null)
        {
            disconnect.setVisibility(View.INVISIBLE);
            connectedName.setVisibility(View.INVISIBLE);
        }
        else if(LoginActivity.isGuest && user != null)
        {
            user=null; //TODO make possible to be connected and still go to guest mode. nowadays need to disconnect for guest.
            disconnect.setVisibility(View.INVISIBLE);
            connectedName.setVisibility(View.INVISIBLE);
        }
        else if(!LoginActivity.isGuest && user == null)
        {
            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else if(!LoginActivity.isGuest && user != null)
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

                MainActivity.user = null;

                Intent disconnectIntent = new Intent(getApplicationContext(), LoginActivity.class);
                disconnectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(disconnectIntent);
                finish();
            }
        });


        ArrayList<TypeMenuCell> menuCellArr = new ArrayList<>();
        addMenuItems(menuCellArr);

        GridView menuGrid = (GridView)findViewById(R.id.menuGrid);
       // menuGrid.setVerticalScrollBarEnabled(false);

        menuGrid.setAdapter(new AdapterMenu(this, R.layout.cell_menu, menuCellArr));

        menuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Item Clicked: " + position, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();

                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, TashchezMenu.class);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, TashchezHigayonMenu.class);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, SudokuMenu.class);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, MadregotMenu.class);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, MeuznachMenu.class);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, BeitHamishpatMenu.class);
                        break;
                    case 6:
                        intent = new Intent(MainActivity.this, SheledMisparimMenu.class);
                        break;
                    case 7:
                        intent = new Intent(MainActivity.this, KaveretMenu.class);
                        break;
                    case 8:
                        intent = new Intent(MainActivity.this, TguvatSharsheretMenu.class);
                        break;
                    case 9:
                        intent = new Intent(MainActivity.this, WallUI.class);
                        break;
                    case 10:
                        intent = new Intent(MainActivity.this, HagdarotMenu.class);
                        break;
                    case 11:
                        intent = new Intent(MainActivity.this, OdotMenu.class);
                        break;

                }

                startActivity(intent);
            }
        });
    }


    private void addMenuItems(ArrayList<TypeMenuCell> menuCellArr)
    {
        String[] mainMenuName = new String[]{"תשחץ", "תשחץ" + "\n" + "אי-גיון", "סודוקו", "מדרג-אות", "מאוזנך", "בית" + "\n" + "המשפט", "שלד מספרים",
                "כוורת", "תגובת" + "\n" + "שרשרת", "ראש בקיר", "הגדרות", "אודות"};

        int[] mainMenuPic = new int[]{R.drawable.ic_tashchez, R.drawable.ic_tashchez_higayon, R.drawable.ic_sudoku1, R.drawable.ic_madregot,
                R.drawable.ic_meuznach, R.drawable.ic_beit_hamishpat, R.drawable.ic_sheled_misparim, R.drawable.ic_kaveret, R.drawable.ic_tguvat_sharsheret,
                R.drawable.ic_rosh_bakir, R.drawable.ic_hagdarot, R.drawable.ic_odot,
        };



        for(int i=0 ; i<12 ; i++)
        {
//            r.setAll(mainMenu[i], R.drawable.ic_mainmenu12);
            TypeMenuCell r = new TypeMenuCell(mainMenuName[i], mainMenuPic[i], 1);
            menuCellArr.add(r);
        }
    }


//if we are in the MainActivity but on a "guest mode" and press back.. want to open the LoginActivity
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {


        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if(LoginActivity.isGuest)
            {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                this.finish();//have to close this activity to be sure that won't have several activities like that
            }

        }
        return super.dispatchKeyEvent(event);
    }
}



