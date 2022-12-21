package learn.scheduler.controller;



import learn.scheduler.model.AppUser;
import learn.scheduler.security.JwtConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.HashMap;

@RestController
//@CrossOrigin(origins={"http:127.0.0.1:3000"})
@ConditionalOnWebApplication
public class AuthController {

    private final AuthenticationManager manager;
    private final JwtConverter converter;
    private final PasswordEncoder encoder;

    public AuthController(AuthenticationManager manager, JwtConverter converter, PasswordEncoder encoder) {
        this.manager = manager;
        this.converter = converter;
        this.encoder = encoder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AppUser user) {

        //System.out.println(user.getEmail());
        //System.out.println(user.getPassword());

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        try {
            Authentication authentication = manager.authenticate(token);
            if (authentication.isAuthenticated()) {
                HashMap<String, String> body = new HashMap<>();
                String jwt = converter.userToToken((AppUser) authentication.getPrincipal());
                body.put("jwt", jwt);
                return new ResponseEntity<>(body, HttpStatus.OK);
            }
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@AuthenticationPrincipal AppUser user) {

        String jwt = converter.userToToken(user);
        HashMap<String, String> body = new HashMap<>();
        body.put("jwt", jwt);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/encode/{value}")
    public String encode(@PathVariable String value) {
        String encodedValue = encoder.encode(value);
       // System.out.println(value);
        //System.out.println(encodedValue);
        return encodedValue;
    }

}
