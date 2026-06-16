package model;

public class Product {
    private int product_id;
    private String product_name;
    private String brand;
    private double price;
    private int stock;

    public Product() {
    }

    public Product(int product_id, String product_name, String brand, double price, int stock) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
