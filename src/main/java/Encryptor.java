import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.concurrent.BlockingQueue;

public class Encryptor implements Runnable{

    private BlockingQueue<Packet> packetsAnswer;
    private BlockingQueue<byte[]> encryptedPackets;
    private final byte[] myPacket;
    private final Packet myPacket2;
    private final int myPacketPerProducer;

    public Encryptor(BlockingQueue<Packet> packetsAnswer, BlockingQueue<byte[]> encryptedPackets, byte[] myPacket, Packet myPacket2, int myPacketPerProducer){
        this.packetsAnswer = packetsAnswer;
        this.encryptedPackets = encryptedPackets;
        this.myPacket = myPacket;
        this.myPacket2 = myPacket2;
        this.myPacketPerProducer = myPacketPerProducer;
    }
    private static final String ALGO = "AES";
    private static final byte[] keyValue =
            new byte[]{'A', 'n', 'd', 'c', 'h', 'e', 'r',
                    'r', 'i', 'c', 'k', 'n', 'e', 'l', 'e', 'e'};
    private  static Key k;

    static {
        try
        {
            k = generateKey();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static byte[] encrypt(byte[] Data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, k);
            byte[] encryptVal = cipher.doFinal(Data);
            return encryptVal;
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static String getALGO(){
        return ALGO;
    }
    public static Key getKey(){
        return k;
    }

    private static Key generateKey() {
        try {
            Key k = new SecretKeySpec(keyValue, ALGO);
            return k;
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void run() {
        Packet pack = null;
        try {
            pack = packetsAnswer.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!pack.equals(myPacket2)) {
            try {
                encryptedPackets.put(pack.encodePackage());
                pack = packetsAnswer.take();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            encryptedPackets.put(myPacket);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }
}
