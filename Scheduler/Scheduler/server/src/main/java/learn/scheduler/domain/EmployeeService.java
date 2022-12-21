package learn.scheduler.domain;

import java.util.List;

import learn.scheduler.data.EmployeeRepository;
import learn.scheduler.model.Employee;
import org.springframework.stereotype.*;

@Service

public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee findById(int agentId) {
        return repository.findById(agentId);
    }

    public Result<Employee> add(Employee em) {
        Result<Employee> result = validate(em);
        if (!result.isSuccess()) {
            return result;
        }

        if (em.getEmployeeId() != 0) {
            result.addMessage("employeeId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        em = repository.add(em);
        result.setPayload(em);
        return result;
    }

    public Result<Employee> update(Employee employee) {
        Result<Employee> result = validate(employee);
        if (!result.isSuccess()) {
            return result;
        }

        if (employee.getEmployeeId() <= 0) {
            result.addMessage("employeeId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(employee)) {
            String msg = String.format("employeeId: %s, not found", employee.getEmployeeId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int agentId) {
        return repository.deleteById(agentId);
    }

    private Result<Employee> validate(Employee em) {
        Result<Employee> result = new Result<>();
        if (em == null) {
            result.addMessage("employee cannot be null", ResultType.INVALID);
            return result;
        }

        if (em.getFirstName() == null || em.getFirstName().isBlank())
        {
            result.addMessage("firstName is required", ResultType.INVALID);
            return result;
        }

        if (em.getLastName() == null || em.getLastName().isBlank())
        {
            result.addMessage("lastName is required", ResultType.INVALID);
            return result;
        }

        if (em.getBio() == null || em.getBio().isBlank())
        {
            result.addMessage("Bio is required", ResultType.INVALID);
            return result;
        }

        if (em.getImageUrl() == null || em.getImageUrl().isBlank())
        {
            result.addMessage("image_url is required", ResultType.INVALID);
            return result;
        }

        return result;
    }








}
