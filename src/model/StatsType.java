package model;

/**
 * @author Sam Fletcher
 * @version 10-26-18
 * Project 4
 * 
 * Enumerator to separate statistics into four categories.
 */
public enum StatsType
{
    AVERAGE,
    MINIMUM,
    MAXIMUM,
    TOTAL;
    
    public static StatsType getStatsType(String stat)
    {
        if(stat.equalsIgnoreCase("Average"))
        {
            return AVERAGE;
        }
        else if(stat.equalsIgnoreCase("Minimum"))
        {
            return MINIMUM;
        }
        else if(stat.equalsIgnoreCase("Maximum"))
        {
            return MAXIMUM;
        }
        else if(stat.equalsIgnoreCase("Total"))
        {
            return TOTAL;
        }
        else
        {
            return null;
        }
    }
}
