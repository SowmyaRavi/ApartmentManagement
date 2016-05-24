package com.example.piyushravi.finalapartment;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PayRent extends AppCompatActivity {

    EditText cardNumber;EditText cardExpireMonth;
    EditText cardExpireYear;
    EditText cardCvv;
    EditText name;
    Button payment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.payment);
        setContentView(R.layout.activity_pay_rent);
        cardNumber =(EditText)findViewById(R.id.cardNumberTextEdit);
        cardExpireMonth =(EditText)findViewById(R.id.cardMonthEditText);

        cardExpireYear =(EditText)findViewById(R.id.cardYearEditText);
        cardCvv =(EditText)findViewById(R.id.verificationEditText);
        name =(EditText)findViewById(R.id.cardHolderEditText);

        payment=(Button)findViewById(R.id.payButton);

        payment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String num=cardNumber.getText().toString();
                String cvv=cardCvv.getText().toString();
                String month=cardExpireMonth.getText().toString();
                String year=cardExpireYear.getText().toString();
                String userName=name.getText().toString();
                if(num.length()==16){
                    if(month.length()==2){
                        if(year.length()==2){
                            if(cvv.length()==3){
                                if(userName.length()>3){
                                    Toast.makeText(getApplicationContext(), "Your payment was successfull!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Enter valid name", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(getApplicationContext(), "Enter cvv", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "Enter valid year", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Enter valid month", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Enter valid card number", Toast.LENGTH_SHORT).show();
                }

            }
        });


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
