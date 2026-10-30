package ridesharingsystemproject;

public abstract class Ride implements IRide {

    private final int rideId;
    private String pickupLocation;
    private String dropoffLocation;
    private final IDateTime pickupTime;
    private final IDateTime dropoffTime;
    private IDriver driver;

    // Creates a ride; throws IllegalArgumentException if the times are invalid
    public Ride(int rideId, String pickupLocation,
                String dropoffLocation, IDateTime pickupTime,
                IDateTime dropoffTime, IDriver driver) {

        if (pickupTime == null || dropoffTime == null) {
            throw new IllegalArgumentException("Invalid date/time");
        }

        if (pickupTime.compareTo(dropoffTime) >= 0) {
            throw new IllegalArgumentException(
                "Dropoff time must be after pickup time");
        }

        this.rideId = rideId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
        this.driver = driver;
    }
// Returns the unique ID of this ride
    @Override
    public int getRideId() {
        return rideId;
    }

    @Override
    public String getPickupLocation() {
        return pickupLocation;
    }

    @Override
    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    @Override
    public IDateTime getPickupTime() {
        return pickupTime;
    }

    @Override
    public IDateTime getDropoffTime() {
        return dropoffTime;
    }

    @Override
    public String getDropoffLocation() {
        return dropoffLocation;
    }

    @Override
    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    @Override
    public IDriver getDriver() {
        return driver;
    }

    @Override
    public void setDriver(IDriver driver) {
        this.driver = driver;
    }
// Compares rides alphabetically by pickup location
    @Override
    public int compareTo(IRide other) {
        return this.pickupLocation.compareTo(
            other.getPickupLocation());
    }

    @Override
    public String toString() {
        return "Ride ID: " + rideId
            + ", Pickup: " + pickupLocation
            + ", Dropoff: " + dropoffLocation
            + ", Pickup Time: " + pickupTime.format()
            + ", Dropoff Time: " + dropoffTime.format();
    }

    // Implemented by PrivateRide and SharedRide
    @Override
    public abstract boolean hasRider(int riderId);
}
