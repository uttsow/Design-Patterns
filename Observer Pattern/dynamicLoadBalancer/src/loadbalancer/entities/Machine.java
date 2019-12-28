package loadbalancer.entities;

import java.util.Map;
import java.util.HashMap;
import loadbalancer.debug.Debug;
import loadbalancer.entities.Service;


public class Machine{
    //Hostname of the machine
    private String hostName;

    //Mapping of machine to services. String is service name, Service is the service object
    private Map<String, Service> servicesMapping;

    /**
    Constructor
    */
    public Machine(String hostNameIn){
        this.hostName = hostNameIn;
        this.servicesMapping = new HashMap<>();
        Debug.print("{MACHINE} Created new Machine " + hostName);
    }

    /**
    This method adds the service name and service to the service mapping
    @param Name of services
    @param Service corresponding to the name
    */
    public void addService(String nameIn, Service sIn){
        // Debug.print("{MACHINE} ADDING Service to Machine " + hostName+ ", " +", "+ sIn.getName());
        servicesMapping.put(nameIn, sIn);
    }

    /**
    This method removes the service from the machine. Used only with remove_service cluster method
    @param service name
    */
    public void removeService(String serviceNameIn){

        if(servicesMapping.containsKey(serviceNameIn)){
            servicesMapping.remove(serviceNameIn);
            // Debug.print("{MACHINE} removing service "+ hostName+ ", " + serviceNameIn);

        }

    }

    /**
    This method returns the list of service names to services hosted on this machine
    @return Map of service names to service
    */
    public Map<String, Service> getServiceList(){
        return servicesMapping;
    }

    /**
    Getter method for getting hostname of the machine
    */
    public String getHostName(){
        return hostName;
    }


    /**
    Check if service is already on machine
    @param name of service
    */
    public boolean checkServiceExist(String serviceNameIn){
        return servicesMapping.containsKey(serviceNameIn);
    }

    /**
    Getter method for geting the Service corresponding to the service name
    @param service name
    @return Services mapped to
    */
    public Service getService(String serviceNameIn){
        // if(!servicesMapping.containsKey(serviceNameIn)){
        //     return null;
        // }
        return servicesMapping.get(serviceNameIn);
    }




}
