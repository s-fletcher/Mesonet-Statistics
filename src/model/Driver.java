package model;

import java.io.IOException;

/**
 * @author Sam Fletcher
 * @version 10-26-18
 * Project 4
 * 
 * The Driver that contains the main method to bring everything together
 */
public class Driver
{

    public static void main(String[] args) throws IOException
    {
        final int YEAR = 2018;
        final int MONTH = 8;
        final int DAY = 30;
        final int HOUR = 17;
        final int MINUTE = 45;
        
//        final int YEAR2 = 2018;
//        final int MONTH2 = 8;
//        final int DAY2 = 1;
//        final int HOUR2 = 7;
//        final int MINUTE2 = 0;
        
//        final int YEAR3 = 2018;
//        final int MONTH3 = 9;
//        final int DAY3 = 9;
//        final int HOUR3 = 9;
//        final int MINUTE3 = 9;
        
        final String directory = "data";
        
        MapData mapData = new MapData(YEAR, MONTH, DAY, HOUR, MINUTE, directory);
//        MapData mapData2 = new MapData(YEAR2, MONTH2, DAY2, HOUR2, MINUTE2, directory);
//        MapData mapData3 = new MapData(YEAR3, MONTH3, DAY3, HOUR3, MINUTE3, directory);
        
//        System.out.println(mapData);
        
        mapData.parseFile();
//        mapData2.parseFile();
//        mapData3.parseFile();
        
        System.out.println(mapData);
//        System.out.println(mapData2);
//        System.out.println(mapData3);
    }

}
