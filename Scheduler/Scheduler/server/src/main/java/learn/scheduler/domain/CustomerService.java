package learn.scheduler.domain;

import learn.scheduler.data.CustomerRepository;
import learn.scheduler.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer findById(int customerId) {
        return repository.findById(customerId);
    }

    public Result<Customer> add(Customer customer) {
        Result<Customer> result = validate(customer);
        if (!result.isSuccess()) {
            return result;
        }

        if (customer.getCustomerId() != 0) {
            result.addMessage("customerId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        customer = repository.add(customer);
        result.setPayload(customer);
        return result;
    }


    public Result<Customer> update(Customer customer) {
        Result<Customer> result = validate(customer);
        if (!result.isSuccess()) {
            return result;
        }

        if (customer.getCustomerId() <= 0) {
            result.addMessage("customerId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(customer)) {
            String msg = String.format("customerId: %s, not found", customer.getCustomerId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }


    public  boolean deleteById(int id) {

        if (!repository.deleteById(id)) {
            return false;
        }

        return true;
    }



    private Result<Customer> validate(Customer customer) {
        Result<Customer> result = new Result<>();
        if (customer == null) {
            result.addMessage("customer cannot be null", ResultType.INVALID);
            return result;
        }

        if (customer.getCustomerName() == null || customer.getCustomerName().isBlank()) {
            result.addMessage("Customer Name is required", ResultType.INVALID);
        }

        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            result.addMessage("Phone Number is required", ResultType.INVALID);
        }

        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            result.addMessage("Email is required", ResultType.INVALID);
        }

        if (isEmailDuplicated(customer)){
            result.addMessage("Email already in use", ResultType.INVALID);
        }

        return result;
    }

    private boolean isEmailDuplicated(Customer customer){

        List<Customer> customers = findAll();
        for (Customer obj : customers){
            if (obj.getEmail().equalsIgnoreCase(customer.getEmail()) &&
                    obj.getCustomerId()!= customer.getCustomerId()){
                return true;
            }
        }
        return false;
    }

}
