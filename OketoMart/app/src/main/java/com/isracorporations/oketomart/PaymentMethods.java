package com.isracorporations.oketomart;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.PaymentApp;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;

public class PaymentMethods extends AppCompatActivity implements PaymentStatusListener {

    RadioGroup radioGroup;
    String payment;
    String address,mobile,name,total,weight,cost,pin;
    TextView t1,t2,t3;
    Button button,payOnline;

    String upiId,payeeName,id;
    String transactionId,randomKey;


    private EasyUpiPayment easyUpiPayment;

    @Override
    protected void onStart() {
        super.onStart();
        Dialog dialog = new Dialog(PaymentMethods.this);
        dialog.setContentView(R.layout.dialog_delivery_time);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        dialog.show();
        Button cc = dialog.findViewById(R.id.delivery_time_agree);
        cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);
        radioGroup = findViewById(R.id.radio_group);
        address = getIntent().getStringExtra("address");
        mobile = getIntent().getStringExtra("mobile");
        name = getIntent().getStringExtra("name");
        total=getIntent().getStringExtra("totalCost");
        weight=getIntent().getStringExtra("totalWeight");
        cost= getIntent().getStringExtra("cost");
        pin=getIntent().getStringExtra("pincode");
        upiId =getIntent().getStringExtra("upi");
        payeeName= getIntent().getStringExtra("payee");
        t1=findViewById(R.id.pm_total);
        t2=findViewById(R.id.pm_delivery_charges);
        t3=findViewById(R.id.pm_grand_total);
        button= findViewById(R.id.pm_btn);





        total();





        payOnline = findViewById(R.id.pay_online);
        payOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payment == null) {

                    startOnlinePay();
                }

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);

                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked)
                {
                    payment=  checkedRadioButton.getText().toString();
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payment != null){
                    Intent i = new Intent(PaymentMethods.this,Orders.class);
                    i.putExtra("mobile", mobile);
                    i.putExtra("name",name);
                    i.putExtra("address", address);
                    i.putExtra("transactionId","xxxxxxxxxxx");
                    i.putExtra("refId","xxxxxxx");
                    i.putExtra("totalCost",t3.getText().toString().replaceAll("\\D+" ,""));
                    i.putExtra("delivery",t2.getText().toString().replaceAll("\\D+", ""));
                    i.putExtra("total",total.replaceAll("\\D+" , ""));
                    i.putExtra("totalWeight",weight);
                    i.putExtra("paymentMethod",payment);
                    i.putExtra("cost",cost);
                    i.putExtra("pincode",pin);
                    startActivity(i);
                }
            }
        });

    }


   private void startOnlinePay(){

        transactionId = "TID" + System.currentTimeMillis();

       Calendar calendar= Calendar.getInstance();
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,YYYY");
       String currentDate= simpleDateFormat.format(calendar.getTime());
       SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss a");
       String currentTime= simpleTimeFormat.format(calendar.getTime());
       randomKey=currentDate +currentTime;


       // START PAYMENT INITIALIZATION
       EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
               .with(PaymentApp.ALL)
               .setPayeeVpa(upiId)
               .setPayeeName(payeeName)
               .setTransactionId(transactionId.replaceAll("\\D+",""))
               .setTransactionRefId(randomKey.replaceAll("\\D+",""))
               .setPayeeMerchantCode("Null")
               .setDescription("OKETO SHOPPING")
               .setAmount(t3.getText().toString().replaceAll("\\D+" ,"") + ".00");
       // END INITIALIZATION

       try {
           // Build instance
           easyUpiPayment = builder.build();

           // Register Listener for Events
           easyUpiPayment.setPaymentStatusListener(this);

           // Start payment / transaction
           easyUpiPayment.startPayment();
       } catch (Exception exception) {
           exception.printStackTrace();
           Toast.makeText(this,"Error: " + exception.getMessage(),Toast.LENGTH_LONG).show();
       }

   }


    private void total(){
        int totalPrice = Integer.parseInt(total.replaceAll("\\D+" ,""));
        int  totalWeight =  Integer.parseInt( weight.replaceAll("\\D+",""));
        int i = 0;
        int j ;

        if(totalWeight > 5 && totalPrice <= 999){
            j = totalWeight -5;
            i =  20+j;
        }else if(totalWeight <5 && totalPrice <= 999){
            i=20;
        }
        else if(totalPrice >= 999) {
            i = 0;
        }

        int k = totalPrice +i;
        String x = String.valueOf(i);
        t2.setText("Rs" + " " + x + "/-");
        String y = String.valueOf(k);
        t3.setText("Rs" + " " + y + "/-");
        t1.setText( "Rs" + " " + String.valueOf(totalPrice) + "/-");

    }





    @Override
    public void onTransactionCancelled() {
      Toast.makeText(this,"Payment cancelled by user",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCompleted(@NotNull TransactionDetails transactionDetails) {


        switch (transactionDetails.getTransactionStatus()) {
            case SUCCESS:
                onTransactionSuccess();
                break;
            case FAILURE:
                onTransactionFailed();
                break;
            case SUBMITTED:
                onTransactionSubmitted();
                break;
        }

    }

    private void onTransactionSubmitted() {
        Toast.makeText(this,"Submitted",Toast.LENGTH_LONG).show();
    }

    private void onTransactionFailed() {
        Toast.makeText(this,"Transaction Failed",Toast.LENGTH_LONG).show();
    }

    private void onTransactionSuccess() {
        String  payment2 = "Paid Online";
        if(payment2 != null){
            Intent i = new Intent(PaymentMethods.this,Orders.class);
            i.putExtra("mobile", mobile);
            i.putExtra("name",name);
            i.putExtra("address", address);
            i.putExtra("transactionId",transactionId);
            i.putExtra("refId",randomKey);
            i.putExtra("totalCost",t3.getText().toString().replaceAll("\\D+" ,""));
            i.putExtra("delivery",t2.getText().toString().replaceAll("\\D+", ""));
            i.putExtra("total",total.replaceAll("\\D+" , ""));
            i.putExtra("totalWeight",weight);
            i.putExtra("paymentMethod",payment2);
            i.putExtra("cost",cost);
            i.putExtra("pincode",pin);
            startActivity(i);
        }


    }
}