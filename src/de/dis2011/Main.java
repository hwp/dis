package de.dis2011;

import de.dis2011.data.Apartment;
import de.dis2011.data.Contract;
import de.dis2011.data.Estate;
import de.dis2011.data.EstateAgent;
import de.dis2011.data.House;
import de.dis2011.data.Person;
import de.dis2011.data.PurchaseContract;
import de.dis2011.data.TenancyContract;

/**
 * Hauptklasse
 */
public class Main {
	private static final String AGENT_MNG_PASSWORD = "dis";

	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
		showMainMenu();
	}

	/**
	 * Zeigt das Hauptmenü
	 */
	public static void showMainMenu() {
		final int MENU_AGENT = 0;
		final int MENU_ESTATE = 1;
		final int MENU_CONTRACT = 2;
		final int QUIT = 3;

		Menu mainMenu = new Menu("Main menu");
		mainMenu.addEntry("Estate Agent Management", MENU_AGENT);
		mainMenu.addEntry("Estate Management", MENU_ESTATE);
		mainMenu.addEntry("Contract Management", MENU_CONTRACT);
		mainMenu.addEntry("Quit", QUIT);

		while (true) {
			int response = mainMenu.show();

			switch (response) {
			case MENU_AGENT:
				showAgentMenu();
				break;
			case MENU_ESTATE:
				showEstateMenu();
				break;
			case MENU_CONTRACT:
				showContractMenu();
				break;
			case QUIT:
				return;
			}
		}
	}

	private static void showContractMenu() {
		// Menu options
		final int CREATE_PERSON = 0;
		final int SIGN_PURCHASE_CONTRACT = 1;
		final int SIGN_TENANCY_CONTRACT = 2;
		final int SHOW_CONTRACTS = 22;
		final int QUIT = 3;

		// Create menu
		Menu mainMenu = new Menu("Contract Manage Menu");
		mainMenu.addEntry("Create Person", CREATE_PERSON);
		mainMenu.addEntry("Sign Purchase Contract", SIGN_PURCHASE_CONTRACT);
		mainMenu.addEntry("Sign Tenancy Contract", SIGN_TENANCY_CONTRACT);
		mainMenu.addEntry("Quit", QUIT);

		// Processing input
		while (true) {
			int response = mainMenu.show();
			switch (response) {
			case CREATE_PERSON:
				createPerson();
				break;
			case SIGN_PURCHASE_CONTRACT:
				signPurchaseContract();
				break;
			case SIGN_TENANCY_CONTRACT:
				signTenancyContract();
				break;
			case SHOW_CONTRACTS:
				Contract.showAll(System.out);
				break;
			case QUIT:
				return;
			}
		}
	}


	private static void signTenancyContract() {
		TenancyContract cont = new TenancyContract();

		cont.setPlace(FormUtil.readString("Place"));
		cont.setStartDate(FormUtil.readDate("Start Date"));
		cont.setDuration(FormUtil.readString("Duration"));
		cont.setAddtionalCosts(FormUtil.readInt("Additional Costs"));
		cont.setTenant(FormUtil.readInt("Tenant ID"));
		cont.setAptID(FormUtil.readInt("Apartment ID"));

		cont.save();
		System.out.println("Tenancy contract with No " + cont.getContractNo()
				+ " signed.");
	}

	private static void signPurchaseContract() {
		PurchaseContract cont = new PurchaseContract();

		cont.setPlace(FormUtil.readString("Place"));
		cont.setNoi(FormUtil.readInt("No. of Intallments"));
		cont.setInterestRate(FormUtil.readDouble("Interest Rate"));
		cont.setPurchaser(FormUtil.readInt("Purchase ID"));
		cont.setHouseID(FormUtil.readInt("House ID"));

		cont.save();
		System.out.println("Purchase contract with No " + cont.getContractNo()
				+ " signed.");
	}

	private static void createPerson() {
		Person p = new Person();

		p.setFirstName(FormUtil.readString("First Name"));
		p.setName(FormUtil.readString("Name"));
		p.setAddress(FormUtil.readString("Adresse"));
		p.save();

		System.out.println("Person with ID " + p.getId() + " created.");
	}

	private static void showEstateMenu() {
		String login = FormUtil.readString("LOGIN");
		String password = FormUtil.readString("PASSWORD");

		EstateAgent e = EstateAgent.login(login, password);

		if (e == null) {
			System.out.println("UserName / Password  does not exist.");
		} else {
			System.out
					.println("UserName / Password exists. Now you can see the create , delete and update estates");
			// Here i can create , delete and update states using the logged
			// user
			// Create New Menu

			// Menu options
			final int CREATE_ESTATE = 0;
			final int DELETE_ESTATE = 1;
			final int UPDATE_ESTATE = 2;
			final int QUIT = 3;

			// Create menu
			Menu mainMenu = new Menu("Estate Manage menu");
			mainMenu.addEntry("Create Estate", CREATE_ESTATE);
			mainMenu.addEntry("Delete Estate", DELETE_ESTATE);
			mainMenu.addEntry("Update Estate", UPDATE_ESTATE);
			mainMenu.addEntry("Quit", QUIT);

			// Processing input
			while (true) {
				int response = mainMenu.show();
				switch (response) {
				case CREATE_ESTATE:
					createEstate(e.getId());
					break;
				case DELETE_ESTATE:
					deleteEstate();
					break;
				case UPDATE_ESTATE:
					updateEstate(e.getId());
					break;
				case QUIT:
					return;
				}
			}
		}
	}

	private static void updateEstate(int managerId) {
		int id = FormUtil.readInt("ID");
		Estate e = Estate.load(id);

		if (e == null) {
			System.out.println("Estate with ID " + id + " does not exist.");
		} else {
			if (e instanceof Apartment) {
				Apartment apt = (Apartment) e;

				apt.setCity(FormUtil.readString("City"));
				apt.setPostalCode(FormUtil.readInt("Postal Code"));
				apt.setStreet(FormUtil.readString("Street"));
				apt.setStreetNo(FormUtil.readInt("Street Number"));
				apt.setArea(FormUtil.readInt("Square Area"));

				apt.setFloor(FormUtil.readInt("Floor"));
				apt.setRent(FormUtil.readInt("Rent"));
				apt.setRooms(FormUtil.readInt("Rooms"));
				apt.setBalcony(FormUtil.readBoolean("Balcony"));
				apt.setKitchen(FormUtil.readBoolean("Kitchen"));

				apt.save(managerId);
				System.out.println("Estate with ID " + id + " updated.");
			} else if (e instanceof House) {
				House hs = (House) e;

				hs.setCity(FormUtil.readString("City"));
				hs.setPostalCode(FormUtil.readInt("Postal Code"));
				hs.setStreet(FormUtil.readString("Street"));
				hs.setStreetNo(FormUtil.readInt("Street Number"));
				hs.setArea(FormUtil.readInt("Square Area"));

				hs.setFloors(FormUtil.readInt("Floor"));
				hs.setPrice(FormUtil.readInt("Price"));
				hs.setGarden(FormUtil.readBoolean("Garden"));

				hs.save(managerId);
				System.out.println("Estate with ID " + id + " updated.");
			}
		}
	}

	private static void deleteEstate() {
		int id = FormUtil.readInt("ID");
		Estate e = Estate.load(id);

		if (e == null) {
			System.out.println("Estate with ID " + id + " does not exist.");
		} else {
			e.delete();
			System.out.println("Estate with ID " + id + " deleted.");
		}
	}

	private static void createEstate(int managerId) {
		final int APARTMENT = 0;
		final int HOUSE = 1;

		Menu menu = new Menu("Apartment OR House");
		menu.addEntry("Apartment", APARTMENT);
		menu.addEntry("House", HOUSE);
		int res = menu.show();
		switch (res) {
		case APARTMENT:
			Apartment apt = new Apartment();

			apt.setCity(FormUtil.readString("City"));
			apt.setPostalCode(FormUtil.readInt("Postal Code"));
			apt.setStreet(FormUtil.readString("Street"));
			apt.setStreetNo(FormUtil.readInt("Street Number"));
			apt.setArea(FormUtil.readInt("Square Area"));

			apt.setFloor(FormUtil.readInt("Floor"));
			apt.setRent(FormUtil.readInt("Rent"));
			apt.setRooms(FormUtil.readInt("Rooms"));
			apt.setBalcony(FormUtil.readBoolean("Balcony"));
			apt.setKitchen(FormUtil.readBoolean("Kitchen"));

			apt.save(managerId);
			System.out.println("Estate with ID " + apt.getId() + " created.");
			break;
		case HOUSE:
			House hs = new House();

			hs.setCity(FormUtil.readString("City"));
			hs.setPostalCode(FormUtil.readInt("Postal Code"));
			hs.setStreet(FormUtil.readString("Street"));
			hs.setStreetNo(FormUtil.readInt("Street Number"));
			hs.setArea(FormUtil.readInt("Square Area"));

			hs.setFloors(FormUtil.readInt("Floor"));
			hs.setPrice(FormUtil.readInt("Price"));
			hs.setGarden(FormUtil.readBoolean("Garden"));

			hs.save(managerId);
			System.out.println("Estate with ID " + hs.getId() + " created.");
			break;
		}
	}

	/**
	 * Zeigt die Maklerverwaltung
	 */
	public static void showAgentMenu() {
		if (!FormUtil.readString("Enter Agent Management Password").equals(
				AGENT_MNG_PASSWORD)) {
			System.out.println("Login incorrect");
			return;
		}

		final int NEW_AGENT = 0;
		// final int SHOW_AGENT = 1;
		final int CHANGE_AGENT = 2;
		final int DELETE_AGENT = 3;
		final int BACK = 4;

		Menu maklerMenu = new Menu("Estate Agent Management");
		maklerMenu.addEntry("New Agent", NEW_AGENT);
		// maklerMenu.addEntry("Show Agents", SHOW_AGENT);
		maklerMenu.addEntry("Change Agent", CHANGE_AGENT);
		maklerMenu.addEntry("Delete Agent", DELETE_AGENT);
		maklerMenu.addEntry("Back to main menu", BACK);

		while (true) {
			int response = maklerMenu.show();

			switch (response) {
			case NEW_AGENT:
				newAgent();
				break;
			// case SHOW_AGENT:
			// showAgent();
			// break;
			case CHANGE_AGENT:
				changeAgent();
				break;
			case DELETE_AGENT:
				deleteAgent();
				break;
			case BACK:
				return;
			}
		}
	}

	// private static void showAgent() {
	// for(EstateAgent a : EstateAgent.getAll()) {
	// System.out.println("***** Agent " + a.getId() + " *****");
	// System.out.println("Name : " * a.getName());
	// }
	// }

	private static void changeAgent() {
		int id = FormUtil.readInt("ID");
		EstateAgent e = EstateAgent.load(id);

		if (e == null) {
			System.out.println("Agent with ID " + id + " does not exist.");
		} else {
			e.setName(FormUtil.readString("Name"));
			e.setAddress(FormUtil.readString("Adresse"));
			e.setLogin(FormUtil.readString("Login"));
			e.setPassword(FormUtil.readString("Passwort"));
			e.save();

			System.out.println("Agent with ID " + id + " modified.");
		}
	}

	private static void deleteAgent() {
		int id = FormUtil.readInt("ID");
		EstateAgent e = EstateAgent.load(id);

		if (e == null) {
			System.out.println("Agent with ID " + id + " does not exist.");
		} else {
			e.delete();
			System.out.println("Agent with ID " + id + " deleted.");
		}
	}

	public static void newAgent() {
		EstateAgent m = new EstateAgent();

		m.setName(FormUtil.readString("Name"));
		m.setAddress(FormUtil.readString("Adresse"));
		m.setLogin(FormUtil.readString("Login"));
		m.setPassword(FormUtil.readString("Passwort"));
		m.save();

		System.out.println("Agent with ID " + m.getId() + " created.");
	}
}
