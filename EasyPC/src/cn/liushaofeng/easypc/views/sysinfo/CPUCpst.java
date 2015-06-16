package cn.liushaofeng.easypc.views.sysinfo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * CPU detail display composite
 * @author liushaofeng
 * @date 2015-6-10下午11:15:48
 * @version 1.0.0
 */
public class CPUCpst extends Composite
{

    private TableViewer tableViewer = null;
    private Sigar sigar;

    /**
     * default constructor
     * @param parent parent composite
     * @param sigar sigar
     */
    public CPUCpst(Composite parent, Sigar sigar)
    {
        super(parent, SWT.NONE);
        this.sigar = sigar;
        initUI();
    }

    private void initUI()
    {
        setLayout(new FillLayout());

        tableViewer = new TableViewer(this, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
        tableViewer.setContentProvider(new CPUTableContentProvider());
        tableViewer.setLabelProvider(new CPUTableLabelProvider());
        tableViewer.getTable().setHeaderVisible(true);
        tableViewer.getTable().setLinesVisible(true);

        final TableColumn indexColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
        indexColumn.setText("CPU Index");

        final TableColumn mhzColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
        mhzColumn.setText("CPU MHZ");

        final TableColumn vendorColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
        vendorColumn.setText("CPU Vendor");

        final TableColumn modeColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
        modeColumn.setText("CPU Mode");

        final TableColumn cacheSizeColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
        cacheSizeColumn.setText("Cache Size");

        final TableColumn userUsagePercentage = new TableColumn(tableViewer.getTable(), SWT.NONE);
        userUsagePercentage.setText("User Usage Percentage");

        final TableColumn sysUsagePercentage = new TableColumn(tableViewer.getTable(), SWT.NONE);
        sysUsagePercentage.setText("System Usage Percentage");

        final TableColumn waitPercentage = new TableColumn(tableViewer.getTable(), SWT.NONE);
        waitPercentage.setText("Wait Percentage");

        final TableColumn errorPercentage = new TableColumn(tableViewer.getTable(), SWT.NONE);
        errorPercentage.setText("Error Percentage");

        final TableColumn freePercentage = new TableColumn(tableViewer.getTable(), SWT.NONE);
        freePercentage.setText("Free Percentage");

        final TableColumn totalUsePercentage = new TableColumn(tableViewer.getTable(), SWT.NONE);
        totalUsePercentage.setText("Total Usage Percentage");

        tableViewer.setInput(initData());
    }

    private List<CPUModel> initData()
    {
        List<CPUModel> dataList = new ArrayList<CPUModel>();
        CpuInfo[] cpuInfoList = null;
        CpuPerc[] cpuList = null;
        try
        {
            cpuInfoList = sigar.getCpuInfoList();
            cpuList = sigar.getCpuPercList();
        }
        catch (SigarException e)
        {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
            return dataList;
        }
        for (int i = 0; i < cpuInfoList.length; i++)
        {
            dataList.add(new CPUModel(cpuInfoList[i], cpuList[i]));
        }
        return dataList;
    }

    /**
     * CPU table content provider
     * @author liushaofeng
     * @date 2015-6-10下午11:32:14
     * @version 1.0.0
     */
    private class CPUTableContentProvider implements IStructuredContentProvider
    {

        @SuppressWarnings("unchecked")
        @Override
        public Object[] getElements(Object inputElement)
        {
            if (inputElement instanceof List)
            {
                return ((List<CPUModel>) inputElement).toArray();
            }
            return null;
        }

        @Override
        public void dispose()
        {
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
        {

        }
    }

    /**
     * CPU table label provider
     * @author liushaofeng
     * @date 2015-6-10下午11:33:17
     * @version 1.0.0
     */
    private class CPUTableLabelProvider implements ITableLabelProvider
    {
        private int index = 0x1;

        @Override
        public Image getColumnImage(Object element, int columnIndex)
        {
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex)
        {
            if (element instanceof CPUModel)
            {
                CPUModel model = (CPUModel) element;
                switch (columnIndex)
                {
                    case 0x0:
                        return String.valueOf(index);
                    case 0x1:
                        return String.valueOf(model.getCpuInfo().getMhz());// CPU的总量MHz
                    case 0x2:
                        return model.getCpuInfo().getVendor();// 获得CPU的卖主，如：Intel
                    case 0x3:
                        return model.getCpuInfo().getModel();// 获得CPU的类别，如：Celeron
                    case 0x4:
                        return String.valueOf(model.getCpuInfo().getCacheSize());// 缓冲存储器数量
                    case 0x5:
                        return CpuPerc.format(model.getCpuPerc().getUser());// 用户使用率
                    case 0x6:
                        return CpuPerc.format(model.getCpuPerc().getSys());// 系统使用率
                    case 0x7:
                        return CpuPerc.format(model.getCpuPerc().getWait());// 当前等待率
                    case 0x8:
                        return CpuPerc.format(model.getCpuPerc().getNice());// 总的错误率
                    case 0x9:
                        return CpuPerc.format(model.getCpuPerc().getIdle());// 当前空闲率
                    case 0xa:
                        return CpuPerc.format(model.getCpuPerc().getCombined());// 总的使用率
                    default:
                        break;
                }
                index++;
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
     * CPU info mode
     * @author liushaofeng
     * @date 2015-6-10下午11:43:36
     * @version 1.0.0
     */
    public class CPUModel
    {
        private CpuInfo cpuInfo;
        private CpuPerc cpuPerc;

        /**
         * constructor
         * @param cpuInfo cpuInfo
         * @param cpuPerc cpuPerc
         */
        public CPUModel(CpuInfo cpuInfo, CpuPerc cpuPerc)
        {
            this.cpuInfo = cpuInfo;
            this.cpuPerc = cpuPerc;
        }

        public CpuInfo getCpuInfo()
        {
            return cpuInfo;
        }

        public CpuPerc getCpuPerc()
        {
            return cpuPerc;
        }

    }
}
