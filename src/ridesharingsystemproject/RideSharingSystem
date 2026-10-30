
package ridesharingsystemproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RideSharingSystem implements IRideSharingSystem {

    private IRiderList riderList;
    private IDriverList driverList;
    private IRideList rideList;
    private int nextRideId; // to generate a unique ID

    public RideSharingSystem() {
        this.riderList = new RiderList();
        this.driverList = new DriverList();
        this.rideList = new RideList();
        this.nextRideId = 1;
    }


    /**
   Reads riders from the riders_100.csv file.
   Format: riderId,name,email,phoneNumber,homeCity
   */
    @Override
    public boolean loadRidersFromCSV(String ridersFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(ridersFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String email = parts[2].trim();
                    String phone = parts[3].trim();
                    String city = parts[4].trim();

                    Rider rider = new Rider(id, name, email, phone, city);
                    addRider(rider);
                } catch (IllegalArgumentException e) {
                    
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
   Reads drivers from the drivers_30.csv file.
   Format: driverId,name,phoneNumber,vehiclePlate,vehicleType
   */
    @Override
    public boolean loadDriversFromCSV(String driversFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(driversFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String phone = parts[2].trim();
                    String plate = parts[3].trim();
                    VehicleType vType = VehicleType.valueOf(parts[4].trim().toUpperCase().replace(" ", "_"));

                    Driver driver = new Driver(id, name, phone, plate, vType);
                    addDriver(driver);
                } catch (IllegalArgumentException e) {
                 
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

   /**
   Reads rides from the rides_40.csv file.
   Format: rideType,pickupLocation,pickupDateTime,dropoffDateTime,dropoffLocation,driverId,riderIds
   */
    @Override
    public boolean loadRidesFromCSV(String ridesFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(ridesFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 7) continue;

                try {
                    String rideType = parts[0].trim().toUpperCase();
                    String pickupLocation = parts[1].trim();
                    IDateTime pickupTime = parseDateTime(parts[2].trim());
                    IDateTime dropoffTime = parseDateTime(parts[3].trim());
                    String dropoffLocation = parts[4].trim();
                    int driverId = Integer.parseInt(parts[5].trim());
                    String rawRiderIds = parts[6].trim();

                    if (rideType.equals("PRIVATE")) {
                        int riderId = Integer.parseInt(rawRiderIds);
                        schedulePrivateRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderId, driverId);
                    } else if (rideType.equals("SHARED")) {
                        String[] idsStr = rawRiderIds.split(";");
                        int[] riderIds = new int[idsStr.length];
                        for (int i = 0; i < idsStr.length; i++) {
                            riderIds[i] = Integer.parseInt(idsStr[i].trim());
                        }
                        scheduleSharedRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderIds, driverId);
                    }
                } catch (Exception e) {
                    
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // =========================================================================
    //                       Scheduling Rides
    // =========================================================================

    @Override
    public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, 
                                       IDateTime dropoffTime, String dropoffLocation, 
                                       int riderId, int driverId) {
        
        if (pickupLocation == null || dropoffLocation == null || 
            pickupTime == null || dropoffTime == null || 
            pickupTime.compareTo(dropoffTime) >= 0) {
            return false;
        }
        
        IRider rider = riderList.findById(riderId);
        IDriver driver = driverList.findById(driverId);
        if (rider == null || driver == null) {
            return false;
        }

        
        if (hasConflict(rider, pickupTime, dropoffTime) || hasConflict(driver, pickupTime, dropoffTime)) {
            return false;
        }

        
        int rideId = nextRideId++;
        PrivateRide ride = new PrivateRide(rideId, pickupLocation, dropoffLocation, pickupTime, dropoffTime, driver, rider);
        rideList.addRide(ride);

        // Updates the ride history for both the rider and the driver
        rider.getRideHistory().insert(ride);
        driver.getRideHistory().insert(ride);
        return true;
    }

    @Override
    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, 
                                      IDateTime dropoffTime, String dropoffLocation, 
                                      int[] riderIds, int driverId) {
        if (riderIds == null || riderIds.length == 0) {
            return false;
        }
        
        if (pickupLocation == null || dropoffLocation == null || 
            pickupTime == null || dropoffTime == null || 
            pickupTime.compareTo(dropoffTime) >= 0) {
            return false;
        }

        // Checks whether the driver exists.
        IDriver driver = driverList.findById(driverId);
        if (driver == null || hasConflict(driver, pickupTime, dropoffTime)) {
            return false;
        }

        // Checks that all riders exist and that none of them have scheduling conflicts.
        IRider[] riders = new IRider[riderIds.length];
        for (int i = 0; i < riderIds.length; i++) {
            IRider r = riderList.findById(riderIds[i]);
            if (r == null || hasConflict(r, pickupTime, dropoffTime)) {
                return false;
            }
            riders[i] = r;
        }

        // create the Shared Ride
        int rideId = nextRideId++;
        SharedRide sharedRide = new SharedRide(rideId, pickupLocation, dropoffLocation, pickupTime, dropoffTime, driver);
        for (IRider r : riders) { // Adds riders to the shared ride.
            sharedRide.addParticipant(r);
        }
        rideList.addRide(sharedRide);

        // Updates the ride history.
        driver.getRideHistory().insert(sharedRide);
        for (IRider r : riders) {
            r.getRideHistory().insert(sharedRide);
        }
        return true;
    }

    
    // Removes a rider and performs cascade deletion of associated rides
    @Override
    public boolean removeRider(int riderId) {
        IRider rider = riderList.findById(riderId);
        if (rider == null) {
            return false;
        }

        // Deletes all rides associated with this rider
        LinkedList<IRide> allRides = rideList.getAllAlphabetically();
        if (allRides != null && !allRides.empty()) {
            allRides.findFirst();
            while (true) {
                IRide ride = allRides.retrieve();
                if (ride != null) {
                    if (ride instanceof IPrivateRide) {
                        IPrivateRide pr = (IPrivateRide) ride;
                        if (pr.getRider() != null && pr.getRider().getId() == riderId) {
                            deleteRide(pr); 
                        }
                    } else if (ride instanceof ISharedRide) {
                        ISharedRide sr = (ISharedRide) ride;
                        if (sr.hasRider(riderId)) {
                            sr.removeParticipantById(riderId);
                            removeFromHistory(rider, sr.getRideId()); // cleanup rider's history 
                            
                            if (sr.isEmpty()) {
                                deleteRide(sr); 
                            }
                        }
                    }
                }
                if (allRides.last()) break;
                allRides.findNext();
            }
        }

        return riderList.removeById(riderId);
    }

    @Override
    public boolean removeDriver(int driverId) {
        IDriver driver = driverList.findById(driverId);
        if (driver == null) {
            return false;
        }

        // Deletes all rides assigned to this driver
        LinkedList<IRide> allRides = rideList.getAllAlphabetically();
        if (allRides != null && !allRides.empty()) {
            allRides.findFirst();
            while (true) {
                IRide ride = allRides.retrieve();
                if (ride != null && ride.getDriver() != null && ride.getDriver().getId() == driverId) {
                    deleteRide(ride); // Deletes the ride and updates the ride histories of the driver and riders.
                }
                if (allRides.last()) break;
                allRides.findNext();
            }
        }
        // Removes the driver itself from the list of drivers.
        return driverList.removeById(driverId);
    }

    
    
    
    @Override
    public boolean addRider(IRider rider) {
        if (rider == null) { return false; }
    if (riderList.findById(rider.getId()) != null) { return false; }

    return riderList.add(rider);
    }

    @Override
    public boolean addDriver(IDriver driver) {
        if (driver == null) { return false; }
    // check whether the driver exist & check plates (no two drivers may share the same plate)
    if (driverList.findById(driver.getId()) != null || driverList.findByVehiclePlate(driver.getVehiclePlate()) != null) {
        return false;
    }
    return driverList.add(driver);
    }

    @Override
    public IRider searchRiderById(int riderId) {
        return riderList.findById(riderId);
    }

    @Override
    public IRider searchRiderByEmail(String email) {
        return riderList.findByEmail(email);
    }

    @Override
    public LinkedList<IRider> searchRidersByName(String fullName) {
        return riderList.findByName(fullName);
    }

    @Override
    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        return riderList.findByHomeCity(homeCity);
    }

    @Override
    public LinkedList<IRider> getAllRiders() {
        return riderList.getAll();
    }

    @Override
    public IDriver searchDriverById(int driverId) {
        return driverList.findById(driverId);
    }

    @Override
    public IDriver searchDriverByVehiclePlate(String vehiclePlate) {
        return driverList.findByVehiclePlate(vehiclePlate);
    }

    @Override
    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
        return driverList.findByVehicleType(vehicleType);
    }

    @Override
    public LinkedList<IDriver> getAllDrivers() {
        return driverList.getAll();
    }

    @Override
    //Searches for all rides whose pickup location matches the given location (may return multiple if pickup locations repeat).
    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        return rideList.findByPickupLocation(pickupLocation);
    }

    @Override
    //Searches for all rides that involve a rider with the given name.
    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        return rideList.findByRiderName(riderName);
    }

    @Override
    //Returns all riders registered on a shared ride identified by its pickup location.
    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation) {
    LinkedList<IRider> result = new LinkedList<IRider>();
    LinkedList<IRide> matchingRides = rideList.findByPickupLocation(pickupLocation); // All matching Rides (Private & Shared )

   
    if (matchingRides == null || matchingRides.empty()) {
        return result;
    }
    // we search for the Shared Rides only
    matchingRides.findFirst();
    while (!matchingRides.last()) {
        IRide ride = matchingRides.retrieve();
        
        if (ride instanceof ISharedRide) {
            // we found a shared ride then we inser all its riders
            ISharedRide sharedRide = (ISharedRide) ride;
            LinkedList<IRider> participants = sharedRide.getParticipants();
            
            if (participants != null && !participants.empty()) {
                participants.findFirst();
                while (!participants.last()) {
                    result.insert(participants.retrieve()); 
                    participants.findNext();
                }
                result.insert(participants.retrieve()); // insert the last
            }
            
        }
        
        matchingRides.findNext();
    }

    // check the last element
    IRide lastRide = matchingRides.retrieve();
    if (lastRide instanceof ISharedRide) {
        ISharedRide sharedRide = (ISharedRide) lastRide;
        LinkedList<IRider> participants = sharedRide.getParticipants();
        
        if (participants != null && !participants.empty()) {
            participants.findFirst();
            while (!participants.last()) {
                result.insert(participants.retrieve());
                participants.findNext();
            }
            result.insert(participants.retrieve()); 
        }
    }

    return result;
    }
    
    @Override
    public LinkedList<IRide> getAllRidesAlphabetically() {
        return rideList.getAllAlphabetically();
    }
    
    // ====================================================================
    //    Helper private methods ( only related to this class <private> ) 
    // ====================================================================

    /**
     * Helper method that completely deletes a ride from the system.
     * It performs full cleanup by removing the ride from the global rideList 
     * and cascading the removal into the personal rideHistory of all involved parties 
     * (the assigned driver, and either the single private rider or all shared ride participants).
     * This ensures data consistency and prevents false scheduling conflicts in future bookings.
     */
    private void deleteRide(IRide ride) {
        if (ride == null) return;

       
        rideList.removeRideById(ride.getRideId());

        
        if (ride.getDriver() != null) {
            removeFromHistory(ride.getDriver(), ride.getRideId());
        }

       
        if (ride instanceof IPrivateRide) {
            IRider r = ((IPrivateRide) ride).getRider();
            if (r != null) {
                removeFromHistory(r, ride.getRideId());
            }
        } else if (ride instanceof ISharedRide) {
            LinkedList<IRider> parts = ((ISharedRide) ride).getParticipants();
            if (parts != null && !parts.empty()) {
                parts.findFirst();
                while (true) {
                    removeFromHistory(parts.retrieve(), ride.getRideId());
                    if (parts.last()) break;
                    parts.findNext();
                }
            }
        }
    }
    
    /**
     * Helper method that removes a specific ride by its ID from a person's ride history.
     * Iterates through the person's rideHistory LinkedList, locates the matching ride, 
     * and removes it to free up their schedule and prevent false schedule overlaps.
     */
    private void removeFromHistory(IPerson person, int rideId) {
        if (person == null) return;
        LinkedList<IRide> history = person.getRideHistory();
        if (history == null || history.empty()) return;

        history.findFirst();
        while (true) {
            IRide r = history.retrieve();
            if (r != null && r.getRideId() == rideId) {
                history.remove();
                return;
            }
            if (history.last()) return;
            history.findNext();
        }
    }
    

    // Checks for scheduling conflicts ( using helper methods )
    
    // helper method
    private boolean isOverlapping(IDateTime p1, IDateTime d1, IDateTime p2, IDateTime d2) {
        return p1.compareTo(d2) < 0 && p2.compareTo(d1) < 0;
    }

    // helper method
    private boolean hasConflict(IPerson person, IDateTime newPickup, IDateTime newDropoff) {
        LinkedList<IRide> history = person.getRideHistory();
        if (history == null || history.empty()) {
            return false;
        }

        history.findFirst();
        while (!history.last()) {
            IRide existing = history.retrieve();
            if (existing != null && isOverlapping(newPickup, newDropoff, existing.getPickupTime(), existing.getDropoffTime())) {
                return true;
            }
            history.findNext();
        }

        IRide lastRide = history.retrieve();
        return lastRide != null && isOverlapping(newPickup, newDropoff, lastRide.getPickupTime(), lastRide.getDropoffTime());
    }
    
    // Helper method to convert the MM/DD/YYYY HH:MM string into a DateTime object.
     private IDateTime parseDateTime(String dtStr) {
        
        String[] spaceSplit = dtStr.split(" ");
        String[] dateParts = spaceSplit[0].split("/");
        String[] timeParts = spaceSplit[1].split(":");

        int month = Integer.parseInt(dateParts[0].trim());
        int day = Integer.parseInt(dateParts[1].trim());
        int year = Integer.parseInt(dateParts[2].trim());
        int hour = Integer.parseInt(timeParts[0].trim());
        int minute = Integer.parseInt(timeParts[1].trim());

        return new DateTime(year, month, day, hour, minute);
    }

}



    
    

