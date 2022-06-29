package lab2;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class Decryptor implements Runnable{

    private final byte[] myPacket;
    private int myPacketPerProducer;
    private BlockingQueue<byte[]> packetsToDecrypt;
    private BlockingQueue<Packet> decryptedPackets;

    public Decryptor(byte[] myPacket,int myPacketPerProducer,BlockingQueue<byte[]> packetsToDecrypt, BlockingQueue<Packet> decryptedPackets){
        this.myPacket = myPacket;
        this.myPacketPerProducer = myPacketPerProducer;
        this.packetsToDecrypt = packetsToDecrypt;
        this.decryptedPackets = decryptedPackets;

    }
    public static byte[] decrypt(byte[] encryptedData) throws Exception {

        Cipher c = Cipher.getInstance(Encryptor.getALGO());
        c.init(Cipher.DECRYPT_MODE, Encryptor.getKey());
        byte[] decValue = c.doFinal(encryptedData);
        return decValue;
    }

    @Override
    public void run() {
        byte[] arr = new byte[0];
        try {
            arr = packetsToDecrypt.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!Arrays.equals(arr, myPacket)) {
            try {
                decryptedPackets.put(new Packet(arr));
                arr =  packetsToDecrypt.take();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            decryptedPackets.put(new Packet(myPacket));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }
}
