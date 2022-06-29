package lab2;

public class ClientPacket {
    private final Packet packet;
    private final byte clientId;

    public ClientPacket(final Packet packet, final byte clientId) {
        this.packet = packet;
        this.clientId = clientId;
    }

    public Packet getPacket() {
        return packet;
    }

    public byte getClientId() {
        return clientId;
    }
}
