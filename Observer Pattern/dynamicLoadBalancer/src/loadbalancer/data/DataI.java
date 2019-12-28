package loadbalancer.data;

import java.util.ArrayList;

public interface DataI{
    void addHostName(String hostNameIn);
    ArrayList<String> getHostNames();
    void setURL(String urlIn);
    String getURL();
    void setServiceName(String serviceNameIn);
    String getServiceName();
    void setOperation(String opsIn);
    String getOperation();
    void clear();
}
