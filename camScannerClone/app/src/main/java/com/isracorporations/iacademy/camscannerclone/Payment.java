package com.isracorporations.iacademy.camscannerclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.PaymentApp;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;

public class Payment extends AppCompatActivity  implements PaymentStatusListener {


    private ImageView imageView;

    private TextView statusView;

    private Button payButton;

    private RadioGroup radioAppChoice;

    private EditText fieldPayeeVpa;
    private EditText fieldPayeeName;
    private EditText fieldPayeeMerchantCode;
    private EditText fieldTransactionId;
    private EditText fieldTransactionRefId;
    private EditText fieldDescription;
    private EditText fieldAmount;

    private EasyUpiPayment easyUpiPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initViews();
        payButton=(Button)findViewById(R.id.button_pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });

    }
    private void initViews() {
        imageView = findViewById(R.id.imageView);
        statusView = findViewById(R.id.textView_status);
        payButton = findViewById(R.id.button_pay);

        fieldPayeeVpa = findViewById(R.id.field_vpa);
        fieldPayeeName = findViewById(R.id.field_name);
        fieldPayeeMerchantCode = findViewById(R.id.field_payee_merchant_code);
        fieldTransactionId = findViewById(R.id.field_transaction_id);
        fieldTransactionRefId = findViewById(R.id.field_transaction_ref_id);
        fieldDescription = findViewById(R.id.field_description);
        fieldAmount = findViewById(R.id.field_amount);

        String transactionId = "TID" + System.currentTimeMillis();
        fieldTransactionId.setText(transactionId);
        fieldTransactionRefId.setText(transactionId);

        radioAppChoice = findViewById(R.id.radioAppChoice);
    }

    private void pay() {
        String payeeVpa = fieldPayeeVpa.getText().toString();
        String payeeName = fieldPayeeName.getText().toString();
        String payeeMerchantCode = fieldPayeeMerchantCode.getText().toString();
        String transactionId = fieldTransactionId.getText().toString();
        String transactionRefId = fieldTransactionRefId.getText().toString();
        String description = fieldDescription.getText().toString();
        String amount = fieldAmount.getText().toString();
        RadioButton paymentAppChoice = findViewById(radioAppChoice.getCheckedRadioButtonId());

        PaymentApp paymentApp;

        switch (paymentAppChoice.getId()) {
            case R.id.app_default:
                paymentApp = PaymentApp.ALL;
                break;
            case R.id.app_amazonpay:
                paymentApp = PaymentApp.AMAZON_PAY;

                break;
            case R.id.app_bhim_upi:
                paymentApp = PaymentApp.BHIM_UPI;
                break;
            case R.id.app_google_pay:
                paymentApp = PaymentApp.GOOGLE_PAY;
                break;
            case R.id.app_phonepe:
                paymentApp = PaymentApp.PHONE_PE;
                break;
            case R.id.app_paytm:
                paymentApp = PaymentApp.PAYTM;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + paymentAppChoice.getId());
        }


        // START PAYMENT INITIALIZATION
        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                .with(paymentApp)
                .setPayeeVpa("8224849403@ybl")
                .setPayeeName("AAMIR KHAN")
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionRefId)
                .setPayeeMerchantCode("Null")
                .setDescription("OKETO")
                .setAmount(amount);
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

    @Override
    public void onTransactionCancelled() {

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
    private void onTransactionSuccess() {
        // Payment Success
        Toast.makeText(this,"Success " ,Toast.LENGTH_LONG).show();

        imageView.setImageResource(R.drawable.ic_baseline_check_24);
    }

    private void onTransactionFailed() {
        // Payment Failed
        Toast.makeText(this,"Failed " ,Toast.LENGTH_LONG).show();

        imageView.setImageResource(R.drawable.ic_baseline_check_24);
    }
    private void onTransactionSubmitted() {
        // Payment Pending
        Toast.makeText(this,"Pending " ,Toast.LENGTH_LONG).show();

        imageView.setImageResource(R.drawable.ic_baseline_check_24);
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       easyUpiPayment.removePaymentStatusListener();
    }
}