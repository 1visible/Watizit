package com.example.watizit.popups;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.ImageViewCompat;

import com.example.watizit.R;
import com.example.watizit.classes.Level;
import com.example.watizit.menus.LevelMenu;
import com.example.watizit.utils.DatabaseUtil;
import com.example.watizit.utils.DesignUtil;

/**
 * This class represents the Win popup (dialog).
 */
public class WinPopup extends Dialog {

    private WinListener listener;

    /**
     * Instantiates a new Win popup.
     *
     * @param context the context
     * @param level   the level
     */
    public WinPopup(final Context context, final Level level) {
        // Apply custom dialog style
        super(context, R.style.app_Dialog);
        // Apply win popup layout
        setContentView(R.layout.win_popup);
        // Prevent the dialog to be cancelled
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        // Try to make the link between the level menu listener and the dialog
        try {
            listener = (WinListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement HintsListener");
        }

        // If the dialog window isn't accessible, abort the operation
        if (getWindow() == null) return;

        /*                  📌 VARIABLES

            • Retrieve views for design, click listener and/or gameplay
            • Retrieve other objects for gameplay purposes
        */

        Button backButton = getWindow().findViewById(R.id.backButton3);
        Button nextLevelButton = getWindow().findViewById(R.id.nextLevelButton);
        ImageView starImage1 = getWindow().findViewById(R.id.starImage1);
        ImageView starImage2 = getWindow().findViewById(R.id.starImage2);
        ImageView starImage3 = getWindow().findViewById(R.id.starImage3);
        TextView moneyWon = getWindow().findViewById(R.id.moneyWon);

        String moneyWonToStr = String.valueOf(level.getStars());
        String strMoneyWonText = moneyWon.getText().toString();
        // Try to get the next level for the "Next Level" button
        final Level nextLevel = DatabaseUtil.getLevel(level.getID() + 1);



        /*                  🎨 DESIGN & ACTIONS

            • Apply background color to buttons drawable
            • Apply animations to stars and money won
            • Update text in views
            • Apply icons to texts in views (if the text is made up of regular text + icons)
            • Apply click listeners to buttons
            • Display the number of won stars
        */

        // Apply background color to buttons
        DesignUtil.setBgColor(nextLevelButton, R.color.COLOR_PRIMARY);
        DesignUtil.setBgColor(backButton, R.color.COLOR_RED);
        // Apply animations to stars and money won
        DesignUtil.startBounceIn(starImage1, 0.4F);
        DesignUtil.startBounceIn(starImage2, 0.8F);
        DesignUtil.startBounceIn(starImage3, 1.2F);
        DesignUtil.startBounceIn(moneyWon, 1.4F);
        // Update money won text
        moneyWon.setText(DesignUtil.applyIcons(strMoneyWonText.replace("%d", moneyWonToStr), 0.8F));

        // When the back button is clicked, dismiss popup and finish level menu activity (and so go back to levels list)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.finishActivity();
            }
        });
        // If the next level can't be found, hide the "Next Level" button, else set its listener
        if (nextLevel == null)
            nextLevelButton.setVisibility(View.GONE);
        else
            /* When the next level button is clicked, dismiss popup, finish level menu activity and open new level
            menu with the next level */
            nextLevelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    listener.finishActivity();
                    Intent levelMenuIntent = new Intent(context, LevelMenu.class);
                    // Send level id as a message to the next level menu activity
                    levelMenuIntent.putExtra("EXTRA_ID", nextLevel.getID());
                    context.startActivity(levelMenuIntent);
                }
            });

        // Reveal stars by removing their gray tint according to the number of won stars
        switch (level.getStars()) {
            case 3:
                ImageViewCompat.setImageTintList(starImage3, null);
            case 2:
                ImageViewCompat.setImageTintList(starImage2, null);
            case 1:
                ImageViewCompat.setImageTintList(starImage1, null);
        }
    }

    /**
     * The interface Win listener which serves as a link between the dialog
     * and the level menu.
     */
    public interface WinListener {
        /**
         * This method finishes the level menu activity.
         * This method will be overridden in the LevelMenu class.
         */
        void finishActivity();
    }
}
