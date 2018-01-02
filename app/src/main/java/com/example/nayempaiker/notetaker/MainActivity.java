package com.example.nayempaiker.notetaker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    public static int REQUEST_IMAGE_CAPTURE = 1901;
    Database db;

    Button new_note, note_list, camera, image_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);

        new_note = (Button) findViewById(R.id.newNote);
        note_list = (Button) findViewById(R.id.noteList);
        camera = (Button) findViewById(R.id.camera);
        image_List = (Button) findViewById(R.id.imageList);

        new_note();
        noteList();
        takePic();
        imageList();

    }

    public void new_note()
    {
        new_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, New_note.class);
                startActivity(intent);
            }
        });
    }

    public void noteList()
    {
        note_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Note_list.class);
                startActivity(intent);
            }
        });
    }

    public void imageList()
    {
        image_List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Image_list.class);
                startActivity(intent);
            }
        });


    }

    public void takePic()
    {
        camera.setOnClickListener(new View.OnClickListener() {
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

}
