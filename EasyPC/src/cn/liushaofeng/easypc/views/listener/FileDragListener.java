package cn.liushaofeng.easypc.views.listener;

import java.io.File;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * file tree drag support listener
 * @author liushaofeng
 * @date 2015-5-12
 * @version 1.0.0
 */
public class FileDragListener implements DragSourceListener
{

    private Tree fileTree;

    /**
     * default constructor
     * @param fileTreeViewer file tree viewer
     */
    public FileDragListener(TreeViewer fileTreeViewer)
    {
        this.fileTree = fileTreeViewer.getTree();
    }

    @Override
    public void dragStart(DragSourceEvent event)
    {
        if (fileTree.getSelection().length == 0)
        {
            event.doit = false;
        }
    }

    @Override
    public void dragSetData(DragSourceEvent event)
    {
        if (FileTransfer.getInstance().isSupportedType(event.dataType))
        {
            TreeItem[] selection = fileTree.getSelection();
            String[] pathArray = new String[selection.length];

            for (int i = 0; i < selection.length; i++)
            {
                pathArray[i] = ((File) selection[i].getData()).getAbsolutePath();
            }
            event.data = pathArray;
        }
    }

    @Override
    public void dragFinished(DragSourceEvent event)
    {
    }

}
