package cn.liushaofeng.easypc.views.cleanup;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

/**
 * clean up view
 * @author liushaofeng
 * @date 2015-5-15
 * @version 1.0.0
 */
public class CleanUpView extends ViewPart
{
    public static final String ID = "cn.liushaofeng.easypc.views.cleanup.cleanupview";

    @Override
    public void createPartControl(Composite parent)
    {
        final CTabFolder folder = new CTabFolder(parent, SWT.BORDER | SWT.BOTTOM);
        folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        folder.setUnselectedImageVisible(false);
        folder.setUnselectedCloseVisible(false);

        CTabItem item = new CTabItem(folder, SWT.NONE);
        item.setText("Clean All");
        Text text = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
        text.setText("Text for item  \n\none, two, three\n\nabcdefghijklmnop");
        item.setControl(text);

        CTabItem systemItem = new CTabItem(folder, SWT.NONE);
        systemItem.setText("System Clean");
        Text text1 = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
        text1.setText("Text 1");
        systemItem.setControl(text1);

        CTabItem mediaChacheItem = new CTabItem(folder, SWT.NONE);
        mediaChacheItem.setText("Media Cache");
        Text text2 = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
        text2.setText("Text 2");
        mediaChacheItem.setControl(text1);

        CTabItem registryItem = new CTabItem(folder, SWT.NONE);
        registryItem.setText("Registry Clean");
        Text text3 = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
        text3.setText("Text 3");
        registryItem.setControl(text3);

        CTabItem otherItem = new CTabItem(folder, SWT.NONE);
        otherItem.setText("Other File");
        OtherFileCpst otherFileCmpst = new OtherFileCpst(folder, SWT.NONE);
        otherItem.setControl(otherFileCmpst);

        folder.setSelection(0x0);

    }

    @Override
    public void setFocus()
    {

    }

}
