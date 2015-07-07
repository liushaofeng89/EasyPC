package cn.liushaofeng.easypc.editors;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

    private File file;

    @Override
    public void init(IEditorSite site, IEditorInput input) throws PartInitException
    {
        if (input instanceof TextEditorInput)
        {
            TextEditorInput textInput = (TextEditorInput) input;
            this.file = textInput.getFile();
            FileReader fileReader = null;
            try
            {
                fileReader = new FileReader(textInput.getFile());
                char[] readBuffer = new char[1];
                while (fileReader.read(readBuffer) != -1)
                {
                    text.append(String.valueOf(readBuffer));
                }
            }
            catch (IOException e)
            {
                Logger.getLogger(this.getClass()).error(e.getMessage(), e);
            }
            finally
            {
                if (fileReader != null)
                {
                    try
                    {
                        fileReader.close();
                    }
                    catch (IOException e)
                    {
                        Logger.getLogger(this.getClass()).error(e.getMessage(), e);
                    }
                    fileReader = null;
                }
            }

        }

        setSite(site);
        setInput(input);
        setPartName(input.getName());
    }

    @Override
    public void createPartControl(Composite arg0)
    {
        text = new Text(arg0, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
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
        BufferedWriter bufferedWriter = null;
        try
        {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(text.getText());
            setDirty(false);
            // 更改编辑器状态
            firePropertyChange(IEditorPart.PROP_DIRTY);
        }
        catch (IOException e1)
        {
            Logger.getLogger(this.getClass()).error(e1.getMessage(), e1);
        }
        finally
        {
            try
            {
                bufferedWriter.close();
            }
            catch (IOException e)
            {
                Logger.getLogger(this.getClass()).error(e.getMessage(), e);
            }
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

}
