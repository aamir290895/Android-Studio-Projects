package com.isracorporations.recyclerview;





import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Context context;
    ArrayList<String>list;
    private RecyclerViewClickInterface recyclerViewClickInterface;
    CustomAdapter(Context context, ArrayList<String> arrayList,RecyclerViewClickInterface clickInterface){
        this.context = context;
        this.list = arrayList;
        list.add("hiii");
        this.recyclerViewClickInterface = clickInterface;

    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_adapter,parent,false);
        ViewHolder viewHolder = new ViewHolder(view, new RecyclerViewClickInterface() {
            @Override
            public void onItemClick (View view ,int position) {
                final Intent intent;
                switch (position){
                    case 0:
                        intent =  new Intent(context,MainActivity3.class);
                        break;

                    case 1:
                        intent =  new Intent(context, MainActivity2.class);
                        break;
                    default:
                        intent =  new Intent(context, MainActivity2.class);
                        break;
                }
                context.startActivity(intent);

            }

            @Override
            public void onLongItemClick(int position) {

            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
         holder.tv.setText(list.get(position));
         ArrayList<String> stringArrayList = new ArrayList<>();
         MemAdapter memAdapter = new MemAdapter(stringArrayList);
         LinearLayoutManager layoutManager =  new LinearLayoutManager(context);
         holder.rv.setLayoutManager(layoutManager);
         holder.rv.setAdapter(memAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerViewClickInterface {

        TextView tv;
        RecyclerView rv;
        Context context;
        RecyclerViewClickInterface recyclerViewClickInterface;

        public ViewHolder(@NonNull View itemView,RecyclerViewClickInterface recyclerViewClickInterface) {
            super(itemView);
            tv = itemView.findViewById(R.id.textView);
            rv=itemView.findViewById(R.id.rv);
            context = itemView.getContext();
            this.recyclerViewClickInterface = recyclerViewClickInterface;

        }



        @Override
        public void onItemClick(View view,int position) {



        }

        @Override
        public void onLongItemClick(int position) {

        }
    }


    }

