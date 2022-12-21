package learn.scheduler.controller;

import learn.scheduler.domain.AppUserServices;
import learn.scheduler.domain.Result;
import learn.scheduler.model.AppUser;
import learn.scheduler.security.JwtConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://127.0.0.1:5500"})
//@CrossOrigin(origins={"http:127.0.0.1:3000"})
@RequestMapping("/api/appuser")
@ConditionalOnWebApplication
public class AppUserController {

    private final AppUserServices service;
    private final PasswordEncoder encoder;

    private final  HttpServletRequest request;

    private final JwtConverter converter;

    public AppUserController(AppUserServices service, PasswordEncoder encoder, HttpServletRequest request, JwtConverter converter) {
        this.service = service;
        this.encoder = encoder;
        this.request = request;
        this.converter = converter;
    }


    @GetMapping
    public List<AppUser> findAll() {
        return service.findAll();
    }

    @GetMapping("/users/employees/enabled")
    public List<AppUser> findAllActiveEmployees() {
        return service.findAllActiveEmployees();
    }

    @GetMapping("/users/employees/disabled")
    public List<AppUser> findAllaInactiveEmployees() {
        return service.findAllInactiveEmployees();
    }

    @GetMapping("/users/customers/enabled")
    public List<AppUser> findAllActiveCustomers() {
        return service.findAllActiveCustomers();
    }

    @GetMapping("/users/customers/disabled")
    public List<AppUser> findAllInactiveCustomers() {
        return service.findAllInactiveCustomers();
    }


    @GetMapping("/usertype/{userType}")
    public List<AppUser> findByUserType(@PathVariable String userType) {
        return service.findByUserType(userType);
    }

    @GetMapping("/username/{email}")
    public AppUser findByUsername(@PathVariable String email)
    {
        return service.findByUsername(email);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findById(@PathVariable int userId) {

        AppUser verifiedUser = null;
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            verifiedUser = converter.tokenToUser(authorization);
        }

        AppUser user = service.findById(userId);
        user.setPassword("");
        if (user.getBio()==null) {
            user.setBio("");
        }
        if (user.getImageUrl()==null){
            user.setImageUrl("");
        }
        if(user.getPhone()==null){
            user.setPhone("");
        }


        if (verifiedUser == null || verifiedUser.getUserId() != user.getUserId()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(user,HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody AppUser user) {
        // System.out.println(user.getEmail());

        Result<AppUser> result = service.add(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping("/customer")
    public ResponseEntity<Object> addCustomer(@RequestBody AppUser user) {
        // System.out.println(user.getEmail());
        user.setUserType("customer");
        user.setBio(null);
        user.setImageUrl(null);
        if (user.getPassword()==null || user.getPassword().isBlank()) {
            user.setPassword(null);
        }else{
            user.setPassword(encode(user.getPassword()));
        }

        Result<AppUser> result = service.add(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping("/employee")
    public ResponseEntity<Object> addEmployee(@RequestBody AppUser user) {
        // System.out.println(user.getEmail());
        user.setUserType("employee");
        user.setBio("TBD");
        user.setImageUrl("https://media.istockphoto.com/vectors/default-avatar-photo-placeholder-icon-grey-profile-picture-business-vector-id1327592449?k=20&m=1327592449&s=612x612&w=0&h=6yFQPGaxmMLgoEKibnVSRIEnnBgelAeIAf8FqpLBNww=");
        user.setPassword(encode(user.getPassword()));

        Result<AppUser> result = service.add(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> update(@PathVariable int userId, @RequestBody AppUser user) {

        if (userId != user.getUserId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        AppUser verifiedUser = null;
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            verifiedUser = converter.tokenToUser(authorization);
        }

        if (verifiedUser == null || verifiedUser.getUserId() != user.getUserId()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }


        user.setUserId(verifiedUser.getUserId());
        user.setUserType(verifiedUser.getUserType());
        if(user.getPassword()==null || user.getPassword()=="" ){
            user.setPassword(service.findById(verifiedUser.getUserId()).getPassword());
        }else{
            user.setPassword(encode(user.getPassword()));
        }
        Result<AppUser> result = service.update(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/enable/{userId}")
    public ResponseEntity<Void> enable(@PathVariable int userId) {
        if (service.enableUser(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/disable/{userId}")
    public ResponseEntity<Void> disable(@PathVariable int userId) {
        if (service.disableUser(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable int userId) {
        if (service.deleteById(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public String encode(@PathVariable String value) {
        String encodedValue = encoder.encode(value);
        // System.out.println(value);
        //System.out.println(encodedValue);
        return encodedValue;
    }
    
    
}
