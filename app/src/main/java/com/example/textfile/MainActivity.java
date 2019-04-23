package com.example.textfile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText et_name,et_content;
    Button b_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }

        et_name=(EditText) findViewById(R.id.et_Name);
        et_content=(EditText) findViewById(R.id.et_content);
        b_save=(Button) findViewById(R.id.b_save);
        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename=et_name.getText().toString();
                String content=et_content.getText().toString();

                if (!filename.equals("")&& !content.equals("")){
                    saveTextAsFile(filename, content);
                }
            }
        });
    }
    public void saveTextAsFile(String filename,String content){
        String fileName= filename+ ".txt";
        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);

        try {
            FileOutputStream fos= new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(MainActivity.this,"SAVED!", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"File not found!", Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"Error Saving!", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1000:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"Permission Granted",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Permission not Granted",Toast.LENGTH_LONG).show();
                    finish();
                }
        }
    }
}
