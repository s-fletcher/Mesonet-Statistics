package model;

/**
 * @author Sam Fletcher
 * @version 10-26-18
 * Project 4
 * 
 * Class that is in charge of calculating statistics.
 */
public abstract class AbstractObservation
{
    protected boolean valid;
    
    /**
     * Super constructor that checks if observation is valid
     */
    public AbstractObservation(double value)
    {
        if(value <= -900)
        {
            valid = false;
        }
        else
        {
            valid = true;
        }
    }
    
    /**
     * Default super constructor
     */
    public AbstractObservation()
    {
        valid = false;
    }
    
    /**
     * Returns object's validity
     * @return
     */
    public boolean isValid()
    {
        return valid;
    }
}
