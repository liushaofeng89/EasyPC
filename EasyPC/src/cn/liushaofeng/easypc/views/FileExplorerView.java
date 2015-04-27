package cn.liushaofeng.easypc.views;

import java.io.File;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import cn.liushaofeng.easypc.app.Activator;
import cn.liushaofeng.easypc.editors.TextEditor;
import cn.liushaofeng.easypc.editors.input.TextEditorInput;
import cn.liushaofeng.easypc.util.FileUtil;
import cn.liushaofeng.easypc.views.provider.FileTreeContentProvider;
import cn.liushaofeng.easypc.views.provider.FileTreeLabelProvider;

/**
 * 资源管理器视图
 * @author liushaofeng
 * @date 2015年4月24日
 * @version 1.0.0
 */
public class FileExplorerView extends ViewPart
{
    public static final String ID = "cn.liushaofeng.easypc.views.fileexplorerview";
    public static final String NAME = "File Explorer"; //$NON-NLS-1$
    public static final String TIPS = "File Explorer"; //$NON-NLS-1$

    private TreeViewer fileTree = null;
    private Vector<String> supportEditExtension = new Vector<String>();// 可以被文本编辑器打开的文件类型

    /**
     * default constructor
     */
    public FileExplorerView()
    {
        supportEditExtension.add("txt");
        supportEditExtension.add("xml");
        supportEditExtension.add("log");
        supportEditExtension.add("ini");
        supportEditExtension.add("html");
        supportEditExtension.add("htm");
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
        fileTree = new TreeViewer(parent, SWT.NONE);
        fileTree.setContentProvider(new FileTreeContentProvider());
        fileTree.setLabelProvider(new FileTreeLabelProvider());
        fileTree.setInput(File.listRoots());
        fileTree.addSelectionChangedListener(new ISelectionChangedListener()
        {
            @Override
            public void selectionChanged(SelectionChangedEvent arg0)
            {
                TreeSelection selection = (TreeSelection) arg0.getSelection();
                updateStatusLine((File) selection.getFirstElement());
            }
        });
        fileTree.getTree().addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetDefaultSelected(SelectionEvent e)
            {
                TreeItem treeItem = (TreeItem) e.item;
                File file = (File) treeItem.getData();
                if (supportEdit(file))
                {
                    TextEditorInput textEditorInput = new TextEditorInput(file);
                    try
                    {
                        IWorkbenchPage activePage = getViewSite().getWorkbenchWindow().getActivePage();
                        IEditorPart findEditor = activePage.findEditor(textEditorInput);
                        if (findEditor != null)
                        {
                            findEditor.setFocus();
                        } else
                        {
                            activePage.openEditor(textEditorInput, TextEditor.ID);
                        }
                    } catch (PartInitException e1)
                    {
                        Logger.getLogger(this.getClass()).error(e1.getMessage(), e1);
                    }
                }
            }
        });
    }

    @Override
    public void setFocus()
    {

    }

    private void updateStatusLine(File file)
    {
        IStatusLineManager statusLineManager = getViewSite().getActionBars().getStatusLineManager();
        statusLineManager.setMessage(file.getPath() + getShowSize(file));
    }

    private String getShowSize(File file)
    {
        String wasteSpace = FileUtil.getWasteSpace(file);
        return wasteSpace.isEmpty() ? "" : " (" + wasteSpace + ")";
    }

    private boolean supportEdit(File f)
    {
        return supportEditExtension.contains(f.getName().substring(f.getName().lastIndexOf(".") + 1));
    }
}
