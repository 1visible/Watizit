package com.example.watizit.popups;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.watizit.R;
import com.example.watizit.classes.Level;
import com.example.watizit.utils.DesignUtil;
import com.example.watizit.utils.MoneyUtil;

public class HintsPopup extends Dialog {

    private HintsListener listener;

    public HintsPopup(Context context)
    {
        super(context, R.style.app_Dialog);
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
        if (getWindow() == null) return;

        Resources res = getContext().getResources();
        TextView moneyText = getWindow().findViewById(R.id.moneyText);
        Button hintButton1 = getWindow().findViewById(R.id.hintButton1);
        Button hintButton2 = getWindow().findViewById(R.id.hintButton2);
        Button hintButton3 = getWindow().findViewById(R.id.hintButton3);
        Button closeButton = getWindow().findViewById(R.id.closeButton);
        String strMoney = String.valueOf(MoneyUtil.getMoney());
        String strMoneyText = res.getText(R.string.hintsPopup_account).toString();
        CharSequence strClue1Text = res.getText(R.string.hintsPopup_hint1);
        CharSequence strClue2Text = res.getText(R.string.hintsPopup_hint2);
        CharSequence strClue3Text = res.getText(R.string.hintsPopup_hint3);

        moneyText.setText(DesignUtil.applyIcons(strMoneyText.replace("%d", strMoney), 0.8F));
        hintButton1.setText(strClue1Text);
        hintButton2.setText(strClue2Text);
        hintButton3.setText(strClue3Text);

        DesignUtil.setBgColor(hintButton1, R.color.COLOR_OVERLAY);
        DesignUtil.setBgColor(hintButton2, R.color.COLOR_OVERLAY);
        DesignUtil.setBgColor(hintButton3, R.color.COLOR_OVERLAY);
        DesignUtil.setBgColor(closeButton, R.color.COLOR_RED);

        DesignUtil.startBounceIn(hintButton1, 0.15F);
        DesignUtil.startBounceIn(hintButton2, 0.3F);
        DesignUtil.startBounceIn(hintButton3, 0.45F);

        if(level.canBuyHint(1))
        {
            hintButton1.setText(DesignUtil.applyIcons(hintButton1.getText(), 0.8F, R.color.COLOR_GOLD));
            hintButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    level.buyHint(1);
                    listener.applyHint(1);
                }
            });
        }
        else
        {
            hintButton1.setText(DesignUtil.applyIcons(hintButton1.getText(), 0.8F, R.color.COLOR_DARK));
            hintButton1.setTextColor(res.getColor(R.color.COLOR_DARK));
        }

        if(level.canBuyHint(2))
        {
            hintButton2.setText(DesignUtil.applyIcons(hintButton2.getText(), 0.8F, R.color.COLOR_GOLD));
            hintButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    level.buyHint(2);
                    listener.applyHint(2);
                }
            });
        }
        else
        {
            hintButton2.setText(DesignUtil.applyIcons(hintButton2.getText(), 0.8F, R.color.COLOR_DARK));
            hintButton2.setTextColor(res.getColor(R.color.COLOR_DARK));
        }

        if(level.canBuyHint(3))
        {
            hintButton3.setText(DesignUtil.applyIcons(hintButton3.getText(), 0.8F, R.color.COLOR_GOLD));
            hintButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    level.buyHint(3);
                    listener.applyHint(3);
                }
            });
        }
        else
        {
            hintButton3.setText(DesignUtil.applyIcons(hintButton3.getText(), 0.8F, R.color.COLOR_DARK));
            hintButton3.setTextColor(res.getColor(R.color.COLOR_DARK));
        }

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
