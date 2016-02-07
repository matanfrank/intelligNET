package com.example.matan.intellignet;

import android.graphics.Picture;
import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by matan on 16/12/2015.
 */
public class TypeMenuCell {
    private int pic;
    private String txt;
    private int id;

    public TypeMenuCell(String txt, int pic, int id)
    {
        this.pic = pic;
        this.txt = txt;
        this.id = id;
    }

    public TypeMenuCell()
    {
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setAll(String txt, int pic) {
        this.txt = txt;
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
