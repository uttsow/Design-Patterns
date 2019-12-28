package loadbalancer.subject;

import java.lang.Class;
import java.lang.Object;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import loadbalancer.data.Data;
import loadbalancer.data.DataI;
import loadbalancer.debug.Debug;
import loadbalancer.entities.Machine;
import loadbalancer.entities.Service;
import loadbalancer.observer.LoadBalancer;
import loadbalancer.observer.ObserverI;
import loadbalancer.observer.ServiceManager;
import loadbalancer.util.ErrorChecking;
import loadbalancer.util.FileProcessor;
import loadbalancer.util.Filtering;

import java.lang.Object;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import loadbalancer.data.DataI;
import loadbalancer.entities.Machine;
import loadbalancer.observer.LoadBalancer;
import loadbalancer.observer.ServiceManager;
import loadbalancer.util.FileProcessor;



public class Cluster implements SubjectI{

    //private memebers
    private FileProcessor fp;
    private ArrayList<String> operations;
    private Map<String, Machine> machines;
    private ErrorChecking e;
    private Data data;


    //load balancer ref
    ObserverI loadbalancer;


    //All the observers;
    public Map<Filtering, ObserverI> observerList;

    //to store the results. I want one copy and it as a class varaible
    public ArrayList<String> result;

    /**
    Constructor
    */
    public Cluster(FileProcessor fpIn){
        this.fp = fpIn;
        this.operations = new ArrayList<>();
        this.result = new ArrayList<>();
        this.machines = new HashMap<>();
        this.observerList = new HashMap<>();
        this.data = new Data();


        //Adding all operations
        for(int i =0; i <fp.getNumLines(); i++){
            String ops = fp.readLineSpecific(i);
            //System.out.println(ops);
            if(ops != null) addOperations(ops);
        }

        this.e = new ErrorChecking(operations);
        // serviceManager = new ServiceManager(this);

    }

    /**
    This method adds all the operations to an arraylist. Called from driver
    */
    private final void addOperations(String opsIn){
        operations.add(opsIn);
    }

    /**
    This method checks if all inputs given are valid operations
    */
    public void checkErrors(){
        e.checkCorrectOperations();
    }

    /**
    This method starts the Observer Pattern
    @return;
    */
    public void start(){
        //register the two observers
        data.clear();
        loadbalancer = new LoadBalancer(this);
        for(int i =0; i <operations.size(); i++){
            String[] operants = e.clearString(operations.get(i));
            callOperation(operants);
        }
    }


    /**
    This method figures out what the operation we are trying to do is and calls it using a switch statment;
    @param String array of operations
    */
    private void callOperation(String[] operantsIn){
        String function = operantsIn[0].toLowerCase();
        switch(function){
            case "cluster_op__scale_up":
                cluster_op__scale_up(operantsIn);
                break;
            case "cluster_op__scale_down":
                cluster_op__scale_down(operantsIn);
                break;
            case "service_op__add_service":
                service_op__add_service(operantsIn);
                break;
            case "service_op__remove_service":
                service_op__remove_service(operantsIn);
                break;
            case "service_op__add_instance":
                service_op__add_instance(operantsIn);
                break;
            case "service_op__remove_instance":
                service_op__remove_instance(operantsIn);
                break;
            case "request":
                request(operantsIn);
                break;
            default:
                System.out.println("method could not be called");
                break;
        }

        /**
        I couldnt get the reflect API working when trying to write to file. Maybe i can figure it out in the future
        */


        //
        // try{
        //     Class<?> c = Class.forName("loadbalancer.subject.Cluster");
        //     Method methods = c.getMethod(function, new Class[]{String[].class});
        //     methods.invoke(this, new Object[]{operantsIn});
        //
        // }
        // catch(ClassNotFoundException e){
        //     Debug.error("Class was not found when calling method {CALL OPERATION}");
        //     e.printStackTrace();
        //     System.exit(1);
        // }catch(NoSuchMethodException e){
        //     Debug.print("Method doesnt exist {CALL OPERATION}");
        //     e.printStackTrace();
        //     System.exit(1);
        // }catch(IllegalAccessException e){
        //     Debug.print("Couldnt access method {CALL OPERATION}");
        //     e.printStackTrace();
        //     System.exit(1);
        // }catch(InvocationTargetException e){
        //     Debug.print("Some type of exception {CALL OPERATION}");
        //     e.getCause().printStackTrace();
        //     System.exit(1);
        // }
    }

