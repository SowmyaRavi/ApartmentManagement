package com.example.piyushravi.finalapartment.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.piyushravi.finalapartment.Adapters.TenantAdapter;
import com.example.piyushravi.finalapartment.Constants;
import com.example.piyushravi.finalapartment.R;
import com.example.piyushravi.finalapartment.models.Tenant;
import com.firebase.client.Firebase;

import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import java.util.ArrayList;


public class ManageTenantList extends Fragment {

    private TenantAdapter mAdapter;
    ArrayList<Tenant> tenant = new ArrayList<>();
    private FloatingActionButton mFab;
    RecyclerView recyclerView;
    private Tenant mPendingDeletionTenant;

    public ManageTenantList() {
        // Required empty public constructor
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         View rootView = inflater.inflate(R.layout.fragment_tenant_list, container, false);
         mFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
         mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTenantDialog(null);

            }
        });
         recyclerView = (RecyclerView) rootView.findViewById(R.id.tenant_recycler_view);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         recyclerView.setHasFixedSize(true);
         mAdapter = new TenantAdapter(this,tenant);
         recyclerView.setAdapter(mAdapter);

         //on swipr to left or right removes tenant details from view, but UNDO can restore it.
         ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                final int position = viewHolder.getAdapterPosition();
                mPendingDeletionTenant = mAdapter.hide(position);
                final Snackbar snackbar = Snackbar
                        .make(mFab, "Tenant removed!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mAdapter.undoHide(mPendingDeletionTenant, position);
                                mPendingDeletionTenant = null;
                                Snackbar snackbarRestore = Snackbar.make(mFab, "Tenant restored!", Snackbar.LENGTH_SHORT);
                                snackbarRestore.show();
                            }
                        })
                        .setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION && event != Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE) {
                                    Log.d(Constants.TAG, "Tenant to remove: " + mPendingDeletionTenant);
                                    if (mPendingDeletionTenant != null) {
                                        mAdapter.firebaseRemove(mPendingDeletionTenant);
                                    }
                                    mPendingDeletionTenant = null;
                                }
                            }
                        });
                snackbar.setActionTextColor(ContextCompat.getColor(getContext(), R.color.Black));
                ((TextView) (snackbar.getView().findViewById(android.support.design.R.id.snackbar_text))).setTextSize(16);
                ((TextView) (snackbar.getView().findViewById(android.support.design.R.id.snackbar_action))).setTextSize(20);
                snackbar.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return rootView;
    }

    @SuppressLint("InflateParams")
    public void showTenantDialog(final Tenant tenant) {

        DialogFragment df = new DialogFragment() {
            @Override
            @NonNull
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(tenant == null ? R.string.dialog_tenant_add_title : R.string.dialog_tenant_edit_title));
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_tenant_add_edit, null);
                builder.setView(view);
                final EditText flatnoEditText=(EditText) view.findViewById(R.id.dialog_flatno);
                final EditText firstNameEditText = (EditText) view.findViewById(R.id.dialog_tenant_first_name);
                final EditText lastNameEditText = (EditText) view.findViewById(R.id.dialog_tenant_last_name);
                final EditText emailEditText = (EditText) view.findViewById(R.id.dialog_tenant_email);
                final EditText passwordEditText = (EditText) view.findViewById(R.id.dialog_tenant_password);
                final EditText rentEditText=(EditText) view.findViewById(R.id.dialog_house_rent);
                if (tenant != null) {
                    flatnoEditText.setText(tenant.getFlatno());
                    firstNameEditText.setText(tenant.getFirstName());
                    lastNameEditText.setText(tenant.getLastName());
                    emailEditText.setText(tenant.getEmail());
                    passwordEditText.setText(tenant.getPassword());
                    rentEditText.setText(tenant.getRent());

                }

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String flatno=flatnoEditText.getText().toString();
                        String firstName = firstNameEditText.getText().toString();
                        String lastName = lastNameEditText.getText().toString();
                        String email = emailEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        String rent=rentEditText.getText().toString();
                         if(email.isEmpty()||flatno.isEmpty()||firstName.isEmpty()||password.isEmpty()||rent.isEmpty()){
                             Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_LONG).show();
                         }else if (tenant == null) {
                                Firebase mRef;
                                Tenant t = new Tenant(flatno, firstName, lastName, email, password, rent);
                                mRef = new Firebase(Constants.TENANT_URL);
                                mRef.push().setValue(t);

                        } else {
                           mAdapter.firebaseEdit(tenant,flatno, firstName, lastName, email,password,rent);
                        }
                        dismiss();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                if (tenant != null) {
                    builder.setNeutralButton(R.string.remove, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showDeleteConfirmationDialog(tenant);
                        }
                    });
                }

                return builder.create();
            }
        };
        df.show(getActivity().getSupportFragmentManager(), "add_edit_tenant");
    }

    private void showDeleteConfirmationDialog(final Tenant tenant) {
        DialogFragment df = new DialogFragment() {
            @Override
            @NonNull
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.remove_question_format, tenant.getFlatno()));
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.firebaseRemove(tenant);
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
