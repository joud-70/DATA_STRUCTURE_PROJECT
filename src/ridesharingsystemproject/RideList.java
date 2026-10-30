
package ridesharingsystemproject;

public class RideList implements IRideList {

    
    private class Node {
        public IRide data;
        public Node next;

        public Node(IRide data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    // Constructor
    public RideList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public boolean addRide(IRide ride) {
       if (ride == null) {
            return false;
        }

        Node newNode = new Node(ride);

        // Case 1: The list is empty, or the new ride comes before the head alphabetically.
        if (head == null || ride.getPickupLocation().compareTo(head.data.getPickupLocation()) < 0) {
            newNode.next = head;
            head = newNode;
            size++;
            return true;
        }

        // Case 2: Searching for the appropriate position between the nodes.
        Node current = head;
        while (current.next != null && current.next.data.getPickupLocation().compareTo(ride.getPickupLocation()) <= 0) {
            current = current.next;
        }

        // put the newNode in its appropriate position
        newNode.next = current.next;
        current.next = newNode;
        size++;
        return true;
    }

    @Override
    public boolean removeRideById(int rideId) {
        if (head == null) {
            return false;
        }

        // If the element that we will deleted is the head of the list
        if (head.data.getRideId() == rideId) {
            head = head.next;
            size--;
            return true;
        }

        // search on the rest of nodes
        Node current = head;
        while (current.next != null) {
            if (current.next.data.getRideId() == rideId) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false; // if the element doesn't exist 
    }

    @Override
    // We sorted the rides in advance in addRide so this method goes through the elements of the list
    public LinkedList<IRide> getAllAlphabetically() {
        LinkedList<IRide> list = new LinkedList<IRide>(); 
        Node current = head;
        while (current != null) {
            list.insert(current.data);
            current = current.next;
        }
        return list;
    }

    @Override
    // Returns all rides whose pickup location matches the given location.
    public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
       LinkedList<IRide> results = new LinkedList<IRide>();
        if (pickupLocation == null) {
            return results;
        }

        Node current = head;
        while (current != null) {
            if (current.data.getPickupLocation().equalsIgnoreCase(pickupLocation)) {
                results.insert(current.data);
            }
            current = current.next;
        }
        return results;
    }

    @Override
    //Returns all rides that involve a rider with the given full name
public LinkedList<IRide> findByRiderName(String riderFullName) {
    LinkedList<IRide> result = new LinkedList<IRide>();

    if (riderFullName == null) {
        return result;
    }

   boolean isFound;
    Node current = head;
    while (current != null) {
        IRide ride = current.data;
        isFound = false;

       
        if (ride instanceof IPrivateRide) {
            IPrivateRide privateRide = (IPrivateRide) ride;
            if (privateRide.getRider() != null && 
                privateRide.getRider().getName().equals(riderFullName)) {
                isFound = true;
            }
        } 
        
        else if (ride instanceof ISharedRide) {
            ISharedRide sharedRide = (ISharedRide) ride;
            LinkedList<IRider> participants = sharedRide.getParticipants();

            if (participants != null && !participants.empty()) {
                participants.findFirst();
                // search among the participants 
                while (!participants.last()) {
                    if (participants.retrieve() != null && 
                        participants.retrieve().getName().equals(riderFullName)) {
                        isFound = true;
                        break;
                    }
                    participants.findNext();
                }
                // for the last one
                if (!isFound && participants.retrieve() != null && 
                    participants.retrieve().getName().equals(riderFullName)) {
                    isFound = true;
                }
            }
        }

        // If the rider is already in this ride, we add the ride to the results
        if (isFound) {
            result.insert(ride);
        }

        current = current.next; // go to next ride
    }

    return result;
}

    @Override
    public int size() { return this.size; }
}
