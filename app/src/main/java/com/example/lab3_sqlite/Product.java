package com.example.lab3_sqlite;

public class Product {
    private int ID;
    private String ProductName;
    public  double ProductPrice;

    public Product(){
    }

    public Product(String productName, double productPrice) {
        ProductName = productName;
        ProductPrice = productPrice;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }
}
