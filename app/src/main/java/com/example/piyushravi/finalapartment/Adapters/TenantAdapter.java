package com.example.piyushravi.finalapartment.Adapters;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.piyushravi.finalapartment.Constants;
import com.example.piyushravi.finalapartment.Fragments.ManageTenantList;
import com.example.piyushravi.finalapartment.R;

import com.example.piyushravi.finalapartment.models.Tenant;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


import java.util.ArrayList;



public class TenantAdapter extends RecyclerView.Adapter<TenantAdapter.ViewHolder> {

    private ArrayList<Tenant> tenant = new ArrayList<>();
    private Firebase ref;
    private Tenant ten;
    private final  ManageTenantList mTenantList;


    public TenantAdapter(ManageTenantList tList,ArrayList<Tenant> tenant) {
        mTenantList=tList;
        this.tenant = tenant;
        ref = new Firebase(Constants.TENANT_URL);
        ref.addChildEventListener(new TenantChildEventListener());
    }

    @Override
    public TenantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_tenant, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TenantAdapter.ViewHolder holder, int position) {

        final Tenant tenantInfo = tenant.get(position);
        String flatno = tenantInfo.getFlatno();
        String name= tenantInfo.getFirstName()+" "+tenantInfo.getLastName();
        holder.aptTxt.setText(flatno);
        holder.nameTxt.setText(name);

    }

    @Override
    public int getItemCount() {
        return tenant.size();
    }

    public void firebaseRemove(Tenant tenantToRemove) {
        ref.child(tenantToRemove.getKey()).removeValue();
    }

    // For swipe to delete
    public Tenant hide(int position) {
        Tenant student = tenant.remove(position);
        notifyItemRemoved(position);
        return student;
    }

    public void undoHide(Tenant student, int position) {
        tenant.add(position, student);
        notifyItemInserted(position);
    }



    private class TenantChildEventListener implements ChildEventListener {
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
            ten = dataSnapshot.getValue(Tenant.class);
            ten.setKey(dataSnapshot.getKey());
            tenant.add(ten);

        }
        private void remove(String key) {
            for (Tenant s : tenant) {
                if (s.getKey().equals(key)) {
                    tenant.remove(s);
                    break;
                }
            }
        }
    }


    public void firebaseEdit(Tenant tenant, String flatno, String firstName, String lastName, String email,String password,String rent) {
        tenant.setFlatno(flatno);
        tenant.setFirstName(firstName);
        tenant.setLastName(lastName);
        tenant.setEmail(email);
        tenant.setPassword(password);
        tenant.setRent(rent);
        ref.child(tenant.getKey()).setValue(tenant);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView aptTxt;
        private TextView nameTxt;


        public ViewHolder(View itemView) {
            super(itemView);
            aptTxt=(TextView) itemView.findViewById(R.id.type_text_view);
            nameTxt = (TextView) itemView.findViewById(R.id.type_name);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View v) {
            Tenant ten1 = tenant.get(getAdapterPosition());
            mTenantList.showTenantDialog(ten1);
            return true;
        }
    }
}
