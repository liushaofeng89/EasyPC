package cn.liushaofeng.easypc.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import cn.liushaofeng.easypc.views.ConsoleView;
import cn.liushaofeng.easypc.views.HttpLookView;
import cn.liushaofeng.easypc.views.ProcessView;

/**
 * 网络工程师透视图
 * @author liushaofeng
 * @date 2015年4月24日
 * @version 1.0.0
 */
public class NetWorkerPerspective implements IPerspectiveFactory
{

    public static final String ID = "cn.liushaofeng.easypc.perspecties.networkerperspective";

    /**
     * 这个方法用来初始化透视图的布局，可以在这里对项目中的视图进行注册
     */
    @Override
    public void createInitialLayout(IPageLayout layout)
    {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(true);

        IFolderLayout rightFolder = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.65f, editorArea);
        rightFolder.addView(HttpLookView.ID);
        rightFolder.addView(ConsoleView.ID);
        rightFolder.addView(ProcessView.ID);
        rightFolder.addPlaceholder(HttpLookView.ID);
        rightFolder.addPlaceholder(ConsoleView.ID);
        rightFolder.addPlaceholder(ProcessView.ID);
    }

}
