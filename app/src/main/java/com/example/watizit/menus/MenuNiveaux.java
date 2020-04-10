package com.example.watizit.menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watizit.R;
import com.example.watizit.database.DatabaseAccess;
import com.example.watizit.objects.Level;
import com.example.watizit.utils.LevelAdapter;

public class MenuNiveaux extends AppCompatActivity {

    private LevelAdapter levels;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_niveaux);

        ListView levels_list = findViewById(R.id.levels_list);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        levels = new LevelAdapter(this, databaseAccess.getLevels());
        databaseAccess.close();
        levels_list.setAdapter(levels);

        levels_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Level level = (Level) parent.getAdapter().getItem(position);
                Intent intent = new Intent(MenuNiveaux.this, MenuJouer.class);
                intent.putExtra("EXTRA_ID", level.getID());
                startActivity(intent);

            }
        });
    }

}
