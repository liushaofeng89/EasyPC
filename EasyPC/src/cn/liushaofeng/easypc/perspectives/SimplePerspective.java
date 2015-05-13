package cn.liushaofeng.easypc.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import cn.liushaofeng.easypc.views.CleanUpView;
import cn.liushaofeng.easypc.views.ContactView;
import cn.liushaofeng.easypc.views.FileExplorerView;

/**
 * Simple user perspective
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

        IFolderLayout rightFolder = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.65f, editorArea);
        rightFolder.addView(CleanUpView.ID);
        rightFolder.addView(ContactView.ID);
        rightFolder.addPlaceholder(CleanUpView.ID);
        rightFolder.addPlaceholder(ContactView.ID);
    }

}
