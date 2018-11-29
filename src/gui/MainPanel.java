package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
    public final Color background = new Color(138,141,130);
    /** Header label */
    public JLabel text = new JLabel("Mesonet - We don't set records, we report them!");
    /** Calculate button */
    public JButton calculate = new JButton("Calculate");
    /** Exit button */
    public JButton exit = new JButton("Exit");

    
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
        buttons.setBackground(background);
        JButton exit = new JButton("Exit");
        buttons.add(calculate);
        buttons.add(exit);
        
        // Adding components
        add(text, BorderLayout.PAGE_START);
        add(buttons, BorderLayout.PAGE_END);
        
        calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Calculate has been pressed");
            } 
        });
        
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            } 
        });
    }
}