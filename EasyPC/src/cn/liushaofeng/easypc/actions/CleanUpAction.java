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
public class CleanUpAction extends Action
{
    public static final String ID = "cn.liushaofeng.easypc.actions.cleanupaction";
    private static final String ACTION_TEXT_CLEAN_UP = "Clean Up";
    private static final String ACTION_TIPS_CLEAN_UP = "Click to start clean the unused files.";

    /**
     * default constructor
     */
    public CleanUpAction()
    {
        super(ACTION_TEXT_CLEAN_UP, Activator.getImageDescriptor("icons" + File.separator + "cleanup.gif"));
    }

    @Override
    public void setToolTipText(String toolTipText)
    {
        super.setToolTipText(ACTION_TIPS_CLEAN_UP);
    }

    @Override
    public void run()
    {
        super.run();
    }
}
