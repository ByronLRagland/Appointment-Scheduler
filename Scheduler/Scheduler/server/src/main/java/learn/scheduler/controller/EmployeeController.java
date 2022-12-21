package learn.scheduler.controller;

import learn.scheduler.domain.*;
import learn.scheduler.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
// @CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/employee")

public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> findAll() {
        return service.findAll();
    }


    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> findById(@PathVariable int employeeId) {
        Employee employee = service.findById(employeeId);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Employee employee) {
        Result<Employee> result = service.add(employee);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Object> update(@PathVariable int employeeId, @RequestBody Employee employee) {
        if (employeeId != employee.getEmployeeId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Employee> result = service.update(employee);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteById(@PathVariable int employeeId) {
        if (service.deleteById(employeeId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
