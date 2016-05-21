package com.example.matan.intellignet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class WallNewsFragment extends Fragment {


    public static WallNewsFragment newInstance(){//(String param1, String param2) {
        WallNewsFragment fragment = new WallNewsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wall_news, container, false);


        ArrayList<TypePost> typePostArr = new ArrayList<>();
        addMenuItems(typePostArr);

        ListView list = (ListView)view.findViewById(R.id.news_posts_list);
        // menuGrid.setVerticalScrollBarEnabled(false);

        list.setAdapter(new AdapterPosts(getActivity(), R.layout.row_post, typePostArr));


        return view;
    }




    private void addMenuItems(ArrayList<TypePost> typePostArr)
    {
        String name;
        if (MainActivity.user != null)
             name = MainActivity.user.getFirstName() + " " + MainActivity.user.getLastName();
        else
             name = "מתן פרנק";
        String date = "21-07-1994 16:25";
        String content = "שלום מישו יודע מה הפרוש של מזל טוב לספונג'ה? הבנתי שמזל זה שם של זקנה מרוקאית והיא עושה הרבה ספונג'ה";
        String type = "תשחץ הגיון";
        String index = "2";



        for(int i=1 ; i<11 ; i++)
        {
            TypePost r = new TypePost(name, date, content, type, index);
            typePostArr.add(r);
        }
    }
}
