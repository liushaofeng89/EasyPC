package cn.liushaofeng.easypc.actions;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;

import cn.liushaofeng.easypc.app.Activator;
import cn.liushaofeng.easypc.ui.dialog.SystemInfoDialog;

/**
 * View Compute System Information
 * @author liushaofeng
 * @date 2015年5月1日
 * @version 1.0.0
 */
public class SystemInfoAction extends Action
{
    public static final String ID = "cn.liushaofeng.easypc.actions.computerinfoaction";
    private static final String TIPS = "Show System Info";

    public SystemInfoAction()
    {
        super("Computer Info", Activator.getImageDescriptor("icons" + File.separator + "info_obj.gif"));
    }

    @Override
    public void run()
    {
        // open the system information dialog
        new SystemInfoDialog(Display.getCurrent().getActiveShell()).open();
    }

    @Override
    public String getToolTipText()
    {
        return SystemInfoAction.TIPS;
    }

}
