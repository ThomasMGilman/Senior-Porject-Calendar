
//Imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.*;
import javax.swing.*;


/**
 *
 * @author Thoma
 */
public class main {

    //GLOBALS
    public static Dimension screenSize;
    private static JFrame mainFrame;
    private static JPanel mainPanel;
    private static JPanel calendarPanel;
    private static CalendarDisplay displayCal;
    
    public static void closeOperation()
    {
        System.exit(0);
    }

    public static void initMenus()
    {
        JButton closeButton = new JButton("Exit");
        closeButton.addActionListener(e -> closeOperation());

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(closeButton);
        
        JMenu optionMenu = new JMenu("Option");
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        menuBar.setBackground(Color.lightGray);
        menuBar.add(fileMenu);
        menuBar.add(optionMenu);

        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0,1));
        mainPanel.add(menuBar);
        
        displayCal = new CalendarDisplay();
        calendarPanel = displayCal.getPanel();
        
    }
    
    public static void initMainFrame()
    {
    	screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	initMenus();
    	
        mainFrame = new JFrame("Test");
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(screenSize.width, screenSize.height);
        
        mainFrame.add(calendarPanel);
    }
    
    public static void main(String[] args) {
        initMainFrame();
    }
    
}