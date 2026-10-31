package ridesharingsystemproject;

import java.util.Scanner;

/**
 * Command-line menu of the Ride-Sharing System.
 * This is the only class that reads input from the user and prints output.
 * It calls the methods of RideSharingSystem and prints what they return.
 */
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static RideSharingSystem system = new RideSharingSystem();

    // Loads the CSV files, then shows the menu until the user chooses 0
    public static void main(String[] args) {

        // Load the data before showing the menu (riders and drivers first, then rides)
        System.out.println("Loading data...");

        if (system.loadRidersFromCSV("riders_100.csv"))
            System.out.println("Riders loaded.");
        else
            System.out.println("Could not load riders_100.csv");

        if (system.loadDriversFromCSV("drivers_30.csv"))
            System.out.println("Drivers loaded.");
        else
            System.out.println("Could not load drivers_30.csv");

        if (system.loadRidesFromCSV("rides_40.csv"))
            System.out.println("Rides loaded.");
        else
            System.out.println("Could not load rides_40.csv");

        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ");
            System.out.println();

            switch (choice) {
                case 1:
                    printRiders(system.getAllRiders());
                    break;
                case 2:
                    searchRidersByHomeCity();
                    break;
                case 3:
                    searchRidersByName();
                    break;
                case 4:
                    addRider();
                    break;
                case 5:
                    printDrivers(system.getAllDrivers());
                    break;
                case 6:
                    searchDriversByVehicleType();
                    break;
                case 7:
                    addDriver();
                    break;
                case 8:
                    printRides(system.getAllRidesAlphabetically());
                    break;
                case 9:
                    searchRidesByPickupLocation();
                    break;
                case 10:
                    searchRidesByRiderName();
                    break;
                case 11:
                    showSharedRideParticipants();
                    break;
                case 12:
                    addRide();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, please enter a number from 0 to 12.");
            }
            System.out.println();
        } while (choice != 0);
    }

    // ================== Menu ==================

    // Prints the 12 menu options and the exit option
    public static void printMenu() {
        System.out.println("========== Ride-Sharing System ==========");
        System.out.println("1.  List all riders");
        System.out.println("2.  Search for riders by home city");
        System.out.println("3.  Search for riders by name");
        System.out.println("4.  Add a rider");
        System.out.println("5.  List all drivers");
        System.out.println("6.  Search for drivers by vehicle type");
        System.out.println("7.  Add a driver");
        System.out.println("8.  List all rides alphabetically by pickup location");
        System.out.println("9.  Search for rides by pickup location");
        System.out.println("10. Search for rides by rider name");
        System.out.println("11. List the participants of a shared ride by pickup location");
        System.out.println("12. Add a ride");
        System.out.println("0.  Exit");
        System.out.println("=========================================");
    }

    // ================== Riders ==================

    // Asks for a home city and prints the riders who live there
    public static void searchRidersByHomeCity() {
        String city = readText("Enter home city: ");
        printRiders(system.searchRidersByHomeCity(city));
    }

    // Asks for a full name and prints the riders with that name
    public static void searchRidersByName() {
        String name = readText("Enter rider full name: ");
        printRiders(system.searchRidersByName(name));
    }

    // Reads the rider data and adds a new rider to the system
    public static void addRider() {
        int id = readInt("Rider ID: ");
        String name = readText("Full name: ");
        String email = readText("Email: ");
        String phone = readText("Phone number (10 digits): ");
        String city = readText("Home city: ");

        try {
            // The constructor throws IllegalArgumentException if the phone is invalid
            Rider rider = new Rider(id, name, email, phone, city);
            if (system.addRider(rider))
                System.out.println("Rider added successfully.");
            else
                System.out.println("Failed to add rider (the ID already exists).");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add rider: " + e.getMessage());
        }
    }

    // ================== Drivers ==================

    // Asks for a vehicle type and prints the drivers who use it
    public static void searchDriversByVehicleType() {
        VehicleType type = readVehicleType();
        if (type == null)
            return;
        printDrivers(system.searchDriversByVehicleType(type));
    }

    // Reads the driver data and adds a new driver to the system
    public static void addDriver() {
        int id = readInt("Driver ID: ");
        String name = readText("Full name: ");
        String phone = readText("Phone number (10 digits): ");
        String plate = readText("Vehicle plate (3 uppercase letters + 4 digits, e.g. ABC1234): ");

        VehicleType type = readVehicleType();
        if (type == null)
            return;

        try {
            // The constructor throws IllegalArgumentException if the phone or plate is invalid
            Driver driver = new Driver(id, name, phone, plate, type);
            if (system.addDriver(driver))
                System.out.println("Driver added successfully.");
            else
                System.out.println("Failed to add driver (the ID or the plate already exists).");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add driver: " + e.getMessage());
        }
    }

    // ================== Rides ==================

    // Asks for a pickup location and prints the rides that start there
    public static void searchRidesByPickupLocation() {
        String location = readText("Enter pickup location: ");
        printRides(system.searchRidesByPickupLocation(location));
    }

    // Asks for a rider name and prints the rides that include this rider
    public static void searchRidesByRiderName() {
        String name = readText("Enter rider full name: ");
        printRides(system.searchRidesByRiderName(name));
    }

    // Asks for a pickup location and prints the riders of the shared rides that start there
    public static void showSharedRideParticipants() {
        String location = readText("Enter pickup location of the shared ride: ");
        printRiders(system.getSharedRideParticipants(location));
    }

    // Reads the ride data and schedules a private or a shared ride
    public static void addRide() {
        String type = readText("Is the ride private or shared? (P = private, S = shared): ").toUpperCase();

        if (!type.equals("P") && !type.equals("S")) {
            System.out.println("Invalid ride type. The ride was not added.");
            return;
        }

        String pickupLocation = readText("Pickup location: ");

        IDateTime pickupTime = readDateTime("Pickup date/time (MM/DD/YYYY HH:MM): ");
        if (pickupTime == null)
            return;

        IDateTime dropoffTime = readDateTime("Drop-off date/time (MM/DD/YYYY HH:MM): ");
        if (dropoffTime == null)
            return;

        String dropoffLocation = readText("Drop-off location: ");

        int driverId = readInt("Driver ID: ");

        boolean scheduled;
        if (type.equals("P")) {
            // Private ride: exactly one rider
            int riderId = readInt("Rider ID: ");
            scheduled = system.schedulePrivateRide( pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderId, driverId);
        } else {
            // Shared ride: more than one rider
            int[] riderIds = readRiderIds();
            if (riderIds == null)
                return;

            scheduled = system.scheduleSharedRide( pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderIds, driverId);
        }

        if (scheduled)
            System.out.println("Ride scheduled successfully.");
        else
            System.out.println("Failed to schedule the ride (rider or driver not found, " + "invalid times, or a schedule conflict).");
    }

    // ================== Printing lists ==================

    // Prints every rider in the list, or a message if the list is empty
    public static void printRiders(LinkedList<IRider> list) {
        if (list == null || list.empty()) {
            System.out.println("No results found.");
            return;
        }

        list.findFirst();
        while (!list.last()) {
            System.out.println(list.retrieve());
            list.findNext();
        }
        System.out.println(list.retrieve()); // the last element
    }

    // Prints every driver in the list, or a message if the list is empty
    public static void printDrivers(LinkedList<IDriver> list) {
        if (list == null || list.empty()) {
            System.out.println("No results found.");
            return;
        }

        list.findFirst();
        while (!list.last()) {
            System.out.println(list.retrieve());
            list.findNext();
        }
        System.out.println(list.retrieve()); // the last element
    }

    // Prints every ride in the list, or a message if the list is empty
    public static void printRides(LinkedList<IRide> list) {
        if (list == null || list.empty()) {
            System.out.println("No results found.");
            return;
        }

        list.findFirst();
        while (!list.last()) {
            System.out.println(list.retrieve());
            list.findNext();
        }
        System.out.println(list.retrieve()); // the last element
    }

    // ================== Reading input ==================

    // Removes the spaces (and other blank characters) from the start and end of a text
    public static String trimText(String text) {
        int left = 0;
        int right = text.length();

        while (left < right && text.charAt(left) <= ' ')
            left++;
        while (right > left && text.charAt(right - 1) <= ' ')
            right--;

        return text.substring(left, right);
    }

    // Prints the message, reads one line, and returns it without spaces at both ends
    public static String readText(String message) {
        System.out.print(message);
        return trimText(scanner.nextLine());
    }

    // Keeps asking until the user enters a valid integer
    public static int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readText(message));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Reads a vehicle type; returns null if the input is not a valid type
    public static VehicleType readVehicleType() {
        String text = readText("Vehicle type (SEDAN, LUXURY_SEDAN, SUV, VAN): ");
        text = text.toUpperCase().replace(" ", "_");
        try {
            return VehicleType.valueOf(text);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid vehicle type.");
            return null;
        }
    }

    // Reads a date/time in the format MM/DD/YYYY HH:MM; returns null if it is invalid
    public static IDateTime readDateTime(String message) {
        String text = readText(message);
        try {
            // Find the positions of the separators
            int firstSlash = -1;
            int secondSlash = -1;
            int space = -1;
            int colon = -1;

            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '/') {
                    if (firstSlash == -1)
                        firstSlash = i;
                    else if (secondSlash == -1)
                        secondSlash = i;
                }

                if (text.charAt(i) == ' ')
                    space = i;

                if (text.charAt(i) == ':')
                    colon = i;
            }

            if (firstSlash == -1 || secondSlash == -1 ||
                space == -1 || colon == -1) {
                throw new IllegalArgumentException();
            }

            int month = Integer.parseInt(text.substring(0, firstSlash));
            int day = Integer.parseInt(text.substring(firstSlash + 1, secondSlash));
            int year = Integer.parseInt(text.substring(secondSlash + 1, space));
            int hour = Integer.parseInt(text.substring(space + 1, colon));
            int minute = Integer.parseInt(text.substring(colon + 1));

            // The DateTime constructor throws IllegalArgumentException for invalid values
            return new DateTime(year, month, day, hour, minute);

        } catch (Exception e) {
            System.out.println("Invalid date/time. Use the format MM/DD/YYYY HH:MM. The ride was not added.");
            return null;
        }
    }

    // Reads the rider IDs of a shared ride separated by ';' (at least two riders)
    public static int[] readRiderIds() {
        String line = readText("Rider IDs separated by ';' (at least two, e.g. 3;7;12): ");

        // Count the IDs: the number of ';' plus one
        int count = 1;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ';') {
                count++;
            }
        }

        if (count < 2) {
            System.out.println("A shared ride needs more than one rider. The ride was not added.");
            return null;
        }

        int[] ids = new int[count];
        int start = 0;

        for (int i = 0; i < count; i++) {
            int end = start;

            while (end < line.length() && line.charAt(end) != ';') {
                end++;
            }
            String part = line.substring(start, end);

            try {
                ids[i] = Integer.parseInt(trimText(part));
            } catch (NumberFormatException e) {
                System.out.println("Invalid rider ID: " + part + ". The ride was not added.");
                return null;
            }

            start = end + 1;
        }

        return ids;
    }
}
