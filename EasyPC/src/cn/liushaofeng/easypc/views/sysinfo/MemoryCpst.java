package cn.liushaofeng.easypc.views.sysinfo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

import cn.liushaofeng.easypc.util.FileUtil;

/**
 * memory composite
 * @author liushaofeng
 * @date 2015-6-21 下午10:14:14
 * @version 1.0.0
 */
public class MemoryCpst extends SystemInfoTableCpst
{

    /**
     * default constructor
     * @param tabFolder parent compositr
     * @param sigar sigar
     */
    public MemoryCpst(CTabFolder tabFolder, Sigar sigar)
    {
        super(tabFolder, sigar);
    }

    /**
     * memory label provider
     * @author liushaofeng
     * @date 2015-6-21 下午11:04:09
     * @version 1.0.0
     */
    private class MemoryLabelProvider implements ITableLabelProvider
    {

        @Override
        public Image getColumnImage(Object element, int columnIndex)
        {
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex)
        {
            if (element instanceof MemoryModel)
            {
                MemoryModel model = (MemoryModel) element;
                switch (columnIndex)
                {
                    case 0x0:
                        return String.valueOf(FileUtil.convertSizeUnit(model.getMemory().getTotal()));
                    case 0x1:
                        return String.valueOf(FileUtil.convertSizeUnit(model.getMemory().getUsed()));
                    case 0x2:
                        return String.valueOf(FileUtil.convertSizeUnit(model.getMemory().getFree()));
                    case 0x3:
                        return String.valueOf(FileUtil.convertSizeUnit(model.getSwap().getTotal()));
                    case 0x4:
                        return String.valueOf(FileUtil.convertSizeUnit(model.getSwap().getUsed()));
                    case 0x5:
                        return String.valueOf(FileUtil.convertSizeUnit(model.getSwap().getFree()));
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
     * memory model
     * @author liushaofeng
     * @date 2015-6-21 下午10:56:29
     * @version 1.0.0
     */
    private class MemoryModel extends SystemInfoDataModel
    {
        private Mem memory;
        private Swap swap;

        /**
         * default constructor
         * @param mem memory object
         * @param swap memory swap
         */
        public MemoryModel(Mem mem, Swap swap)
        {
            this.memory = mem;
            this.swap = swap;
        }

        public Mem getMemory()
        {
            return memory;
        }

        public Swap getSwap()
        {
            return swap;
        }

    }

    @Override
    protected void initColumnNames()
    {
        columnsStr = new String[]
        { "Total Size", "Memory Used", "Memory Remain", "Swap Total Size", "Swap Used", "Swap Remain" };
    }

    @Override
    protected List<SystemInfoDataModel> setInput()
    {
        List<SystemInfoDataModel> dataList = new ArrayList<SystemInfoDataModel>();
        try
        {
            dataList.add(new MemoryModel(sigar.getMem(), sigar.getSwap()));
        } catch (SigarException e)
        {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
        }
        return dataList;
    }

    @Override
    protected ITableLabelProvider setLabelProvider()
    {
        return new MemoryLabelProvider();
    }

}
