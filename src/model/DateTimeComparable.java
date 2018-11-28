package model;

import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

/**
 * @author Sam Fletcher
 * @version 10-26-18
 * Project 4
 * 
 * Interface to be a blueprint for classes regarding dates.
 */
public interface DateTimeComparable
{
    /** Checks if statistic is newer than Gregorian Date */
    public boolean newerThan(GregorianCalendar inDateTimeUTC);
    /** Checks if statistic is older than Gregorian Date */
    public boolean olderThan(GregorianCalendar inDateTimeUTC);
    /** Checks if statistic is same as Gregorian Date */
    public boolean sameAs(GregorianCalendar inDateTimeUTC);
    /** Checks if statistic is newer than Zoned Date */
    public boolean newerThan(ZonedDateTime inDateTimeUTC);
    /** Checks if statistic is older than Zoned Date */
    public boolean olderThan(ZonedDateTime inDateTimeUTC);
    /** Checks if statistic is same as Zoned Date */
    public boolean sameAs(ZonedDateTime inDateTimeUTC);
}
