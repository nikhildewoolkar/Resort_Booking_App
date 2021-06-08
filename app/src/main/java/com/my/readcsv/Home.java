package com.my.readcsv;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity {
    Button b;
    EditText et;
    AlertDialog.Builder builder;
    Spinner sp1;

    Spinner sp2;
    TextView etfrom;
    Button etfrom1;
    TextView etto;
    Calendar eventsCalendar;
    Button etto1;
    public static String filepath = "trial1.csv";
    File f=new File(filepath);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        b = findViewById(R.id.button1);
        et = findViewById(R.id.etTxt);
        etfrom1 = findViewById(R.id.from);
        etto1 = findViewById(R.id.to);
        sp1 = findViewById(R.id.sp1);
        sp2 = findViewById(R.id.sp2);
        builder=new AlertDialog.Builder(this);

        etfrom = findViewById(R.id.et_from);
        etto = findViewById(R.id.et_to);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify_start_end_dates();
            }
        });
        sp1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        sp2.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        etfrom1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    setDate(etfrom);
            }
        });


        etto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.setEnabled(true);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(Home.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day1) {
                                etto.setText(day1 + "/" + (month + 1) + "/" + year);
                            }

                        }, year, month, dayOfMonth);

                Calendar c = Calendar.getInstance();
                String from = "";
                from = String.valueOf(etfrom.getText());
                int c1 = from.indexOf("/");
                int d1 = Integer.valueOf(from.substring(0, c1));
                int m1 = Integer.valueOf(from.substring(c1 + 1, from.indexOf("/", c1 + 1)));
                int d = from.indexOf("/", c1 + 1);
                int y1 = Integer.valueOf(from.substring(d + 1));
                c.set(y1, m1 - 1, d1);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();

                c.set(y1, m1 - 1, d1 + 2);//Year,Mounth -1,Day
                datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            }

        });

    }


    public void setDate(final TextView v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(Home.this,
                new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day1) {
                        v.setText(day1 + "/" + (month + 1) + "/" + year);
                    }

                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();


    }



    public void verify_start_end_dates() {

        String from = "";
        from = String.valueOf(etfrom.getText());
        int c = from.indexOf("/");
        int d1 = Integer.valueOf(from.substring(0, c));
        int m1 = Integer.valueOf(from.substring(c + 1, from.indexOf("/", c + 1)));
        int d = from.indexOf("/", c + 1);
        int y1 = Integer.valueOf(from.substring(d + 1));

        String to = String.valueOf(etto.getText());
        int c1 = to.indexOf("/");
        int d2 = Integer.valueOf(to.substring(0, c1));
        int m2 = Integer.valueOf(to.substring(c1 + 1, to.indexOf("/", c1 + 1)));
        int dd = to.indexOf("/", c1 + 1);
        int y2 = Integer.valueOf(to.substring(dd + 1));

        if ((y1 > y2) ||( (y1 == y2) && (m1 > m2) )||( y1 == y2 && m1 == m2 && d1 > d2)) {
           // Toast.makeText(Home.this,"Start date exceeding end date. \n Select valid dates.",Toast.LENGTH_LONG).show();
            String text="Start date exceeding end date. \n Select valid dates.";
            showToast(text);
            Intent same=new Intent(Home.this,Home.class);
            startActivity(same);
            finish();
        }
        else{
            String y=" ";
        y=verify_existing_data(filepath,d1,m1,y1,d2,m2,y2);
        if (y=="1") {
            write_to_file(filepath, et.getText() + " " + String.valueOf(sp1.getSelectedItem()) + " " + String.valueOf(sp2.getSelectedItem()) + " " + etfrom.getText() + " " + etto.getText().toString());
        }
        else if (y!="1" && y!="0")
        {
            giveVerifyError(y);
        }
        }
        }


        Toast mToastToShow;
        public void showToast(String text) {

            int toastDurationInMilliSeconds = 2147400000;
            mToastToShow = Toast.makeText(this,text, Toast.LENGTH_LONG);

            // Set the countdown to display the toast
            CountDownTimer toastCountDown;
            toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1 /*Tick duration*/) {
                public void onTick(long millisUntilFinished) {
                    mToastToShow.show();
                }
                public void onFinish() {
                    mToastToShow.cancel();
                }
            };

            // Show the toast and starts the countdown
            mToastToShow.show();
            toastCountDown.start();
        }


    public String verify_existing_data(String filepath,int d1,int m1,int y1,int d2,int m2,int y2) {
            String text1=" ";
        try {
            if (f.length() != 0) {
                FileInputStream fis = openFileInput(filepath);

                int size = fis.available();

                byte[] buffer = new byte[size];
                fis.read(buffer);

                fis.close();
                text1 = new String(buffer);
                String d[] = text1.split("\n");


                //for (int i=0;i<d.length;i++)
                // System.out.println("///////////////"+d[i]+"///////////");
                //   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+d.length+"Your data is :"+text1+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

                for (int i = 0; i < d.length; i++) {
                    String s = " ";
                    String f[] = d[i].split(" ");
                    String room_no = f[f.length - 3];
                    String loc_n = f[f.length - 4];
                    String f1 = f[f.length - 2];
                    String f2 = f[f.length - 1];
                    //  System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$Your data is :"+f[0]+" "+f[1]+" "+f[2]+"$$$$$$$$$$$$$$$"+f.length+" "+f1+" "+f2+"$$$$$$$$$$$$$$$$$$$$$");
                    int c = f1.indexOf("/");
                    int d3 = Integer.valueOf(f1.substring(0, c));
                    int r = f1.indexOf("/", c + 1);
                    int m3 = Integer.valueOf(f1.substring(c + 1, r));
                    int y3 = Integer.valueOf(f1.substring(r + 1));

                    int c1 = f2.indexOf("/");
                    int d4 = Integer.valueOf(f2.substring(0, c1));
                    int r1 = f2.indexOf("/", c1 + 1);
                    int m4 = Integer.valueOf(f2.substring(c1 + 1, r1));
                    int y4 = Integer.valueOf(f2.substring(r1 + 1));
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$Your data is :" + d3 + " " + m3 + " " + y3 + "$$$$$$$$$$$$$$$" + d4 + " " + m4 + " " + y4 + "$$$$$$$$$$$$$$$$$$$$$");

                    if (y1 == y3 && m1 == m3 && d1 == d3 && sp1.getSelectedItem() == loc_n && sp2.getSelectedItem() == room_no) {

                        s = d[i].substring(0, d[i].indexOf(" "));
                        return s;
                    } else if (y1 == y3 && m1 == m3) {
                        if (d1 <= d3 && d3 <= d2 && sp1.getSelectedItem() == loc_n && sp2.getSelectedItem() == room_no) {
                            s = d[i].substring(0, d[i].indexOf(" "));
                            return s;
                        } else if (d1 <= d4 && d4 <= d2 && sp1.getSelectedItem() == loc_n && sp2.getSelectedItem() == room_no) {
                            s = d[i].substring(0, d[i].indexOf(" "));
                            return s;
                        }
                    }

                }
                return "1";
            }
                return "1";

        }
        catch(Exception e){
                e.printStackTrace();
                Toast.makeText(Home.this, "Error reading this file", Toast.LENGTH_SHORT).show();
            }
            return "0";

    }

    public void giveVerifyError(String name) {

            builder.setMessage("Already booked by "+name+"\nPlease select some other date.")
            .setCancelable(false)
            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent same=new Intent(Home.this,Home.class);
                    startActivity(same);
                    finish();
                }
            });
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("AlertDialogExample");
        alert.show();
        }


    public void write_to_file(String filepath,String content){
        try{
            FileOutputStream fos=openFileOutput(filepath,Context.MODE_APPEND);

            fos.write(content.getBytes());
            String s="\n";
            fos.write(s.getBytes());
            fos.close();
            //Toast.makeText(Home.this,"Saved! to"+getFilesDir()+"/"+filepath,Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(Home.this, "Error saving..", Toast.LENGTH_SHORT).show();
        }

        Intent newact=new Intent(Home.this,Verified.class);
        newact.putExtra("info",et.getText() + " " + String.valueOf(sp1.getSelectedItem()) + " " + String.valueOf(sp2.getSelectedItem()) + " " + etfrom.getText() + " " + etto.getText().toString());
        startActivity(newact);
        finish();

    }
}
