package com.example.watizit.popups;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.watizit.R;
import com.example.watizit.classes.App;
import com.example.watizit.classes.Level;
import com.example.watizit.utils.DesignUtil;
import com.example.watizit.utils.MoneyUtil;

public class HintsPopup extends Dialog {

    private HintsListener listener;

    public HintsPopup(Context context)
    {
        super(context);
        setContentView(R.layout.hints_popup);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        try
        {
            listener = (HintsListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement HintsListener");
        }
    }

    public void showWith(final Level level)
    {
        if(getWindow() == null) return;

        TextView moneyText = getWindow().findViewById(R.id.moneyText);
        Button clueButton1 =  getWindow().findViewById(R.id.clueButton1);
        Button clueButton2 =  getWindow().findViewById(R.id.clueButton2);
        Button clueButton3 =  getWindow().findViewById(R.id.clueButton3);
        Button closeButton =  getWindow().findViewById(R.id.closeButton);
        String strMoney = String.valueOf(MoneyUtil.getMoney());
        String strMoneyText = App.getContext().getResources().getText(R.string.clue).toString();

        moneyText.setText(DesignUtil.applyIcons(strMoneyText.replace("%d", strMoney), 0.8F));
        clueButton1.setText(DesignUtil.applyIcons(clueButton1.getText(), 0.8F));
        clueButton2.setText(DesignUtil.applyIcons(clueButton2.getText(), 0.8F));
        clueButton3.setText(DesignUtil.applyIcons(clueButton3.getText(), 0.8F));

        DesignUtil.setBgColor(closeButton, R.color.COLOR_RED);

        if(level.canBuyHint(1))
        {
            DesignUtil.setBgColor(clueButton1, R.color.COLOR_GOLD);
            clueButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss(); level.buyHint(1); listener.applyHint(1);
                }
            });
        }
        else
            DesignUtil.setBgColor(clueButton1, R.color.COLOR_BACKGROUND_DARKER);

        if(level.canBuyHint(2))
        {
            DesignUtil.setBgColor(clueButton2, R.color.COLOR_GOLD);
            clueButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss(); level.buyHint(2); listener.applyHint(2);
                }
            });
        }
        else
            DesignUtil.setBgColor(clueButton2, R.color.COLOR_BACKGROUND_DARKER);

        if(level.canBuyHint(3))
        {
            DesignUtil.setBgColor(clueButton3, R.color.COLOR_GOLD);
            clueButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss(); level.buyHint(3); listener.applyHint(3);
                }
            });
        }
        else
            DesignUtil.setBgColor(clueButton3, R.color.COLOR_BACKGROUND_DARKER);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        show();
    }

    public interface HintsListener {
        void applyHint(int hintNumber);
    }

}
