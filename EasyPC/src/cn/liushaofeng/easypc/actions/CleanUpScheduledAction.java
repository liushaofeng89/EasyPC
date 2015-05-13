package cn.liushaofeng.easypc.actions;

import org.eclipse.jface.action.Action;

/**
 * Scheduled Clean Up Action
 * @author liushaofeng
 * @date 2015-5-13
 * @version 1.0.0
 */
public class CleanUpScheduledAction extends Action
{
    public CleanUpScheduledAction()
    {
        super("Scheduled Clean");
    }

    @Override
    public void run()
    {
        System.out.println("Start to scheduled a clean task!");
    }

}
