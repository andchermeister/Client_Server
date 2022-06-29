package TCP_UDP;

import lab2.Message;
import lab2.Packet;

import java.net.*;
import java.util.Arrays;

public class StoreClientUDP {
    private final DatagramSocket socket;
    private final byte clientID;
    static Long id=0L;

    public StoreClientUDP(byte clientID) throws SocketException {
        this.clientID = clientID;
        socket = new DatagramSocket();
    }

    public Packet sendReceive(String message) throws Exception{

        Packet response = new Packet(clientID, id, new Message(2,3,message));
        id++;
        byte[] responseBytes=response.encodePackage();
        DatagramPacket responseDatagram=new DatagramPacket(responseBytes, responseBytes.length, InetAddress.getByName(null), 3000);
        socket.send(responseDatagram);
        DatagramPacket dataPacket = new DatagramPacket(new byte[256], 256);
        socket.receive(dataPacket);
        Packet result = new Packet(Arrays.copyOfRange(dataPacket.getData(), 0, dataPacket.getLength()));
        try
        {
            while(!response.bPktId.equals(result.bPktId))
            {
                socket.send(responseDatagram);
                dataPacket = new DatagramPacket(new byte[256], 256);
                socket.receive(dataPacket);
                result=new Packet(Arrays.copyOfRange(dataPacket.getData(), 0, dataPacket.getLength()));
                Thread.sleep(10000);
            }
        }
        catch (SocketTimeoutException ex)
        {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return result;

    }
}
