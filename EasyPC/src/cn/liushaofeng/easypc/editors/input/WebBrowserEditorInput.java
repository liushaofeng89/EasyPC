package cn.liushaofeng.easypc.editors.input;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import cn.liushaofeng.easypc.app.Activator;

/**
 * web browser editor input
 * @author liushaofeng
 * @date 2015-6-5
 * @version 1.0.0
 */
public class WebBrowserEditorInput implements IEditorInput
{

    @Override
    public Object getAdapter(Class adapter)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean exists()
    {
        return false;
    }

    @Override
    public ImageDescriptor getImageDescriptor()
    {
        return Activator.getImageDescriptor("icons" + File.separator + "internal_browser.gif");
    }

    @Override
    public String getName()
    {
        return "Web Browser";
    }

    @Override
    public IPersistableElement getPersistable()
    {
        return null;
    }

    @Override
    public String getToolTipText()
    {
        return "Web Browser";
    }

}
