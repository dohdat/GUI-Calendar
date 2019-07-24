
public class Event {
	private String name;
	private int year;
	private int month;
	private int day;
	private int time;
	
	public Event(String name, int year, int month, int day, int time) {
		super();
		this.name = name;
		this.year = year;
		this.month = month;
		this.day = day;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public String toString() {
		return name + " - " + day + ". " + month + ". " + year + ". " + time + "h";
	}
	
	
}
