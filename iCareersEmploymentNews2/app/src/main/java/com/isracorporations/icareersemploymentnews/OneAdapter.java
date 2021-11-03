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

public class OneAdapter extends FirebaseRecyclerAdapter<list , OneAdapter.MyHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OneAdapter(@NonNull FirebaseRecyclerOptions<list> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OneAdapter.MyHolder holder, int position, @NonNull list model) {

        holder.sets.setText(model.getSet());
        holder.setsLink.setText(model.getLink());

        holder.sets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(holder.setsLink.getContext(),description.class);
                i2.putExtra("abc",model.getLink());
                i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(model.getLink()!= null) {
                    holder.setsLink.getContext().startActivity(i2);
                }
            }
        });


    }

    @NonNull
    @Override
    public OneAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_adapter,parent,false);

        return new OneAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView sets,setsLink;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

             sets=itemView.findViewById(R.id.setsName);
             setsLink=itemView.findViewById(R.id.setsLink);


        }
    }
}
