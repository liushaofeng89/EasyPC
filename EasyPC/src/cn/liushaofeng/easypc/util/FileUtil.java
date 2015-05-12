package cn.liushaofeng.easypc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * File utils
 * @author liushaofeng
 * @date 2015年4月26日
 * @version 1.0.0
 */
public final class FileUtil
{
    public static final int COMPUTER_UNIT_SIZE = 1024;
    public static final String COMPUTER_UNIT_SIZE_BYTE = " bytes";//$NON-NLS-1$
    public static final String COMPUTER_UNIT_SIZE_KB = " KB";//$NON-NLS-1$
    public static final String COMPUTER_UNIT_SIZE_MB = " MB";//$NON-NLS-1$
    public static final String COMPUTER_UNIT_SIZE_GB = " GB";//$NON-NLS-1$
    public static final String COMPUTER_UNIT_SIZE_TB = " TB";//$NON-NLS-1$
    public static final String COMPUTER_UNIT_SIZE_PB = " PB";//$NON-NLS-1$

    private FileUtil()
    {

    }

    /**
     * delete file
     * @param path the file need to delete
     * @return delete success or not
     */
    public static final boolean deleteFile(String path)
    {
        return deleteFile(new File(path));
    }

    /**
     * delete file
     * @param file the file need to delete
     * @return delete file success or not
     */
    public static final boolean deleteFile(File file)
    {
        if (file.exists())
        {
            return file.delete();
        }
        return false;
    }

    /**
     * copy a file to anther
     * @param srcFile source file
     * @param desFile target file
     * @return copy success or not
     */
    public static boolean copyFile(File srcFile, File desFile)
    {
        int length = 2097152;
        FileOutputStream out = null;
        FileInputStream in = null;
        try
        {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(desFile);
            byte[] buffer = new byte[length];
            while (true)
            {
                int ins = in.read(buffer);
                if (ins == -1)
                {
                    in.close();
                    out.flush();
                    out.close();
                    return true;
                } else
                {
                    out.write(buffer, 0, ins);
                }
            }
        } catch (IOException e)
        {
            Logger.getLogger(FileUtil.class).error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * Get the current disk space occupied by the file
     * @param file file
     * @return occupied space by this file
     */
    public static String getWasteSpace(File file)
    {
        if (file == null)
        {
            throw new NullPointerException(
                "The input file is null,can not get remain space from the input file.");
        }
        if (!file.isDirectory())
        {
            return convertSizeUnit(file.length());
        }
        return "";
    }

    private static String convertSizeUnit(long size)
    {
        long byteSpace = size / COMPUTER_UNIT_SIZE;
        if (byteSpace > 1)
        {
            long kbSpace = byteSpace / COMPUTER_UNIT_SIZE;
            if (kbSpace > 1)
            {
                long mbSpace = kbSpace / COMPUTER_UNIT_SIZE;
                if (mbSpace > 1)
                {
                    long gbSpace = mbSpace / COMPUTER_UNIT_SIZE;
                    if (gbSpace > 1)
                    {
                        long tbSpace = gbSpace / COMPUTER_UNIT_SIZE;
                        if (tbSpace > 1)
                        {
                            return tbSpace + COMPUTER_UNIT_SIZE_PB;
                        } else
                        {
                            return mbSpace + COMPUTER_UNIT_SIZE_TB;
                        }
                    } else
                    {
                        return mbSpace + COMPUTER_UNIT_SIZE_GB;
                    }
                } else
                {
                    return kbSpace + COMPUTER_UNIT_SIZE_MB;
                }
            } else
            {
                return byteSpace + COMPUTER_UNIT_SIZE_KB;
            }
        } else
        {
            return size + COMPUTER_UNIT_SIZE_BYTE;
        }
    }
}
