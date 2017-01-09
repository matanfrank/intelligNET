package com.example.matan.intellignet;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by matan on 01/05/2016.
 */
public class TashchezBL {
    private String data;
    private ArrayList<Integer> defIndex = new  ArrayList<Integer>();
    private ArrayList<String> cellType = new  ArrayList<String>();
    private ArrayList<Integer> cellIndex = new ArrayList<Integer>();
    private ArrayList<String> content = new  ArrayList<String>();
    private ArrayList<Integer> numOfLetter = new  ArrayList<Integer>();
    private ArrayList<String> solution = new  ArrayList<String>();
    private Context context;
    private android.os.Handler ha;
    private Runnable ru;


    private TypeTashchezStruct tashchezStruct;


    public TashchezBL(String data, Context context, android.os.Handler h, Runnable r) {
        this.data = data;
        this.context = context;
        stringToArrays(h, r);

    }

    public TypeTashchezStruct getTashchezStruct() {
        return tashchezStruct;
    }


    private void stringToArrays(final android.os.Handler h, final Runnable r) {
        String tempString = "";
        for (int i = 0; i < data.length(); i++) {
            while (i < data.length() && data.charAt(i) != '*') {
                tempString += data.charAt(i);
                i++;
            }
            try {
                //Log.d("123456789", "the number to parse:::: " + tempString);
                //Integer integer = new Integer(Integer.parseInt(tempString));

                defIndex.add(Integer.parseInt(tempString));//TODO check this is number!!!!!! ot try catch throw
            } catch (NumberFormatException e) {
                Log.d("123456789", e.getMessage());
            }

            i++;//skip the '*'
            tempString = "";


            while (i < data.length() && data.charAt(i) != '*') {
                tempString += data.charAt(i);
                i++;
            }
            cellType.add(tempString);


            i++;//skip the '*'
            tempString = "";


            while (i < data.length() && data.charAt(i) != '|') {
                tempString += data.charAt(i);
                i++;
            }
            cellIndex.add(Integer.parseInt(tempString));//TODO check this is number!!!!!! ot try catch throw

            tempString = "";

        }


//                        for(int i=0 ; i<defIndex.size() ; i++)
//                {
//                    Log.d("123456789", ""+defIndex.get(i));
//                    Log.d("123456789", cellType.get(i));
//                    Log.d("123456789", ""+cellIndex.get(i));
//    }



        //Now i have all the data devided to arrays so i go to the server to get the content of each def in "defIndex" array


        TashchezDAL tashchezDAL = new TashchezDAL(context);

        String s = "";

        for(int i=0 ; i < defIndex.size() ; i++)
        {
            s += "defIndex" + i + "=" + defIndex.get(i);
            if(i<defIndex.size()-1)//last one dont need '&'
            s += "&";
        }

        ha = new android.os.Handler();
        ru = new Runnable() {


            @Override
            public void run() {

                for(int i=0 ; i<defIndex.size() ; i++) {
                    try {
                        content.add(TashchezDAL.jsonArray.getJSONObject(i).getString("definition"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        solution.add(TashchezDAL.jsonArray.getJSONObject(i).getString("solution"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        numOfLetter.add(Integer.parseInt(TashchezDAL.jsonArray.getJSONObject(i).getString("solutionlength")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tashchezStruct = new TypeTashchezStruct(cellType, cellIndex, content, numOfLetter, solution);

                }

                for(int i=0 ; i<defIndex.size() ; i++)
                    Log.d("123456789", defIndex.get(i) + " " + tashchezStruct.cellType.get(i) + " " + tashchezStruct.cellIndex.get(i) + " " + tashchezStruct.content.get(i) + " " + tashchezStruct.numOfLetter.get(i));

                h.post(r);
            }
        };


        tashchezDAL.getDataFrom("definitionGet?" + s, ha, ru, null);




    }



}
