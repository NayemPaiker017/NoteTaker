package com.example.nayempaiker.notetaker;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nayempaiker.notetaker.Update_note;

/**
 * Created by nayempaiker on 8/24/17.
 */

public class Adapter_note extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    String[] mItem;

    public Adapter_note(Context context, String[] Item) {
        this.mContext = context;
        this.mItem = Item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row = inflater.inflate(R.layout.notebox, parent, false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final String nm = mItem[position];
        final String[] n1 = nm.split("\n");
        ((Item)holder).txt.setText(mItem[position]);
        ((Item) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, n1[0], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, Update_note.class);
                intent.putExtra("title", n1[0]);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mItem.length;
    }

    public class Item extends RecyclerView.ViewHolder
    {
        TextView txt;
        LinearLayout linearLayout;

        public Item(View itemView) {
            super(itemView);
            txt = (TextView)itemView.findViewById(R.id.text1);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearID);
        }


    }
}
