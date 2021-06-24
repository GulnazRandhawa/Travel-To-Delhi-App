package example.com.tourismapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import example.com.tourismapp2.classpack.planner_details;

public class Calendar extends AppCompatActivity {
    TextView cancel,ok,timeFrom,timeTo;
    CalendarView calendarView;
    TimePickerDialog picker;
    String current_date = "", current_time_to ="", current_time_from = "",email = "",place_id="";
    DatabaseReference planer_ref,user_add_places_ref;
    String date = "";
    String ask_to_remove = "no",id ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        SharedPreferences sharedPreference=getSharedPreferences("mypref",MODE_PRIVATE);
        email = sharedPreference.getString("email","");

        cancel = findViewById(R.id.cancel);
        ok = findViewById(R.id.ok);
        timeFrom = findViewById(R.id.timeFrom);
        timeTo = findViewById(R.id.timeTo);
        calendarView = findViewById(R.id.calendarView);

        try {
            Intent in = getIntent();
            place_id = in.getStringExtra("place_id");
            ask_to_remove = in.getStringExtra("remove");
            id = in.getStringExtra("id");

        }catch (Exception e ){
            ask_to_remove = "no";
            id = "no";
            Log.d("EX",e.toString());
        }

        planer_ref = FirebaseDatabase.getInstance().getReference("Planners_Details");
        user_add_places_ref = FirebaseDatabase.getInstance().getReference("User_Added_Fav_Places");
        //Logic for selecting Calendar date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
// date is set to the selected one and also shows intent of the same.
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
              date  = dayOfMonth + "-" + (month + 1) + "-" + year;

                String curDate = String.valueOf(dayOfMonth);
                Toast.makeText(Calendar.this, ""+curDate, Toast.LENGTH_SHORT).show();
            }
        });
        //

        //set current date if no other date or time is selected
        Date date_obj = new Date();
        SimpleDateFormat sdf1  = new SimpleDateFormat("dd-MM-yyyy");
        date = sdf1.format(date_obj);

        //Logic for setting Time from on Planner
        timeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar cldr = java.util.Calendar.getInstance();
                int hour = cldr.get(java.util.Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(java.util.Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(Calendar.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                timeFrom.setText(sHour + ":" + sMinute);
//                                Toast.makeText(Calendar.this, ""+sHour + ":" + sMinute, Toast.LENGTH_SHORT).show();
                                current_time_from = sHour + ":" + sMinute;
                            }

                        }, hour, minutes, true);
                picker.show();
            }
        });

// Logic for selecting Time to.
        timeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar cldr = java.util.Calendar.getInstance();
                int hour = cldr.get(java.util.Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(java.util.Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(Calendar.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                timeTo.setText(sHour + ":" + sMinute);
//                                Toast.makeText(Calendar.this, ""+sHour + ":" + sMinute, Toast.LENGTH_SHORT).show();
                                current_time_to = sHour + ":" + sMinute;

                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        // Logic for "OK" button on calendar
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(date.equals("")){

                    Toast.makeText(Calendar.this, "Select Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(current_time_from.equals("")){

                    Toast.makeText(Calendar.this, "Select Time From", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(current_time_to.equals("")){

                    Toast.makeText(Calendar.this, "Select Time TO", Toast.LENGTH_SHORT).show();
                    return;
                }


                // add to firebase
                String key =  planer_ref.push().getKey(); // retrieving place's ID from firebase
                // creating an object of places added to planner
                planner_details obj = new planner_details(key,email,place_id,date,current_time_from,current_time_to);
// pointing to the "KEY" value of place added to planner in Firebase tree
                planer_ref.child(key).setValue(obj); //Object saved
                Toast.makeText(Calendar.this, "Added to Calendar for visiting", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreference=getSharedPreferences("mypref",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreference.edit();
                editor.putBoolean("TASK2",true);
                editor.commit();


               try {
                   if(ask_to_remove.equals("yes")){
                       user_add_places_ref.child(id).removeValue();
                   }
               }catch (Exception e){
                   Log.d("a",e.toString());
               }


                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}