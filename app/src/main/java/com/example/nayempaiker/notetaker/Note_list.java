package com.example.nayempaiker.notetaker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

/**
 * Created by nayempaiker on 8/24/17.
 */

public class Note_list extends AppCompatActivity {

    Database db;
    RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list);

        db = new Database(this);
        rv = (RecyclerView) findViewById(R.id.recycler1);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //use this for horizontal view of RecyclerView -> rv.setLayoutManager(this, LinearLayoutManager.HORIZONTAL, false )

        rv.setAdapter(new Adapter_note(this, viewAll()));

    }

    public String[] viewAll()
    {
        Cursor res = db.getAllData();
        if (res.getCount() == 0)
        {
            String[] data = {"No data found!!!"};
            return data;
        }


        String items = "";

        while (res.moveToNext())
        {
            items = items.concat("Title: " + res.getString(1) + "\n"+ "Date: " + res.getString(3)+ "_o_");
        }

        String[] list = items.split("_o_");
        //Toast.makeText(this, list[2], Toast.LENGTH_SHORT).show();
        return list;
    }

}
