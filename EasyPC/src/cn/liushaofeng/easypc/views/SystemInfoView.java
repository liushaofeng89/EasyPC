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
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
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

        CTabItem diskItem = new CTabItem(tabFolder, SWT.None);
        diskItem.setText(TAB_ITEM_DISK);
        diskItem.setControl(getDiskInfo(tabFolder));

        CTabItem mainBoardItem = new CTabItem(tabFolder, SWT.None);
        mainBoardItem.setText(TAB_ITEM_MAIN_BOARD);
        mainBoardItem.setControl(getMainboardContent(tabFolder));

        CTabItem graphicsItem = new CTabItem(tabFolder, SWT.None);
        graphicsItem.setText(TAB_ITEM_GRAPHICS);

        CTabItem monitorItem = new CTabItem(tabFolder, SWT.None);
        monitorItem.setText(TAB_ITEM_MONITOR);

        CTabItem networkItem = new CTabItem(tabFolder, SWT.None);
        networkItem.setText(TAB_ITEM_NETWORK_CARD);

        CTabItem audioItem = new CTabItem(tabFolder, SWT.None);
        audioItem.setText(TAB_ITEM_AUDIO);

        CTabItem otherItem = new CTabItem(tabFolder, SWT.None);
        otherItem.setText(TAB_ITEM_OTHER);

        tabFolder.setSelection(0x0);
    }

    private Control getDiskInfo(CTabFolder tabFolder)
    {
        FileSystem fslist[];
        try
        {
            fslist = sigar.getFileSystemList();
        }
        catch (SigarException e)
        {
            Label label = new Label(tabFolder, SWT.NONE);
            label.setText("Get Disk information failed!");
            return label;
        }
        // driver name
        String[] deviceNames = new String[fslist.length + 1];
        deviceNames[0] = "Type";
        for (int i = 1; i <= fslist.length; i++)
        {
            deviceNames[i] = "Disk " + i;
        }
        // dir name
        String[] dirNames = new String[fslist.length + 1];
        dirNames[0] = "Directory Name";
        // File system flags
        Object[] fileSysFlags = new Object[fslist.length + 1];
        fileSysFlags[0] = "File system flags";
        // Get the File system os specific type name
        String[] sysTypeNames = new String[fslist.length + 1];
        sysTypeNames[0] = "System type name";
        // local disk、driver、network file system
        String[] typeNames = new String[fslist.length + 1];
        typeNames[0] = "Type name";
        // file system type
        Object[] fileSysType = new Object[fslist.length + 1];
        fileSysType[0] = "File system type";
        // total size
        Object[] totalSize = new Object[fslist.length + 1];
        totalSize[0] = "File system total size";
        // remain size
        Object[] remainSize = new Object[fslist.length + 1];
        remainSize[0] = "File system remain size";
        // available size
        Object[] availSize = new Object[fslist.length + 1];
        availSize[0] = "File system available size";
        // used size
        Object[] usedSize = new Object[fslist.length + 1];
        usedSize[0] = "File system used size";
        // used percent
        Object[] userPercent = new Object[fslist.length + 1];
        userPercent[0] = "File system used percentage";
        // Number of physical disk reads
        Object[] reads = new Object[fslist.length + 1];
        reads[0] = "Number of physical disk reads";
        // Get the Number of physical disk writes.
        Object[] writes = new Object[fslist.length + 1];
        writes[0] = "Number of physical disk writes";

        for (int i = 0; i < fslist.length; i++)
        {
            dirNames[i + 1] = fslist[i].getDirName();
            fileSysFlags[i + 1] = fslist[i].getFlags();
            sysTypeNames[i + 1] = fslist[i].getSysTypeName();// FAT32、NTFS
            typeNames[i + 1] = fslist[i].getTypeName();// local disk、driver、network file system
            fileSysType[i + 1] = fslist[i].getType();
            try
            {
                FileSystemUsage usage = sigar.getFileSystemUsage(fslist[i].getDirName());
                switch (fslist[i].getType())
                {
                    case 0: // TYPE_UNKNOWN ：未知
                        break;
                    case 1: // TYPE_NONE
                        break;
                    case 2: // TYPE_LOCAL_DISK : 本地硬盘
                        // 文件系统总大小
                        totalSize[i + 1] = usage.getTotal() + "KB";
                        // 文件系统剩余大小
                        remainSize[i + 1] = usage.getFree() + "KB";
                        // 文件系统可用大小
                        availSize[i + 1] = usage.getAvail() + "KB";
                        // 文件系统已经使用量
                        usedSize[i + 1] = usage.getUsed() + "KB";
                        // 文件系统资源的利用率
                        userPercent[i + 1] = usage.getUsePercent() * 100D + "%";
                        reads[i + 1] = usage.getDiskReads();
                        writes[i + 1] = usage.getDiskWrites();
                        break;
                    case 3:// TYPE_NETWORK ：网络
                        break;
                    case 4:// TYPE_RAM_DISK ：闪存
                        break;
                    case 5:// TYPE_CDROM ：光驱
                        break;
                    case 6:// TYPE_SWAP ：页面交换
                        break;
                }
            }
            catch (SigarException e)
            {
                Logger.getLogger(this.getClass()).error(e.getMessage(), e);
            }
        }

        final Table table = createTable(tabFolder, deviceNames);
        createTableItem(table, dirNames);
        createTableItem(table, fileSysFlags);
        createTableItem(table, sysTypeNames);
        createTableItem(table, typeNames);
        createTableItem(table, fileSysType);
        createTableItem(table, totalSize);
        createTableItem(table, remainSize);
        createTableItem(table, availSize);
        createTableItem(table, usedSize);
        createTableItem(table, userPercent);
        createTableItem(table, reads);
        createTableItem(table, writes);

        for (int i = 0; i < table.getColumnCount(); i++)
        {
            table.getColumn(i).pack();
        }
        return table;
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

    private TableItem createTableItem(Table table, Object[] data)
    {
        TableItem tableItem = new TableItem(table, SWT.NONE);
        String[] text = new String[data.length];
        for (int i = 0; i < data.length; i++)
        {
            if (data[i] instanceof String)
            {
                text[i] = (String) data[i];
            }
            else
            {
                text[i] = String.valueOf(data[i]);
            }
        }
        tableItem.setText(text);
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
