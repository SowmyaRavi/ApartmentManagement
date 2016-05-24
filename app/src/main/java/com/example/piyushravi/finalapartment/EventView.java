package com.example.piyushravi.finalapartment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.piyushravi.finalapartment.models.Event;
import com.example.piyushravi.finalapartment.models.Notification;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventView extends AppCompatActivity {
    CalendarView cv;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.event);
        setContentView(R.layout.activity_event_view);
        cv = (CalendarView) findViewById(R.id.calendarView1);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub

                date = +dayOfMonth + "-" + month + "-" + year;

                addEvent(date);


            }
        });

    }


    protected void addEvent(final String date) {
        // TODO Auto-generated method stub
        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                EventView.this);
        builderInner.setTitle("Add Event on "+parseDateToddMMyyyy(date));
        final EditText ed =new EditText(EventView.this);
        builderInner.setView(ed);
        builderInner.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {
                        String val =  ed.getText().toString();
                        if(val.length()>0){

                            Firebase ref_3 = new Firebase(Constants.EVENT_URL);
                            //Creating postComplaint object
                           Event event = new Event();

                            //Adding values
                            event.setDate(date);
                            event.setMessage(val);

                            //Storing values to firebase
                            ref_3.push().setValue(event);

                            dialog.dismiss();
                        }else{
                            Toast.makeText(getApplicationContext(), "Need event desc", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        builderInner.show();


    }
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd-MM-yyyy";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
