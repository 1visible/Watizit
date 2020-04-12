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

        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.level_cell, parent, false);
        }

        Level level = getItem(position);
        TextView cellText = convertView.findViewById(R.id.cellText);
        ImageView cellImage = convertView.findViewById(R.id.cellImage);
        Resources res = context.getResources();
        String text = res.getString(R.string.level);

        if(level != null)
        {
            cellText.setText(text.replace("%d", String.valueOf(level.getID())));
            cellImage.setImageDrawable(
                    res.getDrawable(res.getIdentifier(
                            "img_" + level.getWord(), "drawable", context.getPackageName())));
            if (level.isDone())
                ImageViewCompat.setImageTintList(cellImage, null);
        }

        return convertView;
    }

}
