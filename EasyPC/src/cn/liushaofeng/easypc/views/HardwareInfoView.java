package cn.liushaofeng.easypc.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * Hardware information
 * @author liushaofeng
 * @date 2015年4月27日
 * @version 1.0.0
 */
public class HardwareInfoView extends ViewPart
{
    public static final String ID = "cn.liushaofeng.easypc.views.hardwareinfoview";
    private TreeViewer treeViewer;

    @Override
    public void createPartControl(Composite parent)
    {
        treeViewer = new TreeViewer(parent, SWT.NONE);
        treeViewer.setContentProvider(null);
        treeViewer.setLabelProvider(null);
        treeViewer.setInput(null);
    }

    @Override
    public void setFocus()
    {
        treeViewer.getTree().setFocus();
    }

}
