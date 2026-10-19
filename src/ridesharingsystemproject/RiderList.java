
package ridesharingsystemproject;


public class RiderList implements IRiderList {
   
    protected LinkedList<IRider> Riders = new LinkedList<IRider>();
    private int size=0;
    
    
    
    
    
    //   =====Add Method(1)===
    public boolean add(IRider rider) {

    if (rider == null)
        return false;
   //case1: empty list
    if (Riders.empty()) {
        Riders.insert(rider);
        size++;
        return true;
    }

    Riders.findFirst();

    while (true) {

        int cmp = rider.compareTo(Riders.retrieve());
        //case2: ID already exist
        if (cmp == 0)
            return false;

        // Case 3: Insert the new rider in its correct sorted position (before)
        if (cmp < 0) {
            IRider temp = Riders.retrieve();
            Riders.update(rider);
            Riders.insert(temp);
            size++;
            return true;
        }

        if (Riders.last())
            break;

        Riders.findNext();
    }// close while

    //case4 : the new ID is largest
    Riders.insert(rider);
    size++;
    return true;
}
    
   
    
    
    //   =====findById Method(2)===
    
    public IRider findById(int riderId){
        if(Riders.empty()==false){
            
            Riders.findFirst();
            
        while(true){
           IRider temp=Riders.retrieve();
            
            if(temp.getId()==riderId)
                return temp;
            
            // Stop searching if the current ID is larger
            if (temp.getId() > riderId)
                return null;
            
            if(Riders.last())
                break;
            
            Riders.findNext();
            
        }// close while 
          
        } 
        
        return null;
    }//end findById
    
    
    
    
    
//   =====findByName Method(3)===
    public LinkedList<IRider> findByName(String fullName){
        LinkedList<IRider> list = new LinkedList<IRider>();

        if(Riders.empty()==false && fullName!=null){

            Riders.findFirst();

            while(true){
             IRider temp=Riders.retrieve();

                // exact full-name match, add it to the result list
                if(temp.getName().equals(fullName))
                    list.insert(temp);

                if(Riders.last())
                    break;

                Riders.findNext();

            }// close while
        }

        return list;
    } // end findByName
    
    
    
    
    //   =====findByEmail Method(4)===
    public IRider findByEmail(String email) {

    if (Riders.empty() == false  && email!=null) {

        Riders.findFirst();

        while (true) {
           IRider temp = Riders.retrieve();

            if (temp.getEmail().equalsIgnoreCase(email))
                return temp;

            if (Riders.last())
                break;

            Riders.findNext();

        }// close while
    }

    return null;
}// end findByEmail
    
      //   =====findByHomeCity Method(5)===
    public LinkedList<IRider> findByHomeCity(String homeCity){
        LinkedList<IRider> list = new LinkedList<IRider>();

        if(Riders.empty()==false && homeCity!=null){

            Riders.findFirst();

            while(true){
              IRider temp=Riders.retrieve();

                // add every rider from this city to the result list
                if(temp.getHomeCity().equals(homeCity))
                    list.insert(temp);

                if(Riders.last())
                    break;

                Riders.findNext();

            }// close while
        }

        return list;
    }// end findByHomeCity
    
    
    
    
    //   =====getAll Method(6)===
    public LinkedList<IRider> getAll(){ // return the actual list!!!
        return Riders;
    }

    
     //   =====removeById Method(7)===
    public boolean removeById(int riderId){

        if(Riders.empty()==false){

            Riders.findFirst();

            while(true){
              IRider temp=Riders.retrieve();

                if(temp.getId()==riderId){
                    Riders.remove();
                    size--;
                    return true;
                }

                // Stop searching if the current ID is larger (list is sorted)
                if(temp.getId() > riderId)
                    return false;

                if(Riders.last())
                    break;

                Riders.findNext();

            }// close while
        }

        return false;
    }// end removeById
    
    
//   =====removeByEmail Method(8)===
    public boolean removeByEmail(String email){

        if(Riders.empty()==false && email!=null){

            Riders.findFirst();

            while(true){
              IRider temp=Riders.retrieve();

                if(temp.getEmail().equalsIgnoreCase(email)){
                    Riders.remove();
                    size--;
                    return true;
                }

                if(Riders.last())
                    break;

                Riders.findNext();

            }// close while
        }

        return false;
    }// end removeByEmail
    
    
//   =====removeByName Method(9)===
    public int removeByName(String fullName){
        int count=0;

        if(Riders.empty()==false && fullName!=null){

            Riders.findFirst();
            boolean finished=false;

            while(finished==false){
                // case1:  found
                if(Riders.retrieve().getName().equals(fullName)){

                    boolean wasLast=Riders.last();// To avoid losing the value after removing

                    // remove() already moves current to the next rider,
                    // so we must NOT call findNext() here (it would skip one)
                    Riders.remove();
                    count++;
                    size--;

                    // stop if that was the last rider or the list became empty
                    if(wasLast )
                        finished=true;
                }
                
                // case2: not found
                else{
                    if(Riders.last())
                        finished=true;
                    else
                        Riders.findNext();
                }

            }// close while
        }

        return count;
    }// end removeByName
    
    
    
    //   =====removeByHomeCity Method(10)===
    public int removeByHomeCity(String homeCity){
        int count=0;

        if(Riders.empty()==false && homeCity!=null){

            Riders.findFirst();
            boolean finished=false;

            while(finished==false){

                if(Riders.retrieve().getHomeCity().equals(homeCity)){

                    boolean wasLast=Riders.last();

                    // remove() already moves current to the next rider,
                    // so we must NOT call findNext() here (it would skip one)
                    Riders.remove();
                    count++;
                    size--;

                    // stop if that was the last rider or the list became empty
                    if(wasLast)
                        finished=true;
                }
                else{
                    if(Riders.last())
                        finished=true;
                    else
                        Riders.findNext();
                }

            }// close while
        }

        return count;
    }// end removeByHomeCity
 
    
    
    
    //   =====size Method(11)===
     public int size(){
        return size;
    }
    
     
     
     
     
     
     
     
}
