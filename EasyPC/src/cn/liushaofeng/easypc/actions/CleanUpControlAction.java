package cn.liushaofeng.easypc.actions;

import java.io.File;

import org.eclipse.jface.action.Action;

import cn.liushaofeng.easypc.app.Activator;

/**
 * clean rubbish action
 * @author liushaofeng
 * @date 2015-5-11
 * @version 1.0.0
 */
public class CleanUpControlAction extends Action
{
    public static final String ID = "cn.liushaofeng.easypc.actions.cleanupaction";
    private static final String ACTION_TEXT_CLEAN_UP = "Clean Up";
    private static final String ACTION_TIPS_CLEAN_UP_START = "Click to start clean your system.";
    private static final String ACTION_TIPS_CLEAN_UP_STOP = "Click to stop this clean.";

    boolean flag = false;

    /**
     * default constructor
     */
    public CleanUpControlAction()
    {
        super(ACTION_TEXT_CLEAN_UP, Activator.getImageDescriptor("icons" + File.separator + "run.gif"));
        setToolTipText(ACTION_TIPS_CLEAN_UP_START);
    }

    @Override
    public void run()
    {
        if (flag)
        {
            setImageDescriptor(Activator.getImageDescriptor("icons" + File.separator + "run.gif"));
            setToolTipText(ACTION_TIPS_CLEAN_UP_START);
        }
        else
        {
            setImageDescriptor(Activator.getImageDescriptor("icons" + File.separator + "stop.gif"));
            setToolTipText(ACTION_TIPS_CLEAN_UP_STOP);
        }
        flag = !flag;
    }
}
