package cn.liushaofeng.easypc.views;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

import cn.liushaofeng.easypc.actions.SystemInfoExportAction;
import cn.liushaofeng.easypc.util.CMDUtil;

/**
 * system info view
 * @author liushaofeng
 * @date 2015-5-22
 * @version 1.0.0
 */
public class SystemInfoView extends ViewPart
{
    public static final String ID = "cn.liushaofeng.easypc.views.systeminfoview";

    private static final String TAB_ITEM_OVERVIEW = "Overview";//$NON-NLS-1$
    private static final String TAB_ITEM_CPU = "CPU";//$NON-NLS-1$
    private static final String TAB_ITEM_MAIN_BOARD = "Main Board";//$NON-NLS-1$
    private static final String TAB_ITEM_MEMORY = "Memory Info";//$NON-NLS-1$
    private static final String TAB_ITEM_GRAPHICS = "Graphics Info";//$NON-NLS-1$
    private static final String TAB_ITEM_MONITOR = "Monitor Info";//$NON-NLS-1$
    private static final String TAB_ITEM_DISK = "Disk Info";//$NON-NLS-1$
    private static final String TAB_ITEM_NETWORK_CARD = "Network Card";//$NON-NLS-1$
    private static final String TAB_ITEM_AUDIO = "Audio Info";//$NON-NLS-1$
    private static final String TAB_ITEM_OTHER = "Other Info";//$NON-NLS-1$

    private static final String TAB_ITEM_OVERVIEW_SYSTEM_MODE = "System Mode:";//$NON-NLS-1$
    private static final String TAB_ITEM_OVERVIEW_OPERATE_SYSTEM = "Operating System:";//$NON-NLS-1$
    private static final String TAB_ITEM_OVERVIEW_CPU = "CPU:";//$NON-NLS-1$
    private static final String TAB_ITEM_OVERVIEW_MAIN_BOARD = "Main Board:";//$NON-NLS-1$
    private static final String TAB_ITEM_OVERVIEW_MEMORY = "Memory:";//$NON-NLS-1$
    private static final String TAB_ITEM_OVERVIEW_MAIN_DISK = "Main Disk:";//$NON-NLS-1$
    private static final String TAB_ITEM_OVERVIEW_GRAPHICS = "Graphics:";//$NON-NLS-1$
    private static final String TAB_ITEM_OVERVIEW_MONITOR = "Monitor:";//$NON-NLS-1$
    private static final String TAB_ITEM_OVERVIEW_AUDIO_CARD = "Audio Card:";//$NON-NLS-1$
    private static final String TAB_ITEM_OVERVIEW_NETWORD_CARD = "Netword Card:";//$NON-NLS-1$

    private Sigar sigar = new Sigar();
    private SystemInfoExportAction exportAction;

    @Override
    public void createPartControl(Composite parent)
    {

        CTabFolder tabFolder = new CTabFolder(parent, SWT.BOTTOM);
        tabFolder.setSimple(true);
        tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

        CTabItem overviewItem = new CTabItem(tabFolder, SWT.None);
        overviewItem.setText(TAB_ITEM_OVERVIEW);
        overviewItem.setControl(getOverviewContent(tabFolder));

        CTabItem memoryItem = new CTabItem(tabFolder, SWT.None);
        memoryItem.setText(TAB_ITEM_MEMORY);
        memoryItem.setControl(getMemoryContent(tabFolder));

        CTabItem cpuItem = new CTabItem(tabFolder, SWT.None);
        cpuItem.setText(TAB_ITEM_CPU);
        cpuItem.setControl(getCPUContent(tabFolder));

        CTabItem mainBoardItem = new CTabItem(tabFolder, SWT.None);
        mainBoardItem.setText(TAB_ITEM_MAIN_BOARD);
        mainBoardItem.setControl(getMainboardContent(tabFolder));

        CTabItem graphicsItem = new CTabItem(tabFolder, SWT.None);
        graphicsItem.setText(TAB_ITEM_GRAPHICS);

        CTabItem monitorItem = new CTabItem(tabFolder, SWT.None);
        monitorItem.setText(TAB_ITEM_MONITOR);

        CTabItem diskItem = new CTabItem(tabFolder, SWT.None);
        diskItem.setText(TAB_ITEM_DISK);

        CTabItem networkItem = new CTabItem(tabFolder, SWT.None);
        networkItem.setText(TAB_ITEM_NETWORK_CARD);

        CTabItem audioItem = new CTabItem(tabFolder, SWT.None);
        audioItem.setText(TAB_ITEM_AUDIO);

        CTabItem otherItem = new CTabItem(tabFolder, SWT.None);
        otherItem.setText(TAB_ITEM_OTHER);

        tabFolder.setSelection(0x0);
    }

