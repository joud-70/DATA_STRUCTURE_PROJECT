
package ridesharingsystemproject;


public class Rider extends Person implements IRider {
    protected String email;
    protected String homeCity;

    public Rider(String email, String homeCity, int ID, String name, String PhoneNumber) {
        super(ID, name, PhoneNumber);
        this.email = email;
        this.homeCity = homeCity;
    }
    
    
    
    
    
    
    public String getEmail(){
        return email;
    } 
   
    public void setEmail(String email){
        this.email=email;
    }
    public String getHomeCity(){
        return homeCity;
    }
    
    public void setHomeCity(String homeCity){
        this.homeCity=homeCity;
    }
    public int compareTo(IRider other){
        // or return this.Id - other.getId();
        if(this.ID > other.getId())
            return 1;
        else if(this.ID < other.getId())
            return -1;
        else 
            return 0;
    }

   @Override
public String toString() {
    return "Rider{" + super.toString() + ", email=" + email + ", homeCity=" + homeCity + "}";
}
   
    
    
}
