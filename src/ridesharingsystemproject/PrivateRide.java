package ridesharingsystemproject;

public class PrivateRide extends Ride implements IPrivateRide {

    private IRider rider;

    // Constructor
    public PrivateRide(int rideId,
                       String pickupLocation,
                       String dropoffLocation,
                       IDateTime pickupTime,
                       IDateTime dropoffTime,
                       IDriver driver,
                       IRider rider) {

        super(rideId, pickupLocation, dropoffLocation,
              pickupTime, dropoffTime, driver);

        this.rider = rider;
    }

    @Override
    public IRider getRider() {
        return rider;
    }

    @Override
    public void setRider(IRider rider) {
        this.rider = rider;
    }

    @Override
    public boolean hasRider(int riderId) {
        return rider != null && rider.getId() == riderId;
    }
}
