package DB;

public class DB_main {
    public static void main(String[] args) {

        DB_Queries comm = new DB_Queries();
        comm.Create();
        comm.DeleteAll();

        Product product1 = comm.Insert(new Product("Coca-Cola", 37, 17));
        System.out.println(comm.Read());

        System.out.println("-----------------------------------------------------");
        Product product2 = comm.Insert(new Product("Pringles", 96, 30));
        System.out.println(comm.Read());

        System.out.println("-----------------------------------------------------");
        comm.Delete(product1);
        System.out.println(comm.Read());
        comm.updatePrice("Pringles", 98);

        System.out.println("-----------------------------------------------------");
        comm.listByPrice(">40");
        System.out.println(comm.Read());
    }
}
