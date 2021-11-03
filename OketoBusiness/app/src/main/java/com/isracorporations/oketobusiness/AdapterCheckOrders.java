package com.isracorporations.oketobusiness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterCheckOrders extends FirebaseRecyclerAdapter<ModelOrders, AdapterCheckOrders.MyHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterCheckOrders(@NonNull FirebaseRecyclerOptions<ModelOrders> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterCheckOrders.MyHolder holder, int position, @NonNull ModelOrders model) {
        holder.tv.setText(model.getDate());
    }

    @NonNull
    @Override
    public AdapterCheckOrders.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_order,parent,false);

        return new AdapterCheckOrders.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.order_number);
        }
    }
}
