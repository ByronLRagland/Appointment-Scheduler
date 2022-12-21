package learn.scheduler.data;

import learn.scheduler.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> findAll();

    Employee findById(int employeeId);

    Employee add(Employee employee);

    boolean update(Employee employee);

    boolean deleteById(int employeeId);
}
