package com.example.watizit.menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watizit.R;
import com.example.watizit.classes.Level;
import com.example.watizit.utils.DatabaseUtil;
import com.example.watizit.classes.LevelAdapter;

public class LevelsListMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels_list_menu);

        ListView levels_list = findViewById(R.id.levelsList);
        LevelAdapter levels = new LevelAdapter(this, DatabaseUtil.getLevels());
        levels_list.setAdapter(levels);

        levels_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Level level = (Level) parent.getAdapter().getItem(position);
                if (!level.isDone()) {
                    Intent intent = new Intent(LevelsListMenu.this, LevelMenu.class);
                    intent.putExtra("EXTRA_ID", level.getID());
                    startActivity(intent);
                }
            }
        });
    }

}
