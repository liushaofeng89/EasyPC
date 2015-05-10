package easypc;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class JpcapHandler implements PacketReceiver
{

    @Override
    public void receivePacket(Packet arg0)
    {
        System.out.println(arg0);
    }

}
