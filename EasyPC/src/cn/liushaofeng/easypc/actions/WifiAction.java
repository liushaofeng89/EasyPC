package cn.liushaofeng.easypc.actions;

import java.io.File;

import org.eclipse.jface.action.Action;

import cn.liushaofeng.easypc.app.Activator;

/**
 * Wifi create action
 * @author liushaofeng
 * @date 2015-5-17
 * @version 1.0.0
 */
public class WifiAction extends Action
{
    public WifiAction()
    {
        super("Create WIFI", Activator.getImageDescriptor("icons" + File.separator + "wifi.gif"));
    }
}