    private Control getCPUContent(CTabFolder tabFolder)
    {
        CpuInfo[] cpuInfoList = null;
        CpuPerc[] cpuList = null;
        try
        {
            cpuInfoList = sigar.getCpuInfoList();
            cpuList = sigar.getCpuPercList();
        }
        catch (SigarException e)
        {
            Label label = new Label(tabFolder, SWT.NONE);
            label.setText("Get CPU information failed!");
            return label;
        }
        String[] columnNames = new String[cpuInfoList.length + 1];
        columnNames[0] = "Type";
        for (int i = 1; i < columnNames.length; i++)
        {
            columnNames[i] = "CPU " + String.valueOf(i);
        }
        final Table table = createTable(tabFolder, columnNames);

        String[] mhzs = new String[cpuInfoList.length + 1];
        mhzs[0] = "Total MHz";
        String[] vendors = new String[cpuInfoList.length + 1];
        vendors[0] = "Vendor";
        String[] models = new String[cpuInfoList.length + 1];
        models[0] = "Model";
        String[] cacheSize = new String[cpuInfoList.length + 1];
        cacheSize[0] = "CacheSize";
        String[] corePerCPU = new String[cpuInfoList.length + 1];
        corePerCPU[0] = "Cores in CPU";
        String[] userUsePercent = new String[cpuInfoList.length + 1];
        userUsePercent[0] = "User Usage Percentage";
        String[] systemUsePercent = new String[cpuInfoList.length + 1];
        systemUsePercent[0] = "System Usage Percentage";
        String[] waitPercent = new String[cpuInfoList.length + 1];
        waitPercent[0] = "Current Wait Percentage";
        String[] errorPercent = new String[cpuInfoList.length + 1];
        errorPercent[0] = "Error Percentage";
        String[] freePercent = new String[cpuInfoList.length + 1];
        freePercent[0] = "Free Percentage";
        String[] totalUsagePercent = new String[cpuInfoList.length + 1];
        totalUsagePercent[0] = "Total Usage Percentage";

        for (int i = 0; i < cpuInfoList.length; i++)
        {
            mhzs[i + 1] = String.valueOf(cpuInfoList[i].getMhz());
            vendors[i + 1] = String.valueOf(cpuInfoList[i].getVendor());
            models[i + 1] = String.valueOf(cpuInfoList[i].getModel());
            cacheSize[i + 1] = String.valueOf(cpuInfoList[i].getCacheSize());
            corePerCPU[i + 1] = String.valueOf(cpuInfoList[i].getCoresPerSocket());
            userUsePercent[i + 1] = String.valueOf(CpuPerc.format(cpuList[i].getUser()));
            systemUsePercent[i + 1] = String.valueOf(CpuPerc.format(cpuList[i].getSys()));
            waitPercent[i + 1] = String.valueOf(CpuPerc.format(cpuList[i].getWait()));
            errorPercent[i + 1] = String.valueOf(CpuPerc.format(cpuList[i].getNice()));
            freePercent[i + 1] = String.valueOf(CpuPerc.format(cpuList[i].getIdle()));
            totalUsagePercent[i + 1] = String.valueOf(CpuPerc.format(cpuList[i].getCombined()));
        }
        createTableItem(table, mhzs);
        createTableItem(table, vendors);
        createTableItem(table, models);
        createTableItem(table, cacheSize);
        createTableItem(table, userUsePercent);
        createTableItem(table, systemUsePercent);
        createTableItem(table, waitPercent);
        createTableItem(table, errorPercent);
        createTableItem(table, freePercent);
        createTableItem(table, totalUsagePercent);

        for (int i = 0; i < table.getColumnCount(); i++)
        {
            table.getColumn(i).pack();
        }
        return table;
    }

