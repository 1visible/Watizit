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
 * The type Win popup.
 */
public class WinPopup extends Dialog {

    private WinListener listener;

    /**
     * Instantiates a new Win popup.
     *
     * @param context the context
     * @param level   the level
     */
    public WinPopup(final Context context, final Level level)
    {
        super(context, R.style.app_Dialog);
        setContentView(R.layout.win_popup);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        try
        {
            listener = (WinListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement HintsListener");
        }

        if(getWindow() == null) return;

        Button backButton = getWindow().findViewById(R.id.backButton3);
        Button nextLevelButton = getWindow().findViewById(R.id.nextLevelButton);
        ImageView starImage1 = getWindow().findViewById(R.id.starImage1);
        ImageView starImage2 = getWindow().findViewById(R.id.starImage2);
        ImageView starImage3 = getWindow().findViewById(R.id.starImage3);
        TextView moneyWon = getWindow().findViewById(R.id.moneyWon);
        String strMoneyWon = String.valueOf(level.getStars());

        moneyWon.setText(DesignUtil.applyIcons(moneyWon.getText().toString().replace("%d", strMoneyWon), 0.8F));

        DesignUtil.setBgColor(nextLevelButton, R.color.COLOR_PRIMARY);
        DesignUtil.setBgColor(backButton, R.color.COLOR_RED);

        DesignUtil.startBounceIn(starImage1, 0.4F);
        DesignUtil.startBounceIn(starImage2, 0.8F);
        DesignUtil.startBounceIn(starImage3, 1.2F);
        DesignUtil.startBounceIn(moneyWon, 1.4F);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.finishActivity();
            }
        });

        final Level nextLevel = DatabaseUtil.getLevel(level.getID() + 1);

        if(nextLevel == null)
            nextLevelButton.setVisibility(View.GONE);
        else
            nextLevelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    listener.finishActivity();
                    Intent levelMenuIntent = new Intent(context, LevelMenu.class);
                    levelMenuIntent.putExtra("EXTRA_ID", nextLevel.getID());
                    context.startActivity(levelMenuIntent);
                }
            });

        switch(level.getStars())
        {
            case 3:
                ImageViewCompat.setImageTintList(starImage3, null);
            case 2:
                ImageViewCompat.setImageTintList(starImage2, null);
            case 1:
                ImageViewCompat.setImageTintList(starImage1, null);
        }
    }

    /**
     * The interface Win listener.
     */
    public interface WinListener {
        /**
         * Finish activity.
         */
        void finishActivity();
    }
}
