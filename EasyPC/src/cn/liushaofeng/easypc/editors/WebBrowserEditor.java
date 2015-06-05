package cn.liushaofeng.easypc.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

/**
 * web browser editor part
 * @author liushaofeng
 * @date 2015-6-5
 * @version 1.0.0
 */
public class WebBrowserEditor extends EditorPart
{
    public static final String ID = "cn.liushaofeng.easypc.editors.webbrowsereditor";
    private BrowserExample instance;

    @Override
    public void doSave(IProgressMonitor monitor)
    {

    }

    @Override
    public void doSaveAs()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(IEditorSite site, IEditorInput input) throws PartInitException
    {
        this.setSite(site);
        this.setInput(input);
        this.setPartName(input.getName());
    }

    @Override
    public boolean isDirty()
    {
        return false;
    }

    @Override
    public boolean isSaveAsAllowed()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void createPartControl(Composite parent)
    {

        instance = new BrowserExample(parent, true);
    }

    @Override
    public void setFocus()
    {
        instance.focus();
    }

}
