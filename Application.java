import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Application extends JFrame{

	JButton fromFileButton;

	ArrayList<Event> events;
	private EventEditor eventEditor = new EventEditor();

	public Application() {
		// set name for JFrame
		super("Planner");
		
		// hold events
		events = new ArrayList<Event>();
		
		//left part of JFrame
		CalendarView left = new CalendarView();

		Planner planner = new Planner();

		// calendar on left side
		JTable calendarTable = left.getCalendarTable();
		
		// add listener
		calendarTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				// take selected date
				int row = calendarTable.getSelectedRow();
				int col = calendarTable.getSelectedColumn();

				try {
					int dayNum = Integer.parseInt((String) calendarTable.getValueAt(row, col));
					Calendar date = left.getDate();

					int oldMonth = planner.getDate().get(Calendar.MONTH);
					int oldYear = planner.getDate().get(Calendar.YEAR);
					int oldWeek = planner.getDate().get(Calendar.WEEK_OF_YEAR);
					date.set(Calendar.DAY_OF_MONTH, dayNum);
					int newMonth = date.get(Calendar.MONTH);
					int newYear = date.get(Calendar.YEAR);
					int newWeek = date.get(Calendar.WEEK_OF_YEAR);

					// update day tab
					JTable daySchedule = planner.getDaySchedule();

					JTableHeader header= daySchedule.getTableHeader();
					TableColumnModel colMod = header.getColumnModel();
					TableColumn tabCol = colMod.getColumn(1);
					tabCol.setHeaderValue(new SimpleDateFormat("dd. MMM yyyy").format(date.getTime()));
					header.repaint();

					date.set(Calendar.DAY_OF_MONTH, dayNum);

					// update week tab
					if(oldWeek != newWeek) {
						JTable weekSchedule = planner.getWeekSchedule();
					}

					// update month tab
					if(oldMonth != newMonth) {
						JPanel monthSchedule = planner.getMonthSchedule();
						planner.initializeContainer((Calendar)date.clone(), events);
						monthSchedule.repaint();
					}

					// update year tab
					if(oldYear != newYear) {
						planner.updateYear(newYear);
					}
					planner.getDate().set(newYear, newMonth, date.get(Calendar.DAY_OF_MONTH));
				}catch(NumberFormatException ex) {
					// show error window 
					JOptionPane.showMessageDialog(null, "Selected date is empty. Please select another one.");
				}
			}
		});

		JButton todayButton = left.getTodayButton();
		todayButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// set date for CalendarView
				left.setDate(Calendar.getInstance());
				// update CalendarView label
				left.updateLabel();
				
				// update calendar
				left.updateCalendar();
				
				//update month tab
				planner.initializeContainer(Calendar.getInstance(), events);
				
				// update year tab
				planner.updateYear(Calendar.getInstance().get(Calendar.YEAR));
				
				//update week tab
				
			}

		});

		// add listener to create button
		JButton createButton = left.getCreateButton();
		createButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// get result from event editor
				int result = JOptionPane.showConfirmDialog(null, eventEditor,
						"Create Event", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);


				// if it is confirmed 
				if (result == JOptionPane.OK_OPTION) {
					// create new event
					String name = eventEditor.getFieldText(EventEditor.FieldTitle.NAME);
					int year = 0;
					try {
						year = Integer.parseInt(eventEditor.getFieldText(EventEditor.FieldTitle.YEAR));
						if(year < 1000 || year > 10_000) {
							JOptionPane.showMessageDialog(null, "Invalid year.");
							return;
						}
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid year.");
						return;
					}
					int month = 0;
					try {
						month = Integer.parseInt(eventEditor.getFieldText(EventEditor.FieldTitle.MONTH));
						if(month < 1 || month > 12) {
							JOptionPane.showMessageDialog(null, "Invalid month.");
							return;
						}
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid month.");
						return;
					}

					int day = 0;
					try {
						day = Integer.parseInt(eventEditor.getFieldText(EventEditor.FieldTitle.DAY));
						if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
							if(day < 1 || day > 31) {
								JOptionPane.showMessageDialog(null, "Invalid starting time.");
								return;
							}
						}else if(month == 4 || month == 6 || month == 9 || month == 11) {
							if(day < 1 || day > 30) {
								JOptionPane.showMessageDialog(null, "Invalid starting time.");
								return;
							}
						}else {
							if(day < 1 || day > 29) {
								JOptionPane.showMessageDialog(null, "Invalid starting time.");
								return;
							}
						}
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid date.");
						return;
					}

					int time = 0;
					try {
						time = Integer.parseInt(eventEditor.getFieldText(EventEditor.FieldTitle.TIME));
						if(time < 0 || time > 23) {
							JOptionPane.showMessageDialog(null, "Invalid time.");
							return;
						}
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid time.");
						return;
					}

					Event event = new Event(name, year, month, day, time);
					events.add(event);
				}
			}

		});

		this.add(left, BorderLayout.WEST);
		this.add(planner);

		setSize(1000, 600);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel west = new JPanel();
		// create and add listener for from file button
		fromFileButton = new JButton("From file");

			

	}

	

	

	
		
	

	public static void main(String args[]) {
		new Application();
	}
}
