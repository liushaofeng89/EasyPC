package cn.liushaofeng.easypc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * command line util for easy pc
 * @author liushaofeng
 * @date 2015年4月26日
 * @version 1.0.0
 */
public final class CMDUtil
{

    /**
     * private this constructor, call method by static way
     */
    private CMDUtil()
    {
    }

    /**
     * open file by default way
     */
    public static void openFileByDefaultWay(File file)
    {
        try
        {
            Runtime.getRuntime().exec(new String[]
            {
                    "cmd.exe", "/c", file.getAbsolutePath()
            });
        }
        catch (IOException e)
        {
            Logger.getLogger(CMDUtil.class).error(e.getMessage(), e);
        }
    }

    /**
     * open the file dir
     * @param file the file dir need to open
     */
    public static final void explorerDir(File file)
    {
        if (!file.isDirectory())
        {
            throw new IllegalArgumentException("The input file is not a dir!");//$NON-NLS-1$
        }
        if (SystemUtil.getOSType() == SystemUtil.SYSTEM_CONSTANT_OS_TYPE_WINDOWS)
        {
            doCmd("cmd /c start explorer " + file.getAbsolutePath());//$NON-NLS-1$
        }
    }

    /**
     * execute shutdown command in second
     * @param sec second
     * @return command execute success or not
     */
    private boolean executeShutdown(int sec)
    {
        return doCmd("shutdown -s -t " + sec);//$NON-NLS-1$
    }

    private static boolean doCmd(String cmd)
    {
        if (cmd == null || cmd.isEmpty())
        {
            throw new NullPointerException("The input cmd string is null!");//$NON-NLS-1$
        }
        try
        {
            Runtime.getRuntime().exec(cmd);
            return true;
        }
        catch (IOException e)
        {
            Logger.getLogger(CMDUtil.class).error(e.getMessage(), e);
            return false;
        }
    }

    private static InputStream getCmdInputstream(String cmd)
    {
        if (cmd == null || cmd.isEmpty())
        {
            throw new NullPointerException("The input cmd string is null!");//$NON-NLS-1$
        }
        try
        {
            return Runtime.getRuntime().exec(cmd).getInputStream();
        }
        catch (IOException e)
        {
            Logger.getLogger(CMDUtil.class).error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * get system info
     * @return system info
     */
    public static List<String> getSystemInfo()
    {
        InputStream inputstream = getCmdInputstream("cmd /c systeminfo");
        if (inputstream != null)
        {
            List<String> dataList = new ArrayList<String>();
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream, "GBK"));
                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    if (!line.trim().isEmpty())
                    {
                        dataList.add(line);
                    }
                }
            }
            catch (IOException e)
            {
                Logger.getLogger(CMDUtil.class).error(e.getMessage(), e);
            }
            return dataList;
        }
        return null;
    }
}
