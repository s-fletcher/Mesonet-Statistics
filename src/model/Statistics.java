package model;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Sam Fletcher
 * @version 10-26-18
 * Project 4
 * 
 * Class that holds all of the statistics.
 */
public class Statistics extends Observation implements DateTimeComparable
{
    /** Format for Gregorian Calendar */
    protected final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss z";
    /** Date formatter */
    protected DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    /** Stores the date in GregorianCalendar*/
    private GregorianCalendar utcDateTime;
    /** Stores the date in ZonedDateTime */
    private ZonedDateTime zdtDateTime;
    /** Total number of stations */
    private int numberOfReportingStations;
    /** The type of statistic it is */
    private StatsType statType;
    
    /**
     * Stores more data excluding date
     * @param value
     * @param stid
     * @param numberOfValidStations
     * @param inStatType
     */
    public Statistics(double value, String stid, 
            ZonedDateTime dateTime, int numberOfValidStations, StatsType inStatType)
    {
        super(value, stid);
        numberOfReportingStations = numberOfValidStations;
        statType = inStatType;
        zdtDateTime = dateTime;
    }
    
    /**
     * Stores more data including date
     * @param value
     * @param stid
     * @param dateTime
     * @param numberOfValidStations
     * @param inStatType
     */
    public Statistics(double value, String stid, 
            GregorianCalendar dateTime, int numberOfValidStations, StatsType inStatType)
    {
        super(value, stid);
        numberOfReportingStations = numberOfValidStations;
        statType = inStatType;
        utcDateTime = dateTime;
    }
    
    /**
     * Default constructor that holds more data
     */
    public Statistics()
    {
        super();
        numberOfReportingStations = 0;
        statType = null;
        utcDateTime = null;
        zdtDateTime = null;
    }
    
    /**
     * Creates a GregorianCalendar object with the given string
     * @param dateTimeStr String to be converted to a date
     * @return The GregorianCalendar that was created
     * @throws ParseException 
     */
    public GregorianCalendar createDateFromString(String dateTimeStr) throws ParseException
    {
        String[] elements = dateTimeStr.split("-");
        int year = Integer.parseInt(elements[0]);
        int month = Integer.parseInt(elements[1]);
        elements = elements[2].split("'");
        int day = Integer.parseInt(elements[0]);
        elements = elements[2].split(":");
        int hour = Integer.parseInt(elements[0]);
        int minute = Integer.parseInt(elements[1]);
        elements = elements[2].split(" ");
        int second = Integer.parseInt(elements[0]);
        String timeZone = elements[1];
        
        GregorianCalendar cal = new GregorianCalendar(year, month-1, day, hour, minute, second);
        cal.setTimeZone(TimeZone.getTimeZone(timeZone));
        
        utcDateTime = cal;
        
        return cal;
    }
    
    /**
     * Creates a ZonedDateTime object with the given string
     * @param dateTimeStr String to be converted to a date
     * @return The ZonedDateTime that was created
     */
    public ZonedDateTime createZDateFromString(String dateTimeStr)
    {
        ZonedDateTime cal = ZonedDateTime.parse(dateTimeStr, format);
        zdtDateTime = cal;
        return cal;
    }
    
    /**
     * Creates a String with the given date
     * @param calendar The GregorianCalendar to be converted to a string
     * @return The String that was created
     */
    public String createStringFromDate(GregorianCalendar calendar)
    {
        String year = String.format("%04d", calendar.get(Calendar.YEAR));
        String month = String.format("%02d", calendar.get(Calendar.MONTH)+1);
        String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
        String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.format("%02d", calendar.get(Calendar.MINUTE));
        String second = String.format("%02d", calendar.get(Calendar.SECOND));
        String result = year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" 
                + second + " UTC";        
        return result;
    }
    
