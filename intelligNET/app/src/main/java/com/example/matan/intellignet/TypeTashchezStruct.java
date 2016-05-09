package com.example.matan.intellignet;

import java.util.ArrayList;

/**
 * Created by matan on 05/05/2016.
 */
public class TypeTashchezStruct
{
    public ArrayList<String> cellType = new  ArrayList<String>();
    public ArrayList<Integer> cellIndex = new ArrayList<Integer>();
    public ArrayList<String> content = new  ArrayList<String>();
    public ArrayList<Integer> numOfLetter = new  ArrayList<Integer>();

    public TypeTashchezStruct(ArrayList<String> cellType, ArrayList<Integer> cellIndex, ArrayList<String> content, ArrayList<Integer> numOfLetter) {
        this.cellType = cellType;
        this.cellIndex = cellIndex;
        this.content = content;
        this.numOfLetter = numOfLetter;
    }


}
