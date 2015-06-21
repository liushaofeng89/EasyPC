package cn.liushaofeng.easypc.views.sysinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
    private Map<String, Integer> columnMap = new HashMap<String, Integer>();
    private TableViewer tableViewer = null;
    private Sigar sigar;

    private String[] columnsStr = new String[]
    { "Index", "MHZ", "Vendor", "Mode", "Cache Size", "User Usage Percentage", "System Usage Percentage",
            "Wait Percentage", "Error Percentage", "Free Percentage", "Total Usage Percentage" };

    /**
     * default constructor
     * @param parent parent composite
     * @param sigar sigar
     */
    public CPUCpst(Composite parent, Sigar sigar)
    {
        super(parent, SWT.NONE);
        this.sigar = sigar;
        initTableData();
        initUI();
    }

    private void initTableData()
    {
        for (int i = 0; i < columnsStr.length; i++)
        {
            columnMap.put(columnsStr[i], i);
        }
    }

    private void initUI()
    {
        setLayout(new FillLayout());

        tableViewer = new TableViewer(this, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
        tableViewer.setContentProvider(new CPUTableContentProvider());
        tableViewer.setLabelProvider(new CPUTableLabelProvider());
        tableViewer.getTable().setHeaderVisible(true);
        tableViewer.getTable().setLinesVisible(true);

        for (int i = 0; i < columnsStr.length; i++)
        {
            final TableColumn indexColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
            indexColumn.setText(columnsStr[i]);
        }

        tableViewer.setSorter(new TabelSorter());
        initSortListener();

        tableViewer.setInput(initCPUData());

        for (int i = 0; i < tableViewer.getTable().getColumnCount(); i++)
        {
            tableViewer.getTable().getColumn(i).pack();
        }
    }

    private void initSortListener()
    {
        TabelColumnSelectionListener selectListener = new TabelColumnSelectionListener();
        for (int i = 0; i < columnsStr.length; i++)
        {
            tableViewer.getTable().getColumn(i).addSelectionListener(selectListener);
        }
    }

    private List<CPUModel> initCPUData()
    {
        List<CPUModel> dataList = new ArrayList<CPUModel>();
        CpuInfo[] cpuInfoList = null;
        CpuPerc[] cpuList = null;
        try
        {
            cpuInfoList = sigar.getCpuInfoList();
            cpuList = sigar.getCpuPercList();
        } catch (SigarException e)
        {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
            return dataList;
        }
        for (int i = 0; i < cpuInfoList.length; i++)
        {
            dataList.add(new CPUModel((i + 1), cpuInfoList[i], cpuList[i]));
        }
        return dataList;
    }

    /**
     * table column selection listener
     * @author liushaofeng
     * @date 2015-6-20 下午08:57:48
     * @version 1.0.0
     */
    private class TabelColumnSelectionListener extends SelectionAdapter
    {
        @Override
        public void widgetSelected(SelectionEvent e)
        {
            TableColumn tableColumn = (TableColumn) e.widget;

            ((TabelSorter) tableViewer.getSorter()).sort(columnMap.get(tableColumn.getText()));
            tableViewer.refresh();
        }
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
                        return String.valueOf(model.getIndex());
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
     * table sorter
     * @author liushaofeng
     * @date 2015-6-20
     * @version 1.0.0
     */
    private class TabelSorter extends ViewerSorter
    {
        private static final int ASC = 0;
        private static final int DESC = 1;

        private int sortType = 0;// asc or desc
        private int sortColumn = 0; // sort column index

        /**
         * sort clicked column
         * @param column the column need to sort
         */
        public void sort(int column)
        {
            if (column == sortColumn)
            {
                sortType = 0x1 - sortType;
            } else
            {
                this.sortColumn = column;
                sortType = ASC;// 默认升序排列
            }
        }

        @Override
        public int compare(Viewer viewer, Object e1, Object e2)
        {
            CPUModel model1 = (CPUModel) e1;
            CPUModel model2 = (CPUModel) e2;

            switch (sortColumn)
            {
                case 0x0:
                    return model1.getIndex() > model2.getIndex() ? 0x1 : 0xffffffff;
                case 0x1:
                    return collator.compare(model1.getCpuInfo().getMhz(), model2.getCpuInfo().getMhz());
                case 0x2:

                    break;
                case 0x3:

                    break;
                case 0x4:

                    break;
                case 0x5:

                    break;
                case 0x6:

                    break;
                case 0x7:

                    break;
                case 0x8:

                    break;
                case 0x9:

                    break;
                case 0xa:

                    break;

                default:
                    break;
            }
            return super.compare(viewer, e1, e2);
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
        private int index;
        private CpuInfo cpuInfo;
        private CpuPerc cpuPerc;

        /**
         * constructor
         * @param i CPU index
         * @param cpuInfo CPU Info
         * @param cpuPerc CPU cpuPerc
         */
        public CPUModel(int i, CpuInfo cpuInfo, CpuPerc cpuPerc)
        {
            this.index = i;
            this.cpuInfo = cpuInfo;
            this.cpuPerc = cpuPerc;
        }

        public int getIndex()
        {
            return index;
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
