
package ridesharingsystemproject;


public class Driver extends Person implements IDriver {
    protected String vehiclePlate;
    protected VehicleType vehicleType;

    public Driver(String vehiclePlate, VehicleType vehicleType, int ID, String name, String PhoneNumber) {
        super(ID, name, PhoneNumber);
        setVehicleType(vehicleType);
        setVehiclePlate(vehiclePlate);
    }
    
    public String getVehiclePlate(){
        return vehiclePlate;
    }
    
    
    
    //big o (1)
      public void setVehiclePlate(String vehiclePlate){
          
          if (vehiclePlate==null || vehiclePlate.length()!=7)
              throw new IllegalArgumentException("The plate number must consist of 3 uppercase letters followed by 4 numbers");
          
          for (int i=0; i<3; i++){
              char ch = vehiclePlate.charAt(i);
               if (ch < 'A' || ch > 'Z')
                  throw new IllegalArgumentException("The first 3 characters of the plate number must be uppercase letters");      
          }
          
          for (int j=3 ; j<7 ; j++){
              if (vehiclePlate.charAt(j) < '0' || vehiclePlate.charAt(j) > '9')
                  throw new IllegalArgumentException("The last 4 digits of the plate number must be numbers");    
          }
         
          this.vehiclePlate=vehiclePlate;
      }


 
    public   VehicleType getVehicleType() {
        return vehicleType;
    }
    
    
    public void setVehicleType(VehicleType vehicleType){
        this.vehicleType = vehicleType;
    }
    
    public int compareTo(IDriver other){
        return this.getId()-other.getId();
    }

    
}
