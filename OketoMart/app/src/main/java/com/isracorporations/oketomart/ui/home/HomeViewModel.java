package com.isracorporations.oketomart.ui.home;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isracorporations.oketomart.PreCart;
import com.isracorporations.oketomart.R;
import com.isracorporations.oketomart.shopOne.DailyNeeds;


public class HomeViewModel extends FirebaseRecyclerAdapter<Model, HomeViewModel.MyHolder> {
     String pincode;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HomeViewModel(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeViewModel.MyHolder holder, int position, @NonNull Model model) {
        holder.item.setText(model.getSubCategory());

        Glide.with(holder.imageView.getContext()).load(model.getImage()).into(holder.imageView);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("user_info").child(auth.getCurrentUser().getUid());
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model model = snapshot.getValue(Model.class);
                pincode= model.getPin();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.imageView.getContext(), DailyNeeds.class);
                i.putExtra("kit", model.getSubCategory());
                i.putExtra("group", "Groceries");
                i.putExtra("pincode",pincode);
                holder.imageView.getContext().startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public HomeViewModel.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_one, parent,false);

        return new HomeViewModel.MyHolder(view);

    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView item;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.i1);
            item = itemView.findViewById(R.id.ia1);

        }
    }
}