package learn.scheduler.controller;

import learn.scheduler.domain.*;
import learn.scheduler.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/services")
public class ServicesController {

    private final ServicesService service;

    public ServicesController(ServicesService service) {
        this.service = service;
    }

    @GetMapping
    public List<Services> findAll() {
        return service.findAll();
    }

    @GetMapping("/find/enabled")
    public List<Services> findAllEnabled() {
        return service.findAllEnabled();
    }

    @GetMapping("/find/disabled")
    public List<Services> findAllDisabled() {
        return service.findAllDisabled();
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<Services> findById(@PathVariable int serviceId) {
        Services services = service.findById(serviceId);
        if (services == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(services);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Services services) {
        Result<Services> result = service.add(services);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<Object> update(@PathVariable int serviceId, @RequestBody Services services) {
        if (serviceId != services.getServiceId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Services> result = service.update(services);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/enable/{serviceId}")
    public ResponseEntity<Void> enable(@PathVariable int serviceId) {
        if (service.enableService(serviceId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/disable/{serviceId}")
    public ResponseEntity<Void> disable(@PathVariable int serviceId) {
        if (service.disableService(serviceId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteById(@PathVariable int serviceId) {
        if (service.deleteById(serviceId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
