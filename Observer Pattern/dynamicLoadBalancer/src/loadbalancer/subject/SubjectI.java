package loadbalancer.subject;

import java.util.ArrayList;
import java.util.Map;
import loadbalancer.entities.Machine;
import loadbalancer.observer.ObserverI;
import loadbalancer.util.Filtering;

public interface SubjectI{
    void checkErrors();
    void start();
    void print();
    ArrayList<String> getResults();

    void registerObservers(ObserverI oIn, Filtering fIn);
    void removeObserver(ObserverI oIn);

    /**
    Cluster operations
    */
    void cluster_op__scale_up(String[] inputIn);

    // //Needs update to Service manager
    void cluster_op__scale_down(String[] inputIn);

    /**
    Service operations
    */
    //Needs update to LoadBalancer
    void service_op__add_service(String[] inputIn);
    void service_op__remove_service(String[] inputIn);

    //Needs update to Servcie manager
    void service_op__add_instance(String[] inputIn);
    void service_op__remove_instance(String[] inputIn);
    void request(String[] inputIn);
    Map<String, Machine> getMachines();

    //To notify observers
    void notifyObserver();

}
