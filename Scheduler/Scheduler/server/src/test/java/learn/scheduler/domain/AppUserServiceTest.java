package learn.scheduler.domain;

import learn.scheduler.data.AppUserRepository;
import learn.scheduler.data.CustomerRepository;
import learn.scheduler.model.AppUser;
import learn.scheduler.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

public class AppUserServiceTest {

    @Autowired
    AppUserServices service;

    @MockBean
    AppUserRepository repository;

    @Test
    void shouldAddWhenValid() {
        AppUser expected = makeUser();
        AppUser arg = makeUser();
        arg.setUserId(0);

        when(repository.add(arg)).thenReturn(expected);
        Result<AppUser> result = service.add(arg);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalid() {
        AppUser user = makeUser();
        user.setUserId(0);
        user.setFirstName("  ");
        Result<AppUser> actual = service.add(user);
        assertEquals(ResultType.INVALID, actual.getType());

        user = makeUser();
        user.setUserId(0);
        user.setEmail("  ");
        actual = service.add(user);
        assertEquals(ResultType.INVALID, actual.getType());

        user = makeUser();
        user.setUserId(0);
        user.setBio("  ");
        user.setUserType("employee");
        actual = service.add(user);
        assertEquals(ResultType.INVALID, actual.getType());

        user = makeUser();
        user.setUserId(0);
        user.setPhone("  ");
        user.setUserType("customer");
        actual = service.add(user);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        AppUser user = makeUser();
        user.setFirstName("  ");
        Result<AppUser> actual = service.add(user);
        assertEquals(ResultType.INVALID, actual.getType());

        user = makeUser();
        user.setEmail("  ");
        actual = service.add(user);
        assertEquals(ResultType.INVALID, actual.getType());

        user = makeUser();
        user.setBio("  ");
        user.setUserType("employee");
        actual = service.add(user);
        assertEquals(ResultType.INVALID, actual.getType());

        user = makeUser();
        user.setPhone("  ");
        user.setUserType("customer");
        actual = service.add(user);
        assertEquals(ResultType.INVALID, actual.getType());
    }


    AppUser makeUser() {
        AppUser user = new AppUser();
        user.setUserId(1);
        user.setFirstName("test first name");
        user.setLastName("test last name");
        user.setBio("test bio");
        user.setImageUrl("test url");
        user.setPhone("test phone");
        user.setEmail("test email");
        user.setPassword("test password");
        user.setUserType("customer");
        return user;
    }


}
