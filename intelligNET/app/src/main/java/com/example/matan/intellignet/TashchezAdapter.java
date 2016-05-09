package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by matan on 16/12/2015.
 */
public class TashchezAdapter extends ArrayAdapter<TypeTashchezCell> {
    private static final int DEFINITION = 0;
    private static final int SOLVE = 1;
    private static final int ERROR = -1;
    private static final int INIT = -2;
    private int slvLayout;
    private int defLayout;
    private LayoutInflater inflater;
    private InputMethodManager imm;
    private newEditText editText;
    private TashchezPassEditText tashchezPassEditText;
    private ImageView coverImage;
    public static Activity context;
    private int[] lastPaint;
    private int lastPaintCounter = 0;
    private static boolean setFocusDefClick = false;


    public TashchezAdapter(Activity activity, int defLayout, int slvLayout, ArrayList data, TashchezPassEditText tashchezPassEditText) {
        super(activity, slvLayout, data);
        this.slvLayout = slvLayout;
        this.defLayout = defLayout;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tashchezPassEditText = tashchezPassEditText;
        coverImage = (ImageView) activity.findViewById(R.id.symbolImage);
        lastPaint = new int[TashchezUI.NUM_COL];
        for (int i = 0; i < lastPaint.length; i++)
            lastPaint[i] = INIT;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (getItem(position).cellType.contains("definition"))
            return DEFINITION;
        else
            return SOLVE;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        int type = getItemViewType(position);


        if (convertView == null) {
            switch (type) {
                case DEFINITION:
                    convertView = inflater.inflate(defLayout, parent, false);
                    break;

                case SOLVE:
                    convertView = inflater.inflate(slvLayout, parent, false);
                    break;
            }
        }



        final TypeTashchezCell tashchezCell = getItem(position);

        TextView textView;
        ImageView imageView;


        switch (type) {
            case DEFINITION:

                textView = (TextView) convertView.findViewById(R.id.tashchez_text_view);
                textView.setText(tashchezCell.content);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        coverImage.setVisibility(View.GONE);
                        paintAnswer(tashchezCell);
                        TashchezUI.solveMode = true;
                        setFocusDefClick = true;}
                });


                imageView = (ImageView) convertView.findViewById(R.id.tashchez_image_def);
                imageView.setImageResource(tashchezCell.background);
                break;


            case SOLVE:
                editText = (newEditText) convertView.findViewById(R.id.tashchez_edit_text);
                tashchezCell.setEditText(editText);

                tashchezPassEditText.setEditText(editText);

                //lastPaint[0] is the first cell in the answer word.
                //this "if" is to put the cursor in the first cell of answer when the user click on definition
                if (setFocusDefClick && lastPaint[0] != INIT && position == lastPaint[0] && TashchezUI.solveMode)
                {
                    editText.setFocusable(true);
                    editText.requestFocus();

                    imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//
                    imm.showSoftInput(editText, 0);
                }

                //when user writing an answer the cursor has to jump automatically between cells
                editText.addTextChangedListener(new TextWatcher() {
                    private boolean EditTextListenerCall;

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        Log.d("lastPaint", "beforeTextChanged\n");
                        EditTextListenerCall = true;
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Log.d("lastPaint", "onTextChanged\n");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (EditTextListenerCall)
                        {
                            Log.d("lastPaint", "afterTextChanged\n");
                            EditTextListenerCall = false;

                            Log.d("lastPaint", lastPaintCounter + "\n");
                            if ((lastPaintCounter + 1) < lastPaint.length && lastPaint[lastPaintCounter + 1] != INIT) {
                                lastPaintCounter += 1;
                                Log.d("lastPaint", lastPaintCounter + "\n");
                                getItem(lastPaint[lastPaintCounter]).editText.requestFocus();
                            } else {
                                lastPaintCounter = 0;
                            }
                            Log.d("lastPaint", lastPaintCounter + "\n");



                        }
                    }
                });



                imageView = (ImageView) convertView.findViewById(R.id.tashchez_image_solve);
                if (!tashchezCell.onEdit)
                    imageView.setImageResource(tashchezCell.background);
                else
                    imageView.setImageResource(R.drawable.ic_solve_sign);

                //when getting into solveMode the coverImage need to disappear when click on solve cell
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {

                            coverImage.setVisibility(View.GONE);
                            TashchezUI.solveMode = true;
                        }
                    }
                });

                break;
        }

        return convertView;
    }


    private void paintAnswer(TypeTashchezCell typeTashchezCell)//paint the answer cells from white to red
    {
        int j = 0, i = 0;
        int toPaint = typeTashchezCell.index;

        while (i < lastPaint.length && lastPaint[i] != INIT)//make the lastPaint cells back to white before painting the next answer cells
        {
            getItem(lastPaint[i]).onEdit = false;
            i++;
        }

        if (typeTashchezCell.cellType.contains("definition")) {
            for (i = 0; i < typeTashchezCell.answerNumOfLetter; i++) {
                if (typeTashchezCell.cellType.contains("Down") && typeTashchezCell.cellType.contains("Left") && typeTashchezCell.cellType.endsWith("Left")) {
                    if (i == 0)
                        toPaint += TashchezUI.NUM_ROW;
                    else
                        toPaint += 1;
                } else if (typeTashchezCell.cellType.contains("Left") && typeTashchezCell.cellType.contains("Down") && typeTashchezCell.cellType.endsWith("Down")) {
                    if (i == 0)
                        toPaint += 1;
                    else
                        toPaint += TashchezUI.NUM_ROW;
                } else if (typeTashchezCell.cellType.contains("Up") && typeTashchezCell.cellType.contains("Left") && typeTashchezCell.cellType.endsWith("Left")) {
                    if (i == 0)
                        toPaint -= TashchezUI.NUM_ROW;
                    else
                        toPaint += 1;
                } else if (typeTashchezCell.cellType.contains("Right") && typeTashchezCell.cellType.contains("Down") && typeTashchezCell.cellType.endsWith("Down")) {
                    if (i == 0)
                        toPaint -= 1;
                    else
                        toPaint += TashchezUI.NUM_ROW;
                } else if (typeTashchezCell.cellType.contains("Left"))
                    toPaint += 1;
                else if (typeTashchezCell.cellType.contains("Down"))
                    toPaint += TashchezUI.NUM_ROW;

                getItem(toPaint).onEdit = true;
                lastPaint[j] = toPaint;
                j++;
            }
        }
        this.notifyDataSetChanged();

        for (; j < lastPaint.length; j++)//init the cells in lastPaint[] that not in use in a case of an answer with less that NUM_COL letters
            lastPaint[j] = INIT;

    }
}
