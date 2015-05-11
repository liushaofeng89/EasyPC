package cn.liushaofeng.easypc.views;

import java.util.ArrayList;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import cn.liushaofeng.easypc.actions.StartHttpLookAction;
import cn.liushaofeng.easypc.model.HttpLookModel;

/**
 * http look viewer
 * @author liushaofeng
 * @date 2015-5-10
 * @version 1.0.0
 */
public class HttpLookView extends ViewPart
{
    public static final String ID = "cn.liushaofeng.easypc.views.httplookview";

    private StartHttpLookAction startHttpLookAction = new StartHttpLookAction();
    private TreeViewer httplookTreeView = null;

    @Override
    public void createPartControl(Composite parent)
    {
        this.httplookTreeView = new TreeViewer(parent, SWT.BORDER);
        // httplookTreeView.setContentProvider(null);
        // httplookTreeView.setLabelProvider(null);
        // httplookTreeView.setInput(new ArrayList<HttpLookModel>());

        // create menu and cool item
        contributeToActionBars();
    }

    @Override
    public void setFocus()
    {
        // TODO Auto-generated method stub

    }

    // create menu and toolbar
    private void contributeToActionBars()
    {
        IActionBars actionBars = getViewSite().getActionBars();
        fillToolsBar(actionBars.getToolBarManager());
        fillDropdownMenu(actionBars.getMenuManager());
    }

    private void fillDropdownMenu(IMenuManager menuManager)
    {
        menuManager.add(startHttpLookAction);

    }

    private void fillToolsBar(IToolBarManager toolBarManager)
    {
        toolBarManager.add(startHttpLookAction);
    }

}
