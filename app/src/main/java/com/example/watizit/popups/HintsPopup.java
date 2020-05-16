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

/**
 * This class represents the Hints popup (dialog).
 */
public class HintsPopup extends Dialog {

    private HintsListener listener;

    /**
     * Instantiates a new Hints popup.
     *
     * @param context the context
     */
    public HintsPopup(Context context) {
        // Apply custom dialog style
        super(context, R.style.app_Dialog);
        // Apply hints popup layout
        setContentView(R.layout.hints_popup);
        // Prevent the dialog to be cancelled
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        // Try to make the link between the level menu listener and the dialog
        try {
            listener = (HintsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement HintsListener");
        }
    }

    /**
     * Instead of using the basic show() method, we use a custom showWith(Level level) method
     * that can show the dialog with any given level.
     *
     * @param level the level
     */
    public void showWith(final Level level) {
        // If the dialog window isn't accessible, abort the operation
        if (getWindow() == null) return;

        /*                  📌 VARIABLES

            • Retrieve views for design, click listener and/or gameplay
            • Retrieve other objects for gameplay purposes
        */

        TextView moneyText = getWindow().findViewById(R.id.moneyText);
        Button hintButton1 = getWindow().findViewById(R.id.hintButton1);
        Button hintButton2 = getWindow().findViewById(R.id.hintButton2);
        Button hintButton3 = getWindow().findViewById(R.id.hintButton3);
        Button closeButton = getWindow().findViewById(R.id.closeButton);

        Resources res = getContext().getResources();
        String moneyToStr = String.valueOf(MoneyUtil.getMoney());
        String strMoneyText = res.getText(R.string.hintsPopup_account).toString();
        CharSequence strClue1Text = res.getText(R.string.hintsPopup_hint1);
        CharSequence strClue2Text = res.getText(R.string.hintsPopup_hint2);
        CharSequence strClue3Text = res.getText(R.string.hintsPopup_hint3);



        /*                  🎨 DESIGN & ACTIONS

            • Apply background color to buttons drawable
            • Apply animations to buttons
            • Update text and text color in views
            • Apply icons to texts in views (if the text is made up of regular text + icons)
            • Apply click listeners to buttons
        */

        // Apply background color to buttons
        DesignUtil.setBgColor(hintButton1, R.color.COLOR_OVERLAY);
        DesignUtil.setBgColor(hintButton2, R.color.COLOR_OVERLAY);
        DesignUtil.setBgColor(hintButton3, R.color.COLOR_OVERLAY);
        DesignUtil.setBgColor(closeButton, R.color.COLOR_RED);
        // Apply animations to buttons
        DesignUtil.startBounceIn(hintButton1, 0.15F);
        DesignUtil.startBounceIn(hintButton2, 0.3F);
        DesignUtil.startBounceIn(hintButton3, 0.45F);
        // Update text (according to current locale) and money
        moneyText.setText(DesignUtil.applyIcons(strMoneyText.replace("%d", moneyToStr), 0.8F));
        hintButton1.setText(strClue1Text);
        hintButton2.setText(strClue2Text);
        hintButton3.setText(strClue3Text);

        // Apply button listener and text color depending on whether the player bought the hint or not
        if (level.canBuyHint(1)) {
            hintButton1.setText(DesignUtil.applyIcons(hintButton1.getText(), 0.8F, R.color.COLOR_GOLD));
            hintButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    level.buyHint(1); // this will take the money from the player and update database
                    listener.applyHint(1); // this will apply the hint on the level menu
                }
            });
        } else {
            hintButton1.setText(DesignUtil.applyIcons(hintButton1.getText(), 0.8F, R.color.COLOR_DARK));
            hintButton1.setTextColor(res.getColor(R.color.COLOR_DARK));
        }
        // Apply button listener and text color depending on whether the player bought the hint or not
        if (level.canBuyHint(2)) {
            hintButton2.setText(DesignUtil.applyIcons(hintButton2.getText(), 0.8F, R.color.COLOR_GOLD));
            hintButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    level.buyHint(2); // this will take the money from the player and update database
                    listener.applyHint(2); // this will apply the hint on the level menu
                }
            });
        } else {
            hintButton2.setText(DesignUtil.applyIcons(hintButton2.getText(), 0.8F, R.color.COLOR_DARK));
            hintButton2.setTextColor(res.getColor(R.color.COLOR_DARK));
        }
        // Apply button listener and text color depending on whether the player bought the hint or not
        if (level.canBuyHint(3)) {
            hintButton3.setText(DesignUtil.applyIcons(hintButton3.getText(), 0.8F, R.color.COLOR_GOLD));
            hintButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    level.buyHint(3); // this will take the money from the player and update database
                    listener.applyHint(3); // this will apply the hint on the level menu
                }
            });
        } else {
            hintButton3.setText(DesignUtil.applyIcons(hintButton3.getText(), 0.8F, R.color.COLOR_DARK));
            hintButton3.setTextColor(res.getColor(R.color.COLOR_DARK));
        }
        // When the back button is clicked, dismiss the popup (and so go back to level menu)
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        show(); // show popup
    }

    /**
     * The interface Hints listener which serves as a link between the dialog
     * and the level menu.
     */
    public interface HintsListener {
        /**
         * This method applies the hint with hintNumber on the level menu.
         * This method will be overridden in the LevelMenu class.
         *
         * @param hintNumber the hint number to apply on the level menu
         */
        void applyHint(int hintNumber);
    }

}
