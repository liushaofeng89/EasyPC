package cn.liushaofeng.easypc.model.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * easy pc tree date content
 * @author liushaofeng
 * @date 2015年4月24日
 * @version 1.0.0
 */
public class EPTree
{
    private EPTree parent;
    private String name;
    private List<EPTree> children = new ArrayList<EPTree>();
    private Object data;

    public EPTree(Object obj)
    {
        this.data = obj;
    }

    public EPTree getParent()
    {
        return parent;
    }

    public void setParent(EPTree parent)
    {
        this.parent = parent;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<EPTree> getChildren()
    {
        return children;
    }

    public void setChildren(List<EPTree> children)
    {
        this.children = children;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public boolean hasChild()
    {
        return children.size() > 0;
    }

}
