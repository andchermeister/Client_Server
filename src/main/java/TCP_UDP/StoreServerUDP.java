package TCP_UDP;

import lab2.ClientPacket;
import lab2.Packet;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class StoreServerUDP {
    private final DatagramSocket socket;
    public static final Queue<ClientPacket> RESPONSES =new ConcurrentLinkedQueue<>();
    private final ConcurrentMap<Byte, ClientHandler> clients=new ConcurrentHashMap<>();
    private final ConcurrentMap<Byte, SocketAddress> clientsAddress=new ConcurrentHashMap<>();
    public StoreServerUDP(int port) throws SocketException {
        socket=new DatagramSocket(port);
    }


    public void start() {

        new Thread(() -> {
            try {
                receive();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                sender();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void sender(){
        while(true){
            try {
                ClientPacket packet = RESPONSES.poll();
                if (packet != null){
                    byte[] bytes=packet.getPacket().encodePackage();
                    DatagramPacket dataPack=new DatagramPacket(bytes, bytes.length, clientsAddress.get(packet.getClientId()));
                    socket.send(dataPack);
                }
            }catch (Exception e){e.printStackTrace();}
        }

    }
    public void receive()  {
        while (true) {
            try {
                DatagramPacket dataPacket = new DatagramPacket(new byte[256], 256);
                socket.receive(dataPacket);
                Packet request = new Packet(Arrays.copyOfRange(dataPacket.getData(), 0, dataPacket.getLength()));
                clients.computeIfAbsent(request.getBSrc(), clientID -> new ClientHandler()).accept(request);
                clientsAddress.put(request.getBSrc(), dataPacket.getSocketAddress());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
