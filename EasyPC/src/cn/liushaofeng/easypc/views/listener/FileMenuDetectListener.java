package cn.liushaofeng.easypc.views.listener;

import java.io.File;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import cn.liushaofeng.easypc.app.Activator;
import cn.liushaofeng.easypc.util.CMDUtil;
import cn.liushaofeng.easypc.util.FileUtil;
import cn.liushaofeng.easypc.util.validator.FileNameValidator;

/**
 * file tree view menu detected listener
 * @author liushaofeng
 * @date 2015-5-17
 * @version 1.0.0
 */
public class FileMenuDetectListener implements MenuDetectListener
{

    private TreeViewer treeViewer = null;

    /**
     * default constructor
     * @param tree file tree
     */
    public FileMenuDetectListener(TreeViewer fileTreeViewer)
    {
        this.treeViewer = fileTreeViewer;
    }

    @Override
    public void menuDetected(MenuDetectEvent e)
    {
        Menu menu = new Menu(treeViewer.getTree().getShell(), SWT.NONE);

        MenuItem newItem = new MenuItem(menu, SWT.CASCADE);
        newItem.setText("New");

        Menu newMenu = new Menu(treeViewer.getTree().getShell(), SWT.DROP_DOWN);
        MenuItem fileItem = new MenuItem(newMenu, SWT.PUSH);
        fileItem.setText("File...");
        fileItem.setImage(Activator.getImage(false, "icons" + File.separator + "obj_text.gif"));
        fileItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                File srcFile = hasSelectItem();
                if (srcFile == null)
                {
                    return;
                }
                Shell shell = treeViewer.getTree().getShell();
                InputDialog inputDialog = new InputDialog(shell, "New File Dialog", "Input file name, please:",
                    "unuamed", new FileNameValidator());
                if (Window.OK == inputDialog.open())
                {
                    String absolutePath = srcFile.getAbsolutePath();
                    String newFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator) + 0x1)
                        + inputDialog.getValue();
                    File file = new File(newFilePath);
                    if (file.exists())
                    {
                        MessageDialog.openError(shell, "Rename Fail Message", "The destnation file is exists!");
                    }
                    else
                    {
                        FileUtil.createNewFile(file);
                    }
                }
            }
        });

        MenuItem folderItem = new MenuItem(newMenu, SWT.PUSH);
        folderItem.setText("Folder...");
        folderItem.setImage(Activator.getImage(false, "icons" + File.separator + "fldr.gif"));
        folderItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                File srcFile = hasSelectItem();
                if (srcFile == null)
                {
                    return;
                }
                Shell shell = treeViewer.getTree().getShell();
                InputDialog inputDialog = new InputDialog(shell, "New Directory Dialog",
                    "Input Directory name, please:", "unuamed", new FileNameValidator());
                if (Window.OK == inputDialog.open())
                {
                    String absolutePath = srcFile.getAbsolutePath();
                    String newFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator) + 0x1)
                        + inputDialog.getValue();
                    File file = new File(newFilePath);
                    if (file.exists())
                    {
                        MessageDialog.openError(shell, "Rename Fail Message", "The destnation file is exists!");
                    }
                    else
                    {
                        FileUtil.createDirs(file);
                    }
                }
            }
        });
        newItem.setMenu(newMenu);

        new MenuItem(menu, SWT.SEPARATOR);

        MenuItem openItem = new MenuItem(menu, SWT.PUSH);
        openItem.setText("Open");

        MenuItem copyItem = new MenuItem(menu, SWT.PUSH);
        copyItem.setText("Copy\tCtrl+C");
        copyItem.setImage(Activator.getImage(false, "icons" + File.separator + "copy.gif"));

        MenuItem pasteItem = new MenuItem(menu, SWT.PUSH);
        pasteItem.setText("Paste\tCtrl+V");
        pasteItem.setImage(Activator.getImage(false, "icons" + File.separator + "paste.gif"));

        MenuItem deleteItem = new MenuItem(menu, SWT.PUSH);
        deleteItem.setText("Delete...\tDelete");
        deleteItem.setImage(Activator.getImage(false, "icons" + File.separator + "delete.gif"));

        new MenuItem(menu, SWT.SEPARATOR);

        MenuItem renameItem = new MenuItem(menu, SWT.PUSH);
        renameItem.setText("Rename...\tF2");
        renameItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                renameFile();
            }
        });

        new MenuItem(menu, SWT.SEPARATOR);

        final MenuItem refreshItem = new MenuItem(menu, SWT.PUSH);
        refreshItem.setText("Refresh\tF5");
        refreshItem.setImage(Activator.getImage(false, "icons" + File.separator + "refresh.gif"));
        refreshItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                treeViewer.refresh();
            }
        });

        new MenuItem(menu, SWT.SEPARATOR);

        MenuItem explorerItem = new MenuItem(menu, SWT.PUSH);
        explorerItem.setText("Open in Explorer");
        explorerItem.setImage(Activator.getImage(false, "icons" + File.separator + "explorer.gif"));
        explorerItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                TreeItem treeItem = treeViewer.getTree().getSelection()[0];
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
        treeViewer.getTree().setMenu(menu);
    }

    /**
     * rename file
     */
    public void renameFile()
    {
        File srcFile = hasSelectItem();
        if (srcFile == null)
        {
            return;
        }
        Shell shell = treeViewer.getTree().getShell();
        InputDialog inputDialog = new InputDialog(shell, "Rename Dialog", "Input new name, please:", srcFile.getName(),
            new FileNameValidator());
        if (Window.OK == inputDialog.open())
        {
            String absolutePath = srcFile.getAbsolutePath();
            String newFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator) + 0x1)
                + inputDialog.getValue();
            File file = new File(newFilePath);
            if (file.exists())
            {
                MessageDialog.openError(shell, "Rename Fail Message", "The destnation file is exists!");
            }
            else
            {
                if (srcFile.renameTo(file))
                {
                    treeViewer.refresh();
                }
                else
                {
                    MessageDialog.openError(shell, "Rename Fail Message", "The destnation file is exists!");
                }
            }
        }
    }

    private File hasSelectItem()
    {
        Tree tree = treeViewer.getTree();
        if (tree.getSelection().length == 0x0)
        {
            return null;
        }
        File srcFile = (File) tree.getSelection()[0].getData();
        if (srcFile.getName().trim().isEmpty())
        {
            return null;
        }
        return srcFile;
    }
}
