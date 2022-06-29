package TCP_UDP;

public class ServerUDP_Test {
    public static void main(String[] args) throws Exception {
        StoreServerUDP server=new StoreServerUDP(3000);
        server.start();
    }
}
