package ridesharingsystemproject;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static RideSharingSystem system = new RideSharingSystem();

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

    // ============== Riders ===============
    public static void searchRidersByHomeCity() {
        System.out.print("Enter home city: ");
        String city = scanner.nextLine();

        int left = 0;
        int right = city.length();

        while (left < right && city.charAt(left) <= ' ')
            left++;
        while (right > left && city.charAt(right - 1) <= ' ')
            right--;

        city = city.substring(left, right);
        printRiders(system.searchRidersByHomeCity(city));
    }

    public static void searchRidersByName() {
        System.out.print("Enter rider full name: ");
        String name = scanner.nextLine();

        int left = 0;
        int right = name.length();

        while (left < right && name.charAt(left) <= ' ')
            left++;
        while (right > left && name.charAt(right - 1) <= ' ')
            right--;

        name = name.substring(left, right);
        printRiders(system.searchRidersByName(name));
    }

    public static void addRider() {
        int id = readInt("Rider ID: ");

        System.out.print("Full name: ");
        String name = scanner.nextLine();
        int left = 0;
        int right = name.length();

        while (left < right && name.charAt(left) <= ' ')
            left++;
        while (right > left && name.charAt(right - 1) <= ' ')
            right--;
        name = name.substring(left, right);

        System.out.print("Email: ");
        String email = scanner.nextLine();
        left = 0;
        right = email.length();

        while (left < right && email.charAt(left) <= ' ')
            left++;
        while (right > left && email.charAt(right - 1) <= ' ')
            right--;
        email = email.substring(left, right);

        System.out.print("Phone number (10 digits): ");
        String phone = scanner.nextLine();
        left = 0;
        right = phone.length();

        while (left < right && phone.charAt(left) <= ' ')
            left++;
        while (right > left && phone.charAt(right - 1) <= ' ')
            right--;
        phone = phone.substring(left, right);

        System.out.print("Home city: ");
        String city = scanner.nextLine();
        left = 0;
        right = city.length();

        while (left < right && city.charAt(left) <= ' ')
            left++;
        while (right > left && city.charAt(right - 1) <= ' ')
            right--;
        city = city.substring(left, right);

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

    // ============== Drivers ===============
    public static void searchDriversByVehicleType() {
        VehicleType type = readVehicleType();
        if (type == null)
            return;
        printDrivers(system.searchDriversByVehicleType(type));
    }

    public static void addDriver() {
        int id = readInt("Driver ID: ");

        System.out.print("Full name: ");
        String name = scanner.nextLine();
        int left = 0;
        int right = name.length();

        while (left < right && name.charAt(left) <= ' ')
            left++;
        while (right > left && name.charAt(right - 1) <= ' ')
            right--;
        name = name.substring(left, right);

        System.out.print("Phone number (10 digits): ");
        String phone = scanner.nextLine();
        left = 0;
        right = phone.length();

        while (left < right && phone.charAt(left) <= ' ')
            left++;
        while (right > left && phone.charAt(right - 1) <= ' ')
            right--;
        phone = phone.substring(left, right);

        System.out.print("Vehicle plate (3 uppercase letters + 4 digits, e.g. ABC1234): ");
        String plate = scanner.nextLine();
        left = 0;
        right = plate.length();

        while (left < right && plate.charAt(left) <= ' ')
            left++;
        while (right > left && plate.charAt(right - 1) <= ' ')
            right--;
        plate = plate.substring(left, right);

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

    // ============== Rides ==============
    public static void searchRidesByPickupLocation() {
        System.out.print("Enter pickup location: ");
        String location = scanner.nextLine();

        int left = 0;
        int right = location.length();

        while (left < right && location.charAt(left) <= ' ')
            left++;
        while (right > left && location.charAt(right - 1) <= ' ')
            right--;

        location = location.substring(left, right);
        printRides(system.searchRidesByPickupLocation(location));
    }

    public static void searchRidesByRiderName() {
        System.out.print("Enter rider full name: ");
        String name = scanner.nextLine();

        int left = 0;
        int right = name.length();

        while (left < right && name.charAt(left) <= ' ')
            left++;
        while (right > left && name.charAt(right - 1) <= ' ')
            right--;

        name = name.substring(left, right);
        printRides(system.searchRidesByRiderName(name));
    }

    public static void showSharedRideParticipants() {
        System.out.print("Enter pickup location of the shared ride: ");
        String location = scanner.nextLine();

        int left = 0;
        int right = location.length();

        while (left < right && location.charAt(left) <= ' ')
            left++;
        while (right > left && location.charAt(right - 1) <= ' ')
            right--;

        location = location.substring(left, right);
        printRiders(system.getSharedRideParticipants(location));
    }

    public static void addRide() {
        System.out.print("Is the ride private or shared? (P = private, S = shared): ");
        String type = scanner.nextLine();

        int left = 0;
        int right = type.length();

        while (left < right && type.charAt(left) <= ' ')
            left++;
        while (right > left && type.charAt(right - 1) <= ' ')
            right--;

        type = type.substring(left, right).toUpperCase();

        if (!type.equals("P") && !type.equals("S")) {
            System.out.println("Invalid ride type. The ride was not added.");
            return;
        }

        System.out.print("Pickup location: ");
        String pickupLocation = scanner.nextLine();

        left = 0;
        right = pickupLocation.length();

        while (left < right && pickupLocation.charAt(left) <= ' ')
            left++;
        while (right > left && pickupLocation.charAt(right - 1) <= ' ')
            right--;

        pickupLocation = pickupLocation.substring(left, right);

        IDateTime pickupTime = readDateTime("Pickup date/time (MM/DD/YYYY HH:MM): ");
        if (pickupTime == null)
            return;

        IDateTime dropoffTime = readDateTime("Drop-off date/time (MM/DD/YYYY HH:MM): ");
        if (dropoffTime == null)
            return;

        System.out.print("Drop-off location: ");
        String dropoffLocation = scanner.nextLine();

        left = 0;
        right = dropoffLocation.length();

        while (left < right && dropoffLocation.charAt(left) <= ' ')
            left++;
        while (right > left && dropoffLocation.charAt(right - 1) <= ' ')
            right--;

        dropoffLocation = dropoffLocation.substring(left, right);

        int driverId = readInt("Driver ID: ");

        boolean scheduled;
        if (type.equals("P")) {
            // Private ride: exactly one rider

            int riderId = readInt("Rider ID: ");
            scheduled = system.schedulePrivateRide(
                    pickupLocation, pickupTime, dropoffTime,
                    dropoffLocation, riderId, driverId);
        } else {
            // Shared ride: more than one rider

            int[] riderIds = readRiderIds();
            if (riderIds == null)
                return;

            scheduled = system.scheduleSharedRide(
                    pickupLocation, pickupTime, dropoffTime,
                    dropoffLocation, riderIds, driverId);
        }
        if (scheduled)
            System.out.println("Ride scheduled successfully.");
        else
            System.out.println("Failed to schedule the ride (rider or driver not found, "
                    + "invalid times, or a schedule conflict).");
    }

    // =========== Printing lists ===========
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
        System.out.println(list.retrieve());
    }

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
        System.out.println(list.retrieve());
    }

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
        System.out.println(list.retrieve());
    }

    // ============= Reading input ===============
    // Keeps asking until the user enters a valid integer

    public static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                String number = scanner.nextLine();

                int left = 0;
                int right = number.length();

                while (left < right && number.charAt(left) <= ' ')
                    left++;
                while (right > left && number.charAt(right - 1) <= ' ')
                    right--;

                return Integer.parseInt(number.substring(left, right));

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Reads a vehicle type; returns null if the input is not a valid type
    public static VehicleType readVehicleType() {
        System.out.print("Vehicle type (SEDAN, LUXURY_SEDAN, SUV, VAN): ");
        String text = scanner.nextLine();

        int left = 0;
        int right = text.length();

        while (left < right && text.charAt(left) <= ' ')
            left++;
        while (right > left && text.charAt(right - 1) <= ' ')
            right--;

        text = text.substring(left, right);
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
        System.out.print(message);
        String text = scanner.nextLine();
        try {
            int left = 0;
            int right = text.length();

            while (left < right && text.charAt(left) <= ' ')
                left++;

            while (right > left && text.charAt(right - 1) <= ' ')
                right--;

            text = text.substring(left, right);

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

            int month = Integer.parseInt(
                    text.substring(0, firstSlash));

            int day = Integer.parseInt(
                    text.substring(firstSlash + 1, secondSlash));

            int year = Integer.parseInt(
                    text.substring(secondSlash + 1, space));

            int hour = Integer.parseInt(
                    text.substring(space + 1, colon));

            int minute = Integer.parseInt(
                    text.substring(colon + 1));

            // The DateTime constructor throws IllegalArgumentException for invalid values

            return new DateTime(year, month, day, hour, minute);

        } catch (Exception e) {
            System.out.println("Invalid date/time. Use the format MM/DD/YYYY HH:MM. The ride was not added.");
            return null;
        }
    }

    // Reads the rider IDs of a shared ride separated by ';' (at least two riders)
    public static int[] readRiderIds() {
        System.out.print("Rider IDs separated by ';' (at least two, e.g. 3;7;12): ");
        String line = scanner.nextLine();

        int left = 0;
        int right = line.length();

        while (left < right && line.charAt(left) <= ' ')
            left++;
        while (right > left && line.charAt(right - 1) <= ' ')
            right--;

        line = line.substring(left, right);

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
                left = 0;
                right = part.length();
                while (left < right && part.charAt(left) <= ' ')
                    left++;
                while (right > left && part.charAt(right - 1) <= ' ')
                    right--;
                ids[i] = Integer.parseInt(part.substring(left, right));

            } catch (NumberFormatException e) {
                System.out.println("Invalid rider ID: " + part + ". The ride was not added.");
                return null;
            }

            start = end + 1;
        }

        return ids;
    }
}
