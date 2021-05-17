package com.midterm.plantsfirebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.midterm.plantsfirebase1.Model.User;

public class MainActivity extends AppCompatActivity {
    Button btnSignup, btnSignin,btnForgot;
    FirebaseDatabase database;
    DatabaseReference table_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignin = findViewById(R.id.btn_signin);
        btnSignup = findViewById(R.id.btn_sigup);
        btnForgot = findViewById(R.id.btn_fogotpass);
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowForgotPwdDialog();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this,SignUp.class);
                startActivity(signup);
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(MainActivity.this,SignIn.class);
                startActivity(signIn);
            }
        });
    }

    private void ShowForgotPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");
        builder.setMessage("Enter your secure code");

        LayoutInflater inflater = this.getLayoutInflater();
        View Forgot_view = inflater.inflate(R.layout.forgot_password_layout,null);

        builder.setView(Forgot_view);
        builder.setIcon(R.drawable.ic_baseline_security_24);

        EditText edtPhone = (EditText) Forgot_view.findViewById(R.id.edt_phoneForgot);
        EditText edtCode = (EditText) Forgot_view.findViewById(R.id.edt_secureCodeForgot);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.child(edtPhone.getText().toString()).getValue(User.class);

                        if (user.getSecureCode().equals(edtCode.getText().toString()))
                            Toast.makeText(MainActivity.this, "Your Password is : " + user.getPassword(), Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Wrong secure code ! ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}