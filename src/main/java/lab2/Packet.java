package lab2;

import lab2.CRC16;
import lab2.Decryptor;
import lab2.Encryptor;
import lab2.Message;
import lombok.Data;

import java.nio.ByteBuffer;

@Data
public class Packet {

    public final static Byte bMagic =  0x13;
    // static int bPktId;
    public Byte bSrc;
    public Long bPktId;
    protected static long fPktId=0;
    Integer wLen;
    private Short wCrc16;
    protected Message bMsg ;
    private Short wCrc16_end;


    public Packet(Byte bSrc, Message bMsg )
    {
        this.bSrc = bSrc;
        this.bMsg = bMsg;
        this.bPktId = fPktId++;

        wLen = bMsg.message.length();
    }
    public Packet(Byte bSrc,Long bPktId, Message bMsg )
    {
        this.bSrc = bSrc;
        this.bMsg = bMsg;
        this.bPktId = bPktId;

        wLen = bMsg.message.length();
    }
    public byte[] encodePackage ()
    {

        byte[] firstPart =  ByteBuffer
                .allocate(14)
                .put(bMagic)
                .put(bSrc).putLong(bPktId)
                .putInt(wLen).array();

        wCrc16 = (CRC16.crc16(firstPart));

        byte[] secondPart1 = (ByteBuffer
                .allocate( bMsg.getMessageLength())
                .put(bMsg.messageToPacket())
                .array());
        byte[] secondPart = (ByteBuffer
                .allocate( Encryptor
                        .encrypt(bMsg.messageToPacket()).length)
                .put( Encryptor
                        .encrypt(bMsg.messageToPacket()))
                .array()) ;
        wCrc16_end = CRC16.crc16(secondPart1);
        return ByteBuffer
                .allocate( firstPart.length + Integer.BYTES + secondPart.length)
                .put(firstPart)
                .putShort(wCrc16)
                .put(secondPart)
                .putShort(wCrc16_end)
                .array();
    }

    public Packet(byte[] encodedPacket)
    {
        try {

            ByteBuffer byteBuffer = ByteBuffer.wrap(encodedPacket);
            Byte bMag = byteBuffer.get();
            if (!bMag.equals(bMagic))
            {
                throw new Exception("bMagic is not correct");
            }

            bSrc = byteBuffer.get();
            bPktId = byteBuffer.getLong();
            wLen = byteBuffer.getInt();
            wCrc16 = byteBuffer.getShort();

            byte[] header = byteBuffer
                    .allocate(14)
                    .put(bMagic)
                    .put(bSrc)
                    .putLong(bPktId)
                    .putInt(wLen)
                    .array();

            if (CRC16.crc16(header) != wCrc16)
            {
                throw new IllegalArgumentException("crc16_1 is not correct");
            }

            bMsg = new Message();
            byte[] messageBytes = new byte[encodedPacket.length - 18];
            byteBuffer.get(messageBytes);
            byte[] secondPart1 = messageBytes;
            byte[] secondPart = Decryptor.decrypt(messageBytes);
            ByteBuffer encodeMessage = byteBuffer.wrap(secondPart);
            bMsg.set_cType(encodeMessage.getInt());
            bMsg.set_bUserId(encodeMessage.getInt());

            byte[] messageBody = new byte[wLen];

            try
            {
                encodeMessage.get(messageBody);
            }
            catch (Exception ex)
            {

            }

            bMsg.setMessage(new String(messageBody));
            wCrc16_end = byteBuffer.getShort();
            byte[] message = ByteBuffer
                    .allocate(Integer.BYTES * 2 + wLen)
                    .putInt(bMsg.cType)
                    .putInt(bMsg.bUserId)
                    .put(messageBody)
                    .array();

            if(CRC16.crc16(message)!= wCrc16_end)
                throw new IllegalArgumentException("crc16_2 is not correct");
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

}
