package com.example.piyushravi.finalapartment.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.piyushravi.finalapartment.Constants;
import com.example.piyushravi.finalapartment.Fragments.RepairRequestsManage;
import com.example.piyushravi.finalapartment.R;
import com.example.piyushravi.finalapartment.models.Complaint;
import com.example.piyushravi.finalapartment.models.Parking;
import com.example.piyushravi.finalapartment.models.Tenant;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;


public class RepairManageAdapter extends RecyclerView.Adapter<RepairManageAdapter.ViewHolder> {

    private ArrayList<Complaint> complaintList = new ArrayList<>();
    private Firebase complaintRef;
    private Complaint complaint;
    private final RepairRequestsManage mRepair;


    public RepairManageAdapter(RepairRequestsManage tList, ArrayList<Complaint> complaintList) {
        mRepair=tList;
        this.complaintList = complaintList;
        complaintRef = new Firebase(Constants.COMPLAINT_URL);
        complaintRef.addChildEventListener(new ComplaintChildEventListener());

    }

    @Override
    public RepairManageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_tenant, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Complaint complaint = complaintList.get(position);
        String message = complaint.getReportType();
        String flatno=complaint.getNumber();
        holder.aptTxt.setText(flatno);
        holder.repairArea.setText(message);
    }

    public void firebaseRemove(Complaint requestToRemove) {
        complaintRef.child(requestToRemove.getKey()).removeValue();
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    private class ComplaintChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            remove(dataSnapshot.getKey());
            add(dataSnapshot);
            notifyDataSetChanged();

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            remove(dataSnapshot.getKey());
            notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            Log.e(Constants.TAG, "Error: " + firebaseError.getMessage());
        }
        private void add(DataSnapshot dataSnapshot) {
            complaint = dataSnapshot.getValue(Complaint.class);
            complaint.setKey(dataSnapshot.getKey());
            complaintList.add(complaint);

        }
        private void remove(String key) {
            for (Complaint s : complaintList) {
                if (s.getKey().equals(key)) {
                    complaintList.remove(s);
                    break;
                }
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView aptTxt;
        private TextView repairArea;


        public ViewHolder(View itemView) {
            super(itemView);
            aptTxt=(TextView) itemView.findViewById(R.id.type_text_view);
            repairArea = (TextView) itemView.findViewById(R.id.type_name);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View v) {
            complaint = complaintList.get(getAdapterPosition());
            mRepair.showRepairRequestDialog(complaint);
            return true;
        }
    }
}