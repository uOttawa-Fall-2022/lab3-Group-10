package com.example.lab3_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView ProductID;
    EditText ProductName, ProductPrice;
    Button addBtn, findBtn, deleteBtn;
    ListView productListView;

    ArrayList<String> productList;
    ArrayAdapter adapter;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = new ArrayList<>();

        ProductID = findViewById(R.id.productId);
        ProductName = findViewById(R.id.productName);
        ProductPrice = findViewById(R.id.productPrice);

        addBtn = findViewById(R.id.addBtn);
        findBtn = findViewById(R.id.findBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        productListView = findViewById(R.id.productListView);

        dbHandler = new MyDBHandler(this);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ProductName.getText().toString();
                double price = Double.parseDouble(ProductPrice.getText().toString());
                Product product = new Product(name, price);
                dbHandler.addProduct(product);
                ProductName.setText("");
                ProductPrice.setText("");
//                Toast.makeText(MainActivity.this, "Add product", Toast.LENGTH_SHORT).show();
                Cursor cursor = dbHandler.getData();
                viewProducts(cursor);
            }
        });

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Find product", Toast.LENGTH_SHORT).show();
                String name = ProductName.getText().toString();
                String price = ProductPrice.getText().toString();
                Cursor cursor;
                if(name.equals("") && price.equals("")){
                    cursor = dbHandler.getData();
                }else if(price.equals("")){
                    cursor = dbHandler.getDataByName(name);
                }else if(name.equals("")){
                    cursor = dbHandler.getDataByPrice(price);
                }else{
                    cursor = dbHandler.getDataByNameAndPrice(name, price);
                }
                viewProducts(cursor);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Delete product", Toast.LENGTH_SHORT).show();
                String name = ProductName.getText().toString();
                dbHandler.deleteProduct(name);
                Cursor cursor = dbHandler.getData();
                viewProducts(cursor);
            }
        });

        Cursor cursor = dbHandler.getData();
        viewProducts(cursor);
    }
    public void viewProducts(Cursor cursor){
        productList.clear();

        if (cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                productList.add(cursor.getString(1));
            }
        }
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, productList);
        productListView.setAdapter(adapter);
    }
}