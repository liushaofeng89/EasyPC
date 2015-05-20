package cn.liushaofeng.easypc.views.listener;

import java.io.File;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.TreeItem;

/**
 * file tree key listener
 * @author liushaofeng
 * @date 2015-5-18
 * @version 1.0.0
 */
public class FileKeyListener implements KeyListener
{

    private TreeViewer fileTreeViewer;
    private FileMenuDetectListener menuDetectListner;

    /**
     * default constructor
     * @param fileTreeViewer
     * @param menuDetectListner FileMenuDetectListener
     */
    public FileKeyListener(TreeViewer fileTreeViewer, FileMenuDetectListener menuDetectListner)
    {
        this.fileTreeViewer = fileTreeViewer;
        this.menuDetectListner = menuDetectListner;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        File[] selectFiles = getSelectFiles();
        if (selectFiles.length == 0x0)
        {
            return;
        }
        switch (e.keyCode)
        {
            case SWT.F2:
                if (selectFiles.length > 0x0)
                {
                    return;
                }
                menuDetectListner.renameFile(selectFiles[0]);
                break;
            case SWT.F5:
                fileTreeViewer.refresh();
                break;
            case SWT.DEL:
                menuDetectListner.deleteFile(selectFiles);
                break;

            default:
                break;
        }
    }

    private File[] getSelectFiles()
    {
        TreeItem[] selection = fileTreeViewer.getTree().getSelection();
        File[] selectedFiles = new File[selection.length];
        for (int i = 0; i < selection.length; i++)
        {
            selectedFiles[i] = (File) selection[i].getData();
        }
        return selectedFiles;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

}
