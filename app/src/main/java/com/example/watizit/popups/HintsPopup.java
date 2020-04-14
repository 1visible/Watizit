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
        if (getWindow() == null) return;

        TextView moneyText = getWindow().findViewById(R.id.moneyText);
        Button clueButton1 = getWindow().findViewById(R.id.clueButton1);
        Button clueButton2 = getWindow().findViewById(R.id.clueButton2);
        Button clueButton3 = getWindow().findViewById(R.id.clueButton3);
        Button closeButton = getWindow().findViewById(R.id.closeButton);
        String strMoney = String.valueOf(MoneyUtil.getMoney());
        String strMoneyText = App.getContext().getResources().getText(R.string.moneyText).toString();
        CharSequence strClue1Text = App.getContext().getResources().getText(R.string.clue1);
        CharSequence strClue2Text = App.getContext().getResources().getText(R.string.clue2);
        CharSequence strClue3Text = App.getContext().getResources().getText(R.string.clue3);

        moneyText.setText(DesignUtil.applyIcons(strMoneyText.replace("%d", strMoney), 0.8F));
        clueButton1.setText(strClue1Text);
        clueButton2.setText(strClue2Text);
        clueButton3.setText(strClue3Text);

        DesignUtil.setBgColor(clueButton1, R.color.COLOR_OVERLAY);
        DesignUtil.setBgColor(clueButton2, R.color.COLOR_OVERLAY);
        DesignUtil.setBgColor(clueButton3, R.color.COLOR_OVERLAY);
        DesignUtil.setBgColor(closeButton, R.color.COLOR_RED);

        DesignUtil.startBounceIn(clueButton1, 0.15F);
        DesignUtil.startBounceIn(clueButton2, 0.3F);
        DesignUtil.startBounceIn(clueButton3, 0.45F);

        if(level.canBuyHint(1))
        {
            clueButton1.setText(DesignUtil.applyIcons(clueButton1.getText(), 0.8F, R.color.COLOR_GOLD));
            clueButton1.setOnClickListener(new View.OnClickListener() {
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
            clueButton1.setText(DesignUtil.applyIcons(clueButton1.getText(), 0.8F, R.color.COLOR_DARK));
            clueButton1.setTextColor(getContext().getResources().getColor(R.color.COLOR_DARK));
        }

        if(level.canBuyHint(2))
        {
            clueButton2.setText(DesignUtil.applyIcons(clueButton2.getText(), 0.8F, R.color.COLOR_GOLD));
            clueButton2.setOnClickListener(new View.OnClickListener() {
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
            clueButton2.setText(DesignUtil.applyIcons(clueButton2.getText(), 0.8F, R.color.COLOR_DARK));
            clueButton2.setTextColor(getContext().getResources().getColor(R.color.COLOR_DARK));
        }

        if(level.canBuyHint(3))
        {
            clueButton3.setText(DesignUtil.applyIcons(clueButton3.getText(), 0.8F, R.color.COLOR_GOLD));
            clueButton3.setOnClickListener(new View.OnClickListener() {
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
            clueButton3.setText(DesignUtil.applyIcons(clueButton3.getText(), 0.8F, R.color.COLOR_DARK));
            clueButton3.setTextColor(getContext().getResources().getColor(R.color.COLOR_DARK));
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
