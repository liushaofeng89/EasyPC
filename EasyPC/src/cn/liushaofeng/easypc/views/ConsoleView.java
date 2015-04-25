package cn.liushaofeng.easypc.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

/**
 * 控制台
 * @author liushaofeng
 * @date 2015年4月24日
 * @version 1.0.0
 */
public class ConsoleView extends ViewPart
{
    public static final String ID = "cn.liushaofeng.easypc.views.consoleview";
    private Text text = null;

    @Override
    public void createPartControl(Composite arg0)
    {
        text = new Text(arg0, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        text.setEditable(false);
    }

    @Override
    public void setFocus()
    {
        // TODO Auto-generated method stub

    }

}
