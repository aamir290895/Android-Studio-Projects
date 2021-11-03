package com.isracorporations.iacademy.camscannerclone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class editedImage extends AppCompatActivity {
    Button pdf;
    ImageView view;
    Context context;
    Uri edited;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edited_image);
        context= editedImage.this;
        view= (ImageView)findViewById(R.id.imageView1);
        pdf =(Button)findViewById(R.id.pdfMake);
        edited = getIntent().getData();
        view.setImageURI(edited);
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_TITLE, ".pdf");
                startActivityForResult(intent,24);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode ==24){
            Uri uri = data.getData();

            try {

                FileOutputStream stream = (FileOutputStream) getContentResolver().openOutputStream(uri);
                Bitmap original = MediaStore.Images.Media.getBitmap(context.getContentResolver(), edited);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                original.compress(Bitmap.CompressFormat.JPEG, 100, out);
                Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                PdfDocument document=new PdfDocument();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
                PdfDocument.Page page = document.startPage(pageInfo);
                Canvas canvas = page.getCanvas();


                canvas.drawBitmap(bitmap, 0, 0, null);
                document.finishPage(page);
                document.writeTo(stream);
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

