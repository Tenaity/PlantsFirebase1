package com.midterm.plantsfirebase1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.midterm.plantsfirebase1.Commom.Commom;
import com.midterm.plantsfirebase1.Database.Database;
import com.midterm.plantsfirebase1.Model.Order;
import com.midterm.plantsfirebase1.Model.Request;
import com.midterm.plantsfirebase1.ViewHolder.CartAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView mRvCart;
    FirebaseDatabase database;
    DatabaseReference requests;
    TextView mTvTotal;
    Button mBtnOrder;

    List<Order> cart = new ArrayList<>();
    CartAdapter mCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("request");

        //Init View
        mRvCart = findViewById(R.id.recyler_cart);
        mRvCart.setHasFixedSize(true);
        mRvCart.setLayoutManager(new LinearLayoutManager(this));

        mTvTotal = findViewById(R.id.tv_total);
        mBtnOrder = findViewById(R.id.btn_confirmOrder);

        mBtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        loadListCart();

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Đặt hàng! ");
        alertDialog.setMessage("Nhập địa chỉ: ");

        final EditText mEdtAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        mEdtAddress.setLayoutParams(layoutParams);
        alertDialog.setView(mEdtAddress);
        alertDialog.setIcon(R.drawable.ic_cart);

        alertDialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        Commom.currentUser.getName(),
                        Commom.currentUser.getPhone(),
                        mEdtAddress.getText().toString(),
                        mTvTotal.getText().toString(),
                        cart
                );
                //Submit to Firebase
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                //Delete Cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Cảm ơn, hẹn gặp lại!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();


    }

    private void loadListCart() {
        cart = new Database(this).getCarts();
        mCartAdapter = new CartAdapter(cart);

        mRvCart.setAdapter(mCartAdapter);

        //Caculate total price
        int total = 0;
        for(Order order:cart)
            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));

        Locale locale = new Locale("vi","VN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        mTvTotal.setText(fmt.format(total));
    }
}