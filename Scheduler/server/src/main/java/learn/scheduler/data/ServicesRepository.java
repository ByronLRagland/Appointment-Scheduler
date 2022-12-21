package learn.scheduler.data;

import learn.scheduler.model.Services;

import java.util.List;

public interface ServicesRepository {
    List<Services> findAll();
    List<Services> findAllEnabled();
    List<Services> findAllDisabled();

    Services findById(int serviceId);

    Services add(Services service);

    boolean update(Services service);
    boolean disableService(int serviceId);
    boolean enableService(int serviceId);

    boolean deleteById(int serviceId);
}
