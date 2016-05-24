package com.example.piyushravi.finalapartment.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.piyushravi.finalapartment.Constants;
import com.example.piyushravi.finalapartment.Fragments.ParkingRequestManage;
import com.example.piyushravi.finalapartment.Fragments.RepairRequestsManage;
import com.example.piyushravi.finalapartment.R;
import com.example.piyushravi.finalapartment.models.Complaint;
import com.example.piyushravi.finalapartment.models.Parking;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class ParkingRequestAdapter extends RecyclerView.Adapter<ParkingRequestAdapter.ViewHolder> {

    private ArrayList<Parking> parkingList = new ArrayList<>();
    private Firebase parkingRef;
    private Parking parking;
    private final ParkingRequestManage mParking;

    public ParkingRequestAdapter(ParkingRequestManage tList, ArrayList<Parking> parkingList) {
        mParking=tList;
        this.parkingList = parkingList;
        parkingRef = new Firebase(Constants.PARKING_URL);
        parkingRef.addChildEventListener(new ParkingChildEventListener());

        }
    @Override
    public ParkingRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_tenant, parent, false);
        return new ViewHolder(v);
        }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
final Parking parking = parkingList.get(position);
        String message = parking.getMessage();
        String flatno=parking.getNumber();
        holder.aptTxt.setText(flatno);
        holder.repairArea.setText(message);
        }

    public void firebaseRemove(Parking parkingToRemove) {
        parkingRef.child(parkingToRemove.getKey()).removeValue();
        }

    @Override
    public int getItemCount() {
        return parkingList.size();
        }



    private class ParkingChildEventListener implements ChildEventListener {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        add(dataSnapshot);
        // We think using notifyItemInserted can cause crashes due to animation race condition.
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
        parking = dataSnapshot.getValue(Parking.class);
        parking.setKey(dataSnapshot.getKey());
        parkingList.add(parking);

    }
    private void remove(String key) {
        for (Parking s : parkingList) {
            if (s.getKey().equals(key)) {
                parkingList.remove(s);
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
        parking = parkingList.get(getAdapterPosition());
        mParking.showParkingRequestDialog(parking);
        return true;
    }
}
}