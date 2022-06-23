import java.nio.ByteBuffer;
import lombok.Data;

@Data
public class Message {

    protected Integer cType;
    protected Integer bUserId;
    protected String message;

    public  void set_cType(Integer cType)
    {
        this.cType = cType;
    }

    public void set_bUserId(Integer bUserId)
    {
        this.bUserId = bUserId;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getMessageLength()
    {

        return message.length()+Integer.BYTES * 2;
    }


    public Message(){
    }
    public Message(Integer cType, Integer bUserId, String message)
    {
        this.cType = cType;
        this.bUserId = bUserId;
        this.message = message;
    }

    enum commandTypes{
        GET_AMOUNT_OF_PRODUCT_IN_STOCK,
        REMOVE_SOME_AMOUNT_OF_PRODUCT,
        ADD_SOME_AMOUNT_OF_PRODUCT,
        ADD_GROUP_OF_PRODUCTS,
        ADD_NAME_OF_PRODUCT_TO_GROUP,
        SET_PRICE_OF_SPECIFIC_PRODUCT
    }
    public byte[] messageToPacket()
    {
        return ByteBuffer.allocate(this.getMessageLength())
                .putInt(cType)
                .putInt(bUserId)
                .put(message.getBytes())
                .array();
    }

}

