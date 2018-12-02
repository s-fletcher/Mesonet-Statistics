package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * @author Sam Fletcher
 * @version 11-28-18
 * Project 4
 * 
 * A Panel that shows a table of the
 * results.
 */
public class TablePanel extends JPanel
{
    /**
     * Serial ID
     */
    private static final long serialVersionUID = 1L;
    /** Table reference */
    public static JTable table;
    /** Table Model reference */
    public TableModel tableModel;
    /** Table Column Model reference */
    public TableColumnModel columnModel;
    /** Default Table Model reference */
    public DefaultTableModel defaultModel;
    
    public TablePanel()
    {
        super(new GridLayout(1,0));
        
        // Set up data
        String[] header = {"Station","Parameter","Statistics","Value","Reporting Stations","Date"};
        String[] testData = {"HOOK","TAIR","MAXIMUM","968.0","118","2018-08-20T17:45:00 UTC"};
        
        // Set up table
        table = new JTable(0,6);
        columnModel = table.getColumnModel();
        tableModel = table.getModel();
        defaultModel = (DefaultTableModel) table.getModel();
        table.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Creating scroll panel container
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(670,80));
        add(scrollPane);
        
        // Adding headers to the table
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        for(int i = 0; i < columnModel.getColumnCount(); i++)
        {
            columnModel.getColumn(i).setHeaderValue(header[i]);
            columnModel.getColumn(i).setHeaderRenderer(leftRenderer);
        }
        
        // Changing column widths
//        defaultModel.addRow(testData);
//        defaultModel.addRow(testData);
        table.setGridColor(Color.WHITE);
        columnModel.getColumn(0).setPreferredWidth((int) (scrollPane.getPreferredSize().getWidth() * .08));
        columnModel.getColumn(1).setPreferredWidth((int) (scrollPane.getPreferredSize().getWidth() * .10));
        columnModel.getColumn(2).setPreferredWidth((int) (scrollPane.getPreferredSize().getWidth() * .11));
        columnModel.getColumn(3).setPreferredWidth((int) (scrollPane.getPreferredSize().getWidth() * .07));
        columnModel.getColumn(4).setPreferredWidth((int) (scrollPane.getPreferredSize().getWidth() * .18));
        columnModel.getColumn(5).setPreferredWidth((int) (scrollPane.getPreferredSize().getWidth() * .46));
    }
}
