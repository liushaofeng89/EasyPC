package cn.liushaofeng.easypc.app;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import cn.liushaofeng.easypc.actions.WifiAction;
import easypc.MessagePopupAction;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of the actions added to a workbench window.
 * Each window will be populated with new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor
{

    private static final String WORKING_SET_STATUS_LINE_FILE_GROUP = "workingSetStatusLineFileGroup"; //$NON-NLS-1$
    private static final String WORKING_SET_STATUS_LINE_NETWORK_GROUP = "workingSetStatusLineNetworkGroup"; //$NON-NLS-1$
    private static final String WORKING_SET_STATUS_LINE_PROGRESS_GROUP = "workingSetStatusLineProgressGroup"; //$NON-NLS-1$

    // Actions - important to allocate these only in makeActions, and then use
    // them
    // in the fill methods. This ensures that the actions aren't recreated
    // when fillActionBars is called with FILL_PROXY.
    private IWorkbenchAction exitAction;
    private IWorkbenchAction introAction;
    private IWorkbenchAction aboutAction;
    private IWorkbenchAction newWindowAction;
    private IWorkbenchAction saveAction;
    private Action messagePopupAction;
    private IWorkbenchAction openPerspectiveAction;
    private IWorkbenchAction resetPerspectiveAction;

    // user define actions
    private WifiAction wifiAction;

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer)
    {
        super(configurer);
    }

    protected void makeActions(final IWorkbenchWindow window)
    {
        // Creates the actions and registers them.
        // Registering is needed to ensure that key bindings work.
        // The corresponding commands keybindings are defined in the plugin.xml
        // file.
        // Registering also provides automatic disposal of the actions when
        // the window is closed.

        exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);

        openPerspectiveAction = ActionFactory.OPEN_PERSPECTIVE_DIALOG.create(window);
        register(openPerspectiveAction);
        resetPerspectiveAction = ActionFactory.RESET_PERSPECTIVE.create(window);
        register(resetPerspectiveAction);

        aboutAction = ActionFactory.ABOUT.create(window);
        aboutAction.setImageDescriptor(Activator.getImageDescriptor("icons" + File.separator + "info.png"));
        register(aboutAction);

        introAction = ActionFactory.INTRO.create(window);
        introAction.setImageDescriptor(Activator.getImageDescriptor("icons" + File.separator + "welcome.gif"));
        register(introAction);

        newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
        register(newWindowAction);

        saveAction = ActionFactory.SAVE.create(window);
        register(saveAction);

        messagePopupAction = new MessagePopupAction("Open Message", window);
        register(messagePopupAction);

        wifiAction = new WifiAction();

    }

    protected void fillMenuBar(IMenuManager menuBar)
    {
        MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
        MenuManager windowMenu = new MenuManager("&Window", IWorkbenchActionConstants.M_WINDOW);
        MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);
        // Add a group marker indicating where action set menus will appear.
        menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        menuBar.add(helpMenu);

        // File
        fileMenu.add(newWindowAction);
        fileMenu.add(new Separator());
        fileMenu.add(messagePopupAction);
        fileMenu.add(saveAction);
        fileMenu.add(new Separator());
        fileMenu.add(exitAction);

        // Window
        windowMenu.add(openPerspectiveAction);
        windowMenu.add(new Separator());
        windowMenu.add(resetPerspectiveAction);

        // Help
        helpMenu.add(introAction);
        helpMenu.add(new Separator());
        helpMenu.add(aboutAction);
    }

    protected void fillCoolBar(ICoolBarManager coolBar)
    {
        IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
        coolBar.add(new ToolBarContributionItem(toolbar, "main"));
        toolbar.add(saveAction);
        toolbar.add(wifiAction);
    }

    @Override
    protected void fillStatusLine(IStatusLineManager statusLine)
    {
        // statusLine.add(new Separator(WORKING_SET_STATUS_LINE_FILE_GROUP));
        // statusLine.appendToGroup(WORKING_SET_STATUS_LINE_FILE_GROUP, new Separator());

        super.fillStatusLine(statusLine);

        StatusLineContributionItem fileItem = new StatusLineContributionItem(WORKING_SET_STATUS_LINE_FILE_GROUP, 15);
        StatusLineContributionItem networkItem = new StatusLineContributionItem(WORKING_SET_STATUS_LINE_NETWORK_GROUP,
            15);
        StatusLineContributionItem progressItem = new StatusLineContributionItem(
            WORKING_SET_STATUS_LINE_PROGRESS_GROUP, 50);
        fileItem.setText("File Info");
        networkItem.setText("Network Info");
        progressItem.setText("Progress");

        statusLine.add(fileItem);
        statusLine.add(networkItem);
        statusLine.add(progressItem);
    }
}
