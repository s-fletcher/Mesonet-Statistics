package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

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
            if(e.getSource() == exitButton)
            {
                System.exit(0);
            }
            if(e.getSource() == openButton)
            {
                System.out.println("Open");
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(fileChooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                     File file = fileChooser.getSelectedFile();
                     System.out.println(file.getName());
                        try
                        {
                            Driver driver = new Driver(file.getName(), file.getParent());
                            driver.constructAndParse();
                        }
                        catch (IOException e1)
                        {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
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
        
        // RATIO: 6.8:13
        int width = 1000;
        int height = 523;
        
        setJMenuBar(new FileMenuBar());
        
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args)
    {
        MesonetFrame frame = new MesonetFrame("Oklahoma Mesonet - Statistics Calculator");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
