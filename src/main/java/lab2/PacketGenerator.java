package lab2;

import lab2.Message;
import lab2.Packet;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class PacketGenerator implements Runnable {

    private BlockingQueue<byte[] > packets;
    private final byte[] myPacket;
    private final int myPacketPerProducer;

    PacketGenerator(BlockingQueue<byte[]> packets, byte[] myPacket, int myPacketPerProducer){
        this.packets = packets;
        this.myPacket = myPacket;
        this.myPacketPerProducer = myPacketPerProducer;
    }
    public static byte[] generate()  {

        Random myRandom = new Random();
        int commandNumber = myRandom.nextInt(Message.commandTypes.values().length);
        String messageBody = Message.commandTypes.values()[myRandom.nextInt(6)].toString();
        Message message = new Message(commandNumber,2,messageBody);
        return new Packet((byte)3,message).encodePackage();

    }


    @SneakyThrows
    @Override
    public void run() {

        for (int i = 0; i < 4; i++) {
            try {
                packets.put(generate());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        for (int j = 0; j < myPacketPerProducer; j++) {
            packets.put(myPacket);

        }
    }
}
