package learn.scheduler.controller;

import learn.scheduler.domain.*;
import learn.scheduler.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/appointment")

public class AppointmentController {

    //find by employee id
    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Appointment> findAll()
    {
        return service.findAll();
    }

    @GetMapping("/find/{employeeId}")
    public List<Appointment> findByEmployeeId(@PathVariable int employeeId) {
        return service.findByEmployeeId(employeeId);
    }

    @GetMapping("/find/customer/{customerId}")
    public List<Appointment> findByCustomerId(@PathVariable int customerId) {
        return service.findByCustomerId(customerId);
    }


    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> findById(@PathVariable int appointmentId) {
        Appointment appointment = service.findById(appointmentId);
        if (appointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(appointment);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Appointment appointment) {
        Result<Appointment> result = service.add(appointment);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Object> update(@PathVariable int appointmentId, @RequestBody Appointment appointment) {
        if (appointmentId != appointment.getAppointmentId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Appointment> result = service.update(appointment);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteById(@PathVariable int appointmentId) {
        if (service.deleteById(appointmentId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/cancel/{appointmentId}")
    public ResponseEntity<Object> cancelCustomerAppointmentById(@PathVariable int appointmentId) {
        if (service.cancelCustomerAppointmentById(appointmentId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    
}
