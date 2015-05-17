package cn.liushaofeng.easypc.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.IInputValidator;

/**
 * File name validator
 * @author liushaofeng
 * @date 2015-5-17
 * @version 1.0.0
 */
public class FileNameValidator implements IInputValidator
{
    private String regex = "[^\\/<?:>\"|]";

    @Override
    public String isValid(String newText)
    {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(newText);
        if (m.find())
        {
            return "The file name not support chars:^\\/<?:>\"|";
        }
        else
        {
            return "";
        }
    }
}
