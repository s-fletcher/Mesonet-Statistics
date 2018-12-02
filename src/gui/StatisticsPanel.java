package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Sam Fletcher
 * @version 11-28-18
 * Project 4
 * 
 * A Panel that is in charge of selecting
 * the statistics to calculate
 */
public class StatisticsPanel extends JPanel
{

    /**
     * Serial ID
     */
    private static final long serialVersionUID = 1L;
    /** Holds all the radio buttons as an array list */
    private static ArrayList<JRadioButton> radioButtons = new ArrayList<>();    

    /**
     * Main constructor for the Statistics Panel.
     * 
     * Constructs 3 radio buttons.
     */
    public StatisticsPanel()
    {
        super();
        
        GridLayout layout = new GridLayout(3,0);
        setLayout(layout);
        setBackground(new Color(116,121,111));
        JRadioButton radioButton1 = new JRadioButton("MINIMUM");
        JRadioButton radioButton2 = new JRadioButton("AVERAGE");
        JRadioButton radioButton3 = new JRadioButton("MAXIMUM");
        radioButtons.add(radioButton1);
        radioButtons.add(radioButton2);
        radioButtons.add(radioButton3);
        ButtonGroup bg = new ButtonGroup();
        for(JRadioButton element : radioButtons)
        {
            element.setBackground(new Color(116,121,111));
            element.setFocusable(false);
            bg.add(element);
            add(element);
        }
        TitledBorder title = BorderFactory.createTitledBorder("Statistics");
        title.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        setBorder(title);
    }
    
    /**
     * @return an ArrayList of the radio Buttons
     */
    public static ArrayList<JRadioButton> getRadioButtons()
    {
        return radioButtons;
    }
}
