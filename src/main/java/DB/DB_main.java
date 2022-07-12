package DB;

import java.sql.SQLException;

public class DB_main {
    public static void main(String[] args) throws SQLException {

        DB_Queries query = new DB_Queries();
        query.Create();
        query.DeleteAll();

        Product product1 = query.Insert(new Product("Coca-Cola", 37, 17));
        System.out.println(query.Read());

        System.out.println("-----------------------------------------------------");
        Product product2 = query.Insert(new Product("Pringles", 96, 30));
        System.out.println(query.Read());

        System.out.println("-----------------------------------------------------");
        query.Delete(product1);
        System.out.println(query.Read());
        query.updatePrice("Pringles", 98);

        System.out.println("-----------------------------------------------------");
        query.listByPrice(">40");
        System.out.println(query.Read());
        System.out.println(query.getProductByID(product2.getId()));
    }
}
