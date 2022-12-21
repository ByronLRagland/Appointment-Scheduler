package learn.scheduler.model;

import java.sql.Timestamp;

public class Appointment {

    private int appointmentId;
    private Timestamp startTime;
    private Timestamp endTime;
    private int employeeId;
    private String employeeName;
    private int customerId;
    private String customerName;
    private int serviceId;
    private String serviceName;
    

    public Appointment()
    {

    }

    

    public Appointment(int appointmentId, Timestamp startTime, Timestamp endTime, int employeeId, String employeeName,
            int customerId, String customerName, int serviceId, String serviceName) {
        this.appointmentId = appointmentId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.customerId = customerId;
        this.customerName = customerName;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }



    public String getEmployeeName() {
        return employeeName;
    }



    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }



    public String getCustomerName() {
        return customerName;
    }



    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }



    public String getServiceName() {
        return serviceName;
    }



    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }



    public int getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getServiceId() {
        return serviceId;
    }
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp start_time) {
        this.startTime = start_time;
    }
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp end_time) {
        this.endTime = end_time;
    }

    


    
}
