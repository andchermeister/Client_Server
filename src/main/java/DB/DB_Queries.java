package DB;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB_Queries {
    private Connection con;
    public void Create()  {
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:HelloDB");
            PreparedStatement st = con.prepareStatement("create table if not exists 'product' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'price' double, 'amount' double );");
            st.executeUpdate();
        }catch(ClassNotFoundException e){
            System.out.println("JDBC driver not found");
            e.printStackTrace();
            System.exit(0);
        }
        catch(SQLException e){
            System.out.println("SQL query is incorrect");
            e.printStackTrace();
        }
    }
    public Product Insert(Product product){
        try{
            PreparedStatement statement=con.prepareStatement("INSERT INTO product(name, price, amount) VALUES (?,?,?)");

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setDouble(3, product.getAmount());

            statement.executeUpdate();
            statement.close();

            ResultSet setRes = statement.getGeneratedKeys();
            product.setId(setRes.getInt("last_insert_rowid()"));


        }
        catch(SQLException e){
            System.out.println("insert SQL query is incorrect");
            e.printStackTrace();
        }
        return product;
    }
    public List<Product> Read(){
        List<Product> productList = new ArrayList<>();
        Product product;
        try{
            Statement statement = con.createStatement();
            ResultSet setRes = statement.executeQuery("SELECT * FROM product");

            while (setRes.next()) {
                product = new Product(setRes.getString(2), setRes.getDouble(3),  setRes.getDouble(4));
                product.setId((setRes.getInt(1)));
                productList.add(product);
            }
            setRes.close();
            statement.close();
        }
        catch(SQLException e){
            System.out.println("select SQL query is incorrect");
            e.printStackTrace();
        }
        return productList;
    }
    public void updatePrice(String name_product, double newPrice){
        try{

            System.out.println();
            PreparedStatement statement = con.prepareStatement("UPDATE product SET price = ? WHERE name = ?");
            statement.setDouble(1, newPrice );
            statement.setString(2, name_product );

            statement.executeUpdate();
            statement.close();


        }
        catch (SQLException e){
            System.out.println("update set price SQL query is incorrect");
            e.printStackTrace();
        }

    }
    public void updateAmount(String name_product,  double newAmount){
        try{

            System.out.println();
            PreparedStatement statement = con.prepareStatement("UPDATE product SET amount = ? WHERE name = ?");

            statement.setDouble(1, newAmount );
            statement.setString(2, name_product );

            statement.executeUpdate();
            statement.close();

        }
        catch (SQLException e){
            System.out.println("update set amount SQL query is incorrect");
            e.printStackTrace();
        }

    }
    public void Delete(Product product){
        try {
            PreparedStatement statement = con.prepareStatement("DELETE FROM product WHERE id=?");
            statement.setInt(1, product.getId());
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("delete on condition SQL query is incorrect");
            e.printStackTrace();
            throw new RuntimeException("Bidosya",e);
        }
    }
    public void DeleteAll(){
        try {
            Statement st=con.createStatement();
            st.executeUpdate("DELETE FROM product");
        }
        catch (SQLException e){
            System.out.println("delete all SQL query is incorrect");
            e.printStackTrace();
            throw new RuntimeException("Bidosya",e);
        }
    }

    public List<Product> listByName(String prodName){
        List<Product> productList = new ArrayList<>();
        Product product;
        try{
            PreparedStatement statement = con.prepareStatement("SELECT * FROM product WHERE name=?");
            statement.setString(1, prodName);
            ResultSet setRes = statement.executeQuery();

            while (setRes.next()) {
                product = new Product(setRes.getString(2), setRes.getDouble(3), setRes.getDouble(4));
                product.setId((setRes.getInt(1)));
                productList.add(product);
            }
            setRes.close();
        }
        catch(SQLException e){
            System.out.println("select by name SQL query is incorrect");
            e.printStackTrace();
        }
        return productList;
    }
    public List<Product> listByPrice(String price){
        List<Product> productList = new ArrayList<>();
        Product product;
        try{
            PreparedStatement statement = con.prepareStatement("SELECT * FROM product WHERE price " + price);
            ResultSet setRes = statement.executeQuery();
            while (setRes.next()) {
                product = new Product(setRes.getString(2), setRes.getDouble(3), setRes.getDouble(4));
                product.setId((setRes.getInt(1)));
                productList.add(product);
            }
            setRes.close();
        }
        catch(SQLException e){
            System.out.println("select by price SQL query is incorrect");
            e.printStackTrace();
        }
        return productList;
    }
}
