/**
 * @author Jeremy Esch
 * @version 1.0
 * 3/15/2021
 *
 * Object representing a single passenger to be added to a flight
 */
public class Passenger{
	
	private final String name;
	private final String className;
	private String seatPref;

	/**
	 * Initializes passenger object
	 * @param name Name of passenger
	 * @param className Name of service class
	 * @param seatPref Seat preference
	 */
	public Passenger(String name, String className, String seatPref)
	{
		this.name = name;
		this.className = className;
		this.seatPref = seatPref;
	}

	/**
	 * Initializes passenger object
	 * @param name Name of passenger
	 * @param className Name of service class
	 */
	public Passenger(String name, String className)
	{
		this.name = name;
		this.className = className;
	}

	/**
	 * Gets the name of the passenger
	 * @return String name of passenger
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets the class name the passenger belongs to
	 * @return Service class name
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * Gets the seat preference the passenger has
	 * @return Seat preference
	 */
	public String getSeatPref()
	{
		return seatPref;
	}

}