    /**
    returns mapping of hostnames to machines
    @return map of machines
    */
    public Map<String, Machine> getMachines(){
        return this.machines;
    }

    /**
    This method returns the result array. It's static so it can be called from anywhere
    @return arrayList result;
    */
    public ArrayList<String> getResults(){
        return result;
    }


    /**
                        MAIN CLUSTER OPERATIONS
    */

    /**
    This method registers the observer to the cluster
    @param observer and filter
    */
    public void registerObservers(ObserverI oIn, Filtering fIn){
        // Debug.print("Adding Observer: " + oIn.getName());
        // Debug.print("Filter type: " + fIn.getFilterType());
        observerList.put(fIn, oIn);
    }

    /**
    this method removes the observer
    @param observer
    */
    public void removeObserver(ObserverI oIn){
        observerList.values().remove(oIn);
    }

    /**
    This method performs the CLUSTER_OP__SCALE_UP operation.
    @param String array of inputs. should be just machine name
    */
    public void cluster_op__scale_up(String[] inputIn){
        String machineHostName = inputIn[1];
        // Debug.print("{CLUSTER} Adding new machine " + machineHostName);

        if(!machines.containsKey(machineHostName)){
            Machine m = new Machine(machineHostName);
            machines.put(machineHostName, m);
            String retVal = "Cluster Scaled Up";
            result.add(retVal);
            return;

        }else{
            String retVal = "{SCALE_UP) Machine hostname: " + machineHostName + " already exists!";
            // Debug.print(retVal);
            result.add(retVal);
            // System.out.println(result.size());

        }
    }


    /**
    This method performs scale down. it removes the machine from the cluster, thus invalidating all services running on it; Then sends an update to corresponding observer
    @param string of inputs, should only be machineHostName
    */
    public void cluster_op__scale_down(String[] inputIn){

        String machineHostName = inputIn[1];
        Debug.print("{CLUSTER} Removing machine " + machineHostName);

        //When the machine was never there
        if(!machines.containsKey(machineHostName)){
            String retVal = "{SCALE_DOWN} Machine hostname: " + machineHostName + " does not exist!";
            result.add(retVal);

            //clear data and return;
            data.clear();
            return;
        }else{
            //If machine exists
            data.setOperation("scale_down");
            Machine removed = machines.remove(machineHostName);
            data.addHostName(removed.getHostName());

            // System.out.print(data.getHostNames().get(0));
            Map<String, Service> services = removed.getServiceList();
            for(Map.Entry<String, Service> entry : services.entrySet()){

                String serviceName = entry.getKey();
                serviceManagerChange(serviceName, "UPDATE");
                // loadbalancer.setMangerStatus(serviceName, "UPDATE");
                // notifyObserver();
            }

            String retVal = "Cluster Scaled Down";
            result.add(retVal);
        }
    }

