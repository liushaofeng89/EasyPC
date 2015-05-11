package cn.liushaofeng.easypc.perspectives;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import cn.liushaofeng.easypc.views.ContactView;
import cn.liushaofeng.easypc.views.FileExplorerView;

/**
 * 普通用户透视图
 * @author liushaofeng
 * @date 2015年4月24日
 * @version 1.0.0
 */
public class SimplePerspective implements IPerspectiveFactory
{
    public static final String ID = "cn.liushaofeng.easypc.perspecties.simpleperspective";

    @Override
    public void createInitialLayout(IPageLayout layout)
    {

        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(true);

        layout.addView(FileExplorerView.ID, IPageLayout.LEFT, 0.30f, editorArea);

        layout.addView(ContactView.ID, IPageLayout.BOTTOM, 0.66f, editorArea);
    }

}
