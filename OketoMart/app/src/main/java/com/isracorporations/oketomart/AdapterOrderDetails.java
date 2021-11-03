package com.isracorporations.oketomart;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isracorporations.oketomart.cart.CartAdapter;

import java.util.HashMap;
import java.util.Map;

public class AdapterOrderDetails extends FirebaseRecyclerAdapter<ModelOrderDetails, AdapterOrderDetails.MyHolder> {
    String pin;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterOrderDetails(@NonNull FirebaseRecyclerOptions<ModelOrderDetails> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterOrderDetails.MyHolder holder, int position, @NonNull ModelOrderDetails model) {

        DatabaseReference r3 =FirebaseDatabase.getInstance().getReference().child("user_info").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        r3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelOrderDetails model1 = snapshot.getValue(ModelOrderDetails.class);
                pin = model1.getPin();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.date.setText(model.getOrderId());
        holder.time.setText(model.getDate());
        holder.payment.setText(model.getPayment());
        holder.status.setText(model.getStatus());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(holder.cancel.getContext());
                dialog.setContentView(R.layout.dialog_cancel_order);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                dialog.show();

                Button cc = dialog.findViewById(R.id.cancel_confirm_order);
                cc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference refx = FirebaseDatabase.getInstance().getReference().child("orders").child(pin).child(model.getDate()).child("main");
                        Map<String,Object> k = new HashMap<>();
                        k.put("status","Order Cancelled");
                        refx.updateChildren(k);
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("user_orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Map<String,Object> map = new HashMap<>();
                        map.put("status","Requested to cancel");
                        reference.child(model.getDate()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                            }
                        });


                    }
                });

            }
        });
    }

    @NonNull
    @Override
    public AdapterOrderDetails.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_details,parent,false);


        return new AdapterOrderDetails
                .MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView date ,payment,status,cancel,time;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            date= itemView.findViewById(R.id.od_date);
            payment= itemView.findViewById(R.id.od_payment_mode);
            status= itemView.findViewById(R.id.od_status);
            cancel= itemView.findViewById(R.id.cancel_order);
            time=itemView.findViewById(R.id.od_time);
        }
    }
}
