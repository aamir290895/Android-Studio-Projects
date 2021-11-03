package com.isracorporations.oketomart;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isracorporations.oketomart.cart.CartActivity;

public class AdapterDeliveryDetails extends FirebaseRecyclerAdapter<DeliveryModel, AdapterDeliveryDetails.MyHolder> {
     String total,weight,cost,upiId,payeeName;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     * @param total
     */
    public AdapterDeliveryDetails(@NonNull FirebaseRecyclerOptions<DeliveryModel> options, String total ,String weight,String cost) {
        super(options);
        this.total= total;
        this.weight= weight;
        this.cost =cost;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull DeliveryModel model) {

        holder.name.setText(model.getName());
        holder.mobile.setText("Mobile :" + model.getPhone());
        holder.address.setText(model.getAddress()+", " + model.getCity()+ ", " + model.getPinCode());

        DatabaseReference refx = FirebaseDatabase.getInstance().getReference().child("acc_owner").child(model.getPinCode());
        refx.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelOrderDetails model = snapshot.getValue(ModelOrderDetails.class);
                upiId = model.getUpi();
                payeeName = model.getPayee();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.rb.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(total != null) {

                     Intent intent = new Intent(holder.rb.getContext(), PaymentMethods.class);
                     intent.putExtra("mobile", model.getPhone());
                     intent.putExtra("name", model.getName());
                     intent.putExtra("address", model.getAddress() + ", " + model.getCity() + ", " + model.getPinCode());
                     intent.putExtra("totalCost", total);
                     intent.putExtra("totalWeight", weight);
                     intent.putExtra("cost", cost);
                     intent.putExtra("upi",upiId);
                     intent.putExtra("payee",payeeName);
                     intent.putExtra("pincode", model.getPinCode());

                     holder.rb.getContext().startActivity(intent);
                 }else {
                     Intent intent2 = new Intent(holder.rb.getContext(), CartActivity.class);
                     holder.rb.getContext().startActivity(intent2);

                 }
             }
         });
       holder.remove.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Address_Delivery").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
               reference.child(model.getPid()).removeValue();
           }
       });

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_delivery_details,parent,false);


        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name,mobile,address;
        Button rb;
        ImageButton remove;



        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_dd);
            mobile = itemView.findViewById(R.id.mobile_dd);
            address = itemView.findViewById(R.id.address_dd);
            rb = itemView.findViewById(R.id.select_dd);
            remove = itemView.findViewById(R.id.remove_delivery_address);


        }
    }
}
