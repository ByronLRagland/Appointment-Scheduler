package learn.scheduler.controller;

import learn.scheduler.domain.CustomerService;
import learn.scheduler.domain.Result;
import learn.scheduler.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://127.0.0.1:5500"})
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }


    @GetMapping
    public List<Customer> findAll() {
        return service.findAll();
    }

    @GetMapping("/{customerId}")
    public Customer findById(@PathVariable int customerId) {
        return service.findById(customerId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Customer customer) {
        Result<Customer> result = service.add(customer);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Object> update(@PathVariable int customerId, @RequestBody Customer customer) {
        if (customerId != customer.getCustomerId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Customer> result = service.update(customer);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteById(@PathVariable int customerId) {
        if (service.deleteById(customerId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
