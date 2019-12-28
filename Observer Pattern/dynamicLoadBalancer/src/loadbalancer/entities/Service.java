package loadbalancer.entities;

import loadbalancer.debug.Debug;

public class Service{

    //Service URL
    private String URL;

    //Servcie name
    private String name;

    //To say if the service is valid
    private boolean valid;

    /**
    Constructor
    */
    public Service(String nameIn, String urlIn){
        this.name = nameIn;
        this.URL = urlIn;
        this.valid = true;
        // Debug.print("{SERVICE} CREATED NEW SERVICE " + nameIn +", " + urlIn);
    }

    /**
    Getter method for Service name;
    @return service name
    */
    public String getName(){
        return name;
    }

    /**
    Getter method for Service URL
    */
    public String getURL(){
        return URL;
    }

    /**
    This metod sets service value to a different validity
    @param true or false
    */
    public void setValid(boolean validIn){
        this.valid = validIn;
    }

    /**
    Getter method for getting state of the service
    @return true or false
    */
    public boolean isValid(){
        return valid;
    }

}
