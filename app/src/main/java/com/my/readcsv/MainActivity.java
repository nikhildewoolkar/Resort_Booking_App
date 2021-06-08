package com.my.readcsv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT=1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this,Home.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);


        getSupportActionBar().hide();
    }

}




































 //        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                order();
//            }
//        });


//    public void order(){
//        inputStream = getResources().openRawResource(R.raw.myexcelsheet);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        try {
//            String csvLine;
//            String xx="";
//            while ((csvLine = reader.readLine()) != null) {
//
//                ids=csvLine.split(",");
//                try{
//                    Log.e("sno ",""+ids[0]);
//                    xx=xx+ids[0]+" ";
//                    Log.e("x ",""+ids[1]) ;
//                    xx=xx+ids[1]+" ";
//                    Log.e("y ",""+ids[2]) ;
//                    xx=xx+ids[2];
//
//                }catch (Exception e){
//                    Log.e("Unknown error",e.toString());
//                }
//                xx=xx+"\n";
//            }
//            display(xx);
//        }
//        catch (IOException ex) {
//            throw new RuntimeException("Error in reading CSV file: "+ex);
//        }
//
//
//
//    }
//
//    public void display(String xx) {
//    et.setText(xx);
//    }
