
package ridesharingsystemproject;
import java.util.*;

 abstract class  Person implements IPerson {
    protected int ID;
    protected String name ;
    protected String PhoneNumber;
    protected List<IRide> rideHistory;
    Scanner scanner = new Scanner (System.in);

    public Person(int ID, String name, String PhoneNumber, List<IRide> rideHistory) {
        this.ID = ID;
        this.name = name;
        setPhoneNumber(PhoneNumber);
        this.rideHistory = rideHistory;
    }
    
    
    
    
    public int getId(){
    
    return ID;
    }

    //return the full name of this person.
    public String getName(){
    return name;
    }

    //Sets the full name of this person.
    public void setName(String name){
    this.name=name;
    }

    //return the phone number of this person.
    public String getPhoneNumber(){
    return PhoneNumber;
    }

    /**
     * Sets the phone number of this person.
     * The phone number must be exactly 10 digits (numeric characters only,
     * e.g., "0551234567"). Implementations must validate the format and
     * throw IllegalArgumentException if it does not match.
     */
    public void setPhoneNumber(String phoneNumber){
        boolean flag = false; 
        while(flag == false){
        try{
    if(phoneNumber==null || phoneNumber.length()!=10 )
        throw new IllegalArgumentException ("The phone number must be 10 digits long\n");
        
    // Check if the phone number contains only digits
        char x ;
        
        for(int i=0 ; i <phoneNumber.length(); i++){
            x=phoneNumber.charAt(i);
            if(x<'0' || x > '9')
                 throw new IllegalArgumentException ("the phone number must contains digits only\n");
        }
  
               

    this.PhoneNumber=phoneNumber;
    flag = true;
    
        }
        catch(IllegalArgumentException e){
        System.out.println("Please enter the phone number again");
        phoneNumber=scanner.next();
        }
    }
        
    }

    //return the list of rides associated with this person (as a rider, or as an assigned driver).
    public LinkedList<IRide> getRideHistory(){
    
    return null ;
    }

    // Returns a formatted string describing this person.
    @Override
   public  String toString(){
        
    return "";
    }


}
