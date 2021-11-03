package com.isracorporations.icareers_previousyearspapers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class IndexAdapter extends FirebaseRecyclerAdapter<list , IndexAdapter.MyHolder> {
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
       holder.textView.setText(model.getT1());

    }

    @NonNull
    @Override
    public IndexAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_adapter,parent,false);

        return new IndexAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView= itemView.findViewById(R.id.tvIndexAdapter);

        }
    }
}
