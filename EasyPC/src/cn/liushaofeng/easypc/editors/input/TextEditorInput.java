package cn.liushaofeng.easypc.editors.input;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import cn.liushaofeng.easypc.app.Activator;

/**
 * text editor input
 * @author liushaofeng
 * @date 2015年4月26日
 * @version 1.0.0
 */
public class TextEditorInput implements IEditorInput
{
    private String input;

    public TextEditorInput(String input)
    {
        this.input = input;
    }

    @Override
    public Object getAdapter(Class adapter)
    {
        // TODO Auto-generated method stub
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
        return Activator.getImageDescriptor("icons" + File.separator + "home.gif");
    }

    @Override
    public String getName()
    {
        return input;
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
        return input;
    }

}
