package learn.scheduler.domain;

import learn.scheduler.data.CustomerRepository;
import learn.scheduler.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CustomerServiceTest {

    @Autowired
    CustomerService service;

    @MockBean
    CustomerRepository repository;

    @Test
    void shouldFind() {

        Customer expected = makeCustomer();
        when(repository.findById(1)).thenReturn(expected);
        Customer actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAddWhenValid() {
        Customer expected = makeCustomer();
        Customer arg = makeCustomer();
        arg.setCustomerId(0);

        when(repository.add(arg)).thenReturn(expected);
        Result<Customer> result = service.add(arg);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());
    }


    @Test
    void shouldNotAddWhenInvalid() {
        Customer customer = makeCustomer();
        customer.setCustomerId(0);
        customer.setCustomerName("  ");
        Result<Customer> actual = service.add(customer);
        assertEquals(ResultType.INVALID, actual.getType());

        customer = makeCustomer();
        customer.setCustomerId(0);
        customer.setPhoneNumber("  ");
        actual = service.add(customer);
        assertEquals(ResultType.INVALID, actual.getType());

        customer = makeCustomer();
        customer.setCustomerId(0);
        customer.setEmail("  ");
        actual = service.add(customer);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        Customer customer = makeCustomer();
        customer.setCustomerName("  ");
        Result<Customer> actual = service.update(customer);
        assertEquals(ResultType.INVALID, actual.getType());

        customer = makeCustomer();
        customer.setPhoneNumber("  ");
        actual = service.update(customer);
        assertEquals(ResultType.INVALID, actual.getType());

        customer = makeCustomer();
        customer.setEmail("  ");
        actual = service.update(customer);
        assertEquals(ResultType.INVALID, actual.getType());
    }



    Customer makeCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerName("test name");
        customer.setEmail("test@email.com");
        customer.setPhoneNumber("1-800-test");
        return customer;
    }


}