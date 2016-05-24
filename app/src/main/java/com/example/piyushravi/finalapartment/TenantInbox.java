package com.example.piyushravi.finalapartment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piyushravi.finalapartment.Adapters.InboxAdapter;
import com.example.piyushravi.finalapartment.Adapters.ParkingRequestAdapter;
import com.example.piyushravi.finalapartment.models.Notification;
import com.example.piyushravi.finalapartment.models.Parking;
import com.example.piyushravi.finalapartment.models.Tenant;

import java.util.ArrayList;

public class TenantInbox extends AppCompatActivity {

    private InboxAdapter mAdapter;
    ArrayList<Notification> notificationList = new ArrayList<>();
    RecyclerView recyclerView;
    TextView t;
    public static String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.inbox);
        setContentView(R.layout.activity_tenant_inbox);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
         name = sharedPreferences.getString("Flat", "default value");
        recyclerView = (RecyclerView) findViewById(R.id.inbox_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new InboxAdapter(this, notificationList);
        recyclerView.setAdapter(mAdapter);


    }
    @SuppressLint("InflateParams")
    public void showNotificationMessageDialog(final Notification notification) {

        AlertDialog.Builder builderInner = new AlertDialog.Builder(this);
        builderInner.setTitle("Message");
        final TextView inboxMessageEditText =new TextView(this);
        String val=notification.getMessage().toString();
        inboxMessageEditText.setText(val);
        builderInner.setView(inboxMessageEditText);
        builderInner.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {
                        dialog.dismiss();


                    }
                });

        if (notification!= null) {
            builderInner.setNeutralButton(R.string.remove, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showDeleteConfirmationDialog(notification);
                }
            });
        }
        builderInner.show();
    }
    private void showDeleteConfirmationDialog(final Notification notification) {
        DialogFragment df = new DialogFragment() {
            @Override
            @NonNull
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.remove_question_format_repair));
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.firebaseRemove(notification);
                        dismiss();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                return builder.create();
            }
        };
        df.show(this.getSupportFragmentManager(), "confirm");
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
