import java.util.*;


/**
 * @author Jeremy Esch
 * @version 1.0
 * 3/15/2021
 *
 * Class that defines the seats in a flight that can hold passengers
 * Can add and remove passengers from flight
 */
public class Flight {

	private String name;
	private Passenger[][] firstClassList;
	private Passenger[][] economyList;
	private int fcSeats;
	private int ecoSeats;

	private HashMap<String, ArrayList<Passenger>> groups;

	/**
	 * Initializes new flight with set amount of seats
	 * @param name Name of flight
	 */
	public Flight(String name)
	{
		this.name = name;
		firstClassList = new Passenger[2][4];
		economyList = new Passenger[30][6];
		fcSeats = 8;
		ecoSeats = 150;
		groups = new HashMap<>();
	}

	/**
	 * Adds passenger to flight
	 * @param p Passenger to be added to flight
	 * @param printUpdate If true, prints where passenger was seated
	 * @return True if passenger was successfully seated, false if not.
	 */
	public boolean addPassenger(Passenger p, boolean printUpdate)
	{
		/*
		 * FIRST CLASS
		 * W = 0 + 3
		 * A = 1 + 2
		 *
		 * ECONOMY
		 * W = 0 + 5
		 * C = 1 + 4
		 * A = 2 + 3
		 */
		if(p.getClassName().equalsIgnoreCase("First")) {
			for (int i = 0; i < firstClassList.length; i++) {
				for (int j = 0; j < firstClassList[0].length; j++) {
					if ((firstClassList[i][j]) == null) {
						if ((p.getSeatPref().equalsIgnoreCase("W")) && (j == 0 || j == 3)) {
							firstClassList[i][j] = p;
							fcSeats--;
							if(printUpdate)
							{
								System.out.println(p.getName() + " seated in seat [" + (i+1) + toChar(j) + "]");
							}
							return true;
						}
						if ((p.getSeatPref().equalsIgnoreCase("A")) && (j == 1 || j == 2)) {
							firstClassList[i][j] = p;
							if(printUpdate)
							{
								System.out.println(p.getName() + " seated in seat [" + (i+1) + toChar(j) + "]");
							}
							fcSeats--;
							return true;
						}
					}
				}
			}
		}
		else if(p.getClassName().equalsIgnoreCase("Economy"))
		{
			for(int i = 0; i < economyList.length; i++) {
				for (int j = 0; j < economyList[0].length; j++) {
					if((economyList[i][j]) == null) {
						if((p.getSeatPref().equalsIgnoreCase("W")) && (j == 0 || j == 5)) {
							economyList[i][j] = p;
							fcSeats--;
							if(printUpdate)
							{
								System.out.println(p.getName() + " seated in seat [" + (i+10) + toChar(j) + "]");
							}
							return true;
						}
						else if((p.getSeatPref().equalsIgnoreCase("C")) && (j == 1 || j == 4)) {
							economyList[i][j] = p;
							fcSeats--;
							if(printUpdate)
							{
								System.out.println(p.getName() + " seated in seat [" + (i+10) + toChar(j) + "]");
							}
							return true;
						}
						else if((p.getSeatPref().equalsIgnoreCase("A")) && (j == 2 || j == 3)) {
							economyList[i][j] = p;
							fcSeats--;
							if(printUpdate)
							{
								System.out.println(p.getName() + " seated in seat [" + (i+10) + toChar(j) + "]");
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Adds passenger to specific seat in specific service class
	 * @param p Passenger to be added
	 * @param serviceClass Service class
	 * @param row Row
	 * @param col Column
	 */
	public void addPassengerToSeat(Passenger p, String serviceClass, int row, int col)
	{
		//Only to be used for loading file contents
		if(serviceClass.equalsIgnoreCase("first"))
		{
			firstClassList[row-1][col] = p;
			fcSeats--;
		}
		else if(serviceClass.equalsIgnoreCase("economy"))
		{
			economyList[row-1][col] = p;
			ecoSeats--;
		}
	}

	/**
	 * Converts number to character, used for seating
	 * @param num Seat number to be converted to char
	 * @return Single char string corresponding to letter representation of seat
	 */
	public String toChar(int num)
	{
		if(num == 0)
		{
			return "A";
		}
		else if(num == 1)
		{
			return "B";
		}
		else if(num == 2)
		{
			return "C";
		}
		else if(num == 3)
		{
			return "D";
		}
		else if(num == 4)
		{
			return "E";
		}
		else if(num == 5)
		{
			return "F";
		}
		return null;
	}

	/**
	 * Converts seat letter to seat number
	 * @param str Seat letter
	 * @return Seat number
	 */
	public int toInt(String str)
	{
		if(str.equalsIgnoreCase("A"))
		{
			return 0;
		}
		else if(str.equalsIgnoreCase("B"))
		{
			return 1;
		}
		else if(str.equalsIgnoreCase("C"))
		{
			return 2;
		}
		else if(str.equalsIgnoreCase("D"))
		{
			return 3;
		}
		else if(str.equalsIgnoreCase("E"))
		{
			return 4;
		}
		else if(str.equalsIgnoreCase("F"))
		{
			return 5;
		}

		return -1;
	}

	/**
	 * Prints out the flight's manifest
	 * @param flightClass Service class to be printed out
	 */
	public void printManifest(String flightClass)
	{
		if(flightClass.equalsIgnoreCase("first")) {
			System.out.println("\nFirst:");
			for (int i = 0; i < firstClassList.length; i++) {
				for (int j = 0; j < firstClassList[0].length; j++) {
					if (firstClassList[i][j] != null) {
						System.out.println((i + 1) + toChar(j) + ": " + firstClassList[i][j].getName());
					}
				}
			}
		}
		else if(flightClass.equalsIgnoreCase("economy")) {
			System.out.println("\nEconomy:");
			for (int i = 0; i < economyList.length; i++) {
				for (int j = 0; j < economyList[0].length; j++) {
					if (economyList[i][j] != null) {
						System.out.println((i + 10) + toChar(j) + ": " + economyList[i][j].getName());
					}
				}
			}
		}
	}

	/**
	 * Prints out available seats on flight
	 * @param flightClass Service class to be printed out
	 */
	public void printAvailability(String flightClass)
	{
		boolean isNewRow = true;
		if(flightClass.equalsIgnoreCase("first"))
		{
			System.out.println("There are " + this.getFcSeats() + " seats available in first class:");
			for(int i = 0; i < firstClassList.length; i++)
			{
				for(int j = 0; j < firstClassList[0].length; j++)
				{
					if(isNewRow)
					{
						System.out.println("");
						System.out.print((i+1) + ": ");
						isNewRow = false;
					}
					if(firstClassList[i][j] == null)
					{
						System.out.print(toChar(j) + ",");
					}
				}
				isNewRow = true;
			}
		}
		else
		{
			System.out.println("There are " + this.getEcoSeats() + " seats available in economy:");
			for(int i = 0; i < economyList.length; i++)
			{
				for(int j = 0; j < economyList[0].length; j++)
				{
					if(isNewRow)
					{
						System.out.println("");
						System.out.print((i+10) + ": ");
						isNewRow = false;
					}
					if(economyList[i][j] == null)
					{
						System.out.print(toChar(j) + ",");
					}
				}
				isNewRow = true;
			}
		}
	}

	/**
	 * Finds seats for given group of passengers
	 * @param names Array of names of passengers
	 * @param serviceClass Service class to find seats in
	 * @param groupName Name of group of passengers
	 */
	public void findSeats(String[] names, String serviceClass, String groupName)
	{
		boolean seatsFound = false;
		String[] groupSeats = new String[names.length];

		int counter = 0;
		int searchList = names.length;
		int seatIndex = 0;

		ArrayList<Passenger> groupStorage = new ArrayList<>();

		if(serviceClass.equalsIgnoreCase("First")) {
			while(searchList > 0) {
				for (int i = 0; i < firstClassList.length; i++) {
					for (int j = 0; j < firstClassList[0].length; j++) {
						if (firstClassList[i][j] == null) {
							counter++;
						}
						if ((counter == searchList) && (fcSeats >= names.length)) {
							for (int k = searchList - 1; k >= 0; k--) {
								groupSeats[seatIndex] = (i+1) + toChar(j - k);
								Passenger p = new Passenger(names[seatIndex], serviceClass);
								firstClassList[i][j-k] = p;
								groupStorage.add(p);
								fcSeats--;
								seatIndex++;
							}
							seatsFound = true;
						}
						if(seatsFound) { break; }
					}
					if(seatsFound) { break; }
					counter = 0;
				}
				if(seatsFound) { break; }
				searchList--;
			}
		}
		else if(serviceClass.equalsIgnoreCase("Economy"))
		{
			while(searchList > 0) {
				for (int i = 0; i < economyList.length; i++) {
					for (int j = 0; j < economyList[0].length; j++) {
						if (economyList[i][j] == null) {
							counter++;
						}
						if ((counter == searchList) && (ecoSeats >= names.length)) {
							for (int k = searchList - 1; k >= 0; k--) {
								groupSeats[seatIndex] = (i+10) + toChar(j - k);
								Passenger p = new Passenger(names[seatIndex], serviceClass);
								economyList[i][j-k] = p;
								groupStorage.add(p);
								ecoSeats--;
								seatIndex++;
							}
							seatsFound = true;
						}
						if(seatsFound) { break; }
					}
					if(seatsFound) { break; }
					counter = 0;
				}
				if(seatsFound) { break; }
				searchList--;
			}
		}

		if(seatsFound)
		{
			System.out.println("Seats found for group: ");
			for(int i = 0; i < names.length; i++)
			{
				if(serviceClass.equalsIgnoreCase("First"))
				{
					System.out.println(names[i] + " - " + groupSeats[i]);
				}
				else if(serviceClass.equalsIgnoreCase("Economy"))
				{
					System.out.println(names[i] + " - " + groupSeats[i]);
				}
			}
			groups.put(groupName, groupStorage);
		}
		else
		{
			System.out.println("Not enough seats were found for your group!");
		}
	}

	/**
	 * Removes given name(s) of passengers to remove from the flight
	 * @param names Array of passenger names
	 */
	public void removeFromFlight(String[] names)
	{
		int index = 0;
		for(int i = 0; i < firstClassList.length; i++) {
			for (int j = 0; j < firstClassList[0].length; j++) {
				if(firstClassList[i][j] != null) {
					if (firstClassList[i][j].getName().equalsIgnoreCase(names[index])) {
						firstClassList[i][j] = null;
						if (!((index + 1) > names.length - 1)) {
							index++;
						} else {
							return;
						}
						fcSeats++;
					}
				}
			}
		}

		for(int i = 0; i < economyList.length; i++)
		{
			for(int j = 0; j < economyList[0].length; j++)
			{
				if(economyList[i][j] != null) {
					if (economyList[i][j].getName().equalsIgnoreCase(names[index])) {
						economyList[i][j] = null;
						if (!((index + 1) > names.length - 1)) {
							index++;
						} else {
							return;
						}
						ecoSeats++;
					}
				}
			}
		}

	}

	/**
	 * Hashmap used to store group names and passengers in that group
	 * @return Current instance of hashmap that stores group info
	 */
	public HashMap<String, ArrayList<Passenger>> getMap()
	{
		return this.groups;
	}

	/**
	 * Arraylist used to store the names of the passengers in a group
	 * @param name Name of group
	 * @return ArrayList of passengers in given group
	 */
	public ArrayList<Passenger> getGroupInfo(String name)
	{
		return groups.get(name);
	}

	/**
	 * 2D Array of the first class seating chart
	 * @return First class 2d array
	 */
	public Passenger[][] getFirstClassList()
	{
		return firstClassList;
	}

	/**
	 * 2d Array of the economy class seating chart
	 * @return Economy class 2d array
	 */
	public Passenger[][] getEconomyList()
	{
		return economyList;
	}

	/**
	 * Gets remaining seats available in first class
	 * @return Remaining first class seats
	 */
	public int getFcSeats()
	{
		return fcSeats;
	}

	/**
	 * Gets remaining seats available in economy class
	 * @return Remaining economy seats
	 */
	public int getEcoSeats()
	{
		return ecoSeats;
	}

	/**
	 * Sets name of the flight to given String value
	 * @param x New name of this flight
	 */
	public void setName(String x)
	{
		name = x;
	}
	
}
