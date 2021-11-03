package com.isracorporations.oketomart.shopOne;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isracorporations.oketomart.PreCart;
import com.isracorporations.oketomart.R;
import com.isracorporations.oketomart.cart.CartAdapter;
import com.isracorporations.oketomart.cart.CartModel;

public class DailyNeedsAdapter extends FirebaseRecyclerAdapter<ModelDn, DailyNeedsAdapter.MyHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DailyNeedsAdapter(@NonNull FirebaseRecyclerOptions<ModelDn> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DailyNeedsAdapter.MyHolder holder, int position, @NonNull ModelDn model) {
        holder.item.setText(model.getItem());
        holder.price.setText("Rs"+ " "+ model.getPrice() + "/-");
        holder.crossed.setText("Rs"+ " "+ model.getCrossed() + "/-");
        holder.quantity.setText(model.getQuantity());
        Glide.with(holder.imageView.getContext()).load(model.getImage()).into(holder.imageView);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(model.getGroup()).child(model.getSubGroup()).child(model.getPin());
        reference.child(model.getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelDn modelDn = snapshot.getValue(ModelDn.class);
                int mrp = Integer.parseInt(modelDn.getCrossed());
                int sp = Integer.parseInt(modelDn.getPrice());
                int y = mrp - sp;
                holder.discount.setText("Rs " + String.valueOf(y) + "/- off");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.add.getContext(), PreCart.class);
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
    public DailyNeedsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_daily_needs,parent,false);


        return new DailyNeedsAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView item,discount;
        TextView price,crossed,quantity;
        Button add;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.image_view_dn);
            item = itemView.findViewById(R.id.product_name_dn);
            price = itemView.findViewById(R.id.product_price_dn);
            crossed=itemView.findViewById(R.id.crossed_price_dn);
            quantity=itemView.findViewById(R.id.quant_dn);
            discount= itemView.findViewById(R.id.discount);

            add = itemView.findViewById(R.id.add_to_cart_dn);
            crossed.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
