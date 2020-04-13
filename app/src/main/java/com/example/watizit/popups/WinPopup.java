package com.example.watizit.popups;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.widget.ImageViewCompat;

import com.example.watizit.R;
import com.example.watizit.classes.Level;
import com.example.watizit.menus.LevelMenu;
import com.example.watizit.utils.DatabaseUtil;
import com.example.watizit.utils.DesignUtil;

public class WinPopup extends Dialog {

    Context context;

    public WinPopup(final Context context, final Level level)
    {
        super(context);
        this.context = context;
        setContentView(R.layout.win_popup);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        if(getWindow() == null) return;

        Button backButton = getWindow().findViewById(R.id.backButton3);
        Button nextLevelButton = getWindow().findViewById(R.id.nextLevelButton);
        ImageView starImage1 = getWindow().findViewById(R.id.starImage1);
        ImageView starImage2 = getWindow().findViewById(R.id.starImage2);
        ImageView starImage3 = getWindow().findViewById(R.id.starImage3);

        DesignUtil.setBgColor(nextLevelButton, R.color.COLOR_PRIMARY);
        DesignUtil.setBgColor(backButton, R.color.COLOR_RED);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final Level nextLevel = DatabaseUtil.getLevel(level.getID() + 1);

        if(nextLevel == null)
            nextLevelButton.setVisibility(View.GONE);
        else
            nextLevelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LevelMenu.class);
                    intent.putExtra("EXTRA_ID", nextLevel.getID());
                    context.startActivity(intent);
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

}
