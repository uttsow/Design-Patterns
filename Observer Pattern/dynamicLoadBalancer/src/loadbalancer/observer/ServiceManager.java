package loadbalancer.observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import loadbalancer.data.Data;
import loadbalancer.data.DataI;
import loadbalancer.debug.Debug;
import loadbalancer.observer.ObserverI;
import loadbalancer.subject.Cluster;
import loadbalancer.subject.SubjectI;
import loadbalancer.util.Filtering;



/**
    Service and Service Manager share 1:1. For one Service, there is one Service manager
*/

public class ServiceManager implements ObserverI{
    //Ket of service manager
    // private String key;

    //Cluster its an observer for
    private SubjectI cluster;
    private Filtering filter;

    //Info about the service
    private String URL;
    private ArrayList<String> hostNames;



    /**
    Constructor
    */
    public ServiceManager(SubjectI clusterIn, String URLIn, ArrayList<String> hostNamesIn){
        // this.key = keyIn;
        this.URL = URLIn;
        this.hostNames = hostNamesIn;
        this.cluster = clusterIn;
        this.filter = new Filtering("ServiceManager");
        filter.setStatus("NO UPDATE");
        cluster.registerObservers(this, filter);
        // Debug.print("{SERVICE MANAGER} Service manager with key created");

    }

    /**
    this method performs request but that only happens in load balancer
    @param input of the request
    @return data object holding the result
    */
    public DataI request(String inputIn){
        return null;
    }

    /**
    Package private getter for URl
    */
    String getURL(){
        return URL;
    }

    /**
    Package privater getter the list of hostnames for the specifc server
    @return list of service machine hostnames
    */
    String getHostName(){
        String retVal = hostNames.get(0);
        if(retVal == null) System.out.print("no beuno");
        hostNames.remove(0);
        hostNames.add(retVal);
        // Debug.print("{SERVICE MANAGER} got host name " + retVal);
        return retVal;
    }


    /**
    Package private method for getting list of host for the service
    @return list of hosts
    */
    ArrayList<String> getHosts(){
        return hostNames;
    }

    /**
    This method sets the status of the filter
    @param filter status
    */
    public void setFilterStatus(String statusIn){
        this.filter.setStatus(statusIn);
    }

    /**
    Getter method for filter
    @return filter
    */
    public Filtering getFilter(){
        return filter;
    }

    /**
    This method just prints all the info of the service manager
    */
    public void printStuff(){
        Debug.print("{SERVICE MANAGER} Printing...");
        System.out.println(URL);
        for(int i=0; i < hostNames.size(); i++){
            System.out.println(getHostName());
        }
        System.out.print(hostNames.size());
    }

    /**
    This method sets the status of the service manager by changing the filter status
    @param the service name and status
    */
    public void setMangerStatus(String serviceIn, String statusIn){
        this.setFilterStatus(statusIn);
    }

    /**
    This method goes through the options for updating the service managers as requested by the cluster
    @param data to update it to
    */
    public void update(DataI dataIn){
        switch(dataIn.getOperation()){
            case "scale_down":
                String hostname = dataIn.getHostNames().get(0);
                // Debug.print("{SERVICE MANAGER} Scale down removing hostname " + hostname);
                removeHost(hostname);
                break;
            case "add_instance":
                addNewInstance(dataIn);
                break;
            case "remove_instance":
                removeInstance(dataIn);
                break;
            default:
                break;
        }
    }

    /**
    This method performs removing the host. Called during during scale Down
    @param host name to remove
    */
    private void removeHost(String hostNameIn){
        for(int i =0; i <hostNames.size(); i++){
            if(hostNames.get(i).equals(hostNameIn)){
                Debug.print("{SERVICE MANAGER} Removed Host " + hostNames.get(i) + " from Serivce: " + URL);
                hostNames.remove(i);
            }
        }
    }

    /**
    This method performs the opton add instance. Called during add instance
    @param data of the instance
    */
    private void addNewInstance(DataI dataIn){
        String hname = dataIn.getHostNames().get(0);
        Debug.print("{SERVICE MANAGER} Adding new instance " + hname);
        hostNames.add(hname);

    }

    /**
    This method performss remove instance. called during remove instance
    @param data of the instance
    */
    private void removeInstance(DataI dataIn){
        String hname = dataIn.getHostNames().get(0);
        Debug.print("{SERVICE MANAGER} Removing instance " + hname);
        for(int i =0; i<hostNames.size(); i++){
            if(hostNames.get(i).equals(hname)){
                Debug.print("{SERVER MANAGER} Removed " + hostNames.get(i));
                hostNames.remove(i);
            }
        }

    }

}
