package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import model.MapData;
import model.Statistics;
import model.StatsType;

/**
 * @author Sam Fletcher
 * @version 11-28-18
 * Project 4
 * 
 * A Panel that creates the buttons, extra text at the
 * top of the frame, and the file upload function
 */
public class MainPanel extends JPanel
{

    /**
     * Serial ID
     */
    private static final long serialVersionUID = 1L;
    /** Default background color */
    public static final Color BACKGROUND = new Color(138,141,130);
    /** Header label */
    public JLabel text = new JLabel("Mesonet - We don't set records, we report them!");
    /** Calculate button */
    public JButton calculate = new JButton("Calculate");
    /** Exit button */
    public JButton exit = new JButton("Exit");
    /** Holds the TablePanel object */
    public static TablePanel tablePanel = new TablePanel();
    
    /**
     * Default constructor for the main panel.
     * 
     * Constructs the top text and lower buttons. It
     * also brings in all of the other panels to add
     * to the frame.
     */
    public MainPanel()
    {
        super();

        // Setting layout and background color
        setLayout(new BorderLayout());
        setBackground(new Color(138,141,130));
        
        // Setting up top text
        text.setOpaque(true);
        text.setBorder(new EmptyBorder(5,0,5,0));
        text.setBackground(new Color(181,181,169));
        text.setHorizontalAlignment(JLabel.CENTER);
        
        // Setting up buttons
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(BACKGROUND);
        JButton exit = new JButton("Exit");
        buttons.add(calculate);
        buttons.add(exit);
        
        // Adding components
        add(text, BorderLayout.PAGE_START);
        add(buttons, BorderLayout.PAGE_END);
        add(new ParameterPanel(), BorderLayout.LINE_START);
        add(new StatisticsPanel(), BorderLayout.CENTER);
        add(tablePanel, BorderLayout.LINE_END);
        
        // Setting focusable to false
        calculate.setFocusable(false);
        exit.setFocusable(false);
        
        /** Calculate button is pressed */
        calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Getting all of the parameters selected
                ArrayList<String> parameterSelected = new ArrayList<>();
                for(JCheckBox element : ParameterPanel.getCheckBoxes())
                {
                    if(element.isSelected())
                    {
                        parameterSelected.add(element.getText());
                    }
                }
                // Getting all statistics selected
                String statisticSelected = null;
                for(JRadioButton element : StatisticsPanel.getRadioButtons())
                {
                    if(element.isSelected())
                    {
                        statisticSelected = element.getText();
                    }
                }
                
                // Making sure all boxes are checked and a file was selected
                if(MesonetFrame.driver == null)
                {
                    JOptionPane.showMessageDialog(
                            MesonetFrame.frame, 
                            "Make sure you have selected a valid file.",
                            "Unable to calculate.",
                            JOptionPane.INFORMATION_MESSAGE
                            );  
                }
                else
                {   
                    if((parameterSelected.size() == 0) && (statisticSelected != null))
                    {
                        JOptionPane.showMessageDialog(
                                MesonetFrame.frame, 
                                "Make sure you have selected a parameter.",
                                "Unable to calculate.",
                                JOptionPane.INFORMATION_MESSAGE
                                );                
                    }
                    else if((parameterSelected.size() != 0) && (statisticSelected == null))
                    {
                        JOptionPane.showMessageDialog(
                                MesonetFrame.frame, 
                                "Make sure you have selected a statistic.",
                                "Unable to calculate.",
                                JOptionPane.INFORMATION_MESSAGE
                                );
                    }
                    else if((parameterSelected.size() == 0) && (statisticSelected == null))
                    {
                        JOptionPane.showMessageDialog(
                                MesonetFrame.frame, 
                                "Make sure you have selected a parameter and statistic.",
                                "Unable to calculate.",
                                JOptionPane.INFORMATION_MESSAGE
                                );  
                    }
                    // Everything is selected -> complete table
                    else
                    {
                        MapData mapData = MesonetFrame.driver.getMapData();
                        mapData.calculateStatistics();
                        ArrayList<Statistics> statistics = new ArrayList<>();
                        for(int i = 0; i < parameterSelected.size(); i++)
                        {
                            statistics.add(mapData.getStatistics(StatsType.getStatsType(statisticSelected),parameterSelected.get(i)));
                        }
                        tablePanel.defaultModel.setRowCount(0);
                        for(int i = 0; i < statistics.size(); i++)
                        {
                            Statistics stat = statistics.get(i);
                            String[] data = {
                                    stat.getStid(),
                                    parameterSelected.get(i),
                                    stat.getStatType().toString(),
                                    String.valueOf(stat.getValue()),
                                    String.valueOf(stat.getNumberOfReportingStations()),
                                    stat.getUtcDateTime()
                            };
                            tablePanel.defaultModel.addRow(data);
                        }
                        System.out.println(mapData);
                    }
                }
                
            } 
        });
        
        /** Exit button is pressed */
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            } 
        });
    }
}