package cn.liushaofeng.easypc.views.listener;

import java.io.File;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.FileTransfer;
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
    private Clipboard clipboard;

    /**
     * default constructor
     * @param fileTreeViewer
     * @param menuDetectListner FileMenuDetectListener
     * @param clipboard clipboard
     */
    public FileKeyListener(TreeViewer fileTreeViewer, FileMenuDetectListener menuDetectListner, Clipboard clipboard)
    {
        this.fileTreeViewer = fileTreeViewer;
        this.menuDetectListner = menuDetectListner;
        this.clipboard = clipboard;
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
            case 99:// 'C'
                if ((e.stateMask & SWT.CTRL) != 0)
                {
                    String[] filesPath = new String[selectFiles.length];
                    for (int i = 0; i < selectFiles.length; i++)
                    {
                        filesPath[i] = selectFiles[i].getAbsolutePath();
                    }
                    clipboard.setContents(new Object[]
                    {
                        filesPath
                    }, new FileTransfer[]
                    {
                        FileTransfer.getInstance()
                    });
                }
                break;
            case 118:// 'V'
                if ((e.stateMask & SWT.CTRL) != 0)
                {
                    menuDetectListner.doCopyFile(selectFiles[0].isDirectory() ? selectFiles[0] : selectFiles[0]
                        .getParentFile());
                    fileTreeViewer.refresh();
                }
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
