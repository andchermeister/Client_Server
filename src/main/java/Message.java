import lombok.Data;

import java.nio.ByteBuffer;

@Data
public class Message {

    protected Integer cType;
    protected Integer bUserId;
    protected String message;

    public  void set_cType(Integer cType){
        this.cType = cType;
    }
    public void set_bUserId(Integer bUserId){
        this.bUserId = bUserId;
    }
    public int getMessageLength(){

        return message.length()+Integer.BYTES * 2;
    }


    public Message(){
    }
    public Message(Integer cType, Integer bUserId, String message) {
        this.cType = cType;
        this.bUserId = bUserId;
        this.message = message;
    }

    public byte[] messageToPacket(){
        return ByteBuffer.allocate(this.getMessageLength())
                .putInt(cType)
                .putInt(bUserId)
                .put(message.getBytes())
                .array();
    }


}
