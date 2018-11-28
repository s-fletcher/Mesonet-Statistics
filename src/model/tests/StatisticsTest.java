package model.tests;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Test;

import model.Statistics;
import model.StatsType;

import org.junit.Assert;

public class StatisticsTest
{
    
    @Test
    public void testStatisticsDoubleStringZonedDateTimeIntStatsType()
    {
        ZonedDateTime cal = ZonedDateTime.of(2018, 5, 22, 21, 32, 0, 0, TimeZone.getTimeZone("CST").toZoneId());
        Statistics stat = new Statistics(30, "TEST", cal, 5, StatsType.TOTAL);
        
        Assert.assertEquals(30, stat.getValue(), 0.01);
        Assert.assertEquals("TEST", stat.getStid());
        Assert.assertEquals(stat.createStringFromDate(cal), stat.getUtcDateTime());
        Assert.assertEquals(5, stat.getNumberOfReportingStations());
        Assert.assertEquals(StatsType.TOTAL, stat.getStatType());
    }

    @Test
    public void testStatisticsDoubleStringGregorianCalendarIntStatsType()
    {
        GregorianCalendar cal = new GregorianCalendar();
        Statistics stat = new Statistics(30, "TEST", cal, 5, StatsType.TOTAL);
        
        Assert.assertEquals(30, stat.getValue(), 0.01);
        Assert.assertEquals("TEST", stat.getStid());
        Assert.assertEquals(stat.createStringFromDate(cal), stat.getUtcDateTime());
        Assert.assertEquals(5, stat.getNumberOfReportingStations());
        Assert.assertEquals(StatsType.TOTAL, stat.getStatType());
    }

    @Test
    public void testStatistics()
    {
        Statistics stat = new Statistics();
        
        Assert.assertEquals(0, stat.getValue(), 0.01);
        Assert.assertEquals("NOTSET", stat.getStid());
        Assert.assertEquals("Error: No date time set.", stat.getUtcDateTime());
        Assert.assertEquals(0, stat.getNumberOfReportingStations());
        Assert.assertEquals(null, stat.getStatType());
    }

    @Test
    public void testCreateDateFromString() throws ParseException
    {
        Statistics stat1 = new Statistics();
        Statistics stat2 = new Statistics();
        
        stat1.createDateFromString("2018-8-22'T'8:49:30 CST");
        String expected1 = "Wed Aug 22 08:49:30 CDT 2018";
        String actual1 = stat1.getGregorianCalendar().getTime().toString();
        Assert.assertEquals(expected1, actual1);
        
        stat2.createZDateFromString("2018-08-22T08:49:30 UTC");
        String expected2 = "2018-08-22T08:49:30Z[UTC]";
        String actual2 = stat2.getZonedDateTime().toString();
        Assert.assertEquals(expected2, actual2);
    }
    
