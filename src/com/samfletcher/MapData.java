package com.samfletcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TreeMap; 

/**
 * @author Sam Fletcher
 * @version 10-26-18
 * Project 3
 * 
 * An object that stores all the Observations and is able to
 * calculate the max, min, total and average by generating statistics.
 */
public class MapData
{
    /** Holds the name of the file */
    private String fileName;

    /** Acts as a catalog for all of the data 
     * EX:
     * <SRAD, <ALL OBSERVATIONS>>
     * <TAIR, <ALL OBSERVATIONS>>
     * <TA9M, <ALL OBSERVATIONS>>
     */
    private HashMap<String, ArrayList<Observation>> dataCatalog
            = new HashMap<String, ArrayList<Observation>>();
    /** Holds all of the statistics 
     * EX:
     * <AVERAGE, <SRAD, Statistic>>
     * <MINIMUM, <TAIR, Statistic>>
     * <MAXIMUM, <TA9M, Statistic>>
     */
    private EnumMap<StatsType, TreeMap<String, Statistics>> statistics
            = new EnumMap<StatsType, TreeMap<String, Statistics>>(StatsType.class);
    /** Holds all of the positions in file */
    private TreeMap<String, Integer> paramPositions = new TreeMap<String, Integer>();
    
    /** Number of missing observations needed to warn user */
    private final int NUMBER_OF_MISSING_OBSERVATIONS = 10;
    /** Constant for Mesonet name */
    private final String MESONET = "Mesonet";
    /** Constant for TA9M name */
    private final String TA9M = "TA9M";
    /** Constant for TAIR name */
    private final String TAIR = "TAIR";
    /** Constant for SRAD name */
    private final String SRAD = "SRAD";
    /** Constant for STID name */
    private final String STID = "STID";
    
    /** The number of stations within file (ignoring validity) */
    private Integer numberOfStations = null;
    /** Holds date of the file */
    private GregorianCalendar utcDateTime;
        
    /**
     * Creates the MapData Object
     * @param year Year of file                                           
     * @param month Month of file
     * @param day Day of file
     * @param hour Hour of file
     * @param minute Minute of file
     * @param directory Directory file is stored in
     */
    public MapData(int year, int month, int day, int hour, int minute, String directory)
    {
        fileName = directory + "/" + createFileName(year, month, day, hour, minute);
        utcDateTime = new GregorianCalendar(year, month-1, day, hour, minute);
    }
    
    /**
     * Default constructor for MapData object
     */
    public MapData()
    {
        fileName = "data/" + createFileName(0000,00,00,00,00);
        utcDateTime = new GregorianCalendar(0000, 0, 0, 0, 0);    
    }
    /**
     * Creates file name with given information
     * @return properly formatted file name
     */
    public String createFileName(int year, int month, int day, int hour, int minute)
    {
        return String.format("%04d%02d%02d%02d%02d.mdf", year, month, day, hour, minute);
    }
    
