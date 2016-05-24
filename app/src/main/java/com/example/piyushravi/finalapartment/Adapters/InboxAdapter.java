package com.example.piyushravi.finalapartment.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.piyushravi.finalapartment.Constants;
import com.example.piyushravi.finalapartment.Fragments.ParkingRequestManage;
import com.example.piyushravi.finalapartment.R;
import com.example.piyushravi.finalapartment.TenantInbox;
import com.example.piyushravi.finalapartment.models.Notification;
import com.example.piyushravi.finalapartment.models.Parking;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;


public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    private ArrayList<Notification> notificationList = new ArrayList<>();
    private Firebase notificationRef;
    private Notification notification;
    private final TenantInbox mInbox;

    public InboxAdapter(TenantInbox tList, ArrayList<Notification> notificationList) {
        mInbox=tList;
        this.notificationList = notificationList;
        String flatno= mInbox.name;
        notificationRef = new Firebase(Constants.NOTIFICATION_URL+ "/" +flatno);
        notificationRef.addChildEventListener(new NotificationChildEventListener());
    }

    @Override
    public InboxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_tenant, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

            final Notification notification = notificationList.get(position);
            String message = notification.getMessage();
            String from=notification.getFrom();
            holder.fromTxt.setText(from);
            holder.messageArea.setText(message);

    }

    public void firebaseRemove(Notification notificationToRemove) {
        notificationRef.child(notificationToRemove.getKey()).removeValue();
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
    private class NotificationChildEventListener implements ChildEventListener {
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
            notification = dataSnapshot.getValue(Notification.class);
           notification.setKey(dataSnapshot.getKey());
           notificationList.add(notification);

        }
        private void remove(String key) {
            for (Notification s : notificationList) {
                if (s.getKey().equals(key)) {
                   notificationList.remove(s);
                    break;
                }
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView fromTxt;
        private TextView messageArea;


        public ViewHolder(View itemView) {
            super(itemView);
            fromTxt=(TextView) itemView.findViewById(R.id.type_text_view);
            messageArea = (TextView) itemView.findViewById(R.id.type_name);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View v) {
            notification = notificationList.get(getAdapterPosition());
            mInbox.showNotificationMessageDialog(notification);
            return true;
        }
    }
}