package com.isracorporations.oketomart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPReciever extends BroadcastReceiver {
    private static EditText enterOtp;

    public void otpView (EditText enterOtp){
        OTPReciever.enterOtp=enterOtp;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] smsMessages= Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for(SmsMessage smsMessage: smsMessages){
              String messege_body = smsMessage.getMessageBody();
              Pattern pattern = Pattern.compile("(\\d{6})");

              Matcher matcher = pattern.matcher(messege_body);
              String otp = "";
              while (matcher.find()) {
                otp = matcher.group(0);
              }
              enterOtp.setText(otp);
        }
    }
}
