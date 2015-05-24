package cn.liushaofeng.easypc.views;

import java.io.File;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

import cn.liushaofeng.easypc.app.Activator;
import cn.liushaofeng.easypc.util.FileUtil;
import cn.liushaofeng.easypc.views.listener.FileDragListener;
import cn.liushaofeng.easypc.views.listener.FileDropListener;
import cn.liushaofeng.easypc.views.listener.FileKeyListener;
import cn.liushaofeng.easypc.views.listener.FileMenuDetectListener;
import cn.liushaofeng.easypc.views.listener.FileSelectionListener;
import cn.liushaofeng.easypc.views.provider.FileTreeContentProvider;
import cn.liushaofeng.easypc.views.provider.FileTreeLabelProvider;

/**
 * file resource manager view
 * @author liushaofeng
 * @date 2015年4月24日
 * @version 1.0.0
 */
public class FileExplorerView extends ViewPart
{
    public static final String ID = "cn.liushaofeng.easypc.views.fileexplorerview";
    public static final String NAME = "File Explorer"; //$NON-NLS-1$
    public static final String TIPS = "File Explorer"; //$NON-NLS-1$

    private TreeViewer fileTreeViewer = null;
    private Clipboard clipboard = null;

    /**
     * default constructor
     */
    public FileExplorerView()
    {
        this.clipboard = new Clipboard(Display.getCurrent());
    }

    @Override
    protected void setPartName(String partName)
    {
        super.setPartName(FileExplorerView.NAME);
    }

    @Override
    protected void setTitleImage(Image titleImage)
    {
        super.setTitleImage(Activator.getImage(false, "icons" + File.separator + "package.gif"));
    }

    @Override
    protected void setTitleToolTip(String toolTip)
    {
        super.setTitleToolTip(FileExplorerView.NAME);
    }

    @Override
    public void createPartControl(Composite parent)
    {
        fileTreeViewer = new TreeViewer(parent, SWT.MULTI | SWT.FULL_SELECTION);
        fileTreeViewer.setContentProvider(new FileTreeContentProvider());
        fileTreeViewer.setLabelProvider(new FileTreeLabelProvider());
        fileTreeViewer.setInput(File.listRoots());

        fileTreeViewer.addDragSupport(DND.DROP_MOVE, new Transfer[]
        {
            FileTransfer.getInstance()
        }, new FileDragListener(fileTreeViewer));
        fileTreeViewer.addDropSupport(DND.DROP_MOVE | DND.DROP_DEFAULT, new Transfer[]
        {
            FileTransfer.getInstance()
        }, new FileDropListener(fileTreeViewer));

        fileTreeViewer.addSelectionChangedListener(new ISelectionChangedListener()
        {
            @Override
            public void selectionChanged(SelectionChangedEvent arg0)
            {
                TreeSelection selection = (TreeSelection) arg0.getSelection();
                updateStatusLine((File) selection.getFirstElement());
            }
        });
        fileTreeViewer.getTree().addSelectionListener(new FileSelectionListener(fileTreeViewer, getViewSite()));
        FileMenuDetectListener menuDetectListner = new FileMenuDetectListener(fileTreeViewer, clipboard);
        fileTreeViewer.getTree().addMenuDetectListener(menuDetectListner);
        fileTreeViewer.getTree().addKeyListener(new FileKeyListener(fileTreeViewer, menuDetectListner, clipboard));
    }

    @Override
    public void setFocus()
    {

    }

    private void updateStatusLine(File file)
    {
        if (file != null)
        {
            IStatusLineManager statusLineManager = getViewSite().getActionBars().getStatusLineManager();
            statusLineManager.setMessage(file.getPath() + getShowSize(file));
        }
    }

    private String getShowSize(File file)
    {
        String wasteSpace = FileUtil.getWasteSpace(file);
        return wasteSpace.isEmpty() ? "" : " (" + wasteSpace + ")";
    }

}
