package model;

import java.io.IOException;

/**
 * @author Sam Fletcher
 * @version 11-28-18
 * Project 4
 * 
 * The Driver that simplifies the process
 */
public class Driver
{
    
    private MapData mapData;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String directory;
    
    public Driver(String fileName, String directory) throws Exception
    {
        if(fileName.length() != 16)
        {
            throw new Exception("Invalid file");
        }
        year = Integer.parseInt(fileName.substring(0, 4));
        month = Integer.parseInt(fileName.substring(4, 6));
        day = Integer.parseInt(fileName.substring(6, 8));
        hour = Integer.parseInt(fileName.substring(8, 10));
        minute = Integer.parseInt(fileName.substring(10, 12));
        this.directory = directory;
    }
    
    public void constructAndParse() throws IOException
    {
        mapData = new MapData(year, month, day, hour, minute, directory);
        mapData.parseFile();
    }
    
    public void consoleOutput()
    {
        System.out.println(mapData);
    }

    /**
     * @return the mapData
     */
    public MapData getMapData()
    {
        return mapData;
    }
}
