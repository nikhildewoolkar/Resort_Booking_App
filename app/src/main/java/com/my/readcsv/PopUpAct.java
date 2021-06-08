package com.my.readcsv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PopUpAct extends Activity {
    CompactCalendarView compactCalendar;
    TextView tv1;
    Button cancelbt;
    Button donebt;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        cancelbt=findViewById(R.id.cancelb);
        donebt=findViewById(R.id.doneb);
        Calendar c = Calendar.getInstance();

        String[]monthName={"January","February","March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        String month=monthName[c.get(Calendar.MONTH)];
        int year=c.get(Calendar.YEAR);
        tv1=findViewById(R.id.TextView12);
        tv1.setText(month+"- "+year);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;


        getWindow().setAttributes(params);
        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);
//        public void decorate(DayView dayView) {
//            if(!isPastDay(dayView.getDate())){
//                dayView.setBackgroundColor(getResources().getColor(R.color.disable_color));
//            }
//        }





        //Set an event for Teachers' Professional Day 2016 which is 21st of October

        final List<Date> selectedDays = new ArrayList<>();
        Date date1 = null;
        Date date2 = null;
        String sDate1 = "2019-05-07";
        String sDate2 = "2019-05-15";
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //selectedDays.add(date1);
        List<Date> Dates = getDates(sDate1, sDate2);
        for (Date date : Dates) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1" + date + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            selectedDays.add(date);
        }
        //      selectedDays.add(date2);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);

        for (int i = 0; i < selectedDays.size(); i++) {
            System.out.println("###############################################" + selectedDays.get(i) + "####################3");


            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String oldTime = dateFormat.format(selectedDays.get(i));

            Date oldDate = null;
            try {
                oldDate = formatter.parse(oldTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long oldMillis = oldDate.getTime();
            Event ev1 = new Event(Color.RED, oldMillis, "Teachers' Professional Day");
            compactCalendar.addEvent(ev1);

        }

        cancelbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t=new Intent(PopUpAct.this,Home.class);
                startActivity(t);
            }
        });


        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                String t="";
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&777777" + dateClicked.toString() + "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^6");
                if (selectedDays.contains(dateClicked)) {
                    Toast.makeText(context, "Booked!!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    dateClicked = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                   t= dateFormat.format(dateClicked);

                }
                final String finalT = t;
                donebt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(PopUpAct.this,Home.class);
//                        t1.putExtra(finalT,"thedate");
//                        startActivity(t1);

                        intent.putExtra("MESSAGE",finalT);
                        setResult(2,intent);
                        finish();
                    }
                });

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                tv1.setText(dateFormatMonth.format(firstDayOfNewMonth));
               // actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }

    private List<Date> getDates(String dateString1, String dateString2) {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;

    }

}


