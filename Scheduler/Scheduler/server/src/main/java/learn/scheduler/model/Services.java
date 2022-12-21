package learn.scheduler.model;

import java.math.BigDecimal;

public class Services {

    private int serviceId;
    private int duration;
    private BigDecimal price;
    private String serviceName;
    private String serviceDescription;

    public Services()
    {

    }

    public Services(int serviceId, int duration, BigDecimal price, String serviceName, String serviceDescription) {
        this.serviceId = serviceId;
        this.duration = duration;
        this.price = price;
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
    }
    public int getServiceId() {
        return serviceId;
    }
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getServiceDescription() {
        return serviceDescription;
    }
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    
    
}
