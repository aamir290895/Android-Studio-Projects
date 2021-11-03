package com.isracorporations.iacademy.ssccglpreviousyear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class generalAwarenessAdapter extends  RecyclerView.Adapter<generalAwarenessAdapter.MyHolder> {
    private Context context;
    private ItemClicker itemClicker;
    private ArrayList<String> list;
    public generalAwarenessAdapter(Context context, ArrayList<String> list, ItemClicker tit) {
        this.context = context;
        this.list = list;
        this.itemClicker = tit;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.general_adapter,parent,false);
        generalAwarenessAdapter.MyHolder myHolder = new generalAwarenessAdapter.MyHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicker.onItemClick(v,myHolder.getAdapterPosition());
            }
        });
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView.setText(list.get(position).replace("_"," "));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.generalTv);
        }
    }
}
