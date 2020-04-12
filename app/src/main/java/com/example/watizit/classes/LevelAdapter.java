package com.example.watizit.classes;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;

import com.example.watizit.R;
import com.example.watizit.utils.DatabaseUtil;
import com.example.watizit.utils.DesignUtil;

import java.util.ArrayList;

public class LevelAdapter extends ArrayAdapter<Level> {

    public LevelAdapter(Context context, ArrayList<Level> levels)
    {
        super(context, R.layout.level_cell, levels);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        Context context = App.getContext();

        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.level_cell, parent, false);
        }

        Level level = getItem(position);
        LinearLayout panelOverlay = convertView.findViewById(R.id.panelOverlay1);
        TextView cellText = convertView.findViewById(R.id.cellText);
        ImageView cellImage = convertView.findViewById(R.id.cellImage);
        ImageView starImage1 = convertView.findViewById(R.id.littleStarImage1);
        ImageView starImage2 = convertView.findViewById(R.id.littleStarImage2);
        ImageView starImage3 = convertView.findViewById(R.id.littleStarImage3);
        Resources res = context.getResources();
        String text = res.getString(R.string.level);

        if(level != null)
        {
            Level previousLevel = DatabaseUtil.getLevel(level.getID() - 1);
            int identifier, color;

            if(previousLevel != null && !previousLevel.isDone())
            {
                identifier = R.drawable.locked_level;
                color = R.color.COLOR_DARK;
            }
            else
            {
                identifier = res.getIdentifier("img_" + level.getWord(), "drawable", context.getPackageName());
                color = R.color.COLOR_TEXT;
            }

            DesignUtil.setBgColor(panelOverlay, R.color.COLOR_OVERLAY);
            cellImage.setImageDrawable(res.getDrawable(identifier));
            cellText.setText(text.replace("%d", String.valueOf(level.getID())));
            cellText.setTextColor(res.getColor(color));

            switch(level.getStars())
            {
                case 3:
                    ImageViewCompat.setImageTintList(starImage3, null);
                case 2:
                    ImageViewCompat.setImageTintList(starImage2, null);
                case 1:
                    ImageViewCompat.setImageTintList(starImage1, null);
            }

            if(level.isDone() || previousLevel != null && !previousLevel.isDone())
                ImageViewCompat.setImageTintList(cellImage, null);
        }

        return convertView;
    }

}
