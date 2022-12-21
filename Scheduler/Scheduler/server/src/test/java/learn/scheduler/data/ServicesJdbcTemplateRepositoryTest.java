package learn.scheduler.data;

import learn.scheduler.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.math.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ServicesJdbcTemplateRepositoryTest {
    final static int NEXT_ID = 5;

    @Autowired
    ServicesJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Services> services = repository.findAll();
        assertNotNull(services);

        assertTrue(services.size() >= 4 && services.size() <= 6);
    }

    @Test
    void shouldFindById() {
        Services service = repository.findById(3);
        assertEquals("shave",service.getServiceName());

        service = repository.findById(10);
        assertNull(service);
    }

    @Test
    void shouldAdd() {
        Services service = makeService();
        Services actual = repository.add(service);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getServiceId());
    }

    @Test
    void shouldUpdate() {
        Services service = makeService();
        service.setServiceId(2);
        assertTrue(repository.update(service));
        service.setServiceId(10);
        assertFalse(repository.update(service));
    }

    @Test
    void deleteById() {
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(1));
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