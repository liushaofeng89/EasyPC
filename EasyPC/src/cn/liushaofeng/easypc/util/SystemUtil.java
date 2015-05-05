package cn.liushaofeng.easypc.util;

import org.apache.log4j.Logger;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.CpuInfo;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.cmd.SysInfo;

/**
 * System tool for get detailed information
 * @author liushaofeng
 * @date 2015-5-2
 * @version 1.0.0
 */
public final class SystemUtil
{
    private static final Shell shell = new Shell();

    /**
     * get OS name
     * @return OS name
     */
    public static String getOSName()
    {
        return OperatingSystem.getInstance().getDescription();
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
        } catch (SigarException e)
        {
            Logger.getLogger(SystemUtil.class).error(e.getMessage(), e);
        }
        org.hyperic.sigar.CpuInfo info = infos[0];
        return info.getVendor() + " " + info.getModel() + "(" + info.getMhz() + ")";
    }
}
