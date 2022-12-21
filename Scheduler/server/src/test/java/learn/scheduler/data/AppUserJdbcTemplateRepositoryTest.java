package learn.scheduler.data;

import learn.scheduler.model.Appointment;
import org.junit.jupiter.api.Test;

import learn.scheduler.model.AppUser;
import learn.scheduler.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

public class AppUserJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 9;

    @Autowired
    AppUserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }



    @Test
    void shouldFindAll() {
        List<AppUser> users = repository.findAll();
        assertTrue(users.size() >= 8 && users.size() <= 10);
    }

    @Test
    void shouldFindAllEnabledEmployees() {
        List<AppUser> users = repository.findAllEnabledEmployees();
        assertTrue(users.size() >= 3 && users.size() <= 5);
    }

    @Test
    void shouldFindAllDisabledEmployees() {
        List<AppUser> users = repository.findAllDisabledEmployees();
        assertEquals(0,users.size());
    }

    @Test
    void shouldFindAllEnabledCustomers() {
        List<AppUser> users = repository.findAllEnabledCustomers();
        assertTrue(users.size() >= 3 && users.size() <= 5);
    }

    @Test
    void shouldFindAllDisabledCustomers() {
        List<AppUser> users = repository.findAllDisabledCustomers();
        assertEquals(0,users.size());
    }



     @Test
     void shouldFindById() {
         assertEquals(repository.findById(2).getFirstName(), "Adam");
     }

    @Test
    void shouldFindByUserType() {
        List<AppUser> users = repository.findByUserType("admin");
        assertEquals(1,users.size());
    }



     @Test
     void shouldAdd() {

         AppUser temp = new AppUser();
         temp.setFirstName("test first name");
         temp.setLastName("test last name");
         temp.setBio("test bio");
         temp.setImageUrl("test url");
         temp.setPhone("test phone");
         temp.setEmail("test email");
         temp.setPassword("test password");
         temp.setUserType("customer");

         AppUser result =repository.add(temp);
         assertEquals(temp.getFirstName(),result.getFirstName());


     }

    @Test
    void shouldUpdate() {
        AppUser temp = repository.findById(3);
        temp.setFirstName("test update");
        assertTrue(repository.update(temp));
        assertEquals("test update",repository.findById(3).getFirstName());

    }

    @Test
    void shouldDelete() {

        assertTrue(repository.delete(4));
    }



 }
