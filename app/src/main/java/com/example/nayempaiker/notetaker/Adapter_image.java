package com.example.nayempaiker.notetaker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by nayempaiker on 8/27/17.
 */

public class Adapter_image  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    String[] mItem;

    public Adapter_image(Context mContext, String[] mItem) {
        this.mContext = mContext;
        this.mItem = mItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View row = inflater.inflate(R.layout.imagebox, parent, false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Bitmap bm = stringToBitmap(mItem[position]);
        ((Item)holder).imageView.setImageBitmap(bm);

    }

    @Override
    public int getItemCount() {
        return mItem.length;
    }
    public class Item extends RecyclerView.ViewHolder
    {
        ImageView imageView;

        public Item(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.img1);
        }
    }

    //string to bitmap
    public Bitmap stringToBitmap(String encodedString)
    {
        try{
            byte[] encodebyte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodebyte, 0, encodebyte.length);
            return bitmap;
        }catch (Exception e)
        {
            e.getMessage();
            return null;
        }
    }

}
