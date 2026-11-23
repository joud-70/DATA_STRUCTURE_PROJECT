
package ridesharingsystemproject;


public class Driver extends Person implements IDriver {
    private String vehiclePlate;
    private VehicleType vehicleType;

    public Driver(int ID, String name, String PhoneNumber, String vehiclePlate, VehicleType vehicleType) {
        super(ID, name, PhoneNumber);
        if (!validPlate(vehiclePlate))
            throw new IllegalArgumentException("Plate must be 3 uppercase letters followed by 4 digits");
        this.vehiclePlate = vehiclePlate;
        this.vehicleType = vehicleType;
    }
    
    
    
    // plate format: 3 uppercase letters followed by 4 digits (e.g., ABC1234)
    private boolean validPlate(String plate) {
        if (plate == null || plate.length() != 7)
            return false;
        for (int i = 0; i < 3; i++) {
            if (plate.charAt(i) < 'A' || plate.charAt(i) > 'Z')
                return false;
        }
        for (int i = 3; i < 7; i++) {
            if (plate.charAt(i) < '0' || plate.charAt(i) > '9')
                return false;
        }
        return true;
    }
    
    public String getVehiclePlate(){
        return vehiclePlate;
    }
    

 
    public   VehicleType getVehicleType() {
        return vehicleType;
    }
    
    

    
    // compare drivers by ID
    public int compareTo(IDriver other) {
        return getId() - other.getId();
    }

    public String toString() {
        return super.toString() + ", Plate: " + vehiclePlate + ", Vehicle: " + vehicleType;
    }
}
