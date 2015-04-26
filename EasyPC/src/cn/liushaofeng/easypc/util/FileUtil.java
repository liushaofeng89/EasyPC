package cn.liushaofeng.easypc.util;

import java.io.File;

/**
 * 文件工具类
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
