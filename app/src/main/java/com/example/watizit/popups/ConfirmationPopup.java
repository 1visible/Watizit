package com.example.watizit.popups;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.example.watizit.R;
import com.example.watizit.classes.Level;
import com.example.watizit.utils.DesignUtil;

public class ConfirmationPopup extends Dialog {

    private final int hintNum;
    private final Level lvl;

    public ConfirmationPopup(Context context, int hintNumber, Level level)
    {
        super(context);
        setContentView(R.layout.confirmation_popup);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        hintNum = hintNumber;
        lvl = level;

        if(getWindow() == null) return;

        Button noButton =  getWindow().findViewById(R.id.noButton);
        Button yesButton =  getWindow().findViewById(R.id.yesButton);

        DesignUtil.setBgColor(noButton, R.color.COLOR_RED);
        DesignUtil.setBgColor(yesButton, R.color.COLOR_BLUE);

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvl.buyHint(hintNum);
            }
        });
    }

}