    /**
    This method attempts to add a service to the machine hostnames then notify loadbalancer
    @param string array of arguments
    */
    public void service_op__add_service(String[] inputIn){
        String serviceName = inputIn[1];
        data.setServiceName(serviceName);
        String url = inputIn[2];
        data.setURL(url);
        data.setOperation("add_service");
        boolean toAdd = false;
        boolean cont = true;

        for(int i=3; i < inputIn.length; i++){
            //First check to see if the machine exist
            if(!machines.containsKey(inputIn[i])){
                String retVal = "{ADD_SERVICE} Machine with hostname " + inputIn[i] + " does not exist!";
                result.add(retVal);
                cont = false;
                break;
            }

            if(cont){
                //creates new service
                Service s = new Service(serviceName, url);

                //Check to see if it already exist
                if(!machines.get(inputIn[i]).checkServiceExist(serviceName)){
                    machines.get(inputIn[i]).addService(serviceName, s);
                    toAdd = true;

                }else{
                    String retVal = "{ADD_SERVICE} Service already exist";
                    result.add(retVal);
                    toAdd = false;
                    break;
                }
                data.addHostName(inputIn[i]);
            }
        }

        if(toAdd && cont){
            String retVal = "Service Added";
            result.add(retVal);
        }
        loadBalancerChange("UPDATE");
        // loadbalancer.setFilterStatus("UPDATE");
        // notifyObserver();

    }

    /**
    This method attempts to remove the all instances of the service
    @param input string array
    */
    public void service_op__remove_service(String[] inputIn){
        data.setOperation("remove_service");
        String serviceName = inputIn[1];

        data.setServiceName(serviceName);

        Debug.print("{CLUSTER} Removing Service " + serviceName);
        //Check if service name is valid. if valid continue
        if(checkIfValidService(serviceName)){
            for(Map.Entry<String, Machine> entry: machines.entrySet()){
                Machine m = entry.getValue();
                m.removeService(serviceName);
            }
        }else{
            String retVal = "{REMOVE_SERVICE} Service name invalid: " + serviceName;
            result.add(retVal);
            data.clear();
            return;
        }
        //Add it to result array
        String retVal = "Service Removed";
        result.add(retVal);

        //Notify observer
        loadBalancerChange("UPDATE");
        // loadbalancer.setFilterStatus("UPDATE");
        // notifyObserver();

    }


    /**
    This method adds a instance of a servce to a new host
    @param input string array
    */
    public void service_op__add_instance(String[] inputIn){
        String serviceName = inputIn[1];
        String hostName = inputIn[2];
        data.setOperation("add_instance");
        data.setServiceName(serviceName);
        data.addHostName(hostName);

        if(checkIfValidService(serviceName)){
            if(!machines.containsKey(hostName)){
                String retVal = "{ADD_INSTANCE} Machine with hostname " + hostName + " does not exist!";
                result.add(retVal);
                data.clear();
                return;
            }
            Machine m = machines.get(hostName);
            String url = getServiceURL(serviceName);
            if(url.equals("")){
                throw new IllegalAccessError("Couldnt retrieve service url for the given service");
            }
            Service s = new Service(serviceName, url);
            Debug.print("{CLUSTER} Adding instance " + serviceName + ", " + url);
            m.addService(serviceName, s);
            String retVal = "Instance Added";
            result.add(retVal);

            //Get the observer (Service manager and update);
            serviceManagerChange(serviceName, "UPDATE");
            // loadbalancer.setMangerStatus(serviceName, "UPDATE");
            // notifyObserver();

        }else{
            String retVal = "{ADD_INSTANCE} Service: " + serviceName +  " was not previously added using SERVICE_OP__ADD_SERVICE";
            result.add(retVal);

        }

    }