    /**
     * Creates a String with the given date
     * @param calendar The ZonedDateTime to be converted to a string
     * @return The String that was created
     */
    public String createStringFromDate(ZonedDateTime calendar)
    {
        int year = calendar.getYear();
        int month = calendar.getMonthValue();
        int day = calendar.getDayOfMonth();
        int hour = calendar.getHour();
        int minute = calendar.getMinute();
        int second = calendar.getSecond();
        String timeZone = calendar.getZone().getId();
        String result = String.format("%04d-%02d-%02dT%02d:%02d:%02dZ[%s]", 
                year, month, day, hour, minute, second, timeZone);        
        return result;
    }
    
    /**
     * Checks if statistic is newer than Gregorian Date
     * @param inDateTime
     */
    @Override
    public boolean newerThan(GregorianCalendar inDateTime)
    {
        return (inDateTime.compareTo(utcDateTime) == -1);
    }
    
    /**
     * Checks if statistic is older than Gregorian Date
     * @param inDateTime
     */
    @Override
    public boolean olderThan(GregorianCalendar inDateTime)
    {
        return (inDateTime.compareTo(utcDateTime) == 1);
    }
    
    /**
     * Checks if statistic is same as Gregorian Date
     * @param inDateTime
     */
    @Override
    public boolean sameAs(GregorianCalendar inDateTime)
    {
        return (inDateTime.compareTo(utcDateTime) == 0);
    }
    
    /**
     * Checks if statistic is newer than Zoned Date
     * @param inDateTimeUTC
     */
    @Override
    public boolean newerThan(ZonedDateTime inDateTime)
    {
        return (inDateTime.compareTo(zdtDateTime) == -1);
    }

    /**
     * Checks if statistic is older than Zoned Date
     * @param inDateTimeUTC
     */
    @Override
    public boolean olderThan(ZonedDateTime inDateTime)
    {
        return (inDateTime.compareTo(zdtDateTime) == 1);
    }
    
    /**
     * Checks if statistic is same as Zoned Date
     * @param inDateTimeUTC
     */
    @Override
    public boolean sameAs(ZonedDateTime inDateTime)
    {
        return (inDateTime.compareTo(zdtDateTime) == 0);
    }
    
    /**
     * Creates string for user to read
     * @return The string that is created
     */
    public String toString()
    {
        String time;
        if(utcDateTime != null)
        {
            time = createStringFromDate(utcDateTime);
        }
        else if(zdtDateTime != null)
        {
            time = createStringFromDate(zdtDateTime);
        }
        else
        {
            time = "Time-not-set";
        }
        return getStid() + " " + getValue() + " " + isValid() + " " + time + " " 
                + numberOfReportingStations + " " + statType;
    }
    
    //-------------GETTERS AND SETTERS-------------//


    /**
     * @return the utcDateTime in string
     */
    public String getUtcDateTime()
    {
        if(zdtDateTime != null)
        {
            return createStringFromDate(zdtDateTime);
        }
        else if(utcDateTime != null)
        {
            return createStringFromDate(utcDateTime);
        }
        else {
            return "Error: No date time set.";
        }
    }

    /**
     * @return the GregorianCalendar utcDateTime
     */
    public GregorianCalendar getGregorianCalendar()
    {
        return utcDateTime;
    }
    
    /**
     * @return the ZonedDateTime zdtDateTime
     */
    public ZonedDateTime getZonedDateTime()
    {
        return zdtDateTime;
    }
    
    /**
     * @return the numberOfReportingStations
     */
    public int getNumberOfReportingStations()
    {
        return numberOfReportingStations;
    }

    /**
     * @param numberOfReportingStations the numberOfReportingStations to set
     */
    public void setNumberOfReportingStations(int numberOfReportingStations)
    {
        this.numberOfReportingStations = numberOfReportingStations;
    }

    /**
     * @return the statType
     */
    public StatsType getStatType()
    {
        return statType;
    }

    /**
     * @param statType the statType to set
     */
    public void setStatType(StatsType statType)
    {
        this.statType = statType;
    }
}
