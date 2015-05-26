package cn.liushaofeng.easypc.views.network;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import cn.liushaofeng.easypc.views.provider.FileTreeContentProvider;
import cn.liushaofeng.easypc.views.provider.FileTreeLabelProvider;

/**
 * network viewer for showa all computers in current LAN
 * @author liushaofeng
 * @date 2015-5-24
 * @version 1.0.0
 */
public class NetworkView extends ViewPart
{
    public static final String ID = "cn.liushaofeng.easypc.views.network.networkview";
    private TreeViewer treeViewer = null;

    @Override
    public void init(IViewSite site, IMemento memento) throws PartInitException
    {
        Display.getCurrent().asyncExec(new Runnable()
        {
            @Override
            public void run()
            {

            }
        });
    }

    @Override
    public void createPartControl(Composite parent)
    {
        treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.FULL_SELECTION);
        treeViewer.setContentProvider(new FileTreeContentProvider());
        treeViewer.setLabelProvider(new FileTreeLabelProvider());
    }

    @Override
    public void setFocus()
    {

    }

}
