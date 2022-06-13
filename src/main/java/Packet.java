import lombok.Data;

import java.nio.ByteBuffer;

@Data
public class Packet {

    private final static Byte bMagic =  0x13;
    protected Byte bSrc;
    protected Long bPktId;
    private Integer wLen;
    private Short wCrc16;
    protected Message bMsg ;
    private Short wCrc16_end;


    public Packet(Byte bSrc,Long bPktId, Message bMsq ) {
        this.bSrc = bSrc;
        this.bMsg = bMsq;
        this.bPktId = bPktId;

        wLen = bMsq.message.length();

    }
    public  byte[] encodePackage () {
        try
        {

            byte[] firstPart = ByteBuffer.allocate(14)
                    .put(bMagic)
                    .put(bSrc).putLong(bPktId)
                    .putInt(wLen).array();

            wCrc16 = (CRC16.crc16(firstPart));

            byte[] secondPart1 = (ByteBuffer.allocate(bMsg.getMessageLength()).put(bMsg.messageToPacket()).array());
            byte[] secondPart = (ByteBuffer.allocate(Crypto.encrypt(bMsg.messageToPacket()).length).put(Crypto.encrypt(bMsg.messageToPacket())).array());
            wCrc16_end = CRC16.crc16(secondPart1);
            return ByteBuffer.allocate(firstPart.length + 4 + secondPart.length)
                    .put(firstPart)
                    .putShort(wCrc16)
                    .put(secondPart)
                    .putShort(wCrc16_end)
                    .array();
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }

    }

    public Packet(byte[] encodedPacket) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(encodedPacket);
            Byte bMag = byteBuffer.get();

            if (!bMag.equals(bMagic))
            {
                throw new Exception("bMagic is not correct ");
            }

            bSrc = byteBuffer.get();
            bPktId = byteBuffer.getLong();
            wLen = byteBuffer.getInt();
            wCrc16 = byteBuffer.getShort();

            byte[] header = byteBuffer.allocate(14).put(bMag).put(bSrc).putLong(bPktId).putInt(wLen).array();

            if (CRC16.crc16(header) != wCrc16)
            {
                throw new IllegalArgumentException("crc16 is not correct");
            }


            bMsg = new Message();
            byte[] messageBytes = new byte[encodedPacket.length - 18];
            byteBuffer.get(messageBytes);
            byte[] secondPart1 = messageBytes;
            byte[] secondPart = Crypto.decrypt(messageBytes);
            ByteBuffer encodeMessage = byteBuffer.wrap(secondPart);
            bMsg.set_cType(encodeMessage.getInt());
            bMsg.set_bUserId(encodeMessage.getInt());
            ByteBuffer encodeMessage2 = byteBuffer.wrap(secondPart);

            byte[] messageBody = new byte[wLen];
            byte[] messageBody2 = new byte[wLen];

            try
            {
                encodeMessage.get(messageBody);
                encodeMessage2.get(messageBody2);
            }
            catch (Exception ex)
            {
            }

            //System.out.println(new String(messageBody2) + " encoded message");
            bMsg.setMessage(new String(messageBody));
            wCrc16_end = byteBuffer.getShort();
            byte[] message = ByteBuffer.allocate(Integer.BYTES * 2 + wLen).putInt(bMsg.cType).putInt(bMsg.bUserId).put(messageBody).array();

            if (CRC16.crc16(message) != wCrc16_end)
            {
                throw new IllegalArgumentException("crc16_end is not correct");
            }

        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }

    }

}
