package cn.liushaofeng.easypc.views;

import java.io.File;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

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

    private TreeViewer fileTree = null;

    @Override
    public void createPartControl(Composite parent)
    {
        fileTree = new TreeViewer(parent, SWT.NONE);
        fileTree.setContentProvider(new FileTreeContentProvider());
        fileTree.setLabelProvider(new FileTreeLabelProvider());
        fileTree.setInput(File.listRoots());
    }

    @Override
    public void setFocus()
    {

    }

}
