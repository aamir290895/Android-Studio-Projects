package com.isracorporations.oketomart.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.isracorporations.oketomart.R;

public class CartAdapter extends FirebaseRecyclerAdapter<CartModel, CartAdapter.MyHolder> {



    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public CartAdapter(FirebaseRecyclerOptions<CartModel> options ) {
        super(options);

    }


    @Override
    protected void onBindViewHolder(@NonNull CartAdapter.MyHolder holder, int position, @NonNull CartModel model) {
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.quantity.setText(model.getMultiple());


        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("cart_list").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(model.getPid()).removeValue();


            }
        });


    }



    @NonNull
    @Override
    public CartAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_details,parent,false);


        return new CartAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView name,price,quantity;

        ImageButton remove;
        ImageView im;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.cart_item_name);
            price = itemView.findViewById(R.id.cart_item_price);
            quantity = itemView.findViewById(R.id.cart_item_quantity);

            remove=itemView.findViewById(R.id.remove_from_cart);




        }
    }
}
