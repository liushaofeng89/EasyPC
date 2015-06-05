package cn.liushaofeng.easypc.app;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.util.PrefUtil;

import cn.liushaofeng.easypc.editors.WebBrowserEditor;
import cn.liushaofeng.easypc.editors.input.WebBrowserEditorInput;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
{

    private IWorkbenchWindowConfigurer configurer;

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
    {
        super(configurer);
        this.configurer = configurer;
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

        // 定制应用程序的外观
        IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
        // 设置选项卡的样式，不是矩形的边框，而是弧形的
        preferenceStore.setValue(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, false);
        // 设置透视图按钮的位置，默认是左上角，改为放置在右上角
        preferenceStore.setValue(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR,
            IWorkbenchPreferenceConstants.TOP_RIGHT);
    }

    @Override
    public void postWindowOpen()
    {
        try
        {
            configurer.getWindow().getActivePage().openEditor(new WebBrowserEditorInput(), WebBrowserEditor.ID);
        }
        catch (PartInitException e)
        {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
        }

        // start a thread to collect local net information

    }

}
