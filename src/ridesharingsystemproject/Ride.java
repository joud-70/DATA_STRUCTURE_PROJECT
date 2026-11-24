package ridesharingsystemproject;

public abstract class Ride implements IRide {

    private final int rideId;
    private final String pickupLocation;
    private final String dropoffLocation;
    private final ITimeSlot timeSlot;
    private final IDriver driver;

    public Ride(int rideId, String pickupLocation, String dropoffLocation,ITimeSlot timeSlot, IDriver driver) {
        this.rideId = rideId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.timeSlot = timeSlot;
        this.driver = driver;
    }

    @Override
    public int getRideId() {
        return rideId;
    }

    @Override
    public String getPickupLocation() {
        return pickupLocation;
    }

    @Override
    public String getDropoffLocation() {
        return dropoffLocation;
    }

    @Override
    public ITimeSlot getTimeSlot() {
        return timeSlot;
    }

    @Override
    public IDriver getDriver() {
        return driver;
    }

    // Implemented by PrivateRide and SharedRide
    @Override
    public abstract boolean hasRider(int riderId);

    // Compares rides by ride ID
    @Override
    public int compareTo(IRide other) {
        return Integer.compare(this.rideId, other.getRideId());
    }

    @Override
    public String toString() {
        return "Ride ID: " + rideId+ ", Pickup: " + pickupLocation+ ", Dropoff: " + dropoffLocation+ ", Time: " + timeSlot+ ", Driver: " + (driver == null ? "none" : driver.getName());
    }
}
