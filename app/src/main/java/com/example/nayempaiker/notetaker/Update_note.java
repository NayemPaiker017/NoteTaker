package com.example.nayempaiker.notetaker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by nayempaiker on 8/28/17.
 */

public class Update_note extends AppCompatActivity {

    Database db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        db = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_note);

        //retrieve String that was send to this layout
        String string_that_was_promised = getIntent().getExtras().getString("title");

        Toast.makeText(this, string_that_was_promised, Toast.LENGTH_SHORT).show();
        String[] title_detail = viewSelect(string_that_was_promised);

    }

    public String[] viewSelect(String title)
    {
        Cursor res = db.detailsFromTitle(title);
        if (res.getCount() == 0)
        {
            String[] data = {"No data found!!!"};
            return data;
        }


        String items = "";

        while (res.moveToNext())
        {
            items = items.concat("Title: " + res.getString(1) + "\n"+ "Details: " + res.getString(2)+ "_o_");
        }

        String[] list = items.split("_o_");
        Toast.makeText(this, list[1], Toast.LENGTH_SHORT).show();
        return list;
    }
}
