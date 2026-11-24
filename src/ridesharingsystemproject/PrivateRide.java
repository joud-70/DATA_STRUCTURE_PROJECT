package ridesharingsystemproject;

public class PrivateRide extends Ride implements IPrivateRide {

    private final IRider rider;

    public PrivateRide(int rideId, String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, IRider rider, IDriver driver) {
        super(rideId, pickupLocation, dropoffLocation, timeSlot, driver);
        this.rider = rider;
    }

    @Override
    public IRider getRider() {
        return rider;
    }

    @Override
    public boolean hasRider(int riderId) {
        return rider != null && rider.getId() == riderId;
    }

    @Override
    public String toString() {
        return "Private Ride | " + super.toString()+ ", Rider: " + (rider == null ? "none" : rider.getName());
    }
}
