package com.isracorporations.icareersemploymentnews;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class

IndexAdapter  extends FirebaseRecyclerAdapter<list , IndexAdapter.MyHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public IndexAdapter(@NonNull FirebaseRecyclerOptions<list> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull IndexAdapter.MyHolder holder, int position, @NonNull list model) {
        holder.pre.setText(model.getPre());
        holder.mains.setText(model.getMains());
        Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);
        holder.pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.pre.getContext(),One.class);
                i.putExtra("indx",model.getPre());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (model.getPre()!= null) {
                    holder.pre.getContext().startActivity(i);
                }
            }
        });
        holder.mains.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.mains.getContext(),One.class);
                i.putExtra("indx",model.getMains());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (model.getMains()!= null) {
                    holder.mains.getContext().startActivity(i);
                }
            }
        });

    }

    @NonNull
    @Override
    public IndexAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_adapter,parent,false);

        return new IndexAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView pre,mains;
        ImageView image;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            pre= itemView.findViewById(R.id.ia1);
            mains = itemView.findViewById(R.id.ia2);
            image = itemView.findViewById(R.id.indexImage);

        }
    }
}
