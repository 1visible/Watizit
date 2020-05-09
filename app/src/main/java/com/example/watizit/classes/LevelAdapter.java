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
 * This class represents the level adapter for a level cell in the levels list view.
 */
public class LevelAdapter extends ArrayAdapter<Level> {

    /**
     * Instantiates a new Level adapter.
     *
     * @param context the context
     * @param levels a list of levels
     */
    public LevelAdapter(Context context, ArrayList<Level> levels) {
        super(context, R.layout.level_cell, levels); // apply level cell layout
    }

    /**
     * This method creates/updates a level cell view in the levels list view.
     *
     * @param position the position of the view
     * @param convertView the view
     * @param parent  the parent of the view
     * @return the updated the view
     */
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Context context = super.getContext();
        // Create a level cell if it doesn't exist
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.level_cell, parent, false);

        /*                  📌 VARIABLES

            • Retrieve views for design, click listener and/or gameplay
            • Retrieve other objects for gameplay purposes
        */

        TextView cellText = convertView.findViewById(R.id.cellText);
        ImageView cellImage = convertView.findViewById(R.id.cellImage);
        ImageView starImage1 = convertView.findViewById(R.id.littleStarImage1);
        ImageView starImage2 = convertView.findViewById(R.id.littleStarImage2);
        ImageView starImage3 = convertView.findViewById(R.id.littleStarImage3);
        ConstraintLayout panelOverlay = convertView.findViewById(R.id.panelOverlay1);

        Resources res = context.getResources();
        // Get the level associated with the cell view
        Level level = getItem(position);
        String text = res.getString(R.string.levelMenu_title);

        //                      END OF BLOCKS                      //

        // If the level can be found
        if (level != null) {
            ColorStateList c = AppCompatResources.getColorStateList(context, R.color.COLOR_DARK);
            int color = R.color.COLOR_DARK;
            int identifier = R.drawable.props_locked_level;
            String description = res.getString(R.string.sr_lockedImage);
            ColorStateList csl = null;
            /* Set the level cell background color, image, text, text color, image tint
            and description depending on the level state (available, done or locked) */
            if (!level.isLocked()) {
                identifier = res.getIdentifier("img_" + level.getWord(), "drawable", context.getPackageName());
                color = R.color.COLOR_TEXT;
                if (!level.isDone()) {
                    description = res.getString(R.string.sr_levelImage);
                    csl = AppCompatResources.getColorStateList(context, R.color.COLOR_TEXT);
                }else
                    description = level.getWord();
            }
            // Apply background color to level cell
            DesignUtil.setBgColor(panelOverlay, R.color.COLOR_OVERLAY);
            // Set level image in level cell
            cellImage.setImageDrawable(res.getDrawable(identifier));
            // Set level text and text color in level cell
            cellText.setText(text.replace("%d", String.valueOf(level.getID())));
            cellText.setTextColor(res.getColor(color));
            // Set level image tint (in white if the level has not been done)
            ImageViewCompat.setImageTintList(cellImage, csl);
            // Set level image description (for screen-readers)
            cellImage.setContentDescription(description);
            // Apply stars if the level is done
            if (level.isDone()) {
                // Set all stars to visible
                starImage1.setVisibility(View.VISIBLE);
                starImage2.setVisibility(View.VISIBLE);
                starImage3.setVisibility(View.VISIBLE);
                // Remove or add the tint of stars depending on the number of won stars
                switch (level.getStars()) {
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
            } else {
                // Else set all stars invisible (if the level is not done or is locked)
                starImage1.setVisibility(View.INVISIBLE);
                starImage2.setVisibility(View.INVISIBLE);
                starImage3.setVisibility(View.INVISIBLE);
            }
        }
        return convertView;
    }

}
