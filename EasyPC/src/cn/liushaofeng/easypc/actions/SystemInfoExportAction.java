package cn.liushaofeng.easypc.actions;

import java.io.File;

import org.eclipse.jface.action.Action;

import cn.liushaofeng.easypc.app.Activator;

/**
 * export system info to text file
 * @author liushaofeng
 * @date 2015-5-22
 * @version 1.0.0
 */
public class SystemInfoExportAction extends Action
{
    /**
     * default constructor
     */
    public SystemInfoExportAction()
    {
        super("Export System Info", Activator.getImageDescriptor("icons" + File.separator + "export_obj.gif"));
    }

    @Override
    public String getToolTipText()
    {
        return "Export system info to text file";
    }

    @Override
    public void run()
    {
        super.run();
    }
}
