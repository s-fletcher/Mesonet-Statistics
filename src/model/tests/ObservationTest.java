package model.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Observation;

public class ObservationTest extends Observation
{

    @Test
    public void testObservationDoubleString()
    {
        // Setting up objects
        Observation validObs = new Observation(36.2, "TEST");
        Observation invalidObs = new Observation(-990, "TEST2");
        
        
        // Testing objects
        assertEquals(36.2, validObs.getValue(), 0.01);
        assertEquals(-990, invalidObs.getValue(), 0.01);
        assertEquals("TEST", validObs.getStid());
        assertEquals("TEST2", invalidObs.getStid());
        assertEquals(true, validObs.isValid());
        assertEquals(false, invalidObs.isValid());
    }

    @Test
    public void testObservation()
    {
        // Setting up object
        Observation obs = new Observation();
        
        // Testing object
        assertEquals(0, obs.getValue(), 0.01);
        assertEquals("NOTSET", obs.getStid());
        assertEquals(false, obs.isValid());
    }

    @Test
    public void testSets()
    {
        // Setting up object
        Observation obs = new Observation();
        
        // Setting values
        obs.setStid("TESTSET");
        obs.setValue(15.4);
        
        // Testing object
        assertEquals(15.4, obs.getValue(), 0.01);
        assertEquals("TESTSET", obs.getStid());
    }

    @Test
    public void testToString()
    {
        // Setting up object
        Observation obs = new Observation(36.2, "TEST");
        
        // Creating expected
        String expected = "TEST 36.2 true";
        
        // Testing object
        assertEquals(expected, obs.toString());
        
    }
}
