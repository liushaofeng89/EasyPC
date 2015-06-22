package cn.liushaofeng.easypc.views.sysinfo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import cn.liushaofeng.easypc.util.FileUtil;

/**
 * disk info composite
 * @author liushaofeng
 * @date 2015-6-21 上午09:18:04
 * @version 1.0.0
 */
public class DiskCpst extends SystemInfoTableCpst
{

    /**
     * constructor
     * @param parent parent composite
     * @param sigar sigar object
     */
    public DiskCpst(Composite parent, Sigar sigar)
    {
        super(parent, sigar);

    }

    private FileSystemUsage getFileSysUsage(FileSystem fslist)
    {
        FileSystemUsage fileSystemUsage = null;
        try
        {
            fileSystemUsage = sigar.getFileSystemUsage(fslist.getDirName());
        } catch (SigarException e)
        {
            Logger.getLogger(this.getClass()).error(e.getMessage());
        }
        return fileSystemUsage;
    }

    /**
     * disk info label provider
     * @author liushaofeng
     * @date 2015-6-21 上午09:23:39
     * @version 1.0.0
     */
    private class DiskTableLabelProvider implements ITableLabelProvider
    {

        @Override
        public Image getColumnImage(Object element, int columnIndex)
        {
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex)
        {
            if (element instanceof DiskInfoModel)
            {
                DiskInfoModel model = (DiskInfoModel) element;
                switch (columnIndex)
                {
                    case 0x0:
                        return String.valueOf(model.getIndex());
                    case 0x1:
                        return model.getFileSystem().getDirName();
                    case 0x2:
                        return String.valueOf(model.getFileSystem().getFlags());
                    case 0x3:
                        return model.getFileSystem().getSysTypeName();
                    case 0x4:
                        return model.getFileSystem().getTypeName();
                    case 0x5:
                        return model.getUsage() == null ? "0" : FileUtil.convertSizeUnit(model.getUsage()
                            .getTotal()
                            * FileUtil.COMPUTER_UNIT_SIZE);
                    case 0x6:
                        return model.getUsage() == null ? "0" : FileUtil.convertSizeUnit(model.getUsage()
                            .getFree()
                            * FileUtil.COMPUTER_UNIT_SIZE);
                    case 0x7:
                        return model.getUsage() == null ? "0" : FileUtil.convertSizeUnit(model.getUsage()
                            .getAvail()
                            * FileUtil.COMPUTER_UNIT_SIZE);
                    case 0x8:
                        return model.getUsage() == null ? "0" : FileUtil.convertSizeUnit(model.getUsage()
                            .getUsed()
                            * FileUtil.COMPUTER_UNIT_SIZE);
                    case 0x9:
                        return model.getUsage() == null ? "0" : String.valueOf(model.getUsage()
                            .getUsePercent() * 100)
                            + "%";
                    case 0xa:
                        return model.getUsage() == null ? "0" : FileUtil.convertSizeUnit(model.getUsage()
                            .getDiskReads()
                            * FileUtil.COMPUTER_UNIT_SIZE);
                    case 0xb:
                        return model.getUsage() == null ? "0" : FileUtil.convertSizeUnit(model.getUsage()
                            .getDiskWrites()
                            * FileUtil.COMPUTER_UNIT_SIZE);
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public void addListener(ILabelProviderListener listener)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void dispose()
        {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean isLabelProperty(Object element, String property)
        {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void removeListener(ILabelProviderListener listener)
        {
            // TODO Auto-generated method stub

        }
    }

    /**
     * disk info model
     * @author liushaofeng
     * @date 2015-6-21 上午09:34:39
     * @version 1.0.0
     */
    private class DiskInfoModel extends SystemInfoDataModel
    {
        private int index;
        private FileSystem fileSystem;
        private FileSystemUsage usage;

        public DiskInfoModel(int i, FileSystem fileSystem, FileSystemUsage usage)
        {
            this.index = i;
            this.fileSystem = fileSystem;
            this.usage = usage;
        }

        public int getIndex()
        {
            return index;
        }

        public FileSystem getFileSystem()
        {
            return fileSystem;
        }

        public FileSystemUsage getUsage()
        {
            return usage;
        }
    }

    @Override
    protected void initColumnNames()
    {
        columnsStr = new String[]
        { "Index", "Directory Name", "Flags", "System Type Name", "Type Name", "Total Size", "Remain Size",
                "Available Size", "Used Size", "Used Percentage", "Number Of Physical Disk Reads",
                "Number Of Physical Disk Writes" };
    }

    @Override
    protected List<SystemInfoDataModel> setInput()
    {
        List<SystemInfoDataModel> dataList = new ArrayList<SystemInfoDataModel>();
        try
        {
            FileSystem[] fslist = sigar.getFileSystemList();
            for (int i = 0; i < fslist.length; i++)
            {
                FileSystemUsage fileSystemUsage = getFileSysUsage(fslist[i]);
                dataList.add(new DiskInfoModel((i + 1), fslist[i], fileSystemUsage));
            }

        } catch (SigarException e)
        {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
        }
        return dataList;
    }

    @Override
    protected ITableLabelProvider setLabelProvider()
    {
        return new DiskTableLabelProvider();
    }
}
