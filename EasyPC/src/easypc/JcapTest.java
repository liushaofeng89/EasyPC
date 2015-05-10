package easypc;

import java.io.IOException;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class JcapTest
{
    public static void main(String[] args) throws IOException
    {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();

        // 无线网卡
        // System.out.println(devices[0].name);
        // System.out.println(devices[0].description);
        // System.out.println(devices[0].mac_address);
        // System.out.println(devices[0].datalink_name);
        // System.out.println(devices[0].datalink_description);

        // 有线网卡
        // System.out.println(devices[1].name);
        // System.out.println(devices[1].description);
        // System.out.println(devices[1].mac_address);
        // System.out.println(devices[1].datalink_name);
        // System.out.println(devices[1].datalink_description);

        JpcapCaptor captor = JpcapCaptor.openDevice(devices[0], 65535, false, 20);
        captor.setFilter("tcp and ip", true);
        while (true)
        {
            Packet packet = captor.getPacket();
            if (packet != null)
            {
                EthernetPacket ethernetPacket = (EthernetPacket) packet.datalink;
                System.out.println("Source MAC：" + ethernetPacket.getSourceAddress() + "-> Target MAC:"
                    + ethernetPacket.getDestinationAddress());
                if (packet instanceof TCPPacket)
                {
                    TCPPacket tcpPacket = (TCPPacket) packet;
                    System.out.println("From:" + tcpPacket.src_ip + "(" + tcpPacket.src_port + ") To:"
                        + tcpPacket.dst_ip + "(" + tcpPacket.dst_port + ") TTL:" + tcpPacket.hop_limit
                        + " Above Protocol:" + tcpPacket.protocol);
                    System.out.println("Data:");
                    for (int i = 0; i < tcpPacket.data.length; i++)
                    {
                        System.out.print((char) tcpPacket.data[i]);
                    }
                    System.out.println();
                }
                if (packet instanceof UDPPacket)
                {
                    UDPPacket tcpPacket = (UDPPacket) packet;
                    System.out.println("From:" + tcpPacket.src_ip + "(" + tcpPacket.src_port + ") To:"
                        + tcpPacket.dst_ip + "(" + tcpPacket.dst_port + ") TTL:" + tcpPacket.hop_limit
                        + "Above Protocol:" + tcpPacket.protocol);
                    System.out.println("Data:");
                    for (int i = 0; i < tcpPacket.data.length; i++)
                    {
                        System.out.print((char) tcpPacket.data[i]);
                    }
                    System.out.println();
                }
                if (packet instanceof ICMPPacket)
                {
                    ICMPPacket tcpPacket = (ICMPPacket) packet;
                    System.out.println(tcpPacket);
                }
                if (packet instanceof ARPPacket)
                {
                    ARPPacket tcpPacket = (ARPPacket) packet;
                    System.out.println(tcpPacket);
                }
            }
        }
    }
}
