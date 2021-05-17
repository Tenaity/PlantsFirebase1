package com.midterm.plantsfirebase1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.midterm.plantsfirebase1.Commom.Commom;
import com.midterm.plantsfirebase1.Model.User;

public class  SignIn extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    Button btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = findViewById(R.id.edt_password);
        edtPhone = findViewById(R.id.edt_phone_number);
        btnSignIn = findViewById(R.id.btn_signIn);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Check if user not exist in database
                        //Get User information
                        if(snapshot.child(edtPhone.getText().toString()).exists());

                        User user = snapshot.child(edtPhone.getText().toString()).getValue(User.class);
                        user.setPhone(edtPhone.getText().toString());
                        if(user.getPassword().equals(edtPassword.getText().toString())){
                            Toast.makeText(SignIn.this,"Sign In Successfully!",Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(SignIn.this,Home.class);
                            Commom.currentUser = user;
                            startActivity(homeIntent);
                            finish();

                            table_user.removeEventListener(this);
                        }
                        else{
                            Toast.makeText(SignIn.this,"Sign In Fail!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}