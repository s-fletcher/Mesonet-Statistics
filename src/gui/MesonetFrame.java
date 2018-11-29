package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import model.Driver;

/**
 * @author Sam Fletcher
 * @version 11-28-18
 * Project 4
 * 
 * Main class that draws the frame and 
 * brings all the panels together.
 */
public class MesonetFrame extends JFrame
{
    /** Holds the frame object */
    public static MesonetFrame frame;
    
    /**
     * The menu bar of the frame. Responsible for the
     * Open Data File and Exit buttons.
     */
    class FileMenuBar extends JMenuBar implements ActionListener
    {

        /**
         * Serial ID
         */
        private static final long serialVersionUID = 1L;
        
        /** Menu buttons for opening a file and exiting the program */
        JMenuItem openButton, exitButton;
        
        /**
         * Constructs the menu bar
         */
        public FileMenuBar()
        {
            super();
            
            // Create file menu and its buttons
            JMenu fileMenu = new JMenu("File");
            openButton = new JMenuItem("Open Data File");
            exitButton = new JMenuItem("Exit");
            openButton.addActionListener(this);
            exitButton.addActionListener(this);
            fileMenu.add(openButton);
            fileMenu.add(exitButton);
            add(fileMenu);
        }
        
        /**
         * Handles the actions taken after a menu is pressed
         */
        public void actionPerformed(ActionEvent e) 
        {
            // "Exit" is clicked
            if(e.getSource() == exitButton)
            {
                System.exit(0);
            }
            // "Open Data" File is clicked
            if(e.getSource() == openButton)
            {
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(fileChooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) 
                {
                    File file = fileChooser.getSelectedFile();
                    try
                    {
                        Driver driver = new Driver(file.getName(), file.getParent());
                        driver.constructAndParse();
                    }
                    catch (Exception e1)
                    {
                        // Present error message
                        JOptionPane.showMessageDialog(frame,
                                "\'" + e1.getMessage() + "\'\nCheck that you selected the correct file.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
    
    /**
     * Serial ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for adding all panels to the frame
     * @param title of the frame
     */
    public MesonetFrame(String title)
    {
        super(title);
        
        // Add main panel
        setLayout(new BorderLayout());
        MainPanel main = new MainPanel();
        add(main);
        
        // RATIO: 16:29
        int width = 850;
        int height = 470;
        
        // Sets up menu bar
        setJMenuBar(new FileMenuBar());
        
        // Sets size and other stuff and makes frame visible
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args)
    {
        frame = new MesonetFrame("Oklahoma Mesonet - Statistics Calculator");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
