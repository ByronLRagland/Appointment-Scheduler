package learn.scheduler.model;

public class Customer {

    private int customerId;
    private String customerName;
    private String phoneNumber;
    private String email;

    public Customer(int customer_id, String customer_name, String phone_number, String email) {
        this.customerId = customer_id;
        this.customerName = customer_name;
        this.phoneNumber = phone_number;
        this.email = email;
    }
    public Customer(){}

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customer_id) {
        this.customerId = customer_id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customer_name) {
        this.customerName = customer_name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
