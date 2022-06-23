import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    public static void main(String[] args) {
        int BOUND = 10;
        int N_PRODUCERS = 4;
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
        Packet stop = new Packet((byte)1, new Message(3,9,"End of thread"));
        byte[] myPacket = stop.encodePackage();
        int myPacketPerProducer = 1;
        int mod = N_CONSUMERS % N_PRODUCERS;

        BlockingQueue<byte[]> packetsToDecrypt = new LinkedBlockingQueue<>(BOUND);
        BlockingQueue<Packet> packets = new LinkedBlockingQueue<>(BOUND);
        BlockingQueue<Packet> packetsAnswer = new LinkedBlockingQueue<>(BOUND);
        BlockingQueue<byte[]> encryptedPackets = new LinkedBlockingQueue<>(BOUND);

        for (int i = 1; i < N_PRODUCERS; i++) {
            new Thread(new PacketGenerator(packetsToDecrypt, myPacket, myPacketPerProducer)).start();
        }
        for (int i = 1; i < N_PRODUCERS; i++) {
            new Thread(new Decryptor( myPacket, myPacketPerProducer, packetsToDecrypt, packets)).start();
        }
        for (int i = 1; i < N_PRODUCERS; i++) {
            new Thread(new RecieverClass(packetsAnswer, packets, stop)).start();
        }
        for (int i = 1; i < N_PRODUCERS; i++) {
            new Thread(new Encryptor(packetsAnswer, encryptedPackets, myPacket, stop, myPacketPerProducer)).start();
        }
        for (int i = 1; i < N_PRODUCERS; i++) {
            new Thread(new Sender(encryptedPackets, stop)).start();
        }

    }
}
