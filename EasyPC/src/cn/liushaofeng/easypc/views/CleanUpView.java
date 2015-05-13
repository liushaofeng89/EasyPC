package cn.liushaofeng.easypc.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;

import cn.liushaofeng.easypc.actions.CleanUpCheckAction;
import cn.liushaofeng.easypc.actions.CleanUpControlAction;
import cn.liushaofeng.easypc.actions.CleanUpScheduledAction;

/**
 * clean up view
 * @author liushaofeng
 * @date 2015-5-13
 * @version 1.0.0
 */
public class CleanUpView extends ViewPart
{
    public static final String ID = "cn.liushaofeng.easypc.views.cleanupview";

    private CleanUpScheduledAction scheduledAction = null;
    private CleanUpControlAction cleanAction = null;
    private CleanUpCheckAction checkAction = null;

    private FormToolkit toolkit;
    private ScrolledForm form;

    @Override
    public void createPartControl(Composite parent)
    {
        toolkit = new FormToolkit(parent.getDisplay());
        form = toolkit.createScrolledForm(parent);

        TableWrapLayout layout = new TableWrapLayout();
        form.getBody().setLayout(layout);

        Section browserSection = new Section(form.getBody(), Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
        browserSection.addExpansionListener(new ExpansionAdapter()
        {
            public void expansionStateChanged(ExpansionEvent e)
            {
                form.reflow(true);
            }
        });
        browserSection.setText("Browser Clean");
        Composite browserClient = toolkit.createComposite(browserSection);
        browserClient.setLayout(new GridLayout());
        toolkit.createButton(browserClient, "Internet Explorer", SWT.CHECK);
        toolkit.createButton(browserClient, "FireFox", SWT.CHECK);
        toolkit.createButton(browserClient, "Chrome", SWT.CHECK);
        browserSection.setClient(browserClient);
        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
        browserSection.setLayoutData(td);

        Section systemSection = new Section(form.getBody(), Section.TITLE_BAR | Section.TWISTIE);
        systemSection.addExpansionListener(new ExpansionAdapter()
        {
            public void expansionStateChanged(ExpansionEvent e)
            {
                form.reflow(true);
            }
        });
        systemSection.setText("System Clean");
        Composite sysClient = toolkit.createComposite(systemSection);
        sysClient.setLayout(new GridLayout());
        toolkit.createButton(sysClient, "Recycle Bin", SWT.CHECK);
        toolkit.createButton(sysClient, "Traces", SWT.CHECK);
        toolkit.createButton(sysClient, "Temp File", SWT.CHECK);
        toolkit.createButton(sysClient, "Log File", SWT.CHECK);
        toolkit.createButton(sysClient, "Bak File", SWT.CHECK);
        systemSection.setClient(sysClient);
        td = new TableWrapData(TableWrapData.FILL);
        systemSection.setLayoutData(td);

        Section registrySection = new Section(form.getBody(), Section.TITLE_BAR | Section.TWISTIE);
        registrySection.addExpansionListener(new ExpansionAdapter()
        {
            public void expansionStateChanged(ExpansionEvent e)
            {
                form.reflow(true);
            }
        });
        registrySection.setText("Registry Clean");
        Composite registryClient = toolkit.createComposite(registrySection);
        registryClient.setLayout(new GridLayout());
        toolkit.createButton(registryClient, "Radio 1", SWT.CHECK);
        toolkit.createButton(registryClient, "Radio 2", SWT.CHECK);
        registrySection.setClient(registryClient);
        td = new TableWrapData(TableWrapData.FILL);
        registrySection.setLayoutData(td);

        makeActions();
        // 创建action bar和menu bar
        contributeToActionBars();
    }

    private void makeActions()
    {
        // menu bar
        scheduledAction = new CleanUpScheduledAction();

        // tool bar
        cleanAction = new CleanUpControlAction();
        checkAction = new CleanUpCheckAction();
    }

    // create menu and toolbar
    private void contributeToActionBars()
    {
        IActionBars actionBars = getViewSite().getActionBars();
        fillToolsBar(actionBars.getToolBarManager());
        fillMenuBar(actionBars.getMenuManager());
    }

    private void fillMenuBar(IMenuManager menuManager)
    {
        menuManager.add(scheduledAction);
    }

    private void fillToolsBar(IToolBarManager toolBarManager)
    {
        toolBarManager.add(cleanAction);
        toolBarManager.add(checkAction);
    }

    @Override
    public void setFocus()
    {
        form.setFocus();
    }

}
