package com.example.piyushravi.finalapartment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.piyushravi.finalapartment.models.Tenant;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    protected EditText emailEditText;
    protected EditText passwordEditText;
    protected Button loginButton;
    protected Button userLogin;
    String email,password_1;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        emailEditText = (EditText) findViewById(R.id.emailField);
        passwordEditText = (EditText) findViewById(R.id.passwordField);
        loginButton = (Button) findViewById(R.id.loginButton);
        userLogin=(Button) findViewById(R.id.userLogin);

        final Firebase ref = new Firebase(Constants.FIREBASE_URL);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                email = email.trim();
                password = password.trim();

                if (email.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(R.string.login_error_message)
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    final String emailAddress = email;

                    //Login with an email/password combination
                    ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {

                            // Authenticated successfully with payload authData
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("email", emailAddress);
                            ref.child("users").child(authData.getUid()).updateChildren(map);

                            Intent intent = new Intent(MainActivity.this, AdminScreen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            emailEditText.setText("");
                            passwordEditText.setText("");

                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            // Authenticated failed with error firebaseError
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage(firebaseError.getMessage())
                                    .setTitle(R.string.login_error_title)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }

            }});


        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref1=new Firebase(Constants.TENANT_URL);

                email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                email = email.trim();
                password_1 = password.trim();
                if (email.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(R.string.login_error_message)
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                //Getting the data from snapshot
                                Tenant info = postSnapshot.getValue(Tenant.class);

                                String email1 = info.getEmail();
                                String fname = info.getFirstName();
                                String pass = info.getPassword();
                                String flatno1 = info.getFlatno();
                                String rent=info.getRent();

                                if (email1.contains(email) && (pass.contains(password_1))) {

                                    Intent intent = new Intent(MainActivity.this, UserScreen.class);
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Flat", flatno1);
                                    editor.putString("FIRSTNAME",fname);
                                    editor.putString("RENT",rent);
                                    editor.apply();
                                    startActivity(intent);
                                    emailEditText.setText("");
                                    passwordEditText.setText("");
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("Email or Password is incorrect").setTitle(R.string.login_error_title)
                                            .setPositiveButton(android.R.string.ok, null);

                                    builder.setPositiveButton("Ok",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    builder.show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("The read failed: " + firebaseError.getMessage());
                        }
                    });
                }
            }
        });
    }
}
