package cn.liushaofeng.easypc.views.listener;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

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
        switch (e.keyCode)
        {
            case SWT.F2:
                menuDetectListner.renameFile();
                break;
            case SWT.F5:
                fileTreeViewer.refresh();
                break;
            case SWT.DEL:

                break;

            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

}
