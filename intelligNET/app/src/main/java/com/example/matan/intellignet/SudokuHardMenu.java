package com.example.matan.intellignet;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class SudokuHardMenu extends Fragment
{

    public SudokuHardMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sudoku_hard_menu, container, false);
        ArrayList<TypeMenuCell> menuCellArr = new ArrayList<>();
        addMenuItems(menuCellArr);

        GridView menuGrid = (GridView)view.findViewById(R.id.menuGrid3);
        // menuGrid.setVerticalScrollBarEnabled(false);

        menuGrid.setAdapter(new AdapterMenu(getActivity(), R.layout.cell_menu, menuCellArr, menuGrid));
        // Inflate the layout for this fragment
        return view;


//        menuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                Toast.makeText(getApplicationContext(),
//                        "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
//
//                Intent intent= new Intent();
//
//                switch(position)
//                {
//                    case 0:    intent = new Intent(TashchezMenu.this, TashchezMenu.class); break;
//                    case 1:    intent = new Intent(TashchezMenu.this, TashchezMenu.class); break;
//                    case 2:    intent = new Intent(TashchezMenu.this, TashchezMenu.class); break;
//                    case 3:    intent = new Intent(TashchezMenu.this, TashchezMenu.class); break;
//                    case 4:    intent = new Intent(TashchezMenu.this, TashchezMenu.class); break;
//                    case 5:    intent = new Intent(TashchezMenu.this, TashchezMenu.class); break;
//                    case 6:    intent = new Intent(TashchezMenu.this, TashchezMenu.class); break;
//                    case 7:    intent = new Intent(TashchezMenu.this, TashchezMenu.class); break;
//                    case 8:    intent = new Intent(TashchezMenu.this, TashchezMenu.class); break;
//                    case 9:    intent = new Intent(TashchezMenu.this, TashchezMenu.class); break;
//                    case 10:   intent = new Intent(TashchezMenu.this, TashchezMenu.class); break;
//                    case 11:   intent = new Intent(TashchezMenu.this, TashchezMenu.class); break;
//                }
//
//                startActivity(intent);
//            }
//        });

    }


    private void addMenuItems(ArrayList<TypeMenuCell> menuCellArr)
    {
        String menuName = "סודוקו קשה";
        int menuPic = R.drawable.ic_sudoku3;
        for(int i=1 ; i<37 ; i++)
        {
            TypeMenuCell r = new TypeMenuCell(menuName + " " + i, menuPic,1);
            menuCellArr.add(r);
        }
    }
}