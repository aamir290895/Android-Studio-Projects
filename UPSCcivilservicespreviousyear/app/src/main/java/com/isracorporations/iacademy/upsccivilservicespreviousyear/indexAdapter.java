package com.isracorporations.iacademy.upsccivilservicespreviousyear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class indexAdapter extends RecyclerView.Adapter<indexAdapter.MyHolder> {
    private Context mContext;
    private ItemClicker itemClicker;
    private ArrayList<String> list;

    public indexAdapter(Context context, ArrayList<String> list, ItemClicker itemClicker) {
        this.mContext = context;
        this.itemClicker = itemClicker;
        this.list = list;
    }


    @NonNull
    @Override
    public indexAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.index_adapter,parent,false);
        MyHolder myHolder = new MyHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicker.onItemClick(v,myHolder.getAdapterPosition());
            }
        });
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull indexAdapter.MyHolder holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyHolder(@NonNull View itemView) {

            super(itemView);
            textView=itemView.findViewById(R.id.textView);
        }
    }
}