    private Control getMainboardContent(CTabFolder tabFolder)
    {
        Label label = new Label(tabFolder, SWT.NONE);
        label.setText("Get mainboard information failed!");
        return label;
    }

    private Control getMemoryContent(CTabFolder tabFolder)
    {
        final Table table = createTable(tabFolder, new String[]
        {
                "Memory Type", "Size", "Percent"
        });
        DecimalFormat df = new DecimalFormat("0.00");
        try
        {
            Mem mem = sigar.getMem();
            createTableItem(table, new String[]
            {
                    "Memory Total Size", mem.getTotal() / 1024L + "K", ""
            });
            createTableItem(table, new String[]
            {
                    "Memory Used Size", mem.getUsed() / 1024L + "K",
                    String.valueOf(df.format(mem.getUsedPercent())) + "%"
            });
            createTableItem(table, new String[]
            {
                    "Memory Remain Size", mem.getFree() / 1024L + "K", ""
            });
            Swap swap = sigar.getSwap();
            createTableItem(table, new String[]
            {
                    "Swap Total Size", swap.getTotal() / 1024L + "K", ""
            });
            createTableItem(
                table,
                new String[]
                {
                        "Swap Used Size", swap.getUsed() / 1024L + "K",
                        String.valueOf(df.format(swap.getUsed() * 100.0f / swap.getTotal())) + "%"
                });
            createTableItem(table, new String[]
            {
                    "Swap Remain Size", swap.getFree() / 1024L + "K", ""
            });
        }
        catch (SigarException e)
        {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
        }

        for (int i = 0; i < table.getColumnCount(); i++)
        {
            table.getColumn(i).pack();
        }

        return table;
    }

    private Composite getOverviewContent(CTabFolder tabFolder)
    {
        final Table table = createTable(tabFolder, new String[]
        {
                "Info Name", "Info Value"
        });

        Display.getCurrent().asyncExec(new Runnable()
        {
            @Override
            public void run()
            {
                List<String> systemInfo = CMDUtil.getSystemInfo();
                for (String lineData : systemInfo)
                {
                    TableItem item = new TableItem(table, SWT.NONE);
                    int lastIndexOf = lineData.lastIndexOf(": ");
                    if (lastIndexOf == 0xFFFFFFFF)
                    {
                        item.setText(new String[]
                        {
                                "", lineData.trim()
                        });
                    }
                    else
                    {
                        item.setText(new String[]
                        {
                                lineData.substring(0, lastIndexOf).trim(), lineData.substring(lastIndexOf + 0x2).trim()
                        });
                    }
                }

                for (int i = 0; i < table.getColumnCount(); i++)
                {
                    table.getColumn(i).pack();
                }
            }
        });
        // 创建action bar和menu bar
        contributeToActionBars();

        return table;
    }

    private TableItem createTableItem(Table table, String[] data)
    {
        TableItem tableItem = new TableItem(table, SWT.NONE);
        tableItem.setText(data);
        return tableItem;
    }

    private Table createTable(Composite parent, String[] columnNames)
    {
        final Table table = new Table(parent, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        for (int i = 0; i < columnNames.length; i++)
        {
            new TableColumn(table, SWT.NONE).setText(columnNames[i]);
        }
        return table;
    }

    // create menu and toolbar
    private void contributeToActionBars()
    {
        makeActions();

        IActionBars actionBars = getViewSite().getActionBars();
        fillToolsBar(actionBars.getToolBarManager());
        fillMenuBar(actionBars.getMenuManager());
    }

    private void makeActions()
    {
        // menu bar
        exportAction = new SystemInfoExportAction(getViewSite());
    }

    private void fillMenuBar(IMenuManager menuManager)
    {
        menuManager.add(exportAction);
    }

    private void fillToolsBar(IToolBarManager toolBarManager)
    {
        toolBarManager.add(exportAction);
    }

    @Override
    public void setFocus()
    {

    }

}
