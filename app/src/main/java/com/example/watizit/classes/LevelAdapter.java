package com.example.watizit.classes;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;

import com.example.watizit.R;

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
        View cellView = convertView;

        if (cellView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cellView = inflater.inflate(R.layout.level_cell, parent, false);
        }

        TextView cell_level = cellView.findViewById(R.id.cellText);
        ImageView cell_image = cellView.findViewById(R.id.cellImage);

        Resources res = context.getResources();
        Level level = getItem(position);
        if(level != null)
        {
            cell_level.setText(cell_level.getText().toString().replace("%d", String.valueOf(level.getID())));
            cell_image.setImageDrawable(
                    res.getDrawable(res.getIdentifier(
                            "img_" + level.getWord(), "drawable", context.getPackageName())));
            if (level.isDone())
            {
                ImageViewCompat.setImageTintList(cell_image, null);
            }
        }

        return cellView;
    }

}
