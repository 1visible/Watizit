package com.example.watizit.menus;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watizit.R;
import com.example.watizit.classes.Level;
import com.example.watizit.classes.LevelAdapter;
import com.example.watizit.utils.DatabaseUtil;
import com.example.watizit.utils.DesignUtil;

public class LevelsListMenu extends AppCompatActivity {

    @Override
    public void onResume()
    {
        super.onResume();
        setContentView(R.layout.levels_list_menu);
        Button backButton = findViewById(R.id.backButton4);
        TextView levelsText = findViewById(R.id.levelsText);
        ListView levelsList = findViewById(R.id.levelsList);
        LevelAdapter levels = new LevelAdapter(this, DatabaseUtil.getLevels());

        DesignUtil.setBgColor(backButton, R.color.COLOR_RED);
        DesignUtil.setBgColor(levelsText, R.color.COLOR_PRIMARY);
        backButton.setText(DesignUtil.applyIcons(backButton.getText(), 1F));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        levelsList.setAdapter(levels);
        levelsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Level level = (Level) parent.getAdapter().getItem(position);
                Level previousLevel = DatabaseUtil.getLevel(level.getID() - 1);
                if (!level.isDone() && (previousLevel == null || previousLevel.isDone()))
                {
                    Intent intent = new Intent(LevelsListMenu.this, LevelMenu.class);
                    intent.putExtra("EXTRA_ID", level.getID());
                    startActivity(intent);
                }
            }
        });
    }

}
