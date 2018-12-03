package model.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.GregorianCalendar;

import org.junit.Test;

import model.MapData;

public class MapDataTest 
{

    @Test
    public void testMapDataIntIntIntIntIntString()
    {
        // Constructing object
        MapData map = new MapData(2018, 5, 22, 8, 22, "data");
        GregorianCalendar cal = new GregorianCalendar(2018, 4, 22, 8, 22);
        // Testing object
        assertEquals("data\\201805220822.mdf", map.getFileName());
        assertEquals(cal, map.getUtcDateTime());
    }

    @Test
    public void testMapData()
    {
        // Constructing object
        MapData map = new MapData();
        GregorianCalendar cal = new GregorianCalendar(0000, 0, 0, 0, 0);
        // Testing object
        assertEquals("data\\000000000000.mdf", map.getFileName());
        assertEquals(cal, map.getUtcDateTime());
        
    }

    @Test
    public void testParseFile() throws IOException
    {
        // Setting up object
        MapData map = new MapData(2018, 8, 1, 7, 0, "data");
        // Testing invalid constructor
        MapData invalidMap = new MapData(1234, 12, 12, 12, 12, "data");
        invalidMap.parseFile();
        // Parse file
        map.parseFile();

        // Testing things were set
        assertEquals(map.getParamPositions().get("STID"), new Integer(0));
        assertEquals(map.getParamPositions().get("TAIR"), new Integer(4));
        assertEquals(map.getParamPositions().get("SRAD"), new Integer(13));
        assertEquals(map.getParamPositions().get("TA9M"), new Integer(14));
        assertEquals(map.getDataCatalog().get("TAIR").get(0).getStid(), "ACME");
        assertEquals(map.getDataCatalog().get("SRAD").get(1).getStid(), "ADAX");
        assertEquals(map.getDataCatalog().get("TA9M").get(2).getStid(), "ALTU");
    }
    
    @Test
    public void testToString() throws IOException
    {
        // Setting up object
        MapData map = new MapData(2018, 8, 30, 17, 45, "data");
        MapData map2 = new MapData(2018, 9, 9, 9, 9, "data");
        MapData unparsedMap = new MapData(2018, 8, 30, 17, 45, "data");
        
        // Parsing object
        map.parseFile();
        map2.parseFile();
        
        // Making sure exception and warnings are caught
        unparsedMap.toString();
        map2.toString();
        
        String result = "";
        // Adding lines to result
        result += "========================================================\n";
        result += "=== Thu Aug 30 17:45:00 CDT 2018 ===\n";
        result += "========================================================\n";
        result += "Maximum Air Temperature[1.5m] = 36.5 C at HOOK\n";
        result += "Minimum Air Temperature[1.5m] = 20.8 C at MIAM\n";
        result += "Average Air Temperature[1.5m] = 32.4 C at Mesonet\n";
        result += "========================================================\n";
        result += "========================================================\n";
        result += "Maximum Air Temperature[9.0m] = 34.9 C at HOOK\n";
        result += "Minimum Air Temperature[9.0m] = 20.7 C at MIAM\n";
        result += "Average Air Temperature[9.0m] = 31.6 C at Mesonet\n";
        result += "========================================================\n";
        result += "========================================================\n";
        result += "Maximum Solar Radiation[1.5m] = 968.0 W/m^2 at SLAP\n";
        result += "Minimum Solar Radiation[1.5m] = 163.0 W/m^2 at MIAM\n";
        result += "Average Solar Radiation[1.5m] = 828.1 W/m^2 at Mesonet\n";
        result += "========================================================\n";
                        
        assertEquals(result, map.toString());
        
    }
    
}
