package cn.liushaofeng.easypc.views.listener;

import java.io.File;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;

import cn.liushaofeng.easypc.util.FileUtil;

/**
 * file tree drop support listener
 * @author liushaofeng
 * @date 2015-5-12
 * @version 1.0.0
 */
public class FileDropListener implements DropTargetListener
{

    private TreeViewer fileTreeViewer;

    /**
     * default constructor
     * @param fileTreeViewer file tree viewer
     */
    public FileDropListener(TreeViewer fileTreeViewer)
    {
        this.fileTreeViewer = fileTreeViewer;
    }

    @Override
    public void dragEnter(DropTargetEvent event)
    {
    }

    @Override
    public void dragOperationChanged(DropTargetEvent event)
    {
    }

    @Override
    public void dragOver(DropTargetEvent event)
    {
        event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SELECT;
    }

    @Override
    public void drop(DropTargetEvent event)
    {
        if (event.item == null)
        {
            event.detail = DND.DROP_NONE;
            return;
        }
        if (FileTransfer.getInstance().isSupportedType(event.currentDataType))
        {
            File targetFile = (File) event.item.getData();
            File parentFile = targetFile.isDirectory() ? targetFile : targetFile.getParentFile();

            String[] files = (String[]) event.data;
            for (String string : files)
            {
                File srcFile = new File(string);
                boolean copyFile = FileUtil.copyFile(srcFile, new File(parentFile.getPath()
                    + File.separator + srcFile.getName()));
                if (copyFile)
                {
                    FileUtil.deleteFile(string);
                }
            }
            fileTreeViewer.refresh();
        }
    }

    @Override
    public void dropAccept(DropTargetEvent event)
    {

    }

    @Override
    public void dragLeave(DropTargetEvent event)
    {

    }
}
