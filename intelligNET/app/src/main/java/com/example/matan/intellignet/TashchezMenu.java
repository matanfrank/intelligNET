package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.ArrayList;

public class TashchezMenu extends Activity {

    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tashchez_menu);


        GeneralBL.setNameAndDisconnect(this);
        GeneralBL.loadAd(activity);

        final ArrayList<TypeMenuCell> menuCellArr = new ArrayList<>();
        addMenuItems(menuCellArr);

        GridView menuGrid = (GridView)findViewById(R.id.menuGrid);
        // menuGrid.setVerticalScrollBarEnabled(false);

        menuGrid.setAdapter(new AdapterMenu(this, R.layout.cell_menu, menuCellArr, menuGrid));

        menuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Item Clicked: " + position, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();

                //    server.GetGameById(menuCellArr.Id)
                switch (position) {
                    case 0:
                        intent = new Intent(TashchezMenu.this, TashchezUI.class);
                        intent.putExtra("statusIndex", position + 1);
                        break;
                    case 1:
                        intent = new Intent(TashchezMenu.this, TashchezUI.class);
                        intent.putExtra("statusIndex", position + 1);
                        break;
                    case 2:
                        intent = new Intent(TashchezMenu.this, TashchezUI.class);
                        intent.putExtra("statusIndex", position + 1);
                        break;
                    case 3:
                        intent = new Intent(TashchezMenu.this, TashchezUI.class);
                        intent.putExtra("statusIndex", position + 1);
                        break;
                    case 4:
                        intent = new Intent(TashchezMenu.this, TashchezUI.class);
                        intent.putExtra("statusIndex", position + 1);
                        break;
                    case 5:
                        intent = new Intent(TashchezMenu.this, TashchezUI.class);
                        intent.putExtra("statusIndex", position + 1);
                        break;
                    case 6:
                        intent = new Intent(TashchezMenu.this, TashchezUI.class);
                        intent.putExtra("statusIndex", position + 1);
                        break;
                    case 7:
                        intent = new Intent(TashchezMenu.this, TashchezUI.class);
                        intent.putExtra("statusIndex", position + 1);
                        break;
                    case 8:
                        intent = new Intent(TashchezMenu.this, TashchezUI.class);
                        intent.putExtra("statusIndex", position + 1);
                        break;
                    case 9:
                        intent = new Intent(TashchezMenu.this, TashchezUI.class);
                        intent.putExtra("statusIndex", position + 1);
                        break;
                    case 10:
                        intent = new Intent(TashchezMenu.this, TashchezUI.class);
                        intent.putExtra("statusIndex", position + 1);
                        break;
                    case 11:
                        intent = new Intent(TashchezMenu.this, TashchezUI.class);
                        intent.putExtra("statusIndex", position + 1);
                        break;
                }

                startActivity(intent);
            }
        });
    }


    private void addMenuItems(ArrayList<TypeMenuCell> menuCellArr)
    {
        String mainMenuName = "תשחץ";
        int mainMenuPic = R.drawable.ic_tashchez;

        for(int i=1 ; i<37 ; i++)
        {

            TypeMenuCell r = new TypeMenuCell(mainMenuName + " " + i, mainMenuPic, 1);////////////////change 1 TODO
            menuCellArr.add(r);
        }
    }
}

