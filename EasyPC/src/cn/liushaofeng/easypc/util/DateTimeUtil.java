package cn.liushaofeng.easypc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Assert;

/**
 * date time format util
 * @author liushaofeng
 * @date 2015-5-11
 * @version 1.0.0
 */
public class DateTimeUtil
{
    public static final String DATE_TIME_DEFAULT_PATTERNER = "yyyyMMddHHmmsss";

    /**
     * get current time with default pattern
     * @return current time with default pattern
     */
    public String getCurrentTimeStr()
    {
        return new SimpleDateFormat(DATE_TIME_DEFAULT_PATTERNER).format(new Date());
    }

    /**
     * get current time with the format pattern
     * @param pattern the time format pattern
     * @return the time string
     */
    public String getCurrentTimeStrWithPattern(final String pattern)
    {
        Assert.isNotNull(pattern);
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * convert time to string with default pattern
     * @return time string
     */
    public String convertTimeToStr(final Date date)
    {
        Assert.isNotNull(date);
        return new SimpleDateFormat(DATE_TIME_DEFAULT_PATTERNER).format(date);
    }

    /**
     * convert time to string with format pattern
     * @return time string
     */
    public String convertTimeToStrWithPattern(final Date date, final String pattern)
    {
        Assert.isNotNull(date);
        Assert.isNotNull(pattern);
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * convert the input time string to date
     * @param timeStr time string
     * @return date object
     */
    public Date convertStrToTime(String timeStr)
    {
        Assert.isNotNull(timeStr);
        try
        {
            return new SimpleDateFormat(DATE_TIME_DEFAULT_PATTERNER).parse(timeStr);
        } catch (ParseException e)
        {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * convert the input time to the input format pattern
     * @param timeStr the time string need to convert
     * @param pattern the pattern need to format
     * @return date object
     */
    public Date convertStrToTimeWithPattern(final String timeStr, final String pattern)
    {
        Assert.isNotNull(timeStr);
        Assert.isNotNull(pattern);
        try
        {
            return new SimpleDateFormat(pattern).parse(timeStr);
        } catch (ParseException e)
        {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
        }
        return null;
    }

}
