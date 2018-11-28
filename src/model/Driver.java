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
    public Driver(int year, int month, int day, int hour, int minute, String directory) throws IOException
    {
        MapData mapData = new MapData(year, month, day, hour, minute, directory);
        mapData.parseFile();
    }

}
