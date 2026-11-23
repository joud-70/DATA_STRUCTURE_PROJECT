
package ridesharingsystemproject;


public class Rider extends Person implements IRider {
    private String email;
    private String homeCity;

    
    public Rider(int ID, String name, String email, String PhoneNumber, String homeCity) {
        super(ID, name, PhoneNumber);
        this.email = email;
        this.homeCity = homeCity;
    }
    
    public String getEmail(){
        return email;
    } 
   

    public String getHomeCity(){
        return homeCity;
    }
    

    // compare riders by ID
    public int compareTo(IRider other) {
        return getId() - other.getId();
    }

    public String toString() {
        return super.toString() + ", Email: " + email + ", Home city: " + homeCity;
    }
   
    
    
}
