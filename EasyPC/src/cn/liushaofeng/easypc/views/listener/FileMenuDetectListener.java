package cn.liushaofeng.easypc.views.listener;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import cn.liushaofeng.easypc.app.Activator;
import cn.liushaofeng.easypc.util.CMDUtil;

/**
 * file tree view menu detected listener
 * @author liushaofeng
 * @date 2015-5-17
 * @version 1.0.0
 */
public class FileMenuDetectListener implements MenuDetectListener
{

    private Tree tree = null;

    /**
     * default constructor
     * @param tree file tree
     */
    public FileMenuDetectListener(Tree tree)
    {
        this.tree = tree;
    }

    @Override
    public void menuDetected(MenuDetectEvent e)
    {

        Menu menu = new Menu(tree.getShell(), SWT.NONE);

        MenuItem newItem = new MenuItem(menu, SWT.CASCADE);
        newItem.setText("New");

        Menu newMenu = new Menu(tree.getShell(), SWT.DROP_DOWN);
        MenuItem fileItem = new MenuItem(newMenu, SWT.PUSH);
        fileItem.setText("File...");
        fileItem.setImage(Activator.getImage(false, "icons" + File.separator + "obj_text.gif"));
        MenuItem folderItem = new MenuItem(newMenu, SWT.PUSH);
        folderItem.setText("Folder...");
        folderItem.setImage(Activator.getImage(false, "icons" + File.separator + "fldr.gif"));
        newItem.setMenu(newMenu);

        new MenuItem(menu, SWT.SEPARATOR);

        MenuItem openItem = new MenuItem(menu, SWT.PUSH);
        openItem.setText("Open");

        MenuItem copyItem = new MenuItem(menu, SWT.PUSH);
        copyItem.setText("Copy");
        copyItem.setImage(Activator.getImage(false, "icons" + File.separator + "copy.gif"));

        MenuItem pasteItem = new MenuItem(menu, SWT.PUSH);
        pasteItem.setText("Paste");
        pasteItem.setImage(Activator.getImage(false, "icons" + File.separator + "paste.gif"));

        MenuItem deleteItem = new MenuItem(menu, SWT.PUSH);
        deleteItem.setText("Delete...");
        deleteItem.setImage(Activator.getImage(false, "icons" + File.separator + "delete.gif"));

        new MenuItem(menu, SWT.SEPARATOR);

        MenuItem renameItem = new MenuItem(menu, SWT.PUSH);
        renameItem.setText("Rename...");

        MenuItem explorerItem = new MenuItem(menu, SWT.PUSH);
        explorerItem.setText("Open in Explorer");
        explorerItem.setImage(Activator.getImage(false, "icons" + File.separator + "explorer.gif"));
        explorerItem.addSelectionListener(new SelectionAdapter()
        {

            @Override
            public void widgetSelected(SelectionEvent e)
            {
                TreeItem treeItem = tree.getSelection()[0];
                File file = (File) treeItem.getData();
                if (file.isDirectory())
                {
                    CMDUtil.explorerDir(file);
                }
                else
                {
                    CMDUtil.explorerDir(file.getParentFile());
                }
            }
        });
        tree.setMenu(menu);
    }
}
