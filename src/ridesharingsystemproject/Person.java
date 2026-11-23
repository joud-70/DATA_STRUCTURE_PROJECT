
package ridesharingsystemproject;


public abstract class Person implements IPerson {
    private int ID;
    private String name;
    private String PhoneNumber;
    private IBST<ITimeSlot, IRide> schedule;
    
    
    
    public Person(int ID, String name, String PhoneNumber) {
        this.ID = ID;
        this.name = name;
        setPhoneNumber(PhoneNumber);
        schedule = new BST<ITimeSlot, IRide>();
    }
    
    public int getId(){
    return ID;
    }

    
    //return the full name of this person.
    public String getName(){
    return name;
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
     
     
    public IBST<ITimeSlot, IRide> getSchedule() {
        return schedule;
    }

    public String toString() {
        return "ID: " + ID + ", Name: " + name + ", Phone: " + PhoneNumber;
    }
 

  
   

}

