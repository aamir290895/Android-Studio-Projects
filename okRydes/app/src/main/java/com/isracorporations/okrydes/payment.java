package com.isracorporations.okrydes;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shreyaspatil.easyupipayment.EasyUpiPayment;
import com.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import com.shreyaspatil.easyupipayment.model.PaymentApp;

public class payment extends AppCompatActivity {
   String upiId,amount;
   EditText xys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        upiId = "isracorporations.okhdfcbank";
        xys = (EditText)findViewById(R.id.amnt);

       



    }

   private  void payUseUpi (){
       Uri uri = Uri.parse("").buildUpon()
               .appendQueryParameter("pa",upiId)
               .appendQueryParameter("am",amount)
               .appendQueryParameter("tn","hy")
               .appendQueryParameter("cu","INR")
               .build();
       Intent upiPay = new Intent(Intent.ACTION_VIEW);
       upiPay.setData(uri);
   }

}

