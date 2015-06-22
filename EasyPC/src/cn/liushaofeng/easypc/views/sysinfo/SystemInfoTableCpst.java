package cn.liushaofeng.easypc.views.sysinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.hyperic.sigar.Sigar;

/**
 * System info composite
 * @author liushaofeng
 * @date 2015-6-21 下午10:17:40
 * @version 1.0.0
 */
public abstract class SystemInfoTableCpst extends Composite
{
    protected Map<String, Integer> columnMap = new HashMap<String, Integer>();
    protected TableViewer tableViewer = null;
    protected Sigar sigar;

    protected String[] columnsStr = new String[0];

    /**
     * default constructor
     * @param parent parent composite
     * @param sigar sigar object
     */
    public SystemInfoTableCpst(Composite parent, Sigar sigar)
    {
        super(parent, SWT.NONE);
        this.sigar = sigar;
        initTable();
    }

    private void initTable()
    {
        setLayout(new FillLayout());

        initColumnNames();

        for (int i = 0; i < columnsStr.length; i++)
        {
            columnMap.put(columnsStr[i], i);
        }

        tableViewer = new TableViewer(this, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
        tableViewer.setContentProvider(new SystemInfoTableContentProvider());
        tableViewer.setLabelProvider(setLabelProvider());
        tableViewer.getTable().setHeaderVisible(true);
        tableViewer.getTable().setLinesVisible(true);

        for (int i = 0; i < columnsStr.length; i++)
        {
            final TableColumn indexColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
            indexColumn.setText(columnsStr[i]);
        }
        tableViewer.setInput(setInput());

        for (int i = 0; i < tableViewer.getTable().getColumnCount(); i++)
        {
            tableViewer.getTable().getColumn(i).pack();
        }
    }

    /**
     * CPU table content provider
     * @author liushaofeng
     * @date 2015-6-10下午11:32:14
     * @version 1.0.0
     */
    private class SystemInfoTableContentProvider implements IStructuredContentProvider
    {

        @SuppressWarnings("unchecked")
        @Override
        public Object[] getElements(Object inputElement)
        {
            if (inputElement instanceof List)
            {
                return ((List<SystemInfoDataModel>) inputElement).toArray();
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
     * set columns names value to field "columnsStr"
     */
    protected abstract void initColumnNames();

    /**
     * set table label provider
     * @return table label provider
     */
    protected abstract ITableLabelProvider setLabelProvider();

    /**
     * set table data input
     * @return table data input
     */
    protected abstract List<SystemInfoDataModel> setInput();
}
