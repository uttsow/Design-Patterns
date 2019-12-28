package loadbalancer.util;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import loadbalancer.debug.Debug;
import loadbalancer.observer.ObserverI;


public class Filtering{

    private String type;
    private String status;

    /**
    Default Constructor
    */
    public Filtering(){}

    /**
    Paramter based constructor
    */
    public Filtering(String typeIn){
        this.type = typeIn;
        this.status = "NO UPDATE";
        // this.observers = new HashMap<>();
        // Debug.print("{FILTER} Filter created");

    }

    /**
    Set status of filter
    @param filter status
    */
    public void setStatus(String statusIn){
        status = statusIn;
    }

    /**
    get status of filter
    @return filter status
    */
    public String getStatus(){
        return status;
    }

    /**
    Set type of filter depending on the observer
    @param type of filter
    */
    public void setType(String typeIn){
        type = typeIn;
    }

    /**
    get filter type
    @return filter type
    */
    public String getFilterType(){
        return type;
    }

    /**
    check to see if filter type matches the input
    @param type
    @return true or false
    */
    public boolean check(String typeIn){
        if(type.equals(typeIn)){
            return true;
        }
        return false;
    }

}
