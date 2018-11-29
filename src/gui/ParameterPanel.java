package gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Sam Fletcher
 * @version 11-28-18
 * Project 4
 * 
 * A Panel that is in charge of selecting the
 * parameters
 */
public class ParameterPanel extends JPanel
{
    /**
     * Serial ID
     */
    private static final long serialVersionUID = 1L;
    /** Group of all check boxes */
    public static ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
    
    /**
     * Constructs all of the parameters as check boxes
     */
    public ParameterPanel()
    {
        super();
        
        GridLayout layout = new GridLayout(6,0);
        setLayout(layout);
        setBackground(MainPanel.BACKGROUND);
        JCheckBox checkBox1 = new JCheckBox("TAIR");
        JCheckBox checkBox2 = new JCheckBox("TA9M");
        JCheckBox checkBox3 = new JCheckBox("SRAD");
        JCheckBox checkBox4 = new JCheckBox("WSPD");
        JCheckBox checkBox5 = new JCheckBox("PRES");
        checkBoxes.add(checkBox1);
        checkBoxes.add(checkBox2);
        checkBoxes.add(checkBox3);
        checkBoxes.add(checkBox4);
        checkBoxes.add(checkBox5);
        for(JCheckBox element : checkBoxes)
        {
            element.setBackground(MainPanel.BACKGROUND);
            add(element);
        }
        TitledBorder title = BorderFactory.createTitledBorder("Parameter");
        title.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        setBorder(title);
    }
    
    /**
     * @return an ArrayList of the check boxes
     */
    public static ArrayList<JCheckBox> getCheckBoxes()
    {
        return checkBoxes;
    }
}
