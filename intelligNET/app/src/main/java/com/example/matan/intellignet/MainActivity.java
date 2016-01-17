package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ArrayList<TypeMenuCell> menuCellArr = new ArrayList<>();
        addMenuItems(menuCellArr);

        GridView menuGrid = (GridView)findViewById(R.id.menuGrid);
       // menuGrid.setVerticalScrollBarEnabled(false);

        menuGrid.setAdapter(new AdapterMenu(this, R.layout.cell_menu, menuCellArr));

        menuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getApplicationContext(),
                        "Item Clicked: " + position, Toast.LENGTH_SHORT).show();

                Intent intent= new Intent();

                switch(position)
                {
                    case 0:    intent = new Intent(MainActivity.this, TashchezMenu.class); break;
                    case 1:    intent = new Intent(MainActivity.this, TashchezHigayonMenu.class); break;
                    case 2:    intent = new Intent(MainActivity.this, SudokuMenu.class); break;
                    case 3:    intent = new Intent(MainActivity.this, MadregotMenu.class); break;
                    case 4:    intent = new Intent(MainActivity.this, MeuznachMenu.class); break;
                    case 5:    intent = new Intent(MainActivity.this, BeitHamishpatMenu.class); break;
                    case 6:    intent = new Intent(MainActivity.this, SheledMisparimMenu.class); break;
                    case 7:    intent = new Intent(MainActivity.this, KaveretMenu.class); break;
                    case 8:    intent = new Intent(MainActivity.this, TguvatSharsheretMenu.class); break;
                    case 9:    intent = new Intent(MainActivity.this, ChatMenu.class); break;
                    case 10:   intent = new Intent(MainActivity.this, HagdarotMenu.class); break;
                    case 11:   intent = new Intent(MainActivity.this, OdotMenu.class); break;

                }

                startActivity(intent);
            }
        });
    }


    private void addMenuItems(ArrayList<TypeMenuCell> menuCellArr)
    {
        String[] mainMenuName = new String[]{"תשחץ", "תשחץ" + "\n" + "אי-גיון", "סודוקו", "מדרג-אות", "מאוזנך", "בית" + "\n" + "המשפט", "שלד מספרים",
                "כוורת", "תגובת" + "\n" + "שרשרת", "צ'אט", "הגדרות", "אודות"};

        int[] mainMenuPic = new int[]{R.drawable.ic_tashchez, R.drawable.ic_tashchez_higayon, R.drawable.ic_sudoku1, R.drawable.ic_madregot,
                R.drawable.ic_meuznach, R.drawable.ic_beit_hamishpat, R.drawable.ic_sheled_misparim, R.drawable.ic_kaveret, R.drawable.ic_tguvat_sharsheret,
                R.drawable.ic_chat, R.drawable.ic_hagdarot, R.drawable.ic_odot,
        };



        for(int i=0 ; i<12 ; i++)
        {
//            r.setAll(mainMenu[i], R.drawable.ic_mainmenu12);
            TypeMenuCell r = new TypeMenuCell(mainMenuName[i], mainMenuPic[i]);
            menuCellArr.add(r);
        }
    }
}
