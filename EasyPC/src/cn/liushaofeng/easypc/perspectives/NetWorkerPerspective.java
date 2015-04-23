package cn.liushaofeng.easypc.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import easypc.View;

/**
 * network perspective createInitialLayout方法可以对透视图进行布局
 * @author liushaofeng
 * @date 2015年4月23日
 */
public class NetWorkerPerspective implements IPerspectiveFactory
{

    /**
     * 这个方法用来初始化透视图的布局，可以在这里对项目中的视图进行注册
     */
    @Override
    public void createInitialLayout(IPageLayout layout)
    {

        // 得到编辑区域id
        String editorArea = layout.getEditorArea();

        // 添加导航视图
        // String viewId, int relationship, float ratio, String refId
        layout.addView(View.ID, IPageLayout.LEFT, 0.3f, editorArea);

        // 添加搜索视图，添加到导航视图的下方
        // An IFolderLayout is used to define the initial views within a folder.
        // The folder itself is contained within an IPageLayout.
        // IFolderLayout用来给已有的View添加一个folder（文件夹功能），这个folder本身就包含有IPageLayout
        IFolderLayout leftbottom = layout.createFolder("left", IPageLayout.BOTTOM, 0.4f, View.ID);
        leftbottom.addView(View.ID);

        // 添加病人信息和费用信息的视图，并实现两个视图叠加的效果
        IFolderLayout rightbottom = layout
            .createFolder("rightbottom", IPageLayout.BOTTOM, 0.7f, editorArea);
        rightbottom.addView(View.ID);
        rightbottom.addView(View.ID);

    }

}
