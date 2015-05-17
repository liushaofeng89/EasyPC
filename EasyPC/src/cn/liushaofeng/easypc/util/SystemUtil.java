package cn.liushaofeng.easypc.util;

import org.apache.log4j.Logger;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * System tool for get detailed information
 * @author liushaofeng
 * @date 2015-5-2
 * @version 1.0.0
 */
public final class SystemUtil
{
    public static final int SYSTEM_CONSTANT_OS_TYPE_UNKONOWN = 0xFFFFFFFF;
    public static final int SYSTEM_CONSTANT_OS_TYPE_WINDOWS = 0x0;
    public static final int SYSTEM_CONSTANT_OS_TYPE_LINUX = 0x1;
    public static final int SYSTEM_CONSTANT_OS_TYPE_MAXOS = 0x2;

    /**
     * get OS name
     * @return OS name
     */
    public static String getOSName()
    {
        return OperatingSystem.getInstance().getDescription();
    }

    /**
     * get current OS type
     * @return current OS type
     */
    public static int getOSType()
    {
        if (System.getProperty("os.name").toLowerCase().indexOf("windows") != -1)
        {
            return SYSTEM_CONSTANT_OS_TYPE_WINDOWS;
        }
        return SYSTEM_CONSTANT_OS_TYPE_UNKONOWN;
    }

    /**
     * get CPU info
     * @return CPU info like '(英特尔)Intel(R) Core(TM)2 Duo CPU T6600 @
     *         2.20GHz(2200 Mhz)'
     */
    public static String getCPUName()
    {
        Sigar sigar = new Sigar();
        org.hyperic.sigar.CpuInfo[] infos = null;
        try
        {
            infos = sigar.getCpuInfoList();
        }
        catch (SigarException e)
        {
            Logger.getLogger(SystemUtil.class).error(e.getMessage(), e);
        }
        org.hyperic.sigar.CpuInfo info = infos[0];
        return info.getVendor() + " " + info.getModel() + "(" + info.getMhz() + ")";
    }
}
