package DB;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DB_Queries_Test {
    @Test
    void testInsertQueries(){
        DB_Queries comm=new DB_Queries();
        comm.Create();
        int size=comm.Read().size();
        Product prod=comm.Insert(new Product("Coca-Cola", 37, 17));
        assertEquals(size,comm.Read().size()-1);
    }
    @Test
    void testDeleteQueries(){
        DB_Queries comm=new DB_Queries();
        comm.Create();
        comm.DeleteAll();
        Product product1 = comm.Insert(new Product("Coca-Cola", 37, 17));
        Product product2 = comm.Insert(new Product("Revo", 42, 50));
        Product product3 = comm.Insert(new Product("Marlboro", 68, 70));
        Product product4 = comm.Insert(new Product("Pringles", 96, 30));
        Product product5 = comm.Insert(new Product("ElfBar", 280, 112));
        comm.Delete(product1);
        comm.Delete(product4);
        assertEquals(3,comm.Read().size());
        comm.DeleteAll();
    }
    @Test
    void testCriteriaQueries(){
        DB_Queries comm=new DB_Queries();
        comm.Create();
        comm.DeleteAll();
        comm.Insert(new Product("Coca-Cola", 37, 17));
        comm.Insert(new Product("Revo", 42, 50));
        comm.Insert(new Product("ElfBar", 280, 112));
        comm.Insert(new Product("ElfBar", 315, 67));
        comm.Insert(new Product("ElfBar", 245, 34));
        List<Product> res=comm.listByName("ElfBar");
        assertEquals(3, res.size());
        comm.DeleteAll();
    }
    @Test
    void testCriteriaPriceQueries(){
        DB_Queries comm=new DB_Queries();
        comm.Create();
        comm.DeleteAll();
        comm.Insert(new Product("Coca-Cola", 37, 17));
        comm.Insert(new Product("Revo", 42, 50));
        comm.Insert(new Product("ElfBar", 280, 112));
        comm.Insert(new Product("ElfBar", 315, 67));
        comm.Insert(new Product("ElfBar", 245, 34));
        List<Product> res=comm.listByPrice("<250");
        assertEquals(3, res.size() );
        comm.DeleteAll();
    }
    @Test
    void testUpdatePriceQueries(){
        DB_Queries comm=new DB_Queries();
        comm.Create();
        comm.DeleteAll();
        comm.Insert(new Product("Coca-Cola", 37, 17));
        comm.Insert(new Product("Revo", 42, 50));
        comm.Insert(new Product("ElfBar", 280, 112));
        comm.updatePrice("Coca-Cola", 39);
        List<Product> res=comm.listByPrice("=39");
        assertEquals(1, res.size() );
        comm.DeleteAll();
    }
    @Test
    void testUpdateAmount(){
        DB_Queries comm=new DB_Queries();
        comm.Create();
        comm.DeleteAll();
        comm.Insert(new Product("Coca-Cola", 37, 17));
        comm.Insert(new Product("Revo", 42, 50));
        comm.Insert(new Product("ElfBar", 280, 112));
        comm.updateAmount("Revo", 45);
        List<Product> res=comm.listByName("Revo");
        assertEquals(res.get(0).getAmount(), 45 );
        comm.DeleteAll();
    }
}
