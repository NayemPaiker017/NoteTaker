package com.example.nayempaiker.notetaker;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nayempaiker on 8/24/17.
 */

public class New_note extends AppCompatActivity {

    public static int REQUEST_IMAGE_CAPTURE = 1900;

    LinearLayout L1;
    FloatingActionButton menu_more, add_note, noteList, email, cam, Image_List;
    EditText title, details;
    Database db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);

        L1 = (LinearLayout) findViewById(R.id.linear2);
        db = new Database(this);

        title = (EditText) findViewById(R.id.title);
        details = (EditText) findViewById(R.id.details);

        menu_more = (FloatingActionButton) findViewById(R.id.menu_more);
        add_note = (FloatingActionButton) findViewById(R.id.add);
        noteList = (FloatingActionButton) findViewById(R.id.noteList);
        email = (FloatingActionButton) findViewById(R.id.email);
        cam = (FloatingActionButton) findViewById(R.id.cam);

        menuClick();
        addData();
        viewList();
        takePic();
        email();
    }

    public void menuClick()
    {
        menu_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (L1.getVisibility() == View.GONE)
                {
                    L1.setVisibility(View.VISIBLE);
                }
                else
                {
                    L1.setVisibility(View.GONE);
                }
            }
        });
    }

    public void addData()
    {
        add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result =db.insertData(title.getText().toString(), details.getText().toString());
                if (result == true)
                {
                    Toast.makeText(New_note.this, "Note added to the list", Toast.LENGTH_SHORT).show();
                    title.setText("");
                    details.setText("");
                }
                else
                {
                    Toast.makeText(New_note.this, "Error: Note not added to the list!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void viewList()
    {
        noteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(New_note.this, com.example.nayempaiker.notetaker.Note_list.class);
                startActivity(intent);

//                Cursor res = db.getAllData();
//                if (res.getCount() == 0)
//                {
//                    showMessage("Error", "No Data found!");
//                    return;
//                }
//
//                StringBuffer buffer = new StringBuffer();
//                while(res.moveToNext())
//                {
//                    buffer.append("ID: "+res.getString(0)+"\n");
//                    buffer.append("Name: "+res.getString(1)+"\n");
//                    buffer.append("Description: "+res.getString(2)+"\n");
//                }
//                showMessage("Data", buffer.toString());

            }
        });
    }

    public void showMessage(String name, String description) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);
        dialog.setTitle(name);
        dialog.setMessage(description);
        dialog.show();
    }

    public void takePic()
    {
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePic.resolveActivity(getPackageManager()) !=null)
                {
                    startActivityForResult(takePic, REQUEST_IMAGE_CAPTURE);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle pack = data.getExtras();
            Bitmap imageBitmap =(Bitmap) pack.get("data");

            String pathToImage =BitmapToString(imageBitmap);

            Boolean result = db.insertImage(pathToImage);
            //Toast.makeText(this, "-> "+ pathToImage, Toast.LENGTH_SHORT).show();
            if (result == true)
            {
                Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Error!! Image not saved!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //bitmap tp string;
    public String BitmapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp  = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public void sendEmail()
    {
        String date = dateAndTime();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title.getText().toString() + " " + date);
        emailIntent.putExtra(Intent.EXTRA_TEXT, details.getText().toString());

        startActivity(Intent.createChooser(emailIntent, "Pick an email provider"));
    }

    public String dateAndTime()
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String nowDate = format.format(date);
        return nowDate;
    }

    public void email()
    {
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    //save data while rotating the phone
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("title_name", title.getText().toString());
        outState.putString("details_here", details.getText().toString());
        super.onSaveInstanceState(outState);
    }

    //restore the data after rotating the phone
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        title.setText(savedInstanceState.getString("title_name"));
        details.setText(savedInstanceState.getString("details_here"));
    }
}
