package easypc;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

import cn.liushaofeng.easypc.util.SystemUtil;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

public class GetMacTest
{
    public static NetworkInterface device = JpcapCaptor.getDeviceList()[0];

    public static void main(String[] args) throws IOException
    {
        for (int i = 2; i < 256; i++)
        {
            System.out.println(getMac("192.168.3." + i));
        }
    }

    private static String getMac(String ip) throws IOException
    {
        JpcapCaptor jc = JpcapCaptor.openDevice(device, 2000, false, 3000); // 打开网络设备，用来侦听
        JpcapSender sender = jc.getJpcapSenderInstance(); // 发送器JpcapSender，用来发送报文
        InetAddress senderIP = InetAddress.getByName("192.168.3.10"); // 设置本地主机的IP地址，方便接收对方返回的报文
        InetAddress targetIP = InetAddress.getByName(ip); // 目标主机的IP地址

        ARPPacket arp = new ARPPacket(); // 开始构造一个ARP包
        arp.hardtype = ARPPacket.HARDTYPE_ETHER; // 硬件类型
        arp.prototype = ARPPacket.PROTOTYPE_IP; // 协议类型
        arp.operation = ARPPacket.ARP_REQUEST; // 指明是ARP请求包
        arp.hlen = 6; // 物理地址长度
        arp.plen = 4; // 协议地址长度
        arp.sender_hardaddr = device.mac_address; // ARP包的发送端以太网地址,在这里即本地主机地址
        arp.sender_protoaddr = senderIP.getAddress(); // 发送端IP地址, 在这里即本地IP地址

        byte[] broadcast = new byte[]
        {
                (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255
        }; // 广播地址
        arp.target_hardaddr = broadcast; // 设置目的端的以太网地址为广播地址
        arp.target_protoaddr = targetIP.getAddress(); // 目的端IP地址

        // 构造以太帧首部
        EthernetPacket ether = new EthernetPacket();
        ether.frametype = EthernetPacket.ETHERTYPE_ARP; // 帧类型
        ether.src_mac = device.mac_address; // 源MAC地址
        ether.dst_mac = broadcast; // 以太网目的地址，广播地址
        arp.datalink = ether; // 将arp报文的数据链路层的帧设置为刚刚构造的以太帧赋给

        sender.sendPacket(arp); // 发送ARP报文

        while (true)
        { // 获取ARP回复包，从中提取出目的主机的MAC地址，如果返回的是网关地址，表明目的IP不是局域网内的地址
            Packet packet = jc.getPacket();
            if (packet instanceof ARPPacket)
            {
                ARPPacket p = (ARPPacket) packet;
                if (Arrays.equals(p.target_protoaddr, senderIP.getAddress()))
                {
                    System.out.println(p.getSenderProtocolAddress());
                    return SystemUtil.convertToMAC(p.sender_hardaddr);
                }
            }
        }

    }
}
