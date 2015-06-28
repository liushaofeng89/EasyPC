package cn.liushaofeng.easypc.actions;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;

import cn.liushaofeng.easypc.app.Activator;

/**
 * Collapse all file folder action
 * @author liushaofeng
 * @date 2015-6-28下午11:45:26
 * @version 1.0.0
 */
public class CollapseAllAction extends Action
{
    private TreeViewer fileTreeViewer;

    /**
     * default constructor
     * @param fileTreeViewer
     */
    public CollapseAllAction(TreeViewer fileTreeViewer)
    {
        super("Collapse All", Activator.getImageDescriptor("icons" + File.separator + "collapseall.gif"));
        this.fileTreeViewer = fileTreeViewer;
    }

    @Override
    public void run()
    {
        fileTreeViewer.collapseAll();
    }

}
