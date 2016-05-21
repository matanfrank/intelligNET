package com.example.matan.intellignet;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by matan on 03/03/2016.
 * New editText in goal to control on what happening when click on the back button while the soft keyboard is open
 */
public class newEditText extends EditText
{
    public newEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public newEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public newEditText(Context context) {
        super(context);
    }


    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP) {

            InputMethodManager m = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//
            m.hideSoftInputFromWindow(getWindowToken(), 0);

            return false;
        }
        return false;
    }

}