    @Test
    public void testCreateStringFromDate() throws ParseException
    {
        Statistics stat = new Statistics();
        stat.createDateFromString("2018-8-22'T'13:49:30 CST");
        String expected = "2018-8-22'T'13:49:30 CST";
        String actual = stat.createStringFromDate(stat.getGregorianCalendar());
        Assert.assertEquals(expected, actual); 
        
        Statistics stat2 = new Statistics();
        stat2.createZDateFromString("2018-08-22T08:49:30 UTC");
        String expected2 = "2018-08-22T08:49:30Z[UTC]";
        String actual2 = stat2.createStringFromDate(stat2.getZonedDateTime());
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void testNewerThan() throws ParseException
    {
        GregorianCalendar gc = null;
        ZonedDateTime zdt = null;
        
        Statistics gcStat1 = new Statistics(10, "TEST", gc, 1, StatsType.TOTAL);
        Statistics gcStat2 = new Statistics(20, "TEST2", gc, 2, StatsType.TOTAL);
        Statistics gcStat3 = new Statistics(30, "TEST3", gc, 3, StatsType.TOTAL);
        Statistics zdtStat1 = new Statistics(10, "TEST", zdt, 1, StatsType.TOTAL);
        Statistics zdtStat2 = new Statistics(20, "TEST2", zdt, 2, StatsType.TOTAL);
        Statistics zdtStat3 = new Statistics(30, "TEST3", zdt, 3, StatsType.TOTAL);
        gcStat1.createDateFromString("2018-08-22'T'13:49:30 CST");
        gcStat2.createDateFromString("2018-08-23'T'13:49:30 CST");
        gcStat3.createDateFromString("2018-08-21'T'13:49:30 CST");
        zdtStat1.createZDateFromString("2018-08-22T13:49:30 CST");
        zdtStat2.createZDateFromString("2018-08-23T13:49:30 CST");
        zdtStat3.createZDateFromString("2018-08-21T13:49:30 CST");
        Assert.assertEquals(true, gcStat1.newerThan(gcStat3.getGregorianCalendar()));
        Assert.assertEquals(false, gcStat3.newerThan(gcStat1.getGregorianCalendar()));
        Assert.assertEquals(true, zdtStat1.newerThan(zdtStat3.getZonedDateTime()));
        Assert.assertEquals(false, zdtStat3.newerThan(zdtStat1.getZonedDateTime()));
    }

    @Test
    public void testOlderThan() throws ParseException
    {
        GregorianCalendar gc = null;
        ZonedDateTime zdt = null;
        
        Statistics gcStat1 = new Statistics(10, "TEST", gc, 1, StatsType.TOTAL);
        Statistics gcStat2 = new Statistics(20, "TEST2", gc, 2, StatsType.TOTAL);
        Statistics gcStat3 = new Statistics(30, "TEST3", gc, 3, StatsType.TOTAL);
        Statistics zdtStat1 = new Statistics(10, "TEST", zdt, 1, StatsType.TOTAL);
        Statistics zdtStat2 = new Statistics(20, "TEST2", zdt, 2, StatsType.TOTAL);
        Statistics zdtStat3 = new Statistics(30, "TEST3", zdt, 3, StatsType.TOTAL);
        gcStat1.createDateFromString("2018-08-22'T'13:49:30 CST");
        gcStat2.createDateFromString("2018-08-23'T'13:49:30 CST");
        gcStat3.createDateFromString("2018-08-21'T'13:49:30 CST");
        zdtStat1.createZDateFromString("2018-08-22T13:49:30 CST");
        zdtStat2.createZDateFromString("2018-08-23T13:49:30 CST");
        zdtStat3.createZDateFromString("2018-08-21T13:49:30 CST");
        Assert.assertEquals(false, gcStat1.olderThan(gcStat3.getGregorianCalendar()));
        Assert.assertEquals(true, gcStat3.olderThan(gcStat1.getGregorianCalendar()));
        Assert.assertEquals(false, zdtStat1.olderThan(zdtStat3.getZonedDateTime()));
        Assert.assertEquals(true, zdtStat3.olderThan(zdtStat1.getZonedDateTime()));
    }
        
    @Test
    public void testSameAs() throws ParseException
    {
        GregorianCalendar gc = null;
        ZonedDateTime zdt = null;
        
        Statistics gcStat1 = new Statistics(10, "TEST", gc, 1, StatsType.TOTAL);
        Statistics gcStat2 = new Statistics(20, "TEST2", gc, 2, StatsType.TOTAL);
        Statistics gcStat3 = new Statistics(30, "TEST3", gc, 3, StatsType.TOTAL);
        Statistics zdtStat1 = new Statistics(10, "TEST", zdt, 1, StatsType.TOTAL);
        Statistics zdtStat2 = new Statistics(20, "TEST2", zdt, 2, StatsType.TOTAL);
        Statistics zdtStat3 = new Statistics(30, "TEST3", zdt, 3, StatsType.TOTAL);
        gcStat1.createDateFromString("2018-08-22'T'13:49:30 CST");
        gcStat2.createDateFromString("2018-08-23'T'13:49:30 CST");
        gcStat3.createDateFromString("2018-08-22'T'13:49:30 CST");
        zdtStat1.createZDateFromString("2018-08-22T13:49:30 UTC");
        zdtStat2.createZDateFromString("2018-08-23T13:49:30 UTC");
        zdtStat3.createZDateFromString("2018-08-22T13:49:30 UTC");
        Assert.assertEquals(true, gcStat1.sameAs(gcStat3.getGregorianCalendar()));
        Assert.assertEquals(false, gcStat1.sameAs(gcStat2.getGregorianCalendar()));
        Assert.assertEquals(true, zdtStat1.sameAs(zdtStat3.getZonedDateTime()));
        Assert.assertEquals(false, zdtStat1.sameAs(zdtStat2.getZonedDateTime()));
    }
    
    @Test
    public void testToString() throws ParseException
    {
        ZonedDateTime zdt = null;
        ZonedDateTime gc = null;
        
        Statistics stat1 = new Statistics(10, "TEST", zdt, 1, StatsType.TOTAL);
        Statistics stat2 = new Statistics(10, "TEST", zdt, 1, StatsType.TOTAL);
        Statistics stat3 = new Statistics(10, "TEST", gc, 1, StatsType.TOTAL);
        stat1.createZDateFromString("2018-08-22T13:49:30 UTC");
        stat3.createDateFromString("2018-08-22'T'13:49:30 CST");
        String expected1 = "TEST 10.0 true 2018-08-22T13:49:30Z[UTC] 1 TOTAL";
        String expected2 = "TEST 10.0 true Time-not-set 1 TOTAL";
        String expected3 = "TEST 10.0 true 2018-8-22'T'13:49:30 CST 1 TOTAL";
        Assert.assertEquals(expected1, stat1.toString());
        Assert.assertEquals(expected2, stat2.toString());
        Assert.assertEquals(expected3, stat3.toString());
    }

    @Test
    public void testSetNumberOfReportingStations()
    {
        ZonedDateTime zdt = null;
        
        Statistics stat = new Statistics(10, "TEST", zdt, 1, StatsType.TOTAL);
        stat.setNumberOfReportingStations(13);
        Assert.assertEquals(13, stat.getNumberOfReportingStations());
        
    }
    
    @Test
    public void testSetStatType()
    {
        ZonedDateTime zdt = null;
        
        Statistics stat = new Statistics(10, "TEST", zdt, 1, StatsType.TOTAL);
        stat.setStatType(StatsType.AVERAGE);
        Assert.assertEquals(StatsType.AVERAGE, stat.getStatType());   
    }

}
