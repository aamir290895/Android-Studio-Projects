package amazon;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isracorporations.oketomart.ModelOrderDetails;
import com.isracorporations.oketomart.R;

public class Amazon extends AppCompatActivity {
   WebView webView;
   ProgressDialog progressDialog;
   String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon);
        webView = findViewById(R.id.wb_view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("loading");
        AdView mAdView = findViewById(R.id.adView_amazon);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();



            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("amazon");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelOrderDetails model = snapshot.getValue(ModelOrderDetails.class);
                url= model.getLink();
                webView.loadUrl(url);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}