package cn.liushaofeng.easypc.ui.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * create WIFI dialog
 * @author liushaofeng
 * @date 2015-5-19
 * @version 1.0.0
 */
public class WifiDailog extends Dialog
{

    private Button okBtn;

    /**
     * default constructor
     * @param shell parent shell
     */
    public WifiDailog(Shell shell)
    {
        super(shell);
    }

    @Override
    protected void configureShell(Shell newShell)
    {
        newShell.setText("Wifi Configuration Dialog");//$NON-NLS-1$
        super.configureShell(newShell);
    }

    @Override
    protected boolean isResizable()
    {
        return false;
    }

    @Override
    protected Control createDialogArea(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));

        Label nameLabel = new Label(composite, SWT.NONE);
        nameLabel.setText("WIFI Name:");
        nameLabel.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
        Text text = new Text(composite, SWT.BORDER);
        text.setToolTipText("input wifi name please.");
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
        layoutData.widthHint = 250;
        text.setLayoutData(layoutData);

        Label pwdLabel = new Label(composite, SWT.NONE);
        pwdLabel.setText("WIFI Password:");
        pwdLabel.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
        Text pwd = new Text(composite, SWT.PASSWORD | SWT.BORDER);
        pwd.setToolTipText("password must be 8 charactors at least!");
        pwd.setLayoutData(layoutData);

        Label msgLabel = new Label(composite, SWT.NONE);
        msgLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 0x2, 0x1));

        return composite;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent)
    {
        // create OK and Cancel buttons by default
        okBtn = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);
        okBtn.setEnabled(false);
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
    }

}
