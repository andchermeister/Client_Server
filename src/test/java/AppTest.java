import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
public class AppTest
{
    @Test
    public void cipherTest() throws Exception {
        String t1="Secret";
        byte[] encode= Crypto.encrypt(t1.getBytes());
        byte[] decode= Crypto.decrypt(encode);
        String res=new String(decode);
        assertEquals(res, t1);
    }

    @Test
    public void PartsEqual() throws Exception {
        Message mes = new Message(2,3, "r1ght");
        Packet pack = new Packet((byte) 1, (long)2,mes);
        byte[] encoded =  pack.encodePackage();
        Packet decode= new Packet(encoded);
        assertEquals(pack, decode);
    }




    @CsvSource({
            "19, 25, andchermeister, 3, 97",
            "666, 3, rickneelee, 9, 78900",
            "75757, 136, BFFcoolswag, 4, 234"
    })

    @ParameterizedTest
    public void PacketsEqual_param(Integer cType, Integer bUserId, String message, Byte bSrc, Long bPktId) {
        Message mes = new Message(cType,bUserId, message);
        Packet pack = new Packet(bSrc, bPktId,mes);
        byte[] encoded =  pack.encodePackage();
        Packet decode= new Packet(encoded);
        assertEquals(cType, decode.bMsg.cType);
        assertEquals(bUserId, decode.bMsg.bUserId);
        assertEquals( message,decode.bMsg.message);
        assertEquals( bPktId, decode.bPktId);
        assertEquals( bSrc, decode.bSrc);
    }
}
