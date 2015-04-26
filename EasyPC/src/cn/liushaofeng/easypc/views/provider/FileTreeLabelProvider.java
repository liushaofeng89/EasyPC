package cn.liushaofeng.easypc.views.provider;

import java.io.File;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import cn.liushaofeng.easypc.app.Activator;

/**
 * 文件资源管理器标签提供器
 * @author liushaofeng
 * @date 2015年4月24日
 * @version 1.0.0
 */
public class FileTreeLabelProvider implements ILabelProvider
{

    @Override
    public Image getImage(Object arg0)
    {
        if (arg0 instanceof File)
        {
            File f = (File) arg0;
            return Activator.getImage(f);
        }
        return null;
    }

    @Override
    public String getText(Object arg0)
    {
        if (arg0 instanceof File)
        {
            File f = (File) arg0;
            if (f.getParentFile() == null)
            {
                return f.getPath();
            }
            return f.getName();
        }
        return null;
    }

    @Override
    public void addListener(ILabelProviderListener arg0)
    {

    }

    @Override
    public void dispose()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isLabelProperty(Object arg0, String arg1)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener arg0)
    {
        // TODO Auto-generated method stub

    }

}
