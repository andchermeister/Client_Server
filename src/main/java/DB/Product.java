package DB;

import lombok.Data;

@Data

public class Product {
    private Integer id;
    private String name;
    private double price;
    private double amount;

    public Product(){

    }

    public Product(String name, double price, double amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Product(int id, String name, double price, double amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
