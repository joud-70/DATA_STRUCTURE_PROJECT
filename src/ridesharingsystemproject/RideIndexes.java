package ridesharingsystemproject;
// Helper class that manages the three ride indexes (Section 2.3) and keeps
// the schedules of the driver and riders consistent
public class RideIndexes {

    private final IBST<Integer, IRide> ridesById;
    private final IBST<String, IBST<Integer, IRide>> ridesByPickupLocation;
    private final IBST<IDateTime, IBST<Integer, IRide>> ridesByPickupTime;

    public RideIndexes() {
        ridesById = new BST<Integer, IRide>();
        ridesByPickupLocation = new BST<String, IBST<Integer, IRide>>();
        ridesByPickupTime = new BST<IDateTime, IBST<Integer, IRide>>();
    }

    // Adds the ride to the indexes and schedules; false if the ID exists
    // The caller must already have checked that the time slot is free
    public boolean addRide(IRide ride) {
        if (ride == null || !ridesById.insert(ride.getRideId(), ride)) {
            return false;
        }
        addToInner(ridesByPickupLocation, ride.getPickupLocation(), ride);
        addToInner(ridesByPickupTime, ride.getTimeSlot().getStart(), ride);

        ride.getDriver().getSchedule().insert(ride.getTimeSlot(), ride);
        updateRiderSchedules(ride, true);
        return true;
    }

    // Inserts the ride into the inner BST of the key, creating it if needed
    private <K extends Comparable<K>> void addToInner(IBST<K, IBST<Integer, IRide>> index, K key, IRide ride) {
        if (index.findKey(key)) {
            index.retrieve().insert(ride.getRideId(), ride);
        } else {
            IBST<Integer, IRide> inner = new BST<Integer, IRide>();
            inner.insert(ride.getRideId(), ride);
            index.insert(key, inner);
        }
    }

    // Removes the ride from all indexes and schedules; false if not found
    public boolean cancelRide(int rideId) {
        if (!ridesById.findKey(rideId)) {
            return false;
        }
        IRide ride = ridesById.retrieve();

        ridesById.removeKey(rideId);
        removeFromInner(ridesByPickupLocation, ride.getPickupLocation(), rideId);
        removeFromInner(ridesByPickupTime, ride.getTimeSlot().getStart(), rideId);

        ride.getDriver().getSchedule().removeKey(ride.getTimeSlot());
        updateRiderSchedules(ride, false);
        return true;
    }

    // Removes the ride from the inner BST; removes the key if it becomes empty
    private <K extends Comparable<K>> void removeFromInner(IBST<K, IBST<Integer, IRide>> index, K key, int rideId) {
        if (index.findKey(key)) {
            IBST<Integer, IRide> inner = index.retrieve();
            inner.removeKey(rideId);
            if (inner.empty()) {
                index.removeKey(key);
            }
        }
    }

    // Cascade delete: cancels all rides of the driver; returns how many
    public int removeRidesOfDriver(IDriver driver) {
        LinkedList<IRide> rides = driver.getSchedule().inOrder(); // a copy
        int count = 0;
        if (!rides.empty()) {
            rides.findFirst();
            while (true) {
                if (cancelRide(rides.retrieve().getRideId())) count++;
                if (rides.last()) break;
                rides.findNext();
            }
        }
        return count;
    }
    // Returns the ride with this ID, or null
    public IRide searchRideById(int rideId) {
        return ridesById.findKey(rideId) ? ridesById.retrieve() : null;
    }

