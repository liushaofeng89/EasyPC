package cn.liushaofeng.easypc.views.provider;

import java.io.File;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import cn.liushaofeng.easypc.model.tree.EPTree;

/**
 * 资源管理器内容提供器
 * @author liushaofeng
 * @date 2015年4月24日
 * @version 1.0.0
 */
public class FileTreeContentProvider implements ITreeContentProvider
{

    @Override
    public Object[] getChildren(Object arg0)
    {
        if (arg0 instanceof EPTree)
        {
            EPTree tree = (EPTree) arg0;
            return tree.getChildren().toArray();
        }
        return new Object[0];
    }

    @Override
    public Object[] getElements(Object arg0)
    {
        if (arg0 instanceof File[])
        {
            return (File[]) arg0;
        }
        if (arg0 instanceof EPTree)
        {
            EPTree tree = (EPTree) arg0;
            return tree.getChildren().toArray();
        }
        return null;
    }

    @Override
    public Object getParent(Object arg0)
    {
        if (arg0 instanceof EPTree)
        {
            EPTree tree = (EPTree) arg0;
            return tree.getParent();
        }
        return null;
    }

    @Override
    public boolean hasChildren(Object arg0)
    {
        if (arg0 instanceof EPTree)
        {
            EPTree tree = (EPTree) arg0;
            return tree.hasChild();
        }
        return false;
    }

    @Override
    public void inputChanged(Viewer arg0, Object arg1, Object arg2)
    {

    }

    @Override
    public void dispose()
    {
        // TODO Auto-generated method stub

    }
}
