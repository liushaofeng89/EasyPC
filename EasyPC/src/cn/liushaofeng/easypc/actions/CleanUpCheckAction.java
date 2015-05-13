package cn.liushaofeng.easypc.actions;

import java.io.File;

import org.eclipse.jface.action.Action;

import cn.liushaofeng.easypc.app.Activator;

/**
 * clean up view check all or not action
 * @author liushaofeng
 * @date 2015-5-13
 * @version 1.0.0
 */
public class CleanUpCheckAction extends Action
{

    public static final String ID = "cn.liushaofeng.easypc.actions.cleanupcheckaction";
    private static final String ACTION_TEXT_CLEAN_UP_SELECT = "Check Switch";
    private static final String ACTION_TIPS_CLEAN_UP_SELECT = "Click to select all.";
    private static final String ACTION_TIPS_CLEAN_UP_UNSELECT = "Click to unselect all.";

    boolean flag = false;

    /**
     * default constructor
     */
    public CleanUpCheckAction()
    {
        super(ACTION_TEXT_CLEAN_UP_SELECT, Activator.getImageDescriptor("icons" + File.separator + "check.gif"));
        setToolTipText(ACTION_TIPS_CLEAN_UP_UNSELECT);
    }

    @Override
    public void run()
    {
        if (flag)
        {
            setImageDescriptor(Activator.getImageDescriptor("icons" + File.separator + "check.gif"));
            setToolTipText(ACTION_TIPS_CLEAN_UP_UNSELECT);
        }
        else
        {
            setImageDescriptor(Activator.getImageDescriptor("icons" + File.separator + "uncheck.gif"));
            setToolTipText(ACTION_TIPS_CLEAN_UP_SELECT);
        }
        flag = !flag;
    }
}
