package com.midterm.plantsfirebase1;

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
import com.midterm.plantsfirebase1.Model.User;

public class SignUp extends AppCompatActivity {

    EditText edtPhone,edtName,edtPassword,edtSecureCode;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtPhone = findViewById(R.id.edt_phoneSignup);
        edtName = findViewById(R.id.edt_nameSignup);
        edtPassword = findViewById(R.id.edt_passwordSignup);
        edtSecureCode = findViewById(R.id.edt_secureCodeSignup);
        btnSignUp = findViewById(R.id.btn_signUp);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(edtPhone.getText().toString()).exists()){
                            Toast.makeText(SignUp.this,"Số điện thoại đã dùng!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            User user = new User(edtName.getText().toString(),edtPassword.getText().toString(),edtSecureCode.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this,"Đăng ký thành công!",Toast.LENGTH_SHORT).show();
                            finish();
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