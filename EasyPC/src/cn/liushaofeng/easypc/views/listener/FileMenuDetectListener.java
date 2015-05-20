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
        final File[] selectFiles = selectItem();
        if (selectFiles.length <= 0x0)
        {
            return;
        }

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
                if (selectFiles.length > 0x0)
                {
                    return;
                }
                doCreate(true, selectFiles[0x0]);
                treeViewer.refresh();
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
                if (selectFiles.length > 0x0)
                {
                    return;
                }
                doCreate(false, selectFiles[0]);
                treeViewer.refresh();
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
        deleteItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                if (selectFiles.length > 0x0)
                {
                    return;
                }
                deleteFile(selectFiles);
            }
        });

        new MenuItem(menu, SWT.SEPARATOR);

        MenuItem renameItem = new MenuItem(menu, SWT.PUSH);
        renameItem.setText("Rename...\tF2");
        renameItem.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                if (selectFiles.length > 0x0)
                {
                    return;
                }
                renameFile(selectFiles[0]);
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
                if (selectFiles.length > 0x0)
                {
                    return;
                }
                if (selectFiles[0].isDirectory())
                {
                    CMDUtil.explorerDir(selectFiles[0x0]);
                }
                else
                {
                    CMDUtil.explorerDir(selectFiles[0x0].getParentFile());
                }
            }
        });
        treeViewer.getTree().setMenu(menu);
    }

    private void doCreate(boolean isFile, File srcFile)
    {
        String title = isFile ? "New File Dialog" : "New Directory Dialog";
        String msg = isFile ? "Input file name, please:" : "Input Directory name, please:";
        Shell shell = treeViewer.getTree().getShell();
        InputDialog inputDialog = new InputDialog(shell, title, msg, "unuamed", new FileNameValidator());
        if (Window.OK == inputDialog.open())
        {
            createFiles(isFile, srcFile, shell, inputDialog);
        }
    }

    /**
     * create file
     * @param isFile is a file or not
     * @param srcFile source file
     * @param shell parent shell
     * @param inputDialog input dialog
     */
    private void createFiles(boolean isFile, File srcFile, Shell shell, InputDialog inputDialog)
    {
        String absolutePath = srcFile.getAbsolutePath();
        File file = new File(resolvePath(srcFile, inputDialog, absolutePath));
        if (file.exists())
        {
            MessageDialog.openError(shell, "Rename Fail Message", "The destnation file is exists!");
        }
        else
        {
            if (isFile)
            {
                FileUtil.createNewFile(file);
            }
            else
            {
                FileUtil.createDirs(file);
            }
        }
    }

    /**
     * resolve path
     * @param srcFile src file
     * @param inputDialog input dialog
     * @param absolutePath the file path
     * @return new file path
     */
    private String resolvePath(File srcFile, InputDialog inputDialog, String absolutePath)
    {
        String newFilePath;
        if (srcFile.isFile())
        {
            newFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator) + 0x1)
                + inputDialog.getValue();
        }
        else
        {
            newFilePath = absolutePath + File.separator + inputDialog.getValue();
        }
        return newFilePath;
    }

    /**
     * delete file confirm dialog
     * @param file the file need to delete
     */
    public void deleteFile(File[] file)
    {
        boolean openQuestion = MessageDialog.openQuestion(treeViewer.getTree().getShell(), "File Delete Dialog",
            "The file can not recover after deleting, click Yes to delete this file!");
        if (openQuestion)
        {
            for (File f : file)
            {
                FileUtil.deleteFile(f);
            }
            treeViewer.refresh();
        }
    }

    /**
     * rename file
     */
    public void renameFile(File srcFile)
    {
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

    private File[] selectItem()
    {
        TreeItem[] selection = treeViewer.getTree().getSelection();
        File[] selectedFiles = new File[selection.length];
        for (int i = 0; i < selection.length; i++)
        {
            selectedFiles[i] = (File) selection[i].getData();
        }
        return selectedFiles;
    }
}
