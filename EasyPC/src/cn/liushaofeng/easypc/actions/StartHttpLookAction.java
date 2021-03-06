package cn.liushaofeng.easypc.actions;

import java.io.File;

import org.eclipse.jface.action.Action;

import cn.liushaofeng.easypc.app.Activator;

/**
 * start http look action
 * @author liushaofeng
 * @date 2015-6-24上午07:51:04
 * @version 1.0.0
 */
public class StartHttpLookAction extends Action
{
    public static final String ID = "cn.liushaofeng.easypc.actions.starthttplookaction";
    private static final String ACTION_TEXT_START = "Start Http Look";
    private static final String ACTION_TIPS_START = "Click to start http look monitor";
    private static final String ACTION_TEXT_STOP = "Stop Http Look";
    private static final String ACTION_TIPS_STOP = "Click to stop http look monitor";

    private boolean isStart = false;

    /**
     * default constructor
     */
    public StartHttpLookAction()
    {
        super(ACTION_TEXT_START, Activator.getImageDescriptor("icons" + File.separator + "run.gif"));
    }

    @Override
    public void run()
    {
        isStart = true;
        setImageDescriptor(Activator.getImageDescriptor("icons" + File.separator + "stop.gif"));
    }

}
