package cn.liushaofeng.easypc.actions;

import java.io.File;

import org.eclipse.jface.action.Action;

import cn.liushaofeng.easypc.app.Activator;

/**
 * 查看计算机硬件信息
 * @author liushaofeng
 * @date 2015年5月1日
 * @version 1.0.0
 */
public class ComputerInfoAction extends Action
{
    public static final String ID = "cn.liushaofeng.easypc.actions.computerinfoaction";

    public ComputerInfoAction()
    {
        super("Computer Info", Activator.getImageDescriptor("icons" + File.separator + "info_obj.gif"));
    }

    @Override
    public void run()
    {
        System.out.println();
        super.run();
    }

}
