package ridesharingsystemproject;

public class SharedRide extends Ride implements ISharedRide {

    // Participants stored in a BST keyed by rider ID
    private final BST<Integer, IRider> participants = new BST<Integer, IRider>();

    public SharedRide(int rideId, String pickupLocation, String dropoffLocation,ITimeSlot timeSlot, IDriver driver) {
    super(rideId, pickupLocation, dropoffLocation, timeSlot, driver);
    }

    // Returns the participants sorted by rider ID
    @Override
    public LinkedList<IRider> getParticipants() {
        return participants.inOrder();
    }

    @Override
    public boolean addParticipant(IRider rider) {
        if (rider == null) {
            return false;
        }
        return participants.insert(rider.getId(), rider);
    }

    @Override
    public boolean removeParticipantById(int riderId) {
        return participants.removeKey(riderId);
    }

    @Override
    public boolean isEmpty() {
        return participants.empty();
    }

    @Override
    public boolean hasRider(int riderId) {
        return participants.findKey(riderId);
    }

    @Override
    public String toString() {
        String s = "Shared Ride | " + super.toString() + ", Riders: "; LinkedList<IRider> list = participants.inOrder();
        if (list.empty()) {
            return s + "none";
        }
        list.findFirst();
        while (!list.last()) {
            s += list.retrieve().getName() + ", ";
            list.findNext();
        }
        return s + list.retrieve().getName();
    }
}
