package cn.liushaofeng.easypc.views;

import java.io.File;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import cn.liushaofeng.easypc.app.Activator;
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
    public static final String NAME = "资源管理器";
    public static final String TIPS = "资源管理器";

    private TreeViewer fileTree = null;

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

    }

    @Override
    public void setFocus()
    {

    }

}
