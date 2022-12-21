package learn.scheduler.domain;

import learn.scheduler.data.*;
import learn.scheduler.model.*;
import org.springframework.stereotype.*;

import java.math.*;
import java.util.*;

@Service
public class ServicesService {
    private final ServicesRepository repository;

    public ServicesService(ServicesRepository repository) {
        this.repository = repository;
    }

    public List<Services> findAll(){
        return repository.findAll();
    }

    public List<Services> findAllEnabled(){
        return repository.findAllEnabled();
    }

    public List<Services> findAllDisabled(){
        return repository.findAllDisabled();
    }

    public Services findById(int serviceId) {
        return repository.findById(serviceId);
    }

    public Result<Services> add(Services service) {
        Result<Services> result = validate(service);
        if (!result.isSuccess()) {
            return result;
        }

        if (service.getServiceId() != 0) {
            result.addMessage("serviceId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        service = repository.add(service);
        result.setPayload(service);
        return result;
    }

    public Result<Services> update(Services service) {
        Result<Services> result = validate(service);
        if (!result.isSuccess()) {
            return result;
        }

        if (service.getServiceId() <= 0) {
            result.addMessage("serviceId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(service)) {
            String msg = String.format("serviceId: %s, not found", service.getServiceId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean enableService(int serviceId){
       return repository.enableService(serviceId);
    }

    public boolean disableService(int serviceId){
        return repository.disableService(serviceId);
    }

    public boolean deleteById(int serviceId) {
        return repository.deleteById(serviceId);
    }

    private Result<Services> validate(Services service) {
        Result<Services> result = new Result<>();

        if (service == null) {
            result.addMessage("service cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(service.getServiceName())) {
            result.addMessage("service name is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(service.getServiceDescription())) {
            result.addMessage("service description is required", ResultType.INVALID);
        }

        if (service.getDuration() < 5) {
            result.addMessage("Duration cannot be less than 5 minutes", ResultType.INVALID);
        }

        if (service.getPrice().compareTo(BigDecimal.valueOf(0)) != 1) {
            result.addMessage("price must be a positive number", ResultType.INVALID);
        }

        return result;
    }
}
