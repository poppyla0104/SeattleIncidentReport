
/*
Author: Poppy La
Date: 8/14/2019
Assignment 4
Describe: An EventManager application provides user to search for 
	911 incidents record in 2015-2017 in Seattle.
*/

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class EventManager {

	public static Event[] events;
	public static Event aDate;
	public static Event aLocation;

	public static void main(String[] args) throws FileNotFoundException {
		try {
			File myFile = new File("Seattle_911_Incidents.csv");
			Scanner inputSc = new Scanner(myFile);
			Scanner console = new Scanner(System.in);

			// count numbers of lines
			// first line of the file won't be counted
			int lines = -1;
			while (inputSc.hasNextLine()) {
				inputSc.nextLine();
				lines++;
			}

			createArrays(inputSc, myFile, lines);
			totalEventsInYears(events, lines);
			pickOptions(console, lines);

			inputSc.close();
			console.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}

	}

	// create events array from input file
	public static void createArrays(Scanner inputSc, File myFile, int lines) throws FileNotFoundException {
		inputSc = new Scanner(myFile);
		events = new Event[lines];

		// skips the first line.
		inputSc.nextLine();

		int i = 0;
		while (inputSc.hasNextLine()) {
			Event anEvent = new Event();

			String oneLine = inputSc.nextLine();
			Scanner lineSc = new Scanner(oneLine);
			lineSc.useDelimiter(","); // separate tokens of each line by ","

			anEvent.ids = lineSc.next();
			anEvent.type = lineSc.next();
			String dateAndTime = lineSc.next(); // contains both day and time
			anEvent.date = extractEventDate(dateAndTime, "chooseDate");
			anEvent.time = extractEventDate(dateAndTime, "chooseTime");
			anEvent.location = lineSc.next();
			anEvent.sector = lineSc.next();
			anEvent.zone = lineSc.next();
			int month = pickDateElement(anEvent.date, "month");
			int day = pickDateElement(anEvent.date, "day");
			int year = pickDateElement(anEvent.date, "year");
			anEvent.day = day;
			anEvent.month = month;
			anEvent.year = year;

			events[i] = anEvent;
			i++;

			lineSc.close();
		}
		inputSc.close();
	}

	// split a String contains both date and time into 2 String: date-only and
	// time-only
	// return date or time as chosen
	public static String extractEventDate(String dateAndTime, String choice) {
		Scanner sc = new Scanner(dateAndTime);
		String dateOnly = sc.next();
		String timeOnly = sc.next();
		sc.close();

		if (choice.equals("chooseDate")) {
			return dateOnly;
		} else if (choice.equals("chooseTime")) {
			return timeOnly;
		} else {
			return "invalid input.";
		}
	}

	// extract a String of date, takes 3 integers represent month, day and year
	// separately
	// return month, day or year as chosen
	public static int pickDateElement(String inputDate, String choice) {
		Scanner dateSc = new Scanner(inputDate);

		dateSc.useDelimiter("/");
		int month = dateSc.nextInt();
		int day = dateSc.nextInt();
		int year = dateSc.nextInt();

		if (year > 100) { // takes 2 last digits only for year
			year %= 100;
		}
		dateSc.close();

		if (choice.equals("month")) {
			return month;
		} else if (choice.equals("day")) {
			return day;
		} else if (choice.equals("year")) {
			return year;
		}
		return 0;
	}

	// count and print total events in 2015, 2016 ,2017 and all 3 years combined
	public static void totalEventsInYears(Event[] events, int lines) {
		int count2015 = 0, count2016 = 0, count2017 = 0;

		for (int i = 0; i < lines; i++) {
			Scanner dateSc = new Scanner(events[i].date);
			dateSc.useDelimiter("/");
			dateSc.nextInt(); // ignore month and day
			dateSc.nextInt(); // only year is needed.
			int year = dateSc.nextInt();

			if (year == 15) {
				count2015++;
			} else if (year == 16) {
				count2016++;
			} else {
				count2017++;
			}
			dateSc.close();
		}
		System.out.println("Total event: " + lines);
		System.out.println("2015: " + count2015 + " events");
		System.out.println("2016: " + count2016 + " events");
		System.out.println("2017: " + count2017 + " events");
		System.out.println("Total event: " + lines);
	}

	// print options for user to choose
	// search events that matched the choice and input values
	public static void pickOptions(Scanner console, int lines) {
		boolean flags = false;
		aDate = new Event();
		aLocation = new Event();

		while (!flags) {
			try {
				System.out.println("+++++++++++++++++++++++++++++++++");
				System.out.println("Seattle 911 Event Search Manager:");
				System.out.println("1- Search by Date");
				System.out.println("2- Search by Type");
				System.out.println("3- Quit");
				System.out.print("Choose a search operation:");
				int number = console.nextInt();

				if (number == 1) {
					System.out.print("Enter date (dd/mm/yy):");
					String inputDate = console.next();
					aDate.month = pickDateElement(inputDate, "month");
					aDate.day = pickDateElement(inputDate, "day");
					aDate.year = pickDateElement(inputDate, "year");

					printEvents("date", aDate, lines);
				}
				if (number == 2) {
					System.out.print("Enter keyword for type:");
					aLocation.type = console.next();
					System.out.print("Enter sector:");
					aLocation.sector = console.next();

					printEvents("location", aLocation, lines);
				}
				if (number == 3) {
					System.out.println("Stay safe!");
					flags = true;
				}

			} catch (NoSuchElementException e) {
				System.out.println("Invalid input. Try again.");
				console.next();

			}
		}

	}

	// print all events that matched with the chosen options and inputs
	// count the number of matched events
	public static void printEvents(String option, Event input, int lines) {
		int matchedEventNumber = 0;
		boolean answer = false;

		for (int i = 0; i < lines; i++) {
			if (option.equals("date")) {
				answer = input.isEqualDate(events[i]);

			} else if (option.equals("location")) {
				answer = input.isEqualLocation(events[i]);
			}

			if (answer == true) {
				matchedEventNumber++;
				System.out.println("Event-" + (matchedEventNumber) + "---------------------");
				System.out.println(events[i]);
			}
		}
		System.out.println((matchedEventNumber) + " events");
	}
}
