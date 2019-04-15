package com.cmlmobilesolutions.libary.edittext;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateEditText extends AppCompatEditText {
    public DateEditText(Context context) {
        super(context);
        setDate();
    }

    public DateEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setDate();
    }

    public DateEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void setDate(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String today = dateFormat.format(Calendar.getInstance().getTime());
        setText(today);


    }

   
}
