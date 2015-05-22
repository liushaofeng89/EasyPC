package cn.liushaofeng.easypc.util;

import org.apache.log4j.Logger;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
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
    public static final String UNKNOWN = "Unknown";

    public static final int SYSTEM_CONSTANT_OS_TYPE_UNKONOWN = 0xFFFFFFFF;
    public static final int SYSTEM_CONSTANT_OS_TYPE_WINDOWS = 0x0;
    public static final int SYSTEM_CONSTANT_OS_TYPE_LINUX = 0x1;
    public static final int SYSTEM_CONSTANT_OS_TYPE_MAXOS = 0x2;

    private static Sigar sigar = new Sigar();

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
        org.hyperic.sigar.CpuInfo[] infos = null;
        try
        {
            infos = sigar.getCpuInfoList();
            org.hyperic.sigar.CpuInfo info = infos[0];
            return info.getVendor() + " " + info.getModel() + "(" + info.getMhz() + "Mhz)";
        }
        catch (SigarException e)
        {
            Logger.getLogger(SystemUtil.class).error(e.getMessage(), e);
            return UNKNOWN;
        }
    }

    /**
     * get memory info
     * @return memory info
     */
    public static String getMemory()
    {
        try
        {
            return sigar.getMem().getTotal() / 1024L / 1024 + "M";
        }
        catch (SigarException e)
        {
            Logger.getLogger(SystemUtil.class).error(e.getMessage(), e);
            return UNKNOWN;
        }
    }

    /**
     * get main board info
     */
    public static String getMainBoardInfo()
    {
        return UNKNOWN;
    }

    /**
     * get disk information
     * @return disk information
     */
    public static String getDiskInfo()
    {
        long totalSize = 0x0;
        long usedSize = 0x0;
        try
        {
            FileSystem[] fileSystemList = sigar.getFileSystemList();
            for (FileSystem fileSystem : fileSystemList)
            {
                if (fileSystem.getType() == 0x2)// Local Disk
                {
                    FileSystemUsage usage = sigar.getFileSystemUsage(fileSystem.getDirName());
                    totalSize += usage.getTotal();
                    usedSize += usage.getUsed();
                }
            }
        }
        catch (SigarException e)
        {
            Logger.getLogger(SystemUtil.class).error(e.getMessage(), e);
        }
        if ((totalSize == usedSize) && totalSize == 0x0)
        {
            return UNKNOWN;
        }
        else
        {
            return FileUtil.convertSizeUnit(totalSize * FileUtil.COMPUTER_UNIT_SIZE) + " (Used:"
                + FileUtil.convertSizeUnit(usedSize * FileUtil.COMPUTER_UNIT_SIZE) + ")";
        }
    }

    /**
     * get network info
     * @return network info
     */
    public static String getNetworkInfo()
    {
        return UNKNOWN;
    }

    /**
     * get audio info
     * @return audio info
     */
    public static String getAudioInfo()
    {
        return UNKNOWN;
    }

    /**
     * get monitor info
     * @return monitor info
     */
    public static String getMonitorInfo()
    {
        return UNKNOWN;
    }

    /**
     * get graphics info
     * @return graphics info
     */
    public static String getGraphicsInfo()
    {
        return UNKNOWN;
    }
}
