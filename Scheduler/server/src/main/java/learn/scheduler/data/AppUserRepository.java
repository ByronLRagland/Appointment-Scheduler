package learn.scheduler.data;

import java.util.List;

import learn.scheduler.model.AppUser;

public interface AppUserRepository {

    List<AppUser> findAll();


    List<AppUser> findAllEnabledEmployees();

    List<AppUser> findAllDisabledEmployees();

    List<AppUser> findAllEnabledCustomers();

    List<AppUser> findAllDisabledCustomers();

    List<AppUser> findByUserType(String userType);

    AppUser findById(int userId);

    AppUser findByUsername(String email);

    AppUser add(AppUser appUser);

    boolean update(AppUser appUser);

    boolean enableUser(int userId);

    boolean disableUser(int userId);

    boolean delete(int userId);
    
}
