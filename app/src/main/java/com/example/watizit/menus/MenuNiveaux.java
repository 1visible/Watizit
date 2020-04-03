package com.example.watizit.menus;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watizit.R;
import com.example.watizit.database.DatabaseAccess;
import com.example.watizit.other.Level;

import java.util.ArrayList;
import java.util.List;

public class MenuNiveaux extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_niveaux);

        ListView listView = findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<Level> levels = databaseAccess.getLevels();
        databaseAccess.close();

        List<String> liste = new ArrayList<>();

        for(int i = 1; i <= levels.size(); i++){
            liste.add("Niveau "+i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, liste);
        listView.setAdapter(adapter);

    }

}
