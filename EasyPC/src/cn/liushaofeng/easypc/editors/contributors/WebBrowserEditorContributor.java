package cn.liushaofeng.easypc.editors.contributors;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.ui.part.EditorActionBarContributor;

/**
 * web browser editor contributor
 * @author liushaofeng
 * @date 2015-6-5
 * @version 1.0.0
 */
public class WebBrowserEditorContributor extends EditorActionBarContributor
{

    @Override
    public void contributeToMenu(IMenuManager menuManager)
    {
    }

    @Override
    public void contributeToStatusLine(IStatusLineManager statusLineManager)
    {
        // TODO Auto-generated method stub
        super.contributeToStatusLine(statusLineManager);
    }

    @Override
    public void contributeToCoolBar(ICoolBarManager coolBarManager)
    {
        coolBarManager.getItems();
    }

}
