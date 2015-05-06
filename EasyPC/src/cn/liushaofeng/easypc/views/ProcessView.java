package cn.liushaofeng.easypc.views;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ProcessView extends ViewPart
{
    public static final String ID = "cn.liushaofeng.easypc.views.processview";
    private TableViewer tableViewewr;
    private String[] titles = new String[]
    { "", "" };

    @Override
    public void createPartControl(Composite parent)
    {
        tableViewewr = new TableViewer(parent);
        tableViewewr.setContentProvider(null);
        tableViewewr.setLabelProvider(null);
        tableViewewr.setInput(null);
    }

    @Override
    public void setFocus()
    {

    }

}