    /**
     * Parses the parameter header to set correct index's for each category
     * @param inParamStr The string to be parsed
     * @throws IOException 
     */
    private void parseParamHeader(String inParamStr) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("./"+fileName));
        // Skipping first two lines then setting up Scanner of headers
        br.readLine();
        br.readLine();
        
        String[] line = br.readLine().trim().split("\\s+");
        br.close();
        // Checking each token in the line the contains headers
        for(int i = 0; i < line.length; i++)
        {
            if(line[i].equals(STID))
            {
                paramPositions.put(STID, i);
            }
            else if(line[i].equals(TAIR))
            {
                paramPositions.put(TAIR, i);
            }
            else if(line[i].equals(SRAD))
            {
                paramPositions.put(SRAD, i);
            }
            else if(line[i].equals(TA9M))
            {
                paramPositions.put(TA9M, i);
            }
        }    
    }
    
    /**
     * @param inParamStr Param to grab from paramPositions
     * @return The index of the param
     */
    public Integer getIndexOf(String inParamStr)
    {
        return paramPositions.get(inParamStr);
    }
    
    /**
     * Parses file and organizes all the information into the
     * designated instance variables
     * @throws IOException 
     */
    public void parseFile() throws IOException
    {
        File tmpDir = new File("./"+fileName);
        if(tmpDir.exists())
        {
            parseParamHeader(fileName);
            prepareDataCatalog();
        }
        else {
            System.out.println("The file \"" + fileName + "\" does not exist.");
        }
    }
    
    /**
     * Calculates all of the statistics and
     * stores them in the statistics EnumMap
     */
    private void calculateAllStatistics()
    {
        String[] params = {SRAD, TA9M, TAIR};
        TreeMap<String, Statistics> averageTM = new TreeMap<>();
        TreeMap<String, Statistics> maxTM = new TreeMap<>();
        TreeMap<String, Statistics> minTM = new TreeMap<>();
        TreeMap<String, Statistics> totalTM = new TreeMap<>();
        for(String param : params)
        {
            ArrayList<Observation> currentObs = dataCatalog.get(param);
            // Checking how many are invalid
            int invalid = 0;
            for(Observation obs : currentObs)
            {
                if(!obs.isValid())
                {
                    invalid++;
                }
            }
            if(invalid >= NUMBER_OF_MISSING_OBSERVATIONS)
            {
                System.out.println("There are more than " + NUMBER_OF_MISSING_OBSERVATIONS + " " 
                        + param + " missing observations(" + invalid + "). Proceed with caution.");
            }
            
            // Calculating statistics
            double maxValue = -Double.MAX_VALUE;
            Observation maxObs = null;
            double minValue = Double.MAX_VALUE;
            Observation minObs = null;
            double total = 0;
            double valid = 0;
            for(Observation obs : currentObs)
            {
                if(obs.isValid())
                {
                    if(obs.getValue() > maxValue)
                    {
                        maxObs = obs;
                        maxValue = obs.getValue();
                    }
                    if(obs.getValue() < minValue)
                    {
                        minObs = obs;
                        minValue = obs.getValue();
                    }
                    total += obs.getValue();
                    valid ++;
                }
            }
            double average = total/valid;
            average = Math.round(average * 10.0) / 10.0;
            Statistics maxStat = new Statistics(maxValue, maxObs.getStid(), 
                    utcDateTime, numberOfStations, StatsType.MAXIMUM);
            Statistics minStat = new Statistics(minValue, minObs.getStid(),
                    utcDateTime, numberOfStations, StatsType.MINIMUM);
            Statistics averageStat = new Statistics(average, MESONET, 
                    utcDateTime, numberOfStations, StatsType.AVERAGE);
            Statistics totalStat = new Statistics(total, MESONET,
                    utcDateTime, numberOfStations, StatsType.TOTAL);
            averageTM.put(param, averageStat);
            maxTM.put(param, maxStat);
            minTM.put(param, minStat);
            totalTM.put(param, totalStat);
        }
        statistics.put(StatsType.AVERAGE, averageTM);
        statistics.put(StatsType.MAXIMUM, maxTM);
        statistics.put(StatsType.MINIMUM, minTM);
        statistics.put(StatsType.TOTAL, totalTM);
    }
    
    /**
     * Prepares the data catalog
     * @throws IOException 
     */
    private void prepareDataCatalog() throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("./"+fileName));
        // Setting up header index's
        // Skipping to line of irrelevant data
        br.readLine();
        br.readLine();
        br.readLine();
        // Setting up variables to hold data
        String stid = null;
        double tair = 0;
        double srad = 0;
        double ta9m = 0;
        String line;
        ArrayList<Observation> tairObs = new ArrayList<>();
        ArrayList<Observation> sradObs = new ArrayList<>();
        ArrayList<Observation> ta9mObs = new ArrayList<>();
        // Going through whole file, line by line
        int count = 0;
        while((line = br.readLine()) != null) 
        {
            count++;
            String[] lineData = line.trim().split("\\s+");
            stid = lineData[getIndexOf(STID)];
            tair = Double.parseDouble(lineData[getIndexOf(TAIR)]);
            srad = Double.parseDouble(lineData[getIndexOf(SRAD)]);
            ta9m = Double.parseDouble(lineData[getIndexOf(TA9M)]);
            tairObs.add(new Observation(tair, stid));
            sradObs.add(new Observation(srad, stid));
            ta9mObs.add(new Observation(ta9m, stid));
        }
        br.close();
        dataCatalog.put(TAIR, tairObs);
        dataCatalog.put(SRAD, sradObs);
        dataCatalog.put(TA9M, ta9mObs);
        numberOfStations = count;    
    }
    
    /**
     * Calculates all of the statistics
     */
    private void calculateStatistics() 
    {
        calculateAllStatistics();
    }
    
    /**
     * Gets a statistic
     * @param type The StatsType to get
     * @param paramId The id of the param to get
     */
    public Statistics getStatistics(StatsType type, String paramId)
    {
        TreeMap<String, Statistics> tm = statistics.get(type);
        return tm.get(paramId);
    }
    
    /**
     * Print's out statistics of MapData by calculating the
     * different averages, maximums, an minimums.
     */
    @Override
    public String toString()
    {
        String result = "";
        try 
        {
            calculateStatistics();
            
            double tairMaxValue = getStatistics(StatsType.MAXIMUM, TAIR).getValue();
            String tairMaxStid = getStatistics(StatsType.MAXIMUM, TAIR).getStid();
            double tairMinValue = getStatistics(StatsType.MINIMUM, TAIR).getValue();
            String tairMinStid = getStatistics(StatsType.MINIMUM, TAIR).getStid();
            double tairAverageValue = getStatistics(StatsType.AVERAGE, TAIR).getValue();
            String tairAverageStid = getStatistics(StatsType.AVERAGE, TAIR).getStid();
            
            double ta9mMaxValue = getStatistics(StatsType.MAXIMUM, TA9M).getValue();
            String ta9mMaxStid = getStatistics(StatsType.MAXIMUM, TA9M).getStid();
            double ta9mMinValue = getStatistics(StatsType.MINIMUM, TA9M).getValue();
            String ta9mMinStid = getStatistics(StatsType.MINIMUM, TA9M).getStid();
            double ta9mAverageValue = getStatistics(StatsType.AVERAGE, TA9M).getValue();
            String ta9mAverageStid = getStatistics(StatsType.AVERAGE, TA9M).getStid();
            
            double sradMaxValue = getStatistics(StatsType.MAXIMUM, SRAD).getValue();
            String sradMaxStid = getStatistics(StatsType.MAXIMUM, SRAD).getStid();
            double sradMinValue = getStatistics(StatsType.MINIMUM, SRAD).getValue();
            String sradMinStid = getStatistics(StatsType.MINIMUM, SRAD).getStid();
            double sradAverageValue = getStatistics(StatsType.AVERAGE, SRAD).getValue();
            String sradAverageStid = getStatistics(StatsType.AVERAGE, TA9M).getStid();
            
            result += "========================================================\n";
            result += "=== " + utcDateTime.getTime() + " ===\n";
            result += "========================================================\n";
            result += "Maximum Air Temperature[1.5m] = " + tairMaxValue + " C at " + tairMaxStid + "\n";
            result += "Minimum Air Temperature[1.5m] = " + tairMinValue + " C at " + tairMinStid + "\n";
            result += "Average Air Temperature[1.5m] = " + tairAverageValue+" C at "+tairAverageStid+"\n";
            result += "========================================================\n";
            result += "========================================================\n";
            result += "Maximum Air Temperature[9.0m] = " + ta9mMaxValue + " C at " + ta9mMaxStid + "\n";
            result += "Minimum Air Temperature[9.0m] = " + ta9mMinValue + " C at " + ta9mMinStid + "\n";
            result += "Average Air Temperature[9.0m] = " + ta9mAverageValue+" C at "+ta9mAverageStid+"\n";
            result += "========================================================\n";
            result += "========================================================\n";
            result += "Maximum Solar Radiation[1.5m] = " + sradMaxValue +" W/m^2 at " + sradMaxStid + "\n";
            result += "Minimum Solar Radiation[1.5m] = " + sradMinValue +" W/m^2 at " + sradMinStid + "\n";
            result += "Average Solar Radiation[1.5m] = "+sradAverageValue+" W/m^2 at "+sradAverageStid+"\n";
            result += "========================================================\n";
        // Catching and handling errors
        } catch (NullPointerException e)
        {
            System.out.println("Please parse the file (" + fileName + ") first: " + e);
            result = "";
        }
        return result;
    }

    //-------------GETTERS AND SETTERS-------------//    
    
    /**
     * @return the fileName
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * @return the dataCatalog
     */
    public HashMap<String, ArrayList<Observation>> getDataCatalog()
    {
        return dataCatalog;
    }

    /**
     * @return the paramPositions
     */
    public TreeMap<String, Integer> getParamPositions()
    {
        return paramPositions;
    }

    /**
     * @return the utcDateTime
     */
    public GregorianCalendar getUtcDateTime()
    {
        return utcDateTime;
    }
    
}

