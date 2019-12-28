package loadbalancer.observer;

import loadbalancer.data.DataI;
import loadbalancer.util.Filtering;

public interface ObserverI{
    DataI request(String serviceIn);
    Filtering getFilter();
    void setFilterStatus(String statusIn);
    void setMangerStatus(String serviceIn, String statusIn);
    void update(DataI dataIn);
}
