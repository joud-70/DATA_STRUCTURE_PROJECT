package ridesharingsystemproject;


public class DriverList implements IDriverList {
   
    protected LinkedList<IDriver> Drivers = new LinkedList<IDriver>();
    private int size=0;
    
    
    
    
    
    //   =====Add Method(1)===
    public boolean add(IDriver driver) {

    if (driver == null)
        return false;
   //case1: empty list
    if (Drivers.empty()) {
        Drivers.insert(driver);
        size++;
        return true;
    }

    Drivers.findFirst();

    while (true) {

        int cmp = driver.compareTo(Drivers.retrieve());
        //case2: ID already exist
        if (cmp == 0)
            return false;

        // Case 3: Insert the new driver in its correct sorted position (before)
        if (cmp < 0) {
            IDriver temp = Drivers.retrieve();
            Drivers.update(driver);
            Drivers.insert(temp);
            size++;
            return true;
        }

        if (Drivers.last())
            break;

        Drivers.findNext();
    }// close while

    //case4 : the new ID is largest
    Drivers.insert(driver);
    size++;
    return true;
}
    
   
    
    
    //   =====findById Method(2)===
    
    public IDriver findById(int driverId){
        if(Drivers.empty()==false){
            
            Drivers.findFirst();
            
        while(true){
           IDriver temp=Drivers.retrieve();
            
            if(temp.getId()==driverId)
                return temp;
            
            // Stop searching if the current ID is larger
            if (temp.getId() > driverId)
                return null;
            
            if(Drivers.last())
                break;
            
            Drivers.findNext();
            
        }// close while 
          
        } 
        
        return null;
    }//end findById
    
    
    
    
    
//   =====findByName Method(3)===
    public LinkedList<IDriver> findByName(String fullName){
        LinkedList<IDriver> list = new LinkedList<IDriver>();

        if(Drivers.empty()==false && fullName!=null){

            Drivers.findFirst();

            while(true){
             IDriver temp=Drivers.retrieve();

                // exact full-name match, add it to the result list
                if(temp.getName().equals(fullName))
                    list.insert(temp);

                if(Drivers.last())
                    break;

                Drivers.findNext();

            }// close while
        }

        return list;
    } // end findByName
    
    
    
    
    //   =====findByVehiclePlate Method(4)===
    public IDriver findByVehiclePlate(String vehiclePlate) {

    if (Drivers.empty() == false  && vehiclePlate!=null) {

        Drivers.findFirst();

        while (true) {
           IDriver temp = Drivers.retrieve();

            if (temp.getVehiclePlate().equals(vehiclePlate))
                return temp;

            if (Drivers.last())
                break;

            Drivers.findNext();

        }// close while
    }

    return null;
}// end findByVehiclePlate
    
    
    
    
      //   =====findByVehicleType Method(5)===
    public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType){
        LinkedList<IDriver> list = new LinkedList<IDriver>();

        if(Drivers.empty()==false && vehicleType!=null){

            Drivers.findFirst();

            while(true){
              IDriver temp=Drivers.retrieve();

                // add every driver with this vehicle type to the result list
                if(temp.getVehicleType()==vehicleType)
                    list.insert(temp);

                if(Drivers.last())
                    break;

                Drivers.findNext();

            }// close while
        }

        return list;
    }// end findByVehicleType
    
    
    
    
    //   =====getAll Method(6)===
    public LinkedList<IDriver> getAll(){ // return the actual list!!!
        return Drivers;
    }

    
    
    
     //   =====removeById Method(7)===
    public boolean removeById(int driverId){

        if(Drivers.empty()==false){

            Drivers.findFirst();

            while(true){
              IDriver temp=Drivers.retrieve();

                if(temp.getId()==driverId){
                    Drivers.remove();
                    size--;
                    return true;
                }

                // Stop searching if the current ID is larger (list is sorted)
                if(temp.getId() > driverId)
                    return false;

                if(Drivers.last())
                    break;

                Drivers.findNext();

            }// close while
        }

        return false;
    }// end removeById
    
    
    
    
//   =====removeByVehiclePlate Method(8)===
    public boolean removeByVehiclePlate(String vehiclePlate){

        if(Drivers.empty()==false && vehiclePlate!=null){

            Drivers.findFirst();

            while(true){
              IDriver temp=Drivers.retrieve();

                if(temp.getVehiclePlate().equals(vehiclePlate)){
                    Drivers.remove();
                    size--;
                    return true;
                }

                if(Drivers.last())
                    break;

                Drivers.findNext();

            }// close while
        }

        return false;
    }// end removeByVehiclePlate
    
    
    
    
//   =====removeByName Method(9)===
    public int removeByName(String fullName){
        int count=0;

        if(Drivers.empty()==false && fullName!=null){

            Drivers.findFirst();
            boolean finished=false;

            while(finished==false){
                // case1:  found
                if(Drivers.retrieve().getName().equals(fullName)){

                    boolean wasLast=Drivers.last();// To avoid losing the value after removing

                    // remove() already moves current to the next driver,
                    // so we must NOT call findNext() here (it would skip one)
                    Drivers.remove();
                    count++;
                    size--;

                    // stop if that was the last driver or the list became empty
                    if(wasLast)
                        finished=true;
                }
                
                // case2: not found
                else{
                    if(Drivers.last())
                        finished=true;
                    else
                        Drivers.findNext();
                }

            }// close while
        }

        return count;
    }// end removeByName
    
    
    
    
    //   =====removeByVehicleType Method(10)===
    public int removeByVehicleType(VehicleType vehicleType){
        int count=0;

        if(Drivers.empty()==false && vehicleType!=null){

            Drivers.findFirst();
            boolean finished=false;

            while(finished==false){

                if(Drivers.retrieve().getVehicleType()==vehicleType){

                    boolean wasLast=Drivers.last();

                    // remove() already moves current to the next driver,
                    // so we must NOT call findNext() here (it would skip one)
                    Drivers.remove();
                    count++;
                    size--;

                    // stop if that was the last driver or the list became empty
                    if(wasLast)
                        finished=true;
                }
                else{
                    if(Drivers.last())
                        finished=true;
                    else
                        Drivers.findNext();
                }

            }// close while
        }

        return count;
    }// end removeByVehicleType
 
    
    
    
    //   =====size Method(11)===
     public int size(){
        return size;
    }
    
}