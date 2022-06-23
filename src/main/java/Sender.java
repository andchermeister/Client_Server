import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;

public class Sender implements  Runnable{

    private BlockingQueue<byte[]> encryptedPackets;

    private Packet myPacket;
    public Sender(BlockingQueue<byte[]> encryptedPackets,Packet myPacket){
        this.encryptedPackets = encryptedPackets;
        this.myPacket = myPacket;
    }
    public String CheckIfSended(Packet packet) {

        return packet.bMsg.message;
    }

    @SneakyThrows
    @Override
    public void run() {
        Packet packet = new Packet(encryptedPackets.take());
        while (!packet.equals(myPacket)) {
            try {
                System.out.println(CheckIfSended(packet));
                packet = new Packet(encryptedPackets.take());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Thread.currentThread().interrupt();
    }
}
