package com.example.piyushravi.finalapartment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piyushravi.finalapartment.models.Complaint;
import com.example.piyushravi.finalapartment.models.Parking;
import com.firebase.client.Firebase;

public class UserScreen extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    Button payRent;
    Button slide;
    Button postBlog;
    TextView welcomeMsg;
    TextView contactDetails;
    TextView emailId;

    String[] web = {
            "COMPLAINT",
            "REQUEST FOR PARKING",
            "PAY RENT",
            "INBOX",
            "EVENT",
            "LOGOUT"
    } ;
    Integer[] imageId_1 = {  R.drawable.circle   };
    ListView list_1;
    String rent;
    String flat_no;
    String welcomeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(R.string.user_zone);

        setContentView(R.layout.activity_user_screen);
        Firebase.setAndroidContext(this);
        list_1 = (ListView) findViewById(R.id.listview);
        TextView nameEditText = (TextView) findViewById(R.id.textView1);
        welcomeMsg=(TextView) findViewById(R.id.textView);
        contactDetails=(TextView) findViewById(R.id.textView2);
        emailId=(TextView) findViewById(R.id.textView3);

        slide = (Button) findViewById(R.id.slider);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        rent = sharedPreferences.getString("RENT", "default value");
        flat_no=sharedPreferences.getString("Flat","default value");
        welcomeName=sharedPreferences.getString("FIRSTNAME","default value");
        nameEditText.append(welcomeName);

        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {

                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
        try {
            CustomSlider adapter = new CustomSlider(UserScreen.this, web, imageId_1, "HOME");
            //list_1.setCacheColorHint(getResources().getColor(R.color.white));
            list_1.setAdapter(adapter);
        } catch (Exception e) {
            Log.v("Exception", "" + e);
        }


        list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        postComplaint();
                        break;
                    case 1:
                        requestParking();
                        break;
                    case 2:
                        payment();
                        break;
                    case 3:
                       checkInbox();
                        break;
                    case 4:
                        startActivity(new Intent(getApplicationContext(),UserEventView.class));
                        break;
                    case 5:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        break;

                }
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                }
            }
        });

    }

    private void checkInbox() {
       Intent intent=new Intent(UserScreen.this,TenantInbox.class);
        startActivity(intent);

    }

    private void requestParking() {
        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                UserScreen.this);
        builderInner.setTitle("Request for parking");
        final EditText ed =new EditText(UserScreen.this);
        builderInner.setView(ed);
        builderInner.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {
                        dialog.dismiss();
                        String val =  ed.getText().toString();
                        if(val.isEmpty()){
                            Toast.makeText(UserScreen.this,"Please enter message",Toast.LENGTH_LONG).show();
                        }else {
                            Firebase ref_2 = new Firebase(Constants.PARKING_URL);
                            Parking parking = new Parking();
                            parking.setNumber(flat_no);
                            parking.setMessage(val);
                            ref_2.push().setValue(parking);
                            Toast.makeText(getApplicationContext(), "Parking Request made", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        builderInner.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderInner.show();
    }

    protected void postComplaint() {
        // TODO Auto-generated method stub

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                UserScreen.this);
        builderSingle.setTitle("Write a Complaint");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                UserScreen.this,
                android.R.layout.select_dialog_item);
        arrayAdapter.add("Kitchen");
        arrayAdapter.add("Carpenter");
        arrayAdapter.add("Pipes");
        arrayAdapter.add("Bathroom");
        arrayAdapter.add("Plumber");
        arrayAdapter.add("Other");

        builderSingle.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String complaintType = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                UserScreen.this);
                        builderInner.setMessage(complaintType);
                        final TextView flatTextView=new TextView(UserScreen.this);
                        flatTextView.setText(flat_no);
                        final EditText messageEditText = new EditText(UserScreen.this);
                        builderInner.setView(messageEditText);
                        builderInner.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                        String complaintMessage = messageEditText.getText().toString();
                                        if(complaintMessage.isEmpty()){
                                            Toast.makeText(UserScreen.this,"Please enter message",Toast.LENGTH_LONG).show();
                                        }else {

                                            Firebase ref_3 = new Firebase(Constants.COMPLAINT_URL);
                                            //Creating postComplaint object
                                            Complaint postComplaint = new Complaint();

                                            //Adding values
                                            postComplaint.setNumber(flat_no);
                                            postComplaint.setReportType(complaintType);
                                            postComplaint.setMessage(complaintMessage);

                                            //Storing values to firebase
                                            ref_3.push().setValue(postComplaint);
                                            Toast.makeText(getApplicationContext(), "Repair Request made", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                        builderInner.setNegativeButton("cancel",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();
                    }
                });

        builderSingle.show();
    }

    private void payment() {
// TODO Auto-generated method stub
        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                UserScreen.this);
        builderInner.setTitle("Payment");
        final TextView amountPayEditText =new TextView(UserScreen.this);
        amountPayEditText.setText("Amount to be paid is:$"+rent);
        builderInner.setView(amountPayEditText);
        builderInner.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {
                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),PayRent.class));

                    }
                });
        builderInner.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderInner.show();
    }


}
