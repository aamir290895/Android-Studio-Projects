package com.isracorporations.recyclerview;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemAdapter extends RecyclerView.Adapter<MemAdapter.viewHolder> {
    ArrayList<String>stringArrayList;

    public MemAdapter(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;

        stringArrayList.add("hoo");
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mem_adapter,parent,false);


        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemAdapter.viewHolder holder, int position) {
       holder.tv.setText(stringArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder  {
        TextView tv ;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv2);

        }



    }
}
