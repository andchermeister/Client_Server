import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;

public class RecieverClass implements Reciever, Runnable {

    private Packet myPacket;
    private BlockingQueue<Packet> packetsAnswer;
    private BlockingQueue<Packet> packets;

    public RecieverClass(BlockingQueue<Packet> packetsAnswer, BlockingQueue<Packet> packets, Packet myPacket){
        this.myPacket = myPacket;
        this.packets = packets;
        this.packetsAnswer = packetsAnswer;
    }


    @Override
    public void recieveMessage(Packet pack) throws InterruptedException {

        Message message = new Message(pack.bMsg.cType, pack.bMsg.bUserId, "Ok");
        Packet packAnswer = new Packet(pack.bSrc, pack.bPktId, message);
        packetsAnswer.put(packAnswer);
    }

    @SneakyThrows
    @Override
    public void run() {
        Packet  pack = packets.take();
        while(!pack.equals(myPacket)) {
            try {
                recieveMessage(pack);
                pack = packets.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        packetsAnswer.put(myPacket);
        Thread.currentThread().interrupt();
    }
}
