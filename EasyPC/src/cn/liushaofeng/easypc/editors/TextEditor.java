package cn.liushaofeng.easypc.editors;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import cn.liushaofeng.easypc.editors.input.TextEditorInput;

/**
 * Text Editor
 * @author liushaofeng
 * @date 2015年4月26日
 * @version 1.0.0
 */
public class TextEditor extends EditorPart
{
    public static final String ID = "cn.liushaofeng.easypc.editors.texteditorview";

    private Text text;
    private boolean isDirty;

    private TextEditorInput TextEditorInput;

    @Override
    public void init(IEditorSite site, IEditorInput input) throws PartInitException
    {
        this.setSite(site);
        this.setInput(input);
        this.setPartName(input.getName());
        this.TextEditorInput = (TextEditorInput) input;
    }

    @Override
    public void createPartControl(Composite arg0)
    {
        text = new Text(arg0, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
        initValToText(TextEditorInput);
        text.addModifyListener(new ModifyListener()
        {
            @Override
            public void modifyText(ModifyEvent e)
            {
                if (!isDirty())
                {
                    setDirty(true);
                    // 更改编辑器状态
                    firePropertyChange(IEditorPart.PROP_DIRTY);
                }
            }
        });
    }

    @Override
    public void doSave(IProgressMonitor monitor)
    {
        try
        {
            monitor.beginTask("Saving file...", 100);

            for (int i = 0; i < 10 && !monitor.isCanceled(); i++)
            {
                Thread.sleep(500);
                monitor.worked(10);
                double d = (i + 1) / 10d;
                monitor.subTask("Finished" + d * 100 + "%");
            }
            monitor.done();
            if (monitor.isCanceled())
            {
                throw new InterruptedException("Canceled this save event.");
            }
        } catch (InterruptedException e)
        {
            Logger.getLogger(TextEditor.class).error(e.getMessage(), e);
        }

    }

    @Override
    public void doSaveAs()
    {

    }

    @Override
    public boolean isDirty()
    {
        return isDirty;
    }

    private void setDirty(boolean dirty)
    {
        this.isDirty = dirty;
    }

    @Override
    public boolean isSaveAsAllowed()
    {
        return true;
    }

    @Override
    public void setFocus()
    {
        text.setFocus();
    }

    private void initValToText(IEditorInput input)
    {
        if (input instanceof TextEditorInput)
        {
            TextEditorInput textInput = (TextEditorInput) input;
            BufferedReader bufferedReader = null;
            try
            {
                bufferedReader = new BufferedReader(new FileReader(textInput.getFile()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null)
                {
                    text.append(line + System.getProperty("line.separator"));
                }
            } catch (FileNotFoundException e)
            {
                Logger.getLogger(this.getClass()).error(e.getMessage(), e);
            } catch (IOException e)
            {
                Logger.getLogger(this.getClass()).error(e.getMessage(), e);
            } finally
            {
                if (bufferedReader != null)
                {
                    try
                    {
                        bufferedReader.close();
                    } catch (IOException e)
                    {
                        Logger.getLogger(this.getClass()).error(e.getMessage(), e);
                    }
                    bufferedReader = null;
                }
            }

        }
    }
}
