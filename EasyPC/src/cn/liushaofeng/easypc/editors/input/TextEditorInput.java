package cn.liushaofeng.easypc.editors.input;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.ISharedImages;

import cn.liushaofeng.easypc.app.Activator;

/**
 * text editor input
 * @author liushaofeng
 * @date 2015年4月26日
 * @version 1.0.0
 */
public class TextEditorInput implements IEditorInput
{
    private File file;

    public TextEditorInput(File file)
    {
        this.file = file;
    }

    @Override
    public Object getAdapter(Class adapter)
    {
        return null;
    }

    @Override
    public boolean exists()
    {
        return true;
    }

    @Override
    public ImageDescriptor getImageDescriptor()
    {
        return Activator.getSysImageDescriptor(ISharedImages.IMG_OBJ_FILE);
    }

    @Override
    public String getName()
    {
        return file.getName();
    }

    @Override
    public IPersistableElement getPersistable()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getToolTipText()
    {
        return file.getName();
    }

    public File getFile()
    {
        return file;
    }

}
