package cn.liushaofeng.easypc.views.listener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

import cn.liushaofeng.easypc.editors.TextEditor;
import cn.liushaofeng.easypc.editors.input.TextEditorInput;
import cn.liushaofeng.easypc.util.CMDUtil;

/**
 * file double click event(open this file by default way)
 * @author liushaofeng
 * @date 2015-5-14
 * @version 1.0.0
 */
public class FileSelectionListener extends SelectionAdapter
{
    private Vector<String> supportEditExtension = new Vector<String>();// 可以被文本编辑器打开的文件类型
    private Map<String, TextEditorInput> dataMap = new HashMap<String, TextEditorInput>();
    private IViewSite viewSite = null;
    private TreeViewer fileTreeViewer = null;

    /**
     * default constructor
     * @param fileTreeViewer
     * @param viewSite current viewSite
     */
    public FileSelectionListener(TreeViewer fileTreeViewer, IViewSite iViewSite)
    {
        this.fileTreeViewer = fileTreeViewer;
        this.viewSite = iViewSite;

        supportEditExtension.add("txt");
        supportEditExtension.add("xml");
        supportEditExtension.add("log");
        supportEditExtension.add("ini");
        supportEditExtension.add("html");
        supportEditExtension.add("htm");
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e)
    {
        TreeItem treeItem = (TreeItem) e.item;
        File file = (File) treeItem.getData();
        if (file.isDirectory())
        {
            treeItem.setExpanded(!treeItem.getExpanded());
            fileTreeViewer.refresh();
            return;
        }
        if (supportEdit(file))
        {
            IWorkbenchPage activePage = viewSite.getWorkbenchWindow().getActivePage();
            TextEditorInput editorInput = dataMap.get(file.getAbsolutePath());
            if (editorInput == null)
            {
                editorInput = new TextEditorInput(file);
                dataMap.put(file.getAbsolutePath(), editorInput);
            }
            IEditorPart findEditor = activePage.findEditor(editorInput);
            if (findEditor == null)
            {
                openEditor(activePage, editorInput);
                return;
            }
            activePage.activate(findEditor);
        }
        else
        {
            CMDUtil.openFileByDefaultWay(file);
        }
    }

    private void openEditor(IWorkbenchPage activePage, TextEditorInput editorInput)
    {
        try
        {
            activePage.openEditor(editorInput, TextEditor.ID);
        }
        catch (PartInitException e1)
        {
            Logger.getLogger(this.getClass()).error(e1.getMessage(), e1);
        }
    }

    private boolean supportEdit(File f)
    {
        return supportEditExtension.contains(f.getName().substring(f.getName().lastIndexOf(".") + 1));
    }
}
