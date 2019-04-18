import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class CalendarDisplay
{
	private static JPanel calendarPanel;
	private static TimeZone curTimeZone;
	private static Calendar calendar;
	private static Font font;
	
	private static int Year;
	private static int Day;
	private static int Month;
	
	private static Color hightlightColor = Color.BLACK;
	
	private static String[] Days = {
			"Sunday","Monday", "Tuesday", "Wednseday","Thursday","Friday","Saturday"
	};
	
	private static String[] Months = {
			"January", "February", "March",
			"April", "May", "June",
			"July", "August", "September", 
			"October", "November", "December"
	};
	
	private static int[] daysInMonth = {
			31,28,31,30,31,30,31,31,30,31,30,31
	};
	
	/**The colors for each month are based on the BirthStones of each month*/
	private static Color[] colorsOfTheMonths = {
			Color.decode("#781c2e"), Color.decode("#9966cc"), Color.decode("#7fffd4"),
			Color.WHITE, Color.decode("#50c878"), Color.decode("#eae0c8"),
			Color.decode("#9b111e"), Color.decode("#e6e200"), Color.decode("#0f52ba"),
			Color.decode("#6a6971"), Color.decode("#ffc878c"), Color.decode("#40e0d0")
	};
	
	public static HashMap<String, JButton> MonthData = new HashMap<String, JButton>();
	
	
	public JPanel getPanel()
	{
		return calendarPanel;
	}
	public static void update(int monthProvided, int yearProvided)
	{
		MonthData = new HashMap<String, JButton>();
		Month = monthProvided;
		Year = yearProvided;
		Day = 32; //set day past possible days
		System.out.println(Day+"/"+Month+"/"+Year);

		
		checkForLeapYear(yearProvided);
		try {
			setupJPanel(monthProvided);
		} catch (Exception e) {
			e.printStackTrace();
		}
		calendarPanel.repaint();
	}
	private static Color getColorCompliment(Color contrastingColor)
	{
		return new Color(255 - contrastingColor.getRed(), 255 - contrastingColor.getGreen(), 255 - contrastingColor.getBlue());
	}
	private static void checkForLeapYear(int yearProvided)
	{
		//Check for Leap Year
		if(((yearProvided % 4 == 0) && (yearProvided % 100 != 0)) || (yearProvided % 400 == 0))	
			daysInMonth[1] = 29;
		else
			daysInMonth[1] = 28;
	}
	private static void addComponent(Component comp, int x, int y, int numX, int numY)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = numX;
		c.gridheight = numY;
		calendarPanel.add(comp, c);
	}
	private static void addNavigationButtons()
	{
		int prevMonth = Month == 0 ? 11 : Month - 1;
		int nextMonth = Month == 11 ? 0 : Month + 1;
		Color colorOfPrevMonth = colorsOfTheMonths[prevMonth];
		Color colorOfNextMonth = colorsOfTheMonths[nextMonth];
		Color foregroundColorOfPrevMonth = getColorCompliment(colorOfPrevMonth);
		Color foregroundColorOfNextMonth = getColorCompliment(colorOfNextMonth);
		
		JButton previousMonthButton = new JButton("<");
		previousMonthButton.addActionListener(e -> update(Month == 0 ? 11 : Month - 1, Month == 0 ? Year - 1 : Year));
		previousMonthButton.setEnabled(true);
		previousMonthButton.setFont(font);
		previousMonthButton.setBackground(colorOfPrevMonth);
		previousMonthButton.setForeground(foregroundColorOfPrevMonth);
		previousMonthButton.setBorder(new LineBorder(hightlightColor));
		previousMonthButton.setBorderPainted(false);
		addComponent(previousMonthButton, 0, 0, 1, 1);
		
		JButton nextMonthButton = new JButton(">");
		nextMonthButton.addActionListener(e -> update(Month == 11 ? 0 : Month + 1, Month == 11 ? Year + 1 : Year));
		nextMonthButton.setEnabled(true);
		nextMonthButton.setFont(font);
		nextMonthButton.setBackground(colorOfNextMonth);
		nextMonthButton.setForeground(foregroundColorOfNextMonth);
		nextMonthButton.setBorder(new LineBorder(hightlightColor));
		nextMonthButton.setBorderPainted(false);
		addComponent(nextMonthButton, 6, 0, 1, 1);
	}
	private static JButton addButton(String label, int x, int y, int numX, int numY, Color backgroundColor, boolean highlighted, boolean enabled)
	{
		Color foregroundColor = getColorCompliment(backgroundColor);
		JButton Button = new JButton(label);
		Button.setEnabled(enabled);
		Button.setFont(font);
		Button.setBackground(backgroundColor);
		Button.setForeground(foregroundColor);
		Button.setBorder(new LineBorder(hightlightColor));
		Button.setBorderPainted(highlighted);
		addComponent(Button, x, y, numX, numY);
		return Button;
	}
	private static Date getFirstDateOfCurMonth(int monthProvided)
	{
		Calendar calInstance = Calendar.getInstance(curTimeZone);
		calInstance.set(Month, monthProvided);
		calInstance.set(Calendar.DAY_OF_MONTH, calInstance.getActualMinimum(1));
		return calInstance.getTime();
	}
	private static void setupJPanel(int monthProvided) throws Exception
	{
		if(monthProvided < 0 || monthProvided > 11)
			throw new Exception("ERROR!!! Cannot pass an invalid Month!! MonthPassed: "+monthProvided);
		
		calendarPanel = new JPanel();
		calendarPanel.setLayout(new GridBagLayout());
		
		Color colorOfTheMonth = colorsOfTheMonths[monthProvided];
		addButton(Months[monthProvided]+" "+Year, 1, 0, 5, 3, colorOfTheMonth, false, false);
		addNavigationButtons();
		
		String dayName;
		int col, row = 3;
		for(col = 0; col < 7; col++)
		{
			dayName = Days[col];
			addButton(dayName, col, row, 1, 1, colorOfTheMonth, false, true);
		}
		
		Date firstDayOfMonth = getFirstDateOfCurMonth(monthProvided);
		//System.out.println("FirstDayOfMonth:"+firstDayOfMonth.getDay());
		int dayIndex = 0;
		boolean highlighted;
		col = firstDayOfMonth.getDay();
		row = 4;
		
		if(col != 0)
		{
			int lastMonth = monthProvided == 0 ? 11 : monthProvided - 1;
			int lastMonthsDays = daysInMonth[lastMonth];
			Color lastMonthsColor = colorsOfTheMonths[lastMonth];
			for(int numToAdd = col-1; numToAdd >= 0; numToAdd--)
				addButton(Integer.toString(lastMonthsDays--), numToAdd, row, 1, 1, lastMonthsColor, false, true);
		}

		while(dayIndex < daysInMonth[monthProvided])	//create Calendar Labels and Buttons
		{
			if(Day <= 31)//is day of month available
				highlighted = dayIndex == Day ? true : false;
			else
				highlighted = false;
			dayName = Integer.toString(dayIndex+1);
			JButton dayButton = addButton(dayName, col, row, 1, 1, colorOfTheMonth, highlighted, true);

			if(MonthData.containsKey(dayName))
			{
				throw new Exception("ERROR!! Exception setting up calendar buttons!! "
						+dayName+" Already present in MonthData!!!");
			}
			MonthData.put(dayName, dayButton);
			
			if(col == 6)
			{
				col = 0;
				row++;
			}
			else
				col++;
			dayIndex++;
		}
		
		if(col <= 6)
		{
			int nextMonth = monthProvided == 11 ? 1 : monthProvided + 1;
			Color nextMonthsColor = colorsOfTheMonths[nextMonth];
			int nextMonthsDays = 1;
			for(int numToAdd = col; numToAdd <= 6; numToAdd++)
				addButton(Integer.toString(nextMonthsDays++), numToAdd, row, 1, 1, nextMonthsColor, false, true);
		}
	}
	private static void initCalendar()
	{
		//SetGlobals
		font = new Font("Serif", Font.BOLD, 40);
		curTimeZone = Calendar.getInstance().getTimeZone();
		calendar = Calendar.getInstance(curTimeZone);
		Year = calendar.get(Calendar.YEAR);
		Month = calendar.get(Calendar.MONTH);
		Day = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println(Day+"/"+Month+"/"+Year);

		//Check for Leap Year
		checkForLeapYear(Year);
	}
	public CalendarDisplay()
	{
		initCalendar();		//Setup Class Variables
		try
		{
			setupJPanel(Month);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}
}