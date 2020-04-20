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
import com.example.watizit.utils.LocaleUtil;

/**
 * This class represents the levels list menu where we can see the list of levels and choose one to play.
 */
public class LevelsListMenu extends AppCompatActivity {

    /**
     * This method is used when the activity start, restart or gain focus. It applies the main
     * components to the activity such as the language, the design and the logic.
     */
    @Override
    protected void onResume() {
        super.onResume();

        /*                  🌍 LANGUAGE

            • Compare current locale to stored locale and updating it if needed
        */

        if (LocaleUtil.isLocaleStored(this)
                && !LocaleUtil.getLocaleStored(this).equals(LocaleUtil.getLocale(this)))
            LocaleUtil.setLocale(this, LocaleUtil.getLocaleStored(this));



        setContentView(R.layout.levels_list_menu); // apply levels list menu layout

        /*                  📌 VARIABLES

            • Retrieve views for design, click listener and/or gameplay
            • Retrieve other objects for gameplay purposes
        */

        Button backButton = findViewById(R.id.backButton4);
        TextView levelsText = findViewById(R.id.levelsText);
        ListView levelsList = findViewById(R.id.levelsList);
        // Level adapter for level cells in the levels list view
        LevelAdapter levels = new LevelAdapter(this, DatabaseUtil.getLevels());



        /*                  🎨 DESIGN & ACTIONS

            • Apply background color to buttons drawable
            • Apply icons to texts in views (if the text is made up of regular text + icons)
            • Apply click listeners to buttons
        */

        // Apply background color to button and text
        DesignUtil.setBgColor(backButton, R.color.COLOR_RED);
        DesignUtil.setBgColor(levelsText, R.color.COLOR_PRIMARY);
        // Put level cells in levels list view
        levelsList.setAdapter(levels);
        // When the back button is clicked, finish the activity (and so go back to main menu)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // When a level cell is clicked, check if it has an associated level and if the level is available
        levelsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Level level = (Level) parent.getAdapter().getItem(position);
                // If level isn't done and isn't locked, we can play it
                if ( !(level.isDone() || level.isLocked()) ) {
                    // So go to the level menu activity and send the level ID to the activity
                    Intent intent = new Intent(LevelsListMenu.this, LevelMenu.class);
                    intent.putExtra("EXTRA_ID", level.getID());
                    startActivity(intent);
                }
            }
        });
    }
}
