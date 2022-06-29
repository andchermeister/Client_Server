package lab2;

import lab2.Packet;

public interface Reciever {
    void recieveMessage(Packet pack) throws InterruptedException;
}
