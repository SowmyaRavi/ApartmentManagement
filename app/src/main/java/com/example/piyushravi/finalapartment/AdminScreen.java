package com.example.piyushravi.finalapartment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.piyushravi.finalapartment.models.Complaint;
import com.example.piyushravi.finalapartment.models.Notification;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class AdminScreen extends AppCompatActivity {

    Button manageTenants;
    Button logout;
    Button blogManage;
    Button repairRequest;
    Button sendNotification;
    Button parkingRequest;
    TextView welcomeMsg;
    Button eventsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.admin_zone);
        setContentView(R.layout.activity_admin_screen);
        final Firebase ref1=new Firebase(Constants.TENANT_URL);

        logout=(Button) findViewById(R.id.adminLogout);

        repairRequest=(Button) findViewById(R.id.new_repair);
        sendNotification=(Button) findViewById(R.id.send_notification);
        parkingRequest=(Button) findViewById(R.id.parking_request);
        welcomeMsg=(TextView) findViewById(R.id.textView4);
        eventsView=(Button) findViewById(R.id.button2);

        eventsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EventView.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });

        manageTenants=(Button) findViewById(R.id.button1);
        manageTenants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminScreen.this,ManageTenant.class);
                startActivity(intent);


            }
        });

        repairRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminScreen.this,RepairManage.class);
                startActivity(intent);
            }
        });

        parkingRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminScreen.this,ParkingRequest.class);
                startActivity(intent);
            }
        });

        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(AdminScreen.this);
                builderSingle.setTitle("Enter Flat No:");
                final EditText ed1 =new EditText(AdminScreen.this);
                builderSingle.setView(ed1);
                builderSingle.setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builderSingle.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                final String flatNo = ed1.getText().toString();
                                AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                        AdminScreen.this);
                                builderInner.setMessage(flatNo);
                                final EditText ed =new EditText(AdminScreen.this);
                                builderInner.setView(ed);
                                builderInner.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                dialog.dismiss();
                                                String message =  ed.getText().toString();
                                                Firebase ref_3 = new Firebase(Constants.NOTIFICATION_URL);
                                                //Creating postComplaint object
                                                Notification postNotification = new Notification();

                                                //Adding values
                                                postNotification.setFlatNo(flatNo);
                                                postNotification.setMessage(message);
                                                postNotification.setFrom("Admin");
                                                //Storing values to firebase
                                                ref_3.child(flatNo).push().setValue(postNotification);
                                                Toast.makeText(AdminScreen.this,"Notification Sent",Toast.LENGTH_LONG).show();
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
        });


    }
}
