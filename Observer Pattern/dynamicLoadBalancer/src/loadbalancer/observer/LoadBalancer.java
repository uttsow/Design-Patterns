package loadbalancer.observer;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import loadbalancer.observer.ObserverI;
import loadbalancer.subject.SubjectI;
import loadbalancer.observer.ServiceManager;
import loadbalancer.subject.Cluster;
import loadbalancer.debug.Debug;
import loadbalancer.util.Filtering;
import loadbalancer.data.Data;
import loadbalancer.data.DataI;
import loadbalancer.entities.Machine;
import loadbalancer.entities.Service;


/**
Find URL and Hostname for a given service
*/
public class LoadBalancer implements ObserverI{

    //Cluster the services are hosted in
    private SubjectI cluster;
    private Filtering filter;

    //Map to hold the mapping of Service name as key, service manger as value
    private Map<String, ServiceManager> serviceManagers;

    /**
    Constructor
    */
    public LoadBalancer(SubjectI clusterIn){
        this.cluster = clusterIn;
        this.serviceManagers = new HashMap<>();
        this.filter = new Filtering("Loadbalancer");
        this.filter.setStatus("NO UPDATE");
        cluster.registerObservers(this, filter);

        // Debug.print("{LOADBLANCER} Loadbalancer created");
    }

    /**
    This method performs the request option to get the URL and hostnames
    @param service name to get the info of
    @return data containing the return values
    */
    public DataI request(String serviceIn){
        DataI retData = new Data();
        String serviceName = serviceIn;
        Map<String, Machine> machine = cluster.getMachines();
        ServiceManager sm = serviceManagers.get(serviceName);
        //Service manager was removed
        if(sm == null){

            return null;
        }

        ArrayList<String> hosts = sm.getHosts();

        //Service inactive
        if(hosts.size() == 0){
            retData.setServiceName(serviceName);
            return retData;
        }

        boolean found = false;
        // Retrieve only valid hosts
        for(int i =0; i <hosts.size(); i++){
            String hostName = sm.getHostName();
            // Debug.print("{REQUEST} " +hostName);
            if(!found){
                if(machine.get(hostName).getService(serviceName).isValid()){
                    found = true;
                    retData.addHostName(hostName);
                    break;
                }
            }
        }
        // System.out.println(hosts.size());
        String url = sm.getURL();
        retData.setURL(url);
        return retData;
    }

    /**
    Getter for filter
    @return Filter
    */
    public Filtering getFilter(){
        return filter;
    }

    /**
    This method sets the filter status of load balancer
    @param status of the filter (UPDATE or NO UPDATE)
    */
    public void setFilterStatus(String statusIn){
        filter.setStatus(statusIn);
    }



    /**
    Sets the service manager status
    @param service name and status of the service manager
    */
    public void setMangerStatus(String serviceIn, String statusIn){
        // System.out.println(serviceIn);
        ServiceManager sm = serviceManagers.get(serviceIn);
        sm.setMangerStatus(serviceIn, statusIn);
    }

    /**
    Performs the update options of the load balancer
    @param the data containing the information
    */
    public void update(DataI dataIn){
        switch(dataIn.getOperation()){
            case "add_service":
                addToServiceManager(dataIn);
                break;
            case "remove_service":
                removeFromServiceManager(dataIn);
                break;
            default:
                break;
        }
    }

    /**
    This method takes in the data to be added to the service manager, creates a new service manager with the right paramters, then updates the mapping of Service and Service manager
    @param Data to create service manager
    */
    private void addToServiceManager(DataI dataIn){
        ArrayList<String> hostNames = new ArrayList<>();
        String serviceName = dataIn.getServiceName();
        // System.out.println("{LB}" + serviceName);
        String url = dataIn.getURL();

        //Add to new array
        for(int i = 0; i< dataIn.getHostNames().size(); i++){
            hostNames.add(dataIn.getHostNames().get(i));
        }

        //Create new server
        ServiceManager sm = new ServiceManager(cluster, url, hostNames);

        //Updates the map
        serviceManagers.put(serviceName, sm);

        // sm.printStuff();

    }

    /**
    Removes the service name from the service manager
    @param the data to remove
    */
    private void removeFromServiceManager(DataI dataIn){
        Debug.print("{LOADBLANCER} removing service from service manager " + dataIn.getServiceName());
        serviceManagers.remove(dataIn.getServiceName());

    }




}
