package com.example.watizit.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watizit.R;
import com.example.watizit.objects.Level;

import java.util.ArrayList;

public class LevelAdapter extends ArrayAdapter<Level> {

    private final Context context;

    public LevelAdapter(Context context, ArrayList<Level> levels) {
        super(context, R.layout.level_cell, levels);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View cellView = convertView;

        if (cellView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cellView = inflater.inflate(R.layout.level_cell, parent, false);
        }

        TextView cell_level = cellView.findViewById(R.id.cell_level);
        ImageView cell_image = cellView.findViewById(R.id.cell_image);

        Resources res = context.getResources();
        Level level = getItem(position);
        cell_level.setText(cell_level.getText().toString().replace("%d", String.valueOf(level.getID())));
        cell_image.setImageDrawable(
                res.getDrawable(res.getIdentifier(
                        "img_"+level.getWord(), "drawable", context.getPackageName())));

        return cellView;
    }

}
