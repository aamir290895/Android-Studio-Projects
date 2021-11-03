package com.isracorporations.icareers_employmentnews;

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

public class AdapterOne extends FirebaseRecyclerAdapter<list , AdapterOne.MyHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterOne(@NonNull FirebaseRecyclerOptions<list> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull AdapterOne.MyHolder holder, int position, @NonNull list model) {
        holder.header.setText(model.getHeader());
        holder.link.setText(model.getLink());
        Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.image.getContext(),webView.class);
                intent.putExtra("xyz",model.getLink());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.image.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_adapter,parent,false);

        return new  AdapterOne.MyHolder(view);

    }


   public class MyHolder extends RecyclerView.ViewHolder {

        TextView header,link;
        ImageView image;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image5);
            header=itemView.findViewById(R.id.textView4);
            link = itemView.findViewById(R.id.textView3);
        }
    }
}
