package cn.liushaofeng.easypc.model;

/**
 * Network Node Info
 * @author liushaofeng
 * @date 2015-5-24
 * @version 1.0.0
 */
public class NetworkNodeModel
{
    private String name;
    private String ipv4;
    private String ipv6;
    private String mac;

    /**
     * default constructor
     */
    public NetworkNodeModel()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIpv4()
    {
        return ipv4;
    }

    public void setIpv4(String ipv4)
    {
        this.ipv4 = ipv4;
    }

    public String getIpv6()
    {
        return ipv6;
    }

    public void setIpv6(String ipv6)
    {
        this.ipv6 = ipv6;
    }

    public String getMac()
    {
        return mac;
    }

    public void setMac(String mac)
    {
        this.mac = mac;
    }

}
