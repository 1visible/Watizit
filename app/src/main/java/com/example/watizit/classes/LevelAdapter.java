package com.example.watizit.classes;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ImageViewCompat;

import com.example.watizit.R;
import com.example.watizit.utils.DesignUtil;

import java.util.ArrayList;

/**
 * The type Level adapter.
 */
public class LevelAdapter extends ArrayAdapter<Level> {

    /**
     * Instantiates a new Level adapter.
     *
     * @param context the context
     * @param levels  the levels
     */
    public LevelAdapter(Context context, ArrayList<Level> levels)
    {
        super(context, R.layout.level_cell, levels);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {

        Context context = super.getContext();

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.level_cell, parent, false);

        Level level = getItem(position);
        ConstraintLayout panelOverlay = convertView.findViewById(R.id.panelOverlay1);
        TextView cellText = convertView.findViewById(R.id.cellText);
        ImageView cellImage = convertView.findViewById(R.id.cellImage);
        ImageView starImage1 = convertView.findViewById(R.id.littleStarImage1);
        ImageView starImage2 = convertView.findViewById(R.id.littleStarImage2);
        ImageView starImage3 = convertView.findViewById(R.id.littleStarImage3);
        Resources res = context.getResources();
        String text = res.getString(R.string.levelMenu_title);

        if(level != null)
        {

            int color = R.color.COLOR_DARK;
            int identifier = R.drawable.props_locked_level;
            ColorStateList csl = null;

            if(!level.isLocked())
            {
                identifier = res.getIdentifier("img_" + level.getWord(), "drawable", context.getPackageName());
                color = R.color.COLOR_TEXT;
                if(!level.isDone())
                    csl = AppCompatResources.getColorStateList(context, R.color.COLOR_TEXT);
            }

            DesignUtil.setBgColor(panelOverlay, R.color.COLOR_OVERLAY);
            cellImage.setImageDrawable(res.getDrawable(identifier));
            cellText.setText(text.replace("%d", String.valueOf(level.getID())));
            cellText.setTextColor(res.getColor(color));
            ImageViewCompat.setImageTintList(cellImage, csl);

            ColorStateList c = AppCompatResources.getColorStateList(context, R.color.COLOR_DARK);

            if(level.isDone())
            {
                starImage1.setVisibility(View.VISIBLE);
                starImage2.setVisibility(View.VISIBLE);
                starImage3.setVisibility(View.VISIBLE);

                switch(level.getStars())
                {
                    case 3:
                        ImageViewCompat.setImageTintList(starImage1, null);
                        ImageViewCompat.setImageTintList(starImage2, null);
                        ImageViewCompat.setImageTintList(starImage3, null);
                        break;
                    case 2:
                        ImageViewCompat.setImageTintList(starImage1, null);
                        ImageViewCompat.setImageTintList(starImage2, null);
                        ImageViewCompat.setImageTintList(starImage3, c);
                        break;
                    case 1:
                        ImageViewCompat.setImageTintList(starImage1, null);
                        ImageViewCompat.setImageTintList(starImage2, c);
                        ImageViewCompat.setImageTintList(starImage3, c);
                        break;
                    default:
                        ImageViewCompat.setImageTintList(starImage1, c);
                        ImageViewCompat.setImageTintList(starImage2, c);
                        ImageViewCompat.setImageTintList(starImage3, c);
                }
            }
            else
            {
                starImage1.setVisibility(View.INVISIBLE);
                starImage2.setVisibility(View.INVISIBLE);
                starImage3.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }

}
