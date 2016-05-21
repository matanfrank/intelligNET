package com.example.matan.intellignet;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by matan on 16/05/2016.
 */
public class TypePost
{
    private String name;
    private String date;
    private String content;
    private String type;
    private String index;
    private ImageButton deleteButton;
    private EditText writeComment;
    private Button send;
    private Button watchComment;


    public TypePost(String name, String date, String content, String type, String index, ImageButton deleteButton, EditText writeComment, Button send, Button watchComment) {
        this.name = name;
        this.date = date;
        this.content = content;
        this.type = type;
        this.index = index;
        this.deleteButton = deleteButton;
        this.writeComment = writeComment;
        this.send = send;
        this.watchComment = watchComment;
    }


    public TypePost(String name, String date, String content, String type, String index) {
        this.name = name;
        this.date = date;
        this.content = content;
        this.type = type;
        this.index = index;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getIndex() {
        return index;
    }
}
