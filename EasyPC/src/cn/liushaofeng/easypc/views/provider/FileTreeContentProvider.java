package cn.liushaofeng.easypc.views.provider;

import java.io.File;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

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
        if (arg0 instanceof File)
        {
            File f = (File) arg0;
            if (f.isDirectory())
            {
                return f.listFiles();
            }
            return null;
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
        return null;
    }

    @Override
    public Object getParent(Object arg0)
    {
        if (arg0 instanceof File)
        {
            File f = (File) arg0;
            return f.getParentFile();
        }
        return null;
    }

    @Override
    public boolean hasChildren(Object arg0)
    {
        if (arg0 instanceof File)
        {
            File f = (File) arg0;
            if (f.isDirectory())
            {
                return true;
            }
            return false;
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

    }
}
