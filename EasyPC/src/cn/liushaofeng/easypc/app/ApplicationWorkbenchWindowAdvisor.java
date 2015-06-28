package cn.liushaofeng.easypc.app;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import cn.liushaofeng.easypc.editors.WebBrowserEditor;
import cn.liushaofeng.easypc.editors.input.WebBrowserEditorInput;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
{

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
    {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer)
    {
        return new ApplicationActionBarAdvisor(configurer);
    }

    @Override
    public void preWindowOpen()
    {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(800, 600));
        configurer.setShowPerspectiveBar(true);
        configurer.setShowMenuBar(true);
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(true);
    }

    @Override
    public void postWindowOpen()
    {
        openBrowserEditor();
        // update status line information
        IStatusLineManager statusLineManager = getWindowConfigurer().getActionBarConfigurer().getStatusLineManager();
        IContributionItem[] items = statusLineManager.getItems();
        for (IContributionItem iContributionItem : items)
        {
            if (iContributionItem instanceof GroupMarker)
            {
                GroupMarker groupMarker = (GroupMarker) iContributionItem;
                groupMarker.setVisible(true);
            }
        }
        statusLineManager.setMessage("Welcome to use EasyPC.");
    }

    private void openBrowserEditor()
    {
        Shell shell = getWindowConfigurer().getWindow().getShell();
        Rectangle screenSize = Display.getDefault().getClientArea();
        Rectangle frameSize = shell.getBounds();
        shell.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        try
        {
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .openEditor(new WebBrowserEditorInput(), WebBrowserEditor.ID);
        }
        catch (PartInitException e)
        {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
        }
    }

    @Override
    public boolean preWindowShellClose()
    {
        boolean result = false;

        MessageBox msgBox = new MessageBox(new Shell(), SWT.YES | SWT.NO | SWT.ICON_QUESTION);
        msgBox.setText("Confirm Exit");
        msgBox.setMessage("Do you want exit application system?");
        int open = msgBox.open();
        if (open == SWT.YES)
        {
            result = true;
        }
        return result;
    }

}
