package com.example.demoandroid;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

public class OrderListInputFilter implements InputFilter {
    Character first = null;
    Character second = null;

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if(source == null || "".equals(source)){
            return source;
        }

        if(source.charAt(0) == '\n'){
            first = null;
            second = null;
        }

        if(Character.isDigit(source.charAt(0)) && first == null ){
            first = source.charAt(0);
        }

        if(source.charAt(0) == '.' && first != null && second == null){
            second = source.charAt(0);
            return source.charAt(0) + " ";
        }

        return source;
    }
}
