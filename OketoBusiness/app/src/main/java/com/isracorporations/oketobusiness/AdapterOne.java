package com.isracorporations.oketobusiness;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterOne extends FirebaseRecyclerAdapter<Modal, AdapterOne.MyHolder> {
    String group,subGroup,pinCode;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterOne(@NonNull FirebaseRecyclerOptions<Modal> options,String group ,String subGroup,String pinCode) {
        super(options);
        this.group =group;
        this.subGroup=subGroup;
        this.pinCode=pinCode;
    }



    @Override
    protected void onBindViewHolder(@NonNull AdapterOne.MyHolder holder, int position, @NonNull Modal model) {
        holder.item.setText(model.getItem());
        holder.price.setText("cp ="+ "Rs"+ " "+ model.getCostPrice() + "/-");
        holder.crossed.setText("sp ="+ "Rs"+ " "+ model.getPrice() + "/-");
        holder.quantity.setText(model.getQuantity());
        Glide.with(holder.imageView.getContext()).load(model.getImage()).into(holder.imageView);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.add.getContext(), EditProductDetails.class);
                i.putExtra("pid", model.getKey());
                i.putExtra("group", model.getGroup());
                i.putExtra("subGroup", model.getSubGroup());
                i.putExtra("pin", model.getPin());
                holder.add.getContext().startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public AdapterOne.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_two,parent,false);

        return new AdapterOne.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView item;
        TextView price,crossed,quantity;
        Button add;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.image_x);
            item = itemView.findViewById(R.id.name_x);
            price = itemView.findViewById(R.id.price_x);
            crossed=itemView.findViewById(R.id.price_y);
            quantity=itemView.findViewById(R.id.quant_x);

            add = itemView.findViewById(R.id.edit_x);

        }
    }
}
