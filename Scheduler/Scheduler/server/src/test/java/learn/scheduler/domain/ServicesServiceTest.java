package learn.scheduler.domain;

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
class ServicesServiceTest {

    @Autowired
    ServicesService service;

    @MockBean
    ServicesRepository repository;

    @Test
    void shouldNotAddWhenInvalid() {
        Services services = makeService();
        services.setServiceName("   ");

        Result<Services> actual = service.add(services);
        assertEquals(ResultType.INVALID, actual.getType());

        services = makeService();
        services.setServiceDescription(null);
        actual = service.add(services);
        assertEquals(ResultType.INVALID, actual.getType());

        services = makeService();
        services.setDuration(0);
        actual = service.add(services);
        assertEquals(ResultType.INVALID, actual.getType());

        services = makeService();
        services.setPrice(BigDecimal.valueOf(0));
        actual = service.add(services);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldAdd() {
        Services services = makeService();
        Services mockOut = makeService();
        mockOut.setServiceId(1);

        when(repository.add(services)).thenReturn(mockOut);

        Result<Services> actual = service.add(services);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        Services services = makeService();
        Result<Services> actual = service.update(services);
        assertEquals(ResultType.INVALID, actual.getType());

        services = makeService();
        services.setServiceId(1);
        services.setServiceName("");
        actual = service.update(services);
        assertEquals(ResultType.INVALID, actual.getType());

        services = makeService();
        services.setServiceId(1);
        services.setServiceDescription(null);
        actual = service.update(services);
        assertEquals(ResultType.INVALID, actual.getType());

        services = makeService();
        services.setServiceId(1);
        services.setDuration(0);
        actual = service.update(services);
        assertEquals(ResultType.INVALID, actual.getType());

        services = makeService();
        services.setServiceId(1);
        services.setPrice(BigDecimal.valueOf(0));
        actual = service.update(services);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Services services = makeService();
        services.setServiceId(1);

        when(repository.update(services)).thenReturn(true);

        Result<Services> actual = service.update(services);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    Services makeService() {
        Services service = new Services();
        service.setServiceName("test name");
        service.setServiceDescription("test description");
        service.setDuration(5);
        service.setPrice(BigDecimal.valueOf(100));
        return service;
    }
}