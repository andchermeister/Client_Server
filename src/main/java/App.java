public class App
{
    public static void main(String[] args) throws Exception {

        Message message = new Message(2,17, "Hello_World");
        Packet packet = new Packet((byte) 1, (long)2,message);
        byte[] encoded =  packet.encodePackage();
        System.out.println("initial packet : " + packet);

        Packet decoded = new Packet(encoded);
        System.out.println("decoded packet : " + decoded);

    }
}
