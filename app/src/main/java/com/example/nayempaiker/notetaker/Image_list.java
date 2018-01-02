package com.example.nayempaiker.notetaker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by nayempaiker on 8/27/17.
 */

public class Image_list extends AppCompatActivity {

    Database db;
    RecyclerView rv;
    int numOfCol = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_list);

        db = new Database(this);
        rv = (RecyclerView) findViewById(R.id.rid);
        //rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setLayoutManager(new GridLayoutManager(this, numOfCol));
        rv.setAdapter(new Adapter_image(this, viewAll()));

    }

    public String[] viewAll()
    {
        Cursor res = db.getAllImage();
        if (res.getCount() == 0)
        {
            String[] data = {"No data found!!!"};
            return data;
        }

        String items = "";

        while (res.moveToNext())
        {
            items = items.concat(res.getString(1) + "_o_");
        }

        String[] list = items.split("_o_");
        return list;
    }


}
