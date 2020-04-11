package com.example.watizit.popups;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.watizit.R;
import com.example.watizit.utils.DesignUtil;
import com.example.watizit.utils.MoneyUtil;

public class HintsPopup extends Dialog {

    public HintsPopup(Context context)
    {
        super(context);
        setContentView(R.layout.hints_popup);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        if(getWindow() == null) return;

        TextView moneyText = getWindow().findViewById(R.id.moneyText);
        Button clueButton1 =  getWindow().findViewById(R.id.clueButton1);
        Button clueButton2 =  getWindow().findViewById(R.id.clueButton2);
        Button clueButton3 =  getWindow().findViewById(R.id.clueButton3);
        Button closeButton =  getWindow().findViewById(R.id.closeButton);
        String strMoney = String.valueOf(MoneyUtil.getMoney());

        moneyText.setText(moneyText.getText().toString().replace("%d", strMoney));

        DesignUtil.setBgColor(clueButton1, R.color.COLOR_GOLD);
        DesignUtil.setBgColor(clueButton2, R.color.COLOR_GOLD);
        DesignUtil.setBgColor(clueButton3, R.color.COLOR_GOLD);
        DesignUtil.setBgColor(closeButton, R.color.COLOR_RED);

        moneyText.setText(DesignUtil.applyIcons(moneyText.getText(), 0.8F));
        clueButton1.setText(DesignUtil.applyIcons(clueButton1.getText(), 0.8F));
        clueButton2.setText(DesignUtil.applyIcons(clueButton2.getText(), 0.8F));
        clueButton3.setText(DesignUtil.applyIcons(clueButton3.getText(), 0.8F));

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
