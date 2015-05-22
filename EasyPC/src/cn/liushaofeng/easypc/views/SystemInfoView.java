package cn.liushaofeng.easypc.views;

import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

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

        CTabItem cpuItem = new CTabItem(tabFolder, SWT.None);
        cpuItem.setText(TAB_ITEM_CPU);

        CTabItem mainBoardItem = new CTabItem(tabFolder, SWT.None);
        mainBoardItem.setText(TAB_ITEM_MAIN_BOARD);

        CTabItem memoryItem = new CTabItem(tabFolder, SWT.None);
        memoryItem.setText(TAB_ITEM_MEMORY);

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

    private Composite getOverviewContent(CTabFolder tabFolder)
    {
        Table table = new Table(tabFolder, SWT.SINGLE | SWT.FULL_SELECTION);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        final TableColumn column1 = new TableColumn(table, SWT.NONE);
        column1.setText("Info Name");
        final TableColumn column2 = new TableColumn(table, SWT.NONE);
        column2.setText("Info Value");

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
        // 创建action bar和menu bar
        contributeToActionBars();

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
        exportAction = new SystemInfoExportAction();
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
