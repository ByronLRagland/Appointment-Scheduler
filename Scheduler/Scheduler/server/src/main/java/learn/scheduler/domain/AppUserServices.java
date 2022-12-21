package learn.scheduler.domain;

import learn.scheduler.data.AppUserRepository;
import learn.scheduler.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserServices {

    private final AppUserRepository repository;

    public AppUserServices(AppUserRepository repository) {
        this.repository = repository;
    }

    public List<AppUser> findAll() {
        return repository.findAll();
    }

    public List<AppUser> findAllActiveEmployees() {
        return repository.findAllEnabledEmployees();
    }

    public List<AppUser> findAllInactiveEmployees() {
        return repository.findAllDisabledEmployees();
    }

    public List<AppUser> findAllActiveCustomers() {
        return repository.findAllEnabledCustomers();
    }
    public List<AppUser> findAllInactiveCustomers() {
        return repository.findAllDisabledCustomers();
    }


    public List<AppUser> findByUserType(String type){

        return repository.findByUserType(type);

    }

    public AppUser findByUsername(String email)
    {
        return repository.findByUsername(email);
    }

    public AppUser findById(int appUserId)
    {
        return repository.findById(appUserId);
    }

    public Result<AppUser> add(AppUser user) {
        Result<AppUser> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
            result.addMessage("UserId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        user = repository.add(user);
        result.setPayload(user);
        return result;
    }


    public Result<AppUser> update(AppUser user) {
        Result<AppUser> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() <= 0) {
            result.addMessage("userId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(user)) {
            String msg = String.format("userId: %s, not found",  user.getUserId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean enableUser(int userId){
        return repository.enableUser(userId);
    }

    public boolean disableUser(int userId){
        return repository.disableUser(userId);
    }


    public boolean deleteById(int appUserId) {

        if (!repository.delete(appUserId)) {
            return false;
        }

        return true;
    }

    private Result<AppUser> validate(AppUser user) {
        Result<AppUser> result = new Result<>();
        if (user == null) {
            result.addMessage("AppUser cannot be null", ResultType.INVALID);
            return result;
        }

        // private int userId;//Admin, Customer, Employee
    
        // private String firstName; //Admin, Customer, Employee
        // private String lastName; //Admin, Customer, Employee
        // private String bio; //Employee
        // private String imageUrl; //Employee
        // private String phone; //Customer
    
        // private String email; //Admin, Customer, Employee
        // private String password; //Admin, Customer, Employee
    
        // private String userType; //Admin, Customer, Employee

        //Global validation:

        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            result.addMessage("First Name is required", ResultType.INVALID);
        }

        if (user.getLastName() == null || user.getLastName().isBlank()) {
            result.addMessage("Last Name is required", ResultType.INVALID);
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            result.addMessage("Email is required", ResultType.INVALID);
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            result.addMessage("Password is required", ResultType.INVALID);
        }

        if (isEmailDuplicated(user)){
            result.addMessage("Email already in use", ResultType.INVALID);
        }

        //Employee validaton: 
        if (user.getUserType().equals("employee"))
        {
            if (user.getBio() == null || user.getBio().isBlank()) {
                result.addMessage("Bio is required for employees", ResultType.INVALID);
            }

            if (user.getImageUrl() == null || user.getImageUrl().isBlank()) {
                result.addMessage("ImageUrl is required for employees", ResultType.INVALID);
            }
        }

        //Customer validation:
        if (user.getUserType().equals("customer"))
        {
            if (user.getPhone()==null || user.getPhone().isBlank())
            {
                result.addMessage("Phone Number is required for customers", ResultType.INVALID);
            }
        }

        return result;
    }

    private boolean isEmailDuplicated(AppUser user){

        List<AppUser> all = findAll();
        for (AppUser obj : all){
            if (obj.getEmail().equalsIgnoreCase(user.getEmail()) &&
                    obj.getUserId()!= user.getUserId()){
                return true;
            }
        }
        return false;
    }

}
