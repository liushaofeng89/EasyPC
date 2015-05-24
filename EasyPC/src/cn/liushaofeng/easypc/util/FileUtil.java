package cn.liushaofeng.easypc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

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
     * create a new file
     * @param file new file
     * @return create file success or not
     */
    public static final boolean createNewFile(File file)
    {
        try
        {
            return file.createNewFile();
        }
        catch (IOException e)
        {
            Logger.getLogger(FileUtil.class).error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * create a new directory
     * @param file new directory
     * @return create directory success or not
     */
    public static final boolean createDirs(File file)
    {
        return file.mkdirs();
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
    public static boolean deleteFile(File file)
    {
        if (!file.exists())
        {
            return false;
        }

        if (file.isFile())
        {
            return file.delete();
        }
        else
        {
            return deleteDir(file);
        }
    }

    /**
     * delete directory
     * @param dirFile directory
     * @return delete success or not
     */
    public static boolean deleteDir(File dirFile)
    {
        File[] listFiles = dirFile.listFiles();
        for (File file : listFiles)
        {
            if (file.isFile())
            {
                deleteFile(file);
            }
            else
            {
                deleteDir(file);
            }
        }
        return dirFile.delete();
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
                }
                else
                {
                    out.write(buffer, 0, ins);
                }
            }
        }
        catch (IOException e)
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
            throw new NullPointerException("The input file is null,can not get remain space from the input file.");
        }
        if (!file.isDirectory())
        {
            return convertSizeUnit(file.length());
        }
        return "";
    }

    /**
     * convert size
     * @param size size
     * @return other way to show size
     */
    public static String convertSizeUnit(long size)
    {
        double kiloByte = size / COMPUTER_UNIT_SIZE;
        if (kiloByte < 0x1)
        {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte / COMPUTER_UNIT_SIZE;
        if (megaByte < 0x1)
        {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(0x2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / COMPUTER_UNIT_SIZE;
        if (gigaByte < 0x1)
        {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(0x2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / COMPUTER_UNIT_SIZE;
        if (teraBytes < 0x1)
        {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(0x2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(0x2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
