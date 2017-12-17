import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// class Ship which contains the ship details like name and size.
class Ship{
	String name, size;
	
	public Ship(String name) {
		this.name = name;
	}

	public Ship(String name, String size) {
		this.name = name;
		this.size = size;
	}

	public boolean dockShip(Port port) {
		if(size.equalsIgnoreCase("Large")){
			if(checkLargeShipSize(port)) {
				return true;
			}
		}
		else if(size.equalsIgnoreCase("Medium")) {
			if(checkMediumShipSize(port)) {
				return true;
			}
			else if(checkLargeShipSize(port)) {
				return true;
			}
		}
		else if(size.equalsIgnoreCase("Small")) {
			if(checkSmallShipSize(port)) {
				return true;
			}
			else if(checkMediumShipSize(port)) {
				return true;
			}
			else if(checkLargeShipSize(port)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkLargeShipSize(Port port) {
		for(Dock dock : port.docks) {
			for(Row row : dock.rows) {
				if(row.largeShip.size()<1) {
					row.largeShip.add(this);
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkMediumShipSize(Port port) {
		for(Dock dock : port.docks) {
			for(Row row : dock.rows) {
				if(row.mediumShip.size()<2) {
					row.mediumShip.add(this);
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkSmallShipSize(Port port) {
		for(Dock dock : port.docks) {
			for(Row row : dock.rows) {
				if(row.smallShip.size()<2) {
					row.smallShip.add(this);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean undockShip(Port port) {
		for(int dock = 0; dock< port.docks.size();dock++) {
			Dock d = port.docks.get(dock);
			for(int row = 0;row<d.rows.size();row++) {
				Row r = d.rows.get(row);
				for(Ship ship : r.smallShip) {
					if(ship.name.equalsIgnoreCase(name)) {
						return r.smallShip.remove(ship);
					}
				}
				for(Ship ship : r.mediumShip) {
					if(ship.name.equalsIgnoreCase(name)) {
						return r.mediumShip.remove(ship);
					}
				}
				for(Ship ship : r.largeShip) {
					if(ship.name.equalsIgnoreCase(name)) {
						return r.largeShip.remove(ship);
					}
				}
			}
		}
		return false;
	}
	
	public String toString() {
		return name+"\t"+size;
	}
}

// class Row which can have 3 types of ships i.e. 2 small, 2 medium and 1 large
class Row{
	List<Ship> smallShip, mediumShip,largeShip;
	
	public Row() {
		smallShip = new ArrayList<>();
		mediumShip = new ArrayList<>();
		largeShip = new ArrayList<>();
	}
}

// class Dock which has 2 rows i.e. List of size 2
class Dock{
	List<Row> rows;
	public Dock() {
		rows = new ArrayList<>();
		rows.add(new Row());
		rows.add(new Row());
	}
}

// class Port which has 2 docks i.e. List of size 2
class Port{
	List<Dock> docks;
	public Port() {
		docks = new ArrayList<>();
		docks.add(new Dock());
		docks.add(new Dock());
	}
	
	public void getPortStatus(List<Ship> waitingList) {
		System.out.println("Dock No.\tRow No.\tShip Name\tShip Size");
		for(int dock = 0; dock< docks.size();dock++) {
			Dock d = docks.get(dock);
			for(int row = 0;row<d.rows.size();row++) {
				Row r = d.rows.get(row);
				List<Ship> allShips = new ArrayList<>();
				allShips.addAll(r.smallShip);
				allShips.addAll(r.mediumShip);
				allShips.addAll(r.largeShip);
				for(Ship ship: allShips) {
					System.out.println(dock+"\t"+row+"\t"+ship);
				}
			}
		}

		System.out.println("Waiting List");
		if(!waitingList.isEmpty()) {
			System.out.println("Ship Name\tShip Size");
			for(Ship s : waitingList) {
				System.out.println(s);
			}
		}
		else
			System.out.println("Empty!!");
	}
}

public class Main {

	/* waitingList will contain a list of ships that need to be docked.
	Since it is common for all the docks that is why it is a public class variable. */
	public static List<Ship> waitingList = new ArrayList<>();

	/* As there is only 1 Port it is a public class variable shared by all. */
	public static Port port = new Port();

	public static void main(String[] args) {

		int choice = 0;		// initially choice is 0 because user will select 1 choice from menu.
		Scanner sc = new Scanner(System.in);

		// we need to keep asking the user various choices untill they say exit.
		while(choice != 4) {
			// List of choices user has.
			System.out.println("1. Dock ship");
			System.out.println("2. Undock ship");
			System.out.println("3. Get Port Status");
			System.out.println("4. Exit");
			System.out.print("Enter choice : ");
			choice = sc.nextInt();

			switch(choice) {
			case 1:
				// Dock Ship.
				// So we need the ship details and we create an object of that ship.
			
				sc.nextLine(); // to store the enter pressed after entering choice
				System.out.print("Enter name of ship :");
				String name = sc.nextLine();
				System.out.print("Enter size of ship :");
				String size = sc.nextLine();
				Ship ship  = new Ship(name, size);
				// check if ship was docked or not.
				if(!ship.dockShip(port)) {
					// ship was not docked so now it has to enter the waitingList
					// if the capacity of waitingList is less than 5 the ship can waiting
					// otherwise it has to go back.
					System.out.println("Port full enter in waiting list");
					if(waitingList.size()<5) {
						waitingList.add(ship);
					}
				}
				else {
					System.out.println("Ship docked!!");
				}
				break;
			case 2 :
				// Undock Ship.
				// Take the name of the ship that needs to be undocked.
				sc.nextLine(); 
				System.out.println("Enter name of ship :");
				String shipName = sc.nextLine();
				Ship unDockShip = new Ship(shipName);
				// if the ship exists in the row then undock it
				// else the ship does not exist in any row.
				if(unDockShip.undockShip(port)) {
					// ship has been undocked so now we can move a ship from waiting list to that empty position.
					if(!waitingList.isEmpty()) {
						// for every ship in the waitingList check the 1st ship that can fit in the empty position
						// and remove that ship from the waitingList.
						for(Ship s : waitingList) {
							if(s.dockShip(port)) {
								waitingList.remove(s);
								break;
							}
						}
					}
				}
				else {
					System.out.println("Ship does not exist");
				}
				break;
			case 3:
				port.getPortStatus(waitingList);
				break;
			}
		}
		sc.close();
	}
}
