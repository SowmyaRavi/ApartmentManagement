package com.example.piyushravi.finalapartment.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.piyushravi.finalapartment.Adapters.RepairManageAdapter;
import com.example.piyushravi.finalapartment.R;
import com.example.piyushravi.finalapartment.models.Complaint;



import java.util.ArrayList;

public class RepairRequestsManage extends Fragment {

    private RepairManageAdapter mAdapter;
    ArrayList<Complaint> complaintList = new ArrayList<>();
    RecyclerView recyclerView;

    public RepairRequestsManage() {
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
        mAdapter = new RepairManageAdapter(this,complaintList);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }
    @SuppressLint("InflateParams")
    public void showRepairRequestDialog(final Complaint complaint) {

                AlertDialog.Builder builderInner = new AlertDialog.Builder(getActivity());
                builderInner.setTitle("Repair Description");
                final TextView repairMessageEditText =new TextView(getActivity());
        String val=complaint.getMessage().toString();
        repairMessageEditText.setText(val);

                builderInner.setView(repairMessageEditText);
                builderInner.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
                                dialog.dismiss();


                            }
                        });

        if (complaint != null) {
            builderInner.setNeutralButton(R.string.remove, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showDeleteConfirmationDialog(complaint);
                }
            });
        }
        builderInner.show();
    }
    private void showDeleteConfirmationDialog(final Complaint complaint) {
        DialogFragment df = new DialogFragment() {
            @Override
            @NonNull
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.remove_question_format_repair));
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.firebaseRemove(complaint);
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
