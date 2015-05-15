package cn.liushaofeng.easypc.views.cleanup;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * other file need to clean defined in this composite
 * @author liushaofeng
 * @date 2015-5-15
 * @version 1.0.0
 */
public class OtherFileCmpst extends Composite
{

    /**
     * default constructor
     * @param parent parent composite
     * @param style composite style
     */
    public OtherFileCmpst(Composite parent, int style)
    {
        super(parent, style);
        super.setLayout(new GridLayout(10, false));

        initControl();
    }

    private void initControl()
    {
        Button tmpBtn = new Button(this, SWT.CHECK);
        tmpBtn.setText("*.tmp");

        Button mpBtn = new Button(this, SWT.CHECK);
        mpBtn.setText("*._mp");

        Button logBtn = new Button(this, SWT.CHECK);
        logBtn.setText("*.log");

        Button gidBtn = new Button(this, SWT.CHECK);
        gidBtn.setText("*.gid");

        Button chkBtn = new Button(this, SWT.CHECK);
        chkBtn.setText("*.chk");

        Button oldBtn = new Button(this, SWT.CHECK);
        oldBtn.setText("*.old");

        Button bakBtn = new Button(this, SWT.CHECK);
        bakBtn.setText("*.bak");
    }

}
