package cn.liushaofeng.easypc.views.listener;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.widgets.Display;

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
            String[] files = (String[]) event.data;
            for (String string : files)
            {
                File srcFile = new File(string);
                if (srcFile.isFile())
                {
                    hendleFile(srcFile, new File(getDesPath(event) + File.separator + srcFile.getName()));
                }
                else
                {

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

    private void hendleFile(File srcFile, File desFile)
    {
        // judge the destination file is exist or not
        if (desFile.exists())
        {
            boolean openQuestion = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(),
                "Remove File Notice", "The destination file is exist, do you want to override it?");
            if (openQuestion)
            {
                boolean copyFile = FileUtil.copyFile(srcFile, desFile);
                if (copyFile)
                {
                    FileUtil.deleteFile(srcFile);
                }
            }
        }
    }

    // get destination file directory
    private String getDesPath(DropTargetEvent event)
    {
        File targetFile = (File) event.item.getData();
        if (targetFile.isDirectory())
        {
            return targetFile.getAbsolutePath();
        }
        else
        {
            return targetFile.getParentFile().getAbsolutePath();
        }
    }
}
