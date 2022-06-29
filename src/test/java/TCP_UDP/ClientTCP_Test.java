package TCP_UDP;

import lab2.Packet;

public class ClientTCP_Test {
    public static void main(String[] args) {
        for (byte i = 0; i < 10; i++) {
            client(i);
        }

    }
    private static void client(byte clientId){
        new Thread(()-> {
            StoreClientTCP client2 = null;
            try {
                client2 = new StoreClientTCP(clientId);
                client2.send("Hello from client "+ clientId);

                Packet packet= client2.receive();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        ).start();
    }}
