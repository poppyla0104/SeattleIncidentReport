/*
Author: Poppy La
Date: 8/14/2019
Assignment 4
Describe: An Event class of EventManager application, which provides user to search for 
	911 incidents record in 2015-2017 in Seattle.
*/

public class Event {
	public String ids;
	public String type;
	public String date;
	public String time;
	public String location;
	public String sector;
	public String zone;

	public int day;
	public int month;
	public int year;

	public Event() {

	}

	// compares dates of 2 objects, returns true if months, days, years are matched
	public boolean isEqualDate(Event other) {
		if ((this.month == other.month) && (this.day == other.day) && (this.year == other.year))
			return true;
		return false;
	}

	// compare locations of 2 objects
	// return true if "other" object contains any "this" object's type and sector.
	public boolean isEqualLocation(Event other) {
		if ((other.type.contains(this.type.toUpperCase())) && (other.sector.contains(this.sector.toUpperCase())))
			return true;
		return false;
	}

	@Override
	public String toString() {
		String str = "Type:" + type + "\n";
		str += "Date:" + date + "\n";
		str += "Time:" + time + "\n";
		str += "Address:" + location + "\n";
		str += "Sector:" + sector + "\n";
		str += "Zone:" + zone;
		return str;
	}

}
