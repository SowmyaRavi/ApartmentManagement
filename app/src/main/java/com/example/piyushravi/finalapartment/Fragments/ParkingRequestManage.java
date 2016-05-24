package com.example.piyushravi.finalapartment.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.piyushravi.finalapartment.Adapters.ParkingRequestAdapter;
import com.example.piyushravi.finalapartment.Adapters.RepairManageAdapter;
import com.example.piyushravi.finalapartment.R;
import com.example.piyushravi.finalapartment.models.Complaint;
import com.example.piyushravi.finalapartment.models.Parking;

import java.util.ArrayList;


public class ParkingRequestManage extends Fragment {

    private ParkingRequestAdapter mAdapter;
    ArrayList<Parking> parkingList = new ArrayList<>();
    RecyclerView recyclerView;

    public ParkingRequestManage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_complaint_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.complaint_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        mAdapter = new ParkingRequestAdapter(this,parkingList);
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }
    @SuppressLint("InflateParams")
    public void showParkingRequestDialog(final Parking parking) {

        AlertDialog.Builder builderInner = new AlertDialog.Builder(getActivity());
        builderInner.setTitle("Parking Request");
        final TextView parkingEditText =new TextView(getActivity());
        String val=parking.getMessage().toString();
        parkingEditText.setText(val);

        builderInner.setView(parkingEditText);
        builderInner.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {
                        dialog.dismiss();


                    }
                });

        if (parking != null) {
            builderInner.setNeutralButton(R.string.remove, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showDeleteConfirmationDialog(parking);
                }
            });
        }
        builderInner.show();
    }
    private void showDeleteConfirmationDialog(final Parking parking) {
        DialogFragment df = new DialogFragment() {
            @Override
            @NonNull
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.remove_question_format_repair));
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.firebaseRemove(parking);
                        dismiss();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                return builder.create();
            }
        };
        df.show(getActivity().getSupportFragmentManager(), "confirm");
    }
}
