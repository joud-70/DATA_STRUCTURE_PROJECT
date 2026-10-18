
package ridesharingsystemproject;


public class RiderList  {
   
    protected LinkedList<IRider> Riders;
    
    public boolean add(IRider rider){
      if(rider==null)
          return false;
      
      //chesk if the rider already exist//
      boolean found=false;
      if(Riders.empty()==false){
          Riders.findFirst();
          
          while(true){
              if(rider.getId()==Riders.retrieve().getId()){
                  found=true;
                  break;
              }
              if(Riders.last())
                  break;
              else
                  Riders.findNext();
          } //close while
          
      }//close if
      // end of checking//
      
      if(found)
          return false;
      // inserting //
      if(Riders.empty())
          Riders.insert(rider);
      
      
      return true;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public LinkedList<IRider> getAll(){
        return Riders;
    }

    
}
