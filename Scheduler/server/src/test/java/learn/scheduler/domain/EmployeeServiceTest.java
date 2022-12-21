package learn.scheduler.domain;

import org.junit.jupiter.api.Test;

import learn.scheduler.data.*;
import learn.scheduler.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;

import java.math.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @MockBean
    EmployeeRepository repository;

    @Test
    void shouldNotAddInvalidEmployees() {

        Employee employee = makeEmployee();
        employee.setFirstName(" ");

        Result<Employee> actual = employeeService.add(employee);
        assertEquals(ResultType.INVALID, actual.getType());

        employee.setFirstName("Tom");
        employee.setEmployeeId(3);
        actual = employeeService.add(employee);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void testAdd() {

        Employee employee = makeEmployee();
        Employee mockOut = makeEmployee();
        mockOut.setEmployeeId(2);

        when(repository.add(employee)).thenReturn(mockOut);

        Result<Employee> actual = employeeService.add(employee);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());

    }

    @Test
    void shouldNotUpdateInvalidEmployees() {

        Employee employee = makeEmployee();
        employee.setFirstName(" ");

        Result<Employee> actual = employeeService.update(employee);
        assertEquals(ResultType.INVALID, actual.getType());

        employee.setFirstName("Tom");
        employee.setEmployeeId(-1);
        actual = employeeService.update(employee);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Employee employee = makeEmployee();
        employee.setEmployeeId(1);

        when(repository.update(employee)).thenReturn(true);

        Result<Employee> actual = employeeService.update(employee);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }


   
    Employee makeEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Tom");
        employee.setLastName("McBeard");
        employee.setBio("Some Bio");
        employee.setImageUrl("Some link");

        return employee;
    }
}
