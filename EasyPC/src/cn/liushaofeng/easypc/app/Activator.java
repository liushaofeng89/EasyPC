package cn.liushaofeng.easypc.app;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{
    // The plug-in ID
    public static final String PLUGIN_ID = "EasyPC"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;

    /**
     * The constructor
     */
    public Activator()
    {
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext context) throws Exception
    {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    public void stop(BundleContext context) throws Exception
    {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * @return the shared instance
     */
    public static Activator getDefault()
    {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path)
    {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * get a image object by input path
     * @param isSysImage system image path
     * @param path image icon path
     * @return image object
     */
    public static Image getImage(boolean isSysImage, String path)
    {
        if (getDefault() == null)
        {
            return null;
        }
        ImageRegistry registry = getDefault().getImageRegistry();
        Image image = registry.get(path);
        if (image != null)
        {
            return image;
        }
        if (isSysImage)
        {
            registry.put(path, getSysImageDescriptor(path));
        } else
        {
            registry.put(path, getImageDescriptor(path));
        }
        return registry.get(path);
    }

    /**
     * get image descriptor
     * @param path image path(such as:ISharedImages.IMG_DEF_VIEW)
     * @return ImageDescriptor
     */
    public static ImageDescriptor getSysImageDescriptor(String path)
    {
        return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(path);
    }

}
