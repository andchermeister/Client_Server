package TCP_UDP;

import lab2.ClientPacket;
import lab2.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class StoreServerTCP implements Serializable {
    public static final Queue<ClientPacket> RESPONSES = new ConcurrentLinkedQueue<>();
    private final ConcurrentMap<Byte, ClientHandler> clients = new ConcurrentHashMap<>();
    ServerSocket serverSocket;
    Socket socket;
    private static int port = 1336;

    public StoreServerTCP() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Connected");
    }

    public void start() {

        new Thread(() -> {
            try
            {
                receiveReply();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }, "Server receiver ").start();

    }

    public void send() throws IOException, ClassNotFoundException {


        while (true) {
            try
            {

                ClientPacket packet = RESPONSES.poll();
                if (packet != null) {
                    {
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(packet.getPacket());
                    }
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    public void receiveReply() throws Exception {
        while (true) {
            System.out.println("Waiting for the client request");

            socket = serverSocket.accept();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            byte[] messagebytes = (byte[]) ois.readObject();
            System.out.println("Message Received: " + messagebytes);
            Packet pack = new Packet(messagebytes);

            clients.computeIfAbsent(pack.bSrc, clientID -> new ClientHandler()).accept(pack);
            ClientPacket packet = RESPONSES.poll();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            if (packet != null) {
                {
                    oos.writeObject(packet.getPacket());
                }
                socket.close();
            }
        }
    }
}

