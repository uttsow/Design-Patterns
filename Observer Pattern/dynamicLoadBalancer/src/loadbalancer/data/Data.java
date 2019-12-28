package loadbalancer.data;

import java.util.ArrayList;
import loadbalancer.debug.Debug;

public class Data implements DataI{
    private ArrayList<String> hostName;
    private String URL;
    private String serviceName;
    private String operation;

    /**
    Constructor
    */
    public Data(){
        this.hostName = new ArrayList<>();
        this.URL = "";
        this.serviceName = "";
        this.operation = "";
    }

    /**
    Add host name to array
    @param hostname
    */
    public void addHostName(String hostNameIn){
        hostName.add(hostNameIn);
    }

    /**
    Get host names
    @return list of host names
    */
    public ArrayList<String> getHostNames(){
        return hostName;
    }

    /**
    set service URL
    @param url
    */
    public void setURL(String urlIn){
        URL = new String(urlIn);
    }

    /**
    get URL
    @param url string
    */
    public String getURL(){
        return URL;
    }

    /**
    Set service name
    @param service name
    */
    public void setServiceName(String serviceNameIn){
        serviceName = serviceNameIn;
    }

    /**
    get service name
    @return service name
    */
    public String getServiceName(){
        return serviceName;
    }

    /**
    set operation type
    @param operation type
    */
    public void setOperation(String opIn){
        operation = opIn;
    }

    /**
    get operation type
    @return operations
    */
    public String getOperation(){
        return operation;
    }

    /**
    clear all the data fields of data memebers for next processing
    */
    public void clear(){
        // Debug.print("{DATA} Data cleared");
        hostName.clear();
        URL = null;
        serviceName = null;
    }
}