    // Rides from the pickup location, sorted by ride ID
    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        LinkedList<IRide> result = new LinkedList<IRide>();
        if (ridesByPickupLocation.findKey(pickupLocation)) {
            append(result, ridesByPickupLocation.retrieve().inOrder());
        }
        return result;
    }
    // Rides with from <= pickup time <= to, sorted by time then ride ID
    public LinkedList<IRide> searchRidesByPickupTimeRange(IDateTime from, IDateTime to) {
        LinkedList<IRide> result = new LinkedList<IRide>();
        appendAllInners(result, ridesByPickupTime.rangeSearch(from, to));
        return result;
    }
    // All rides alphabetically by pickup location (ties by ride ID)
    public LinkedList<IRide> getAllRidesAlphabetically() {
        LinkedList<IRide> result = new LinkedList<IRide>();
        appendAllInners(result, ridesByPickupLocation.inOrder());
        return result;
    }
    // Participants of the shared ride; empty if missing or not shared
    public LinkedList<IRider> getSharedRideParticipants(int rideId) {
        IRide ride = searchRideById(rideId);
        if (ride instanceof ISharedRide) {
            return ((ISharedRide) ride).getParticipants();
        }
        return new LinkedList<IRider>();
    }
    // Rides of the given riders (via their schedules), each once, sorted by ID
    // Used by searchRidesByRiderName with the riders found by name
    public LinkedList<IRide> searchRidesOfRiders(LinkedList<IRider> riders) {
        IBST<Integer, IRide> temp = new BST<Integer, IRide>(); // temporary BST
        if (!riders.empty()) {
            riders.findFirst();
            while (true) {
            LinkedList<IRide> rides = riders.retrieve().getSchedule().inOrder();
            if (!rides.empty()) {
            rides.findFirst();
             while (true) {
            temp.insert(rides.retrieve().getRideId(), rides.retrieve());
             if (rides.last()) break;
             rides.findNext();
                 }
                }
            if (riders.last()) break;
            riders.findNext();
            }
        }
        return temp.inOrder();
    }

    // Number of keys of a ride index (outer BST); -1 for other indexes
    public int indexSize(IndexName index) {
        switch (index) {
            case RIDES_BY_ID: return ridesById.size();
            case RIDES_BY_PICKUP_LOCATION: return ridesByPickupLocation.size();
            case RIDES_BY_PICKUP_TIME: return ridesByPickupTime.size();
            default: return -1;
        }
    }
    // Height of a ride index (outer BST); -1 for other indexes
    public int indexHeight(IndexName index) {
        switch (index) {
            case RIDES_BY_ID: return ridesById.height();
            case RIDES_BY_PICKUP_LOCATION: return ridesByPickupLocation.height();
            case RIDES_BY_PICKUP_TIME: return ridesByPickupTime.height();
            default: return -1;
        }
    }
    // Adds (or removes) the ride in the schedule of each of its riders
    private void updateRiderSchedules(IRide ride, boolean add) {
        if (ride instanceof IPrivateRide) {
        changeSchedule(((IPrivateRide) ride).getRider(), ride, add);
        } else if (ride instanceof ISharedRide) {
         LinkedList<IRider> riders = ((ISharedRide) ride).getParticipants();
        if (!riders.empty()) {
        riders.findFirst();
        while (true) {
        changeSchedule(riders.retrieve(), ride, add);
        if (riders.last()) break;
        riders.findNext();
                }
            }
        }
    }
    private void changeSchedule(IRider rider, IRide ride, boolean add) {
     if (add) {
     rider.getSchedule().insert(ride.getTimeSlot(), ride);
        } else {
    rider.getSchedule().removeKey(ride.getTimeSlot());
        }
    }
    // Appends the rides of every inner BST, in order, to result
    private void appendAllInners(LinkedList<IRide> result,LinkedList<IBST<Integer, IRide>> inners) {
        if (inners.empty()) return;
        inners.findFirst();
        while (true) {
            append(result, inners.retrieve().inOrder());
            if (inners.last()) break;
            inners.findNext();
        }
    }
    // Appends all elements of src to the end of dst
    private <T> void append(LinkedList<T> dst, LinkedList<T> src) {
        if (src.empty()) return;
        src.findFirst();
        while (true) {
            dst.insert(src.retrieve());
            if (src.last()) break;
            src.findNext();
        }
    }
}
