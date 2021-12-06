
import java.io.*;
import java.util.*;

/**
 * @author Jeremy Esch
 * @version 1.0
 * 3/15/2021
 *
 * Implements reservation system mimicing an airline flight registration program, where users are able to add passengers,
 * remove them, see seat availability, and access the flight manifest
 */
public class FlightReservation {

	/**
	 * Allows user to add or remove passengers, then saves flight info to flight file
	 * @param args Command line argument that specifies the name of the flight
	 */
	public static void main(String[] args) {

		Flight flight = new Flight("Default");
		File flightFile = new File("src/" + args[0] + ".txt");
		String flightName = args[0];
		try
		{
			if (flightFile.createNewFile()) {
				System.out.println(flightName + " does not exist, creating new flight.");
				flight.setName(flightName);
			}
			else {
				System.out.println(flightName + " exists, loading flight contents.");
				flight.setName(flightName);
				//THE VERY LONG PROCESS OF LOADING FLIGHT CONTENTS THAT I COULDNT MAKE INTO A METHOD FOR SOME REASON
				try{
					String loadingClass = "";
					Scanner fileReader = new Scanner(flightFile);

					while(fileReader.hasNextLine())
					{
						String line = fileReader.nextLine();
						if(line.equalsIgnoreCase("FIRST"))
						{
							loadingClass = "first";
							continue;
						}
						else if(line.equalsIgnoreCase("ECONOMY"))
						{
							loadingClass = "economy";
							continue;
						}

						String[] splitLine = line.split("-");
						String[] seatSplit = splitLine[1].split("");
						int[] seatInfo = {Integer.parseInt(seatSplit[0]), flight.toInt(seatSplit[1])};
						flight.addPassengerToSeat(new Passenger(splitLine[0], loadingClass), loadingClass, seatInfo[0], seatInfo[1]);
					}
					fileReader.close();
				}
				catch(FileNotFoundException e)
				{
					System.out.println("File not found while loading flight contents. Something went really wrong.");
					e.printStackTrace();
				}

				//Making file empty so it can be written into later
				try{
					FileOutputStream writer = new FileOutputStream("src/" + flightName + ".txt");
					writer.write(("").getBytes());
					writer.close();
				}
				catch(FileNotFoundException e)
				{
					System.out.println("FileNotFoundException");
					e.printStackTrace();
				}
				catch(IOException e)
				{
					System.out.println("IOException");
					e.printStackTrace();
				}

			}
		}
		catch(IOException e)
		{
			System.out.println("An error occurred");
			e.printStackTrace();
		}

		Scanner sc = new Scanner(System.in);
		String input = "";
		while(!input.equalsIgnoreCase("Q"))
		{
			System.out.println("\n\nThank you for choosing SJSU airlines, please select one of the following options...");
			System.out.println("Add [P]assenger, Add [G]roup, [C]ancel Reservations, Print Seating [A]vailability Chart, Print [M]anifest, [Q]uit");
			input = sc.nextLine();
			if(input.equalsIgnoreCase("P")) //Add passenger
			{
				System.out.println("------- Add Passenger -------");
				System.out.println("Enter name: ");
				String pName = sc.nextLine();
				System.out.println("Service class (First or Economy): ");
				String pClass = sc.nextLine();
				while(!pClass.equalsIgnoreCase("First") && !pClass.equalsIgnoreCase("Economy"))
				{
					System.out.println("Error: Please enter [first] or [economy] for service class!");
					System.out.println("Service class:");
					pClass = sc.nextLine();
				}
				System.out.println("Seating options - [W]indow, [C]enter, [A]isle");
				System.out.println("Seat preference: ");
				String pPref = sc.nextLine();
				boolean isFirst = pClass.equalsIgnoreCase("first");
				if(isFirst)
				{
					while (!pPref.equalsIgnoreCase("W") && !pPref.equalsIgnoreCase("A"))
					{
						System.out.println("Error: Please enter [W]indow or [A]isle");
						System.out.println("Seat preference: ");
						pPref = sc.nextLine();
					}
				}
				else
				{
					while (!pPref.equalsIgnoreCase("W") && !pPref.equalsIgnoreCase("C") && !pPref.equalsIgnoreCase("A"))
					{
						System.out.println("Error: Please enter [W]indow, [C]enter, or [A]isle");
						System.out.println("Seat preference: ");
						pPref = sc.nextLine();
					}
				}
				Passenger newPassenger = new Passenger(pName, pClass, pPref);
				boolean pAddSuccessful =  flight.addPassenger(newPassenger, true);
				if(!pAddSuccessful)
				{
					System.out.println("Request failed! There is no open seat with your preference!");
				}

			}
			else if(input.equalsIgnoreCase("G")) //Add group
			{
				System.out.println("Enter group name: ");
				String gGroupName = sc.nextLine();
				System.out.println("Names (separated by a comma): ");
				String gNames = sc.nextLine();
				System.out.println("Service class: ");
				String gClass = sc.nextLine();
				while(!gClass.equalsIgnoreCase("First") && !gClass.equalsIgnoreCase("Economy"))
				{
					System.out.println("Error: Please enter [first] or [economy] for service class!");
					System.out.println("Service class:");
					gClass = sc.nextLine();
				}
				String[] gNameArr = gNames.split(",");
				flight.findSeats(gNameArr, gClass, gGroupName);
			}
			else if(input.equalsIgnoreCase("C")) //Cancel reservations
			{
				System.out.println("Cancel [I]ndividual or [G]roup? ");
				String cChoice = sc.nextLine();
				while(!cChoice.equalsIgnoreCase("I") && !cChoice.equalsIgnoreCase("G"))
				{
					System.out.println("Error: Please enter [I] for individual cancellation or [G] for group cancellation. ");
					cChoice = sc.nextLine();
				}

				if(cChoice.equalsIgnoreCase("I"))
				{
					System.out.println("Names: ");
					String[] cNames = sc.nextLine().split(",");
					flight.removeFromFlight(cNames);
				}
				else if(cChoice.equalsIgnoreCase("G"))
				{
					System.out.println("Group name: ");
					String cGroupName = sc.nextLine();
					if(flight.getMap().get(cGroupName) == null)
					{
						System.out.println("Error: Group name does not exist.");
					}
					else
					{
						String[] cArr = new String[flight.getGroupInfo(cGroupName).size()];
						for(int i = 0; i < cArr.length; i++)
						{
							cArr[i] = flight.getGroupInfo(cGroupName).get(i).getName();
						}
						flight.removeFromFlight(cArr);
					}
				}
			}
			else if(input.equalsIgnoreCase("A")) //Print availability chart
			{
				System.out.println("Please enter desired service class: ");
				String aClass = sc.nextLine();
				if(aClass.equalsIgnoreCase("first") || aClass.equalsIgnoreCase("economy"))
				{
					flight.printAvailability(aClass);
				}
				else
				{
					System.out.println("Input error: service class " + aClass + " does not exist.");
				}
			}
			else if(input.equalsIgnoreCase("M")) //Print manifest
			{
				System.out.println("Print manifest for [first] class or [economy]?");
				String mClass = sc.nextLine();
				if(mClass.equalsIgnoreCase("first") || mClass.equalsIgnoreCase("economy"))
				{
					flight.printManifest(mClass);
				}
				else
				{
					System.out.println("Input error: service class " + mClass + " does not exist.");
				}
			}
		}
		System.out.println("Saving given flight info..");

		BufferedWriter br;
		Passenger[][] fc = flight.getFirstClassList();
		Passenger[][] ec = flight.getEconomyList();
		try{
			br = new BufferedWriter(new FileWriter("src/" + flightName + ".txt"));
			//First class
			br.write("FIRST");
			for(int i = 0; i < fc.length; i++)
			{
				for(int j = 0; j < fc[0].length; j++)
				{
					if(fc[i][j] != null)
					{
						br.newLine();
						br.write(fc[i][j].getName() + "-" + (i+1) + flight.toChar(j));
					}
				}
			}

			br.newLine();
			br.write("ECONOMY");
			for(int i = 0; i < ec.length; i++)
			{
				for(int j = 0; j < ec[0].length; j++)
				{
					if(ec[i][j] != null)
					{
						br.newLine();
						br.write(ec[i][j].getName() + "-" + (i+1) + flight.toChar(j));
					}
				}
			}
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("Error occurred with writing flight info to file.");
			e.printStackTrace();
		}
		System.out.println("\nGoodbye!");
	}
}


