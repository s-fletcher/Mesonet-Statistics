package com.samfletcher;
/**
 * @author Sam Fletcher
 * @version 10-26-18
 * Project 3
 * 
 * An object that stores the values of each observation.
 */

public class Observation extends AbstractObservation
{
    /** Value of the Observation object */
    private double value;
    /** STID of the Observation object */
    private String stid;
    
    /**
     * Creates Observation object
     * @param value Value of the observation
     * @param stid STID of the observation
     */
    public Observation(double value, String stid)
    {
        super(value);
        this.value = value;
        this.stid = stid;
    }

    /**
     * Default constructor for Observation object
     */
    public Observation()
    {
        super();
        stid = "NOTSET";
    }
    
    /**
     * Print's out Observation
     * Method used to debug
     */
    public String toString()
    {
        return stid + " " + value + " " + isValid();
    }
    
    //-------------GETTERS AND SETTERS-------------//
    
    /**
     * @return the value
     */
    public double getValue()
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value)
    {
        this.value = value;
    }

    /**
     * @return the STID
     */
    public String getStid()
    {
        return stid;
    }

    /**
     * @param stid the STID to set
     */
    public void setStid(String stid)
    {
        this.stid = stid;
    }
}
