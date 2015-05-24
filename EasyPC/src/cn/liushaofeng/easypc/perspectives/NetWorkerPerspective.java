package cn.liushaofeng.easypc.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import cn.liushaofeng.easypc.views.ConsoleView;
import cn.liushaofeng.easypc.views.HttpLookView;
import cn.liushaofeng.easypc.views.NetworkView;
import cn.liushaofeng.easypc.views.ProcessView;

/**
 * Network perspective
 * @author liushaofeng
 * @date 2015年4月24日
 * @version 1.0.0
 */
public class NetWorkerPerspective implements IPerspectiveFactory
{

    public static final String ID = "cn.liushaofeng.easypc.perspecties.networkerperspective";

    @Override
    public void createInitialLayout(IPageLayout layout)
    {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(true);

        layout.addView(NetworkView.ID, IPageLayout.LEFT, 0.30f, editorArea);

        IFolderLayout rightFolder = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.65f, editorArea);
        rightFolder.addView(HttpLookView.ID);
        rightFolder.addView(ConsoleView.ID);
        rightFolder.addView(ProcessView.ID);
    }

}
