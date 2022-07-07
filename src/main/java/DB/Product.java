package DB;

import lombok.Data;

@Data

public class Product {
    private Integer id;
    private String name;
    private double price;
    private double amount;

    public Product(String name, double price, double amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

}
