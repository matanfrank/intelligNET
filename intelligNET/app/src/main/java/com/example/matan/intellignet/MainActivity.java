package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;


public class MainActivity extends Activity
{
    public static TypeUser user;
    public static ArrayList<TypeGameGrid<?>> savedbBoardsData = new ArrayList<>();
    public static ArrayList<String> savedbBoardsType = new ArrayList<String>();
    public static ArrayList<Integer> savedbBoardsIndex = new ArrayList<Integer>();

    private Activity activity = this;
    private TashchezDAL tashchezDAL = new TashchezDAL(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GeneralBL.setNameAndDisconnect(this);
        GeneralBL.loadAd(activity);




        ArrayList<TypeMenuCell> menuCellArr = new ArrayList<>();
        addMenuItems(menuCellArr);

        GridView menuGrid = (GridView)findViewById(R.id.menuGrid);
       // menuGrid.setVerticalScrollBarEnabled(false);
        menuGrid.setVerticalScrollBarEnabled(false);
        menuGrid.setAdapter(new AdapterMenu(this, R.layout.cell_menu, menuCellArr, menuGrid));

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
                        intent = new Intent(MainActivity.this, OdotActivity.class);
                        break;

                }

                startActivity(intent);
            }
        });
    }


//
//    public void onWindowFocusChanged(boolean hasFocus) {
//
//        super.onWindowFocusChanged(hasFocus);
//
//        if(hasFocus)
//        {
//            scaleContents(findViewById(R.id.linearLay), findViewById(R.id.linearLay));
//        }
//    }



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




    // Scales the contents of the given view so that it completely fills the given
// container on one axis (that is, we're scaling isotropically).
    private void scaleContents(View rootView, View container)
    {
        // Compute the scaling ratio
        float xScale = (float)container.getWidth() / rootView.getWidth();
        float yScale = (float)container.getHeight() / rootView.getHeight();
        float scale = Math.min(xScale, yScale);

        // Scale our contents
        scaleViewAndChildren(rootView, scale);
    }
    // Scale the given view, its contents, and all of its children by the given factor.
    public static void scaleViewAndChildren(View root, float scale)
    {
        // Retrieve the view's layout information
        ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
        // Scale the view itself
        if (layoutParams.width != ViewGroup.LayoutParams.FILL_PARENT &&
                layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            layoutParams.width *= scale;
        }
        if (layoutParams.height != ViewGroup.LayoutParams.FILL_PARENT &&
                layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            layoutParams.height *= scale;
        }

        // If this view has margins, scale those too
        if (layoutParams instanceof ViewGroup.MarginLayoutParams)
        {
            ViewGroup.MarginLayoutParams marginParams =
                    (ViewGroup.MarginLayoutParams)layoutParams;
            marginParams.leftMargin *= scale;
            marginParams.rightMargin *= scale;
            marginParams.topMargin *= scale;
            marginParams.bottomMargin *= scale;
        }

        // Set the layout information back into the view
        // Scale the view's padding
        root.setPadding(
                (int)(root.getPaddingLeft() * scale),
                (int)(root.getPaddingTop() * scale),
                (int)(root.getPaddingRight() * scale),
                (int)(root.getPaddingBottom() * scale));

        // If the root view is a TextView, scale the size of its text
        if (root instanceof TextView)
        {
            TextView textView = (TextView)root;
            textView.setTextSize(textView.getTextSize() * scale);
        }

        // If the root view is a ViewGroup, scale all of its children recursively
        if (root instanceof ViewGroup)
        {
            ViewGroup groupView = (ViewGroup)root;
            for (int cnt = 0; cnt < groupView.getChildCount(); ++cnt)
                scaleViewAndChildren(groupView.getChildAt(cnt), scale);
        }
    }
}



