package com.my.readcsv;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Verified extends AppCompatActivity {

    TextView tv;
    Button b;
    TextView t1;
    TextView t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);
        tv=findViewById(R.id.infotv);
        b=findViewById(R.id.email_send_but);
        t1=findViewById(R.id.symbol);
        t2=findViewById(R.id.status_tv);
        Intent i=getIntent();
        String t=i.getStringExtra("info");
        final String[] res=t.split(" ");
        if(res.length==6)
            tv.setText("Your data:\nName: "+res[0]+" "+res[1]+"\nLocation: "+res[2]+"\nRoom no.:"+res[3]+"\nStart date: "+res[4]+"\nEnd date: "+res[5]);
        else if(res.length==5)
            tv.setText("Your data:\nName: "+res[0]+"\nLocation: "+res[1]+"\nRoom no.:"+res[2]+"\nStart date: "+res[3]+"\nEnd date: "+res[4]);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{"pooja.sangle@spit.ac.in"});
                i.putExtra(Intent.EXTRA_SUBJECT,"Resort Booking Confirmation.");
                if(res.length==6)
                    i.putExtra(Intent.EXTRA_TEXT,"Data:\nName: "+res[0]+" "+res[1]+"\nLocation: "+res[2]+"\nRoom no.:"+res[3]+"\nStart date: "+res[4]+"\nEnd date: "+res[5]);
                else if(res.length==5)
                    i.putExtra(Intent.EXTRA_TEXT,"Data:\nName: "+res[0]+"\nLocation: "+res[1]+"\nRoom no.:"+res[2]+"\nStart date: "+res[3]+"\nEnd date: "+res[4]);


                i.setType("message/rfc822");
                startActivity(i.createChooser(i,"Select Email Sending App."));
                b.setEnabled(false);
                b.setVisibility(View.INVISIBLE);

                t1.setTextColor(Color.BLUE);
                t2.setText("TICKETS BOOKED SUCCESSFULLY.");


//                Intent j=new Intent(Verified.this,SuccessBook.class);
//                startActivity(j);
            }
        });



    }


}
