package com.isracorporations.iacademy.camscannerclone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class multiPagesPdf extends AppCompatActivity {


    ArrayList<Uri>  myList;

    Button save;
    Context context;
    EditText nameOfPdf;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_pages_pdf);
        context = multiPagesPdf.this;
        Intent intent = getIntent();
        myList = intent.getParcelableArrayListExtra("multipleImage");
        nameOfPdf = findViewById(R.id.fileName);

        save = (Button)findViewById(R.id.savePdf);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_TITLE, ".pdf");
                startActivityForResult(intent,23);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 23 && resultCode==RESULT_OK) {
            Uri uri =data.getData();

            PdfDocument document=new PdfDocument();
            try {

               FileOutputStream stream = (FileOutputStream) getContentResolver().openOutputStream(uri);


                for (int i = 0; i <myList.size(); i++) {

                    Bitmap original  = MediaStore.Images.Media.getBitmap(this.getContentResolver(), myList.get(i));
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    original.compress(Bitmap.CompressFormat.JPEG, 20, out);
                    Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), i + 1).create();
                    PdfDocument.Page page = document.startPage(pageInfo);
                    Canvas canvas = page.getCanvas();



                    canvas.drawBitmap(bitmap, 0, 0, null);
                    document.finishPage(page);
                    bitmap.recycle();
                }

                document.writeTo(stream);
                document.close();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();

            }


        }
    }

    }
