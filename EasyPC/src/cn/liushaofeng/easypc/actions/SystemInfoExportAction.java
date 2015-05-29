package cn.liushaofeng.easypc.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IViewSite;

import cn.liushaofeng.easypc.app.Activator;
import cn.liushaofeng.easypc.util.CMDUtil;
import cn.liushaofeng.easypc.util.SystemUtil;

/**
 * export system info to text file
 * @author liushaofeng
 * @date 2015-5-22
 * @version 1.0.0
 */
public class SystemInfoExportAction extends Action
{
    private IViewSite iViewSite;

    /**
     * default constructor
     * @param iViewSite iViewSite
     */
    public SystemInfoExportAction(IViewSite iViewSite)
    {
        super("Export System Info", Activator.getImageDescriptor("icons" + File.separator + "export_obj.gif"));
        this.iViewSite = iViewSite;
    }

    @Override
    public String getToolTipText()
    {
        return "Export system info to text file";
    }

    @Override
    public void run()
    {
        FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
        dialog.setFilterNames(new String[]
        {
                "Text File (*.txt)", "All Files (*.*)"
        });
        dialog.setFilterExtensions(new String[]
        {
                "*.txt", "*.*"
        }); // Windows wild cards
        dialog.setFilterPath(SystemUtil.getSysDktpDirFile().getAbsolutePath()); // Windows
                                                                                // path
        dialog.setFileName("systeminfo.txt");
        writeSysInfoToFile(dialog.open());
    }

    private void writeSysInfoToFile(final String filePath)
    {
        // if user canceled save action
        if (filePath == null)
        {
            return;
        }
        final IStatusLineManager statusLineManager = iViewSite.getActionBars().getStatusLineManager();
        statusLineManager.setMessage("Saving file:'" + filePath + "'...");

        Display.getCurrent().asyncExec(new Runnable()
        {
            @Override
            public void run()
            {
                BufferedWriter writer = null;
                List<String> systemInfo = CMDUtil.getSystemInfo();
                try
                {
                    IProgressMonitor progressMonitor = statusLineManager.getProgressMonitor();
                    progressMonitor.beginTask("Saving file:'" + filePath + "'", systemInfo.size());

                    writer = new BufferedWriter(new FileWriter(new File(filePath)));
                    for (int i = 0; i < systemInfo.size() && !progressMonitor.isCanceled(); i++)
                    {
                        writer.write(systemInfo.get(i) + System.getProperty("line.separator"));
                        progressMonitor.worked(1);
                    }
                    progressMonitor.done();
                    statusLineManager.setMessage("'" + filePath + "' saved success!");
                }
                catch (IOException e)
                {
                    Logger.getLogger(this.getClass()).error(e.getMessage(), e);
                }
                finally
                {
                    if (writer != null)
                    {
                        try
                        {
                            writer.close();
                        }
                        catch (IOException e)
                        {
                            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
                        }
                    }
                }
            }
        });
    }
}
