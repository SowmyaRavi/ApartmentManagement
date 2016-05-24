package com.example.piyushravi.finalapartment;

import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.piyushravi.finalapartment.models.Event;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserEventView extends AppCompatActivity {
     CalendarView calendarView;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.event);
        setContentView(R.layout.activity_user_event_view);
        calendarView = (CalendarView) findViewById(R.id.calendarView2);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub

                date = +dayOfMonth + "-" + month + "-" + year;

                try {

                    showEvent(date);
                }
                catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(getApplicationContext(), "No event found on this day", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    protected void showEvent(final String date) {
        // TODO Auto-generated method stub

        Firebase ref_3 = new Firebase(Constants.EVENT_URL);
        Query CourseRef = ref_3.orderByChild(Event.COURSE_KEY).equalTo(date);
        CourseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                           Event event = postSnapshot.getValue(Event.class);
                           String message = event.getMessage();

                           AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                   UserEventView.this);
                           builderInner.setTitle("Event on " + parseDateToddMMyyyy(date));

                           builderInner.setMessage(message);

                           builderInner.setMessage(message);
                           builderInner.setPositiveButton("Ok",
                                   new DialogInterface.OnClickListener() {

                                       @Override
                                       public void onClick(
                                               DialogInterface dialog,
                                               int which) {
                                           dialog.dismiss();
                                       }
                                   });

                           builderInner.show();

                    }
                }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
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
