import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CalendarView extends JPanel{
	private Calendar date;

	private JButton todayButton;
	private JButton leftButton;
	private JButton rightButton;

	private JLabel dateAndYearLabel;
	private JButton createButton;

	private JTable calendarTable;

	public CalendarView() {
		date = Calendar.getInstance();

		todayButton = new JButton("Today");


		leftButton = new JButton("<");
		leftButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				decrementMonth();
				updateLabel();
				updateCalendar();
			}

		});

		rightButton = new JButton(">");

		rightButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				incrementMonth();
				updateLabel();
				updateCalendar();
			}

		});

		JPanel todayLeftRight = new JPanel();
		todayLeftRight.add(todayButton);
		todayLeftRight.add(leftButton);
		todayLeftRight.add(rightButton);

		createButton = new JButton("Create");
		createButton.setBackground(Color.RED);

		dateAndYearLabel = new JLabel();
		dateAndYearLabel.setText(new SimpleDateFormat("MMM-YYYY").format(date.getTime()));

		String dates[][] = new String[6][7];
		String days[] = {"S","M","T","W","T","F","S"};
		calendarTable = new JTable(dates, days);
		calendarTable.setDefaultEditor(Object.class, null);
		calendarTable.getTableHeader().setReorderingAllowed(false);

		JScrollPane sp=new JScrollPane(calendarTable);
		sp.setPreferredSize(new Dimension(120,120));

		JPanel createAndTable = new JPanel();
		createAndTable.setLayout(new BoxLayout(createAndTable, BoxLayout.Y_AXIS));

		createAndTable.add(todayLeftRight);
		createAndTable.add(createButton);
		createAndTable.add(dateAndYearLabel);
		createAndTable.add(sp);

		this.add(createAndTable, BorderLayout.SOUTH);
		updateCalendar();
	}

	public JButton getTodayButton() {
		return todayButton;
	}

	public void setTodayButton(JButton todayButton) {
		this.todayButton = todayButton;
	}

	public JButton getLeftButton() {
		return leftButton;
	}

	public void setLeftButton(JButton leftButton) {
		this.leftButton = leftButton;
	}

	public JButton getRightButton() {
		return rightButton;
	}

	public void setRightButton(JButton rightButton) {
		this.rightButton = rightButton;
	}

	public JLabel getDateAndYearLabel() {
		return dateAndYearLabel;
	}

	public void setDateAndYearLabel(JLabel dateAndYearLabel) {
		this.dateAndYearLabel = dateAndYearLabel;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public JTable getCalendarTable() {
		return calendarTable;
	}

	public void setCalendarTable(JTable calendarTable) {
		this.calendarTable = calendarTable;
	}

	public JButton getCreateButton() {
		return createButton;
	}

	public void setCreateButton(JButton createButton) {
		this.createButton = createButton;
	}

	public void updateCalendar() {
		int month = date.get(Calendar.MONTH);
		int firstDayOfWeek = date.getFirstDayOfWeek();
		date.set(Calendar.DATE, date.getMinimum(Calendar.DATE));

		// Now display the dates, one week per line

		StringBuilder week = new StringBuilder();

		String days[] = {"S","M","T","W","T","F","S"};
		String dates[][] = new String[6][7];

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
		decrementMonth();
	}

	public void updateLabel() {
		dateAndYearLabel.setText(new SimpleDateFormat("MMM-YYYY").format(date.getTime()));
	}

	public void incrementDay() {
		date.add(Calendar.DATE, 1);
	}

	public void incrementMonth() {
		date.add(Calendar.MONTH, 1);
	}

	public void decrementMonth() {
		date.add(Calendar.MONTH, -1);
	}

	public void incrementYear() {
		date.add(Calendar.YEAR, 1);
	}

}
