import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Planner extends JTabbedPane{
	private Calendar date;
	private JComponent dayTab;
	private JComponent weekTab;
	private JComponent monthTab;
	private JComponent yearTab;


	private JLabel monthLabel;

	private JTable daySchedule;
	private JTable weekSchedule;
	private JPanel monthSchedule;

	private JTextField startDateTextField;
	private JTextField endDateTextField;
	private JTextArea resultTextField;

	private JButton confirmButton;

	public Planner() {
		// create day tab
		dayTab = new JPanel();

		date = Calendar.getInstance();
		String dayEvents[][] = new String[24][2];
		for(int i = 0; i < 24; i++) {
			dayEvents[i][0] = i + "";
		}
		// string for table
		String dayHeader[] = {"Time", new SimpleDateFormat("dd.MMM.yyyy").format(date.getTime())};
		
		//create day table
		daySchedule = new JTable(dayEvents, dayHeader);
		daySchedule.setDefaultEditor(Object.class, null);
		daySchedule.getColumnModel().getColumn(0).setMaxWidth(40);
		JScrollPane sp=new JScrollPane(daySchedule);
		
		// add to current panels
		this.add("Day", sp);

		// create week tab
		weekTab = new JPanel();
		String weekEvents[][] = new String[24][8];
		for(int i = 0; i < 24; i++) {
			weekEvents[i][0] = i + "";
		}
		// for weekSchedule table
		String weekHeader[] = {"Time", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
		
		//create table for week tab
		weekSchedule = new JTable(weekEvents, weekHeader);
		weekSchedule.setDefaultEditor(Object.class, null);
		weekSchedule.getColumnModel().getColumn(0).setMaxWidth(40);
		JScrollPane sp1 = new JScrollPane(weekSchedule);
		
		// add it to current panel
		this.add("Week", sp1);

		// create table for mothTab
		monthTab = new JPanel();
		monthLabel = new JLabel( new SimpleDateFormat("MMM.yyyy").format(date.getTime()));
		monthTab.add(monthLabel, BorderLayout.NORTH);
		monthSchedule = new JPanel();
		monthSchedule.setLayout(new GridLayout(0, 7, 10, 1));

		// populate table for month tab
		initializeContainer(date, null);

		// add month tab to current schedule
		monthTab.add(monthSchedule);
		this.add("Month", monthTab);

		// create panel for yearTab
		yearTab = new JPanel();
		yearTab.setLayout(new GridLayout(3, 4));
		JPanel months[][] = new JPanel[3][4];
		String monthNames[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		
		// populate year tab
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 4; j++) {
				months[i][j] = new JPanel();
				JLabel monthNamesLabel = new JLabel(monthNames[i * 4 + j]);
				JTable currentMonth = initializeCalendar(i * 4 + j, Calendar.getInstance().get(Calendar.YEAR));
				currentMonth.setDefaultEditor(Object.class, null);
				currentMonth.getTableHeader().setReorderingAllowed(false);
				JScrollPane monthPane=new JScrollPane(currentMonth);
				monthPane.setPreferredSize(new Dimension(150,120));

				months[i][j].add(monthNamesLabel, BorderLayout.NORTH);
				months[i][j].add(monthPane);
				yearTab.add(months[i][j], i * 4, i * 4 + j);
			}
		}

		// add year tab to current panel
		this.add("Year", yearTab);

		//create agenda tab
		JPanel agendaTab = new JPanel();
		agendaTab.setLayout(new BorderLayout());
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		
		// text field for agenda tab
		JLabel promptTextLabel = new JLabel("Input start date");
		startDateTextField = new JTextField();
		startDateTextField.setText("dd/mm/yyyy");
		startDateTextField.setPreferredSize(new Dimension(150, 50));
		
		// add listener to delete prompted date format
		startDateTextField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				startDateTextField.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {

			}

		});
		north.add(promptTextLabel);
		north.add(startDateTextField);

		// other text field in agenda tab
		JLabel promptEndTextLabel = new JLabel("Input start date");
		endDateTextField = new JTextField();
		endDateTextField.setText("dd/mm/yyyy");
		endDateTextField.setPreferredSize(new Dimension(150, 50));
		endDateTextField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				endDateTextField.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
			}

		});
		north.add(promptEndTextLabel);
		north.add(endDateTextField);

		// confirm button in agenda tab
		confirmButton = new JButton("Confirm");
		north.add(confirmButton);

		// result text filed in agenda tab
		// all results go here
		resultTextField = new JTextArea();
		resultTextField.setEditable(false);
		JScrollPane jpr = new JScrollPane(resultTextField);
		agendaTab.add(jpr);

		agendaTab.add(north, BorderLayout.NORTH);

		this.add("Agenda", agendaTab);
	}

	public JTable getDaySchedule() {
		return daySchedule;
	}

	public void setDaySchedule(JTable daySchedule) {
		this.daySchedule = daySchedule;
	}

	public JTable getWeekSchedule() {
		return weekSchedule;
	}

	public void setWeekSchedule(JTable weekSchedule) {
		this.weekSchedule = weekSchedule;
	}

	public JLabel getMonthLabel() {
		return monthLabel;
	}

	public void setMonthLabel(JLabel monthLabel) {
		this.monthLabel = monthLabel;
	}

	public JPanel getMonthSchedule() {
		return monthSchedule;
	}

	public void setMonthSchedule(JPanel monthSchedule) {
		this.monthSchedule = monthSchedule;
	}

	public JTextField getStartDateTextField() {
		return startDateTextField;
	}

	public void setStartDateTextField(JTextField startDateTextField) {
		this.startDateTextField = startDateTextField;
	}

	public JTextField getEndDateTextField() {
		return endDateTextField;
	}

	public void setEndDateTextField(JTextField endDateTextField) {
		this.endDateTextField = endDateTextField;
	}

	public JButton getConfirmButton() {
		return confirmButton;
	}

	public void setConfirmButton(JButton confirmButton) {
		this.confirmButton = confirmButton;
	}

	public JTextArea getResultTextField() {
		return resultTextField;
	}

	public void setResultTextField(JTextArea resultTextField) {
		this.resultTextField = resultTextField;
	}

	// to initialize calendar
	JTable initializeCalendar(int month, int year) {
		Calendar date = Calendar.getInstance();

		date.set(Calendar.MONTH, month);
		date.set(Calendar.YEAR, year);

		String dates[][] = new String[6][7];
		String days[] = {"S","M","T","W","T","F","S"};
		JTable calendarTable = new JTable(dates, days);

		int firstDayOfWeek = date.getFirstDayOfWeek();
		date.set(Calendar.DATE, date.getMinimum(Calendar.DATE));

		// Now display the dates, one week per line

		StringBuilder week = new StringBuilder();			
		int i = 0;

		while (month==date.get(Calendar.MONTH)) {
			int j = 0;

			// Display date

			week.append(String.format("%d ", date.get(Calendar.DATE)));

			// Increment date

			date.add(Calendar.DATE, 1);

			// Check if week needs to be printed

			if (date.get(Calendar.MONTH)!=month) {

				// end of month
				// just need to output the month

				String weekDays[] = week.toString().split(" ");
				for(j = 0;j < weekDays.length; j++) {
					dates[i][j] = weekDays[j];
				}

				i++;

				week.setLength(0);

			} else if (date.get(Calendar.DAY_OF_WEEK)==firstDayOfWeek) {

				// new week so print out the current week
				// first check if any padding needed

				int padding = 14 - week.length();

				String weekDays[] = new String[7];

				while(padding > 0) {
					weekDays[j++] = " ";
					padding -= 2;
				}
				String temp[] = week.toString().split(" ");
				int k = 0;
				while(k < temp.length) {
					weekDays[j++] = temp[k++];
				}

				for(j = 0;j < weekDays.length; j++) {
					dates[i][j] = weekDays[j];
				}

				i++;

				week.setLength(0);
			}
		}


		for(i = 0; i < dates.length; i++) {
			for(int j = 0; j < dates[i].length; j++) {
				if(dates[i][j] != null) {
					calendarTable.getModel().setValueAt(dates[i][j], i, j);
				}else {
					calendarTable.getModel().setValueAt(" ", i, j);
				}
			}
		}

		return calendarTable;
	}

	//initialize month tab
	public void initializeContainer(Calendar date, ArrayList<Event> events) {
		monthSchedule.removeAll();
		int firstDayOfWeek = date.getFirstDayOfWeek();
		int month = date.get(Calendar.MONTH);
		date.set(Calendar.DATE, date.getMinimum(Calendar.DATE));
		monthLabel.setText(new SimpleDateFormat("MMM.yyyy").format(date.getTime()));

		ArrayList<Event> reduced = new ArrayList<>();
		if(events != null) {
			for(Event e : events) {
				if(e.getYear() == date.get(Calendar.YEAR) && (e.getMonth() - 1) == date.get(Calendar.MONTH)) {
					reduced.add(e);
				}
			}
		}

		StringBuilder week = new StringBuilder();

		ArrayList<JTextArea> textAreas = new ArrayList<>();

		while (month==date.get(Calendar.MONTH)) {

			int j = 0;
			week.append(String.format("%d ", date.get(Calendar.DATE)));  
			JTextArea textArea = new JTextArea(4, 9);
			textArea.setEditable(false);
			textArea.setText("");
			for(Event e : reduced) {
				if(e.getDay() == date.get(Calendar.DAY_OF_MONTH)) {
					textArea.append(e.getTime() + "h - " + e.getName() + "\n");
				}
			}
			date.add(Calendar.DATE, 1);


			if (date.get(Calendar.MONTH)!=month) {

				textAreas.add(textArea);
				String weekDays[] = week.toString().split(" ");
				for(j = 0;j < weekDays.length; j++) {
					JPanel monthDay = new JPanel();
					monthDay.setLayout(new BorderLayout());
					JLabel day = new JLabel(weekDays[j]);
					monthDay.add(day, BorderLayout.NORTH);
					monthDay.add(textAreas.get(j));

					monthSchedule.add(monthDay);
				}
				textAreas = new ArrayList<JTextArea>();
				continue;
			} else if(date.get(Calendar.DAY_OF_WEEK) == firstDayOfWeek){
				textAreas.add(textArea);
				int padding = 14 - week.length();

				String weekDays[] = new String[7];

				while(padding > 0) {
					JTextArea ta = new JTextArea(4, 9);
					ta.setEditable(false);
					ta.setText("");
					textAreas.add(0, ta);
					weekDays[j++] = " ";
					padding -= 2;
				}
				String temp[] = week.toString().split(" ");
				int k = 0;
				while(k < temp.length) {
					weekDays[j++] = temp[k++];
				}

				for(j = 0;j < weekDays.length; j++) {
					JPanel monthDay = new JPanel();
					monthDay.setLayout(new BorderLayout());
					JLabel day = new JLabel(weekDays[j]);
					monthDay.add(day, BorderLayout.NORTH);
					monthDay.add(textAreas.get(j));

					monthSchedule.add(monthDay);
				}

				textAreas = new ArrayList<JTextArea>();
				week.setLength(0);
				continue;
			}
			textAreas.add(textArea);
		}
		this.date.add(Calendar.MONTH, -1);
		monthSchedule.updateUI();

	}

	// if year change updates year tab
	public void updateYear(int year) {
		yearTab.removeAll();
		JPanel months[][] = new JPanel[3][4];
		String monthNames[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 4; j++) {
				months[i][j] = new JPanel();
				JLabel monthNamesLabel = new JLabel(monthNames[i * 4 + j]);
				JTable currentMonth = initializeCalendar(i * 4 + j, year);
				currentMonth.setDefaultEditor(Object.class, null);
				currentMonth.getTableHeader().setReorderingAllowed(false);
				JScrollPane monthPane=new JScrollPane(currentMonth);
				monthPane.setPreferredSize(new Dimension(150,120));

				months[i][j].add(monthNamesLabel, BorderLayout.NORTH);
				months[i][j].add(monthPane); 
				yearTab.add(months[i][j], i * 4, i * 4 + j);
			}
		}
		yearTab.repaint();
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

}
