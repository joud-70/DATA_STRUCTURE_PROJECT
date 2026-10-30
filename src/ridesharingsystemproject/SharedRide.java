package ridesharingsystemproject;

public class SharedRide extends Ride implements ISharedRide {

    private LinkedList<IRider> participants;

    public SharedRide(int rideId, String pickupLocation, String dropoffLocation, 
                  IDateTime pickupTime, IDateTime dropoffTime, IDriver driver) {
    super(rideId, pickupLocation, dropoffLocation, pickupTime, dropoffTime, driver);
        this.participants = new LinkedList<IRider>();
    }

    @Override // Checks whether a rider participates in this ride.
    public boolean hasRider(int riderId) {
        if (participants.empty()) {
            return false;
        }
        participants.findFirst();
        while (!participants.last()) {
            if (participants.retrieve().getId() == riderId) {
                return true;
            }
            participants.findNext();
        }
        return participants.retrieve() != null && participants.retrieve().getId() == riderId; // To check the last element
    }

    @Override
    public LinkedList<IRider> getParticipants() {
        return this.participants;
    }

    @Override
    public boolean addParticipant(IRider rider) {
        if (rider == null || hasRider(rider.getId())) { // To check whether it's null or already exist
            return false;
        }

        participants.insert(rider);
        return true;
    }

    @Override
    public boolean removeParticipantById(int riderId) {
        if (participants.empty()) {
            return false;
        }

        participants.findFirst();
        while (!participants.last()) {
            if (participants.retrieve().getId() == riderId) {
                participants.remove();
                return true;
            }
            participants.findNext();
        }
        if (participants.retrieve() != null && participants.retrieve().getId() == riderId) { // To check the last element
            participants.remove();
            return true;
        }

        return false;

    }

    @Override
    public boolean isEmpty() {
        return participants.empty();
    }

    public String toString() {
        String s = "Shared Ride | " + super.toString()
                + ", Driver: " + getDriver().getName()
                + ", Riders: ";
        if (participants.empty()) {
            return s + "none";
        } else {
            participants.findFirst();
            while (!participants.last()) {
                s += participants.retrieve().getName() + ", ";
                participants.findNext();
            }
            s += participants.retrieve().getName();
            return s;
        }
    }
}
