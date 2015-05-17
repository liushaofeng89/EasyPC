package cn.liushaofeng.easypc.util;

import java.io.File;
import java.io.IOException;

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
     * 
     */
    public static final void explorerDir(File file)
    {
        if (!file.isDirectory())
        {
            throw new IllegalArgumentException("The input file is not a dir!");
        }
        if (SystemUtil.getOSType() == SystemUtil.SYSTEM_CONSTANT_OS_TYPE_WINDOWS)
        {
            doCmd("cmd /c start explorer " + file.getAbsolutePath());
        }
    }

    /**
     * execute shutdown command in second
     * @param sec second
     * @return command execute success or not
     */
    private boolean executeShutdown(int sec)
    {
        return false;
    }

    private static boolean doCmd(String cmd)
    {
        if (cmd == null || cmd.isEmpty())
        {
            throw new NullPointerException("The input cmd string is null!");
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
}