    /**
    This method aims to remove an instance of a service on the machine with the given host name
    @param input string array
    */
    public void service_op__remove_instance(String[] inputIn){
        String serviceName = inputIn[1];
        String hostName = inputIn[2];
        data.setOperation("remove_instance");
        data.setServiceName(serviceName);
        data.addHostName(hostName);
        boolean toAdd = false;
        //Check if service is present
        if(checkIfValidService(serviceName)){
            //Service is valid

            //Check if hostname exisit
            if(!machines.containsKey(hostName)){
                String retVal = "{REMOVE_INSTANCE} Machine with hostname " + hostName + " does not exist!";
                result.add(retVal);
                data.clear();
                return;
            }else{
                Machine m = machines.get(hostName);
                boolean cont = m.checkServiceExist(serviceName);
                if(cont){
                    Debug.print("{CLUSTER} removing instance of " + serviceName + " from machine " + hostName);
                    Service s = m.getService(serviceName);
                    m.removeService(serviceName);
                    toAdd = true;

                    //if no more of the service remains, mark as inactive
                    if(!checkIfValidService(serviceName)){
                        Debug.print("INVALID SERVICE " + serviceName);
                        s.setValid(false);
                    }
                }else{
                    String retVal = "{REMOVE_INSTANCE} No instance of service avaliable on machine " + serviceName + ", " + hostName;
                    result.add(retVal);

                }
            }

        }else{
            String retVal = "{REMOVE_INSTANCE} Service: " + serviceName +  " was not previously added using SERVICE_OP__ADD_SERVICE";
            result.add(retVal);
        }

        if(toAdd){
            String retVal = "Instance Removed";
            result.add(retVal);
            //Get the observer (Service manager and update);
            serviceManagerChange(serviceName, "UPDATE");

            // loadbalancer.setMangerStatus(serviceName, "UPDATE");
            // notifyObserver();
        }

    }

    /**
    This method performs the request option and forwards it to the load blanacer
    @param the input values of the request
    */
    public void request(String[] inputIn){
        //Check if the service even exists {
            DataI retData = loadbalancer.request(inputIn[1]);
            // System.out.println(retData.getURL());
            // System.out.println(retData.getHostNames().get(0));
            if(retData == null){
                String retVal = "Invalid Service";
                result.add(retVal);
            }else if(retData.getURL().equals("")){
                String ret = "Service Inactive - Service::" + retData.getServiceName();
                result.add(ret);
            }else{
                String ret = "Processed Request - Service_URL::" + retData.getURL() + " Host::" + retData.getHostNames().get(0);
                // System.out.println(ret);
                result.add(ret);
            }

    }



    /**
    This method notifys the list of observers
    */
    public void notifyObserver(){
        // Filtering f = obsIn.getFilter();
        for(Map.Entry<Filtering, ObserverI> entry : observerList.entrySet()){
            Filtering f = entry.getKey();
            if((f.getStatus().equals("UPDATE"))){
                // System.out.println(f.getFilterType());
                entry.getValue().update(data);
                f.setStatus("NO UPDATE");
            }
        }

        data.clear();
    }





    /**

                            HELPER FUNCTIONS

    */


    /**
    This method changes filter status of loadbalancer
    @param status of filter
    */
    private void loadBalancerChange(String statusIn){
        loadbalancer.setFilterStatus(statusIn);
        notifyObserver();
    }

    /**
    This method changes filter status of service manager
    @param service name and filter status
    */
    private void serviceManagerChange(String serviceNameIn, String statusIn){
        loadbalancer.setMangerStatus(serviceNameIn, statusIn);
        notifyObserver();
    }




    /**
    This private method checks if service exists on the machines        @param name of service
    @return true or false
    */
    private boolean checkIfValidService(String serviceNameIn){
        for(Map.Entry<String, Machine> entry : machines.entrySet()){
            Machine m = entry.getValue();
            if(m.checkServiceExist(serviceNameIn)){
                return true;
            }
        }

        return false;

    }

    /**
    this method gets the service url
    @param service name
    @return service url
    */
    private String getServiceURL(String serviceNameIn){
        for(Map.Entry<String, Machine> entry : machines.entrySet()){
            Machine m = entry.getValue();
            if(m.checkServiceExist(serviceNameIn)){
                return m.getService(serviceNameIn).getURL();
            }
        }
        return "";
    }






    /**
    This method prints out everything in the results array
    */
    public void print(){
        for(int i =0; i < result.size(); i++){
            System.out.println(result.get(i));
        }
    }

}
