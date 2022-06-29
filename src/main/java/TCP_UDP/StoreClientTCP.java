package TCP_UDP;

import lab2.Message;
import lab2.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class StoreClientTCP {

    InetAddress host = InetAddress.getLocalHost();
    Socket socket = new Socket(host.getHostName(), 1336);;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    byte clientID;

    public StoreClientTCP(byte clientID) throws IOException {
        this.clientID = clientID;
    }

    public StoreClientTCP() throws IOException {
    }

    public void send(String message) throws Exception {
        Packet response = new Packet(clientID, 120L, new Message(1, 2, message));
        byte[] responseBytes = response.encodePackage();
        oos = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Sending request to Socket Server");

        oos.writeObject(responseBytes);
    }


    public Packet receive() throws Exception {

        ois = new ObjectInputStream(socket.getInputStream());
        byte[] messagebytes = (byte[]) ois.readObject();
        System.out.println("Message: " + messagebytes);


        return new Packet(messagebytes);

    }

}
