
package ridesharingsystemproject;


 abstract class  Person implements IPerson {
    protected final int ID;
    protected String name ;
    protected String PhoneNumber;
    protected LinkedList<IRide> rideHistory = new LinkedList<IRide>();

    public Person(int ID, String name, String PhoneNumber) {
        this.ID = ID;
        this.name = name;
        setPhoneNumber(PhoneNumber);
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


     public void setPhoneNumber(String phoneNumber) {
       if (phoneNumber == null || phoneNumber.length() != 10) {
           throw new IllegalArgumentException("The phone number must be exactly 10 digits");
       }
       for (int i = 0; i < phoneNumber.length(); i++) {
           char c = phoneNumber.charAt(i);
           if (c < '0' || c > '9') {
               throw new IllegalArgumentException("The phone number must contain digits only");
           }
       }
       this.PhoneNumber = phoneNumber;
   }
     
     
     
    //return the list of rides associated with this person (as a rider, or as an assigned driver).
    public LinkedList<IRide> getRideHistory(){
    
    return rideHistory ;
    }

    // Returns a formatted string describing this person.

    @Override
    public String toString() {
        return  "ID=" + ID + ", name=" + name + ", PhoneNumber=" + PhoneNumber ;
    }
   

}
