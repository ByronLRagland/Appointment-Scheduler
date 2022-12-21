package learn.scheduler.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppUser implements UserDetails {

    private int userId;//Admin, Customer, Employee
    
    private String firstName; //Admin, Customer, Employee
    private String lastName; //Admin, Customer, Employee
    private String bio; //Employee
    private String imageUrl; //Employee
    private String phone; //Customer

    private String email; //Admin, Customer, Employee
    private String password; //Admin, Customer, Employee

    private String userType;
    private boolean enabled;

    //private String userType; //Admin, Customer, Employee
    private List<GrantedAuthority> authorities = new ArrayList<>();



    public AppUser()
   {
        
   }

   

    public AppUser(int userId, String firstName, String lastName, String bio, String imageUrl, String phone, String email,
        String password, String userType, List<GrantedAuthority> authorities) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.bio = bio;
    this.imageUrl = imageUrl;
    this.phone = phone;
    this.email = email;
    this.password = password;
    this.userType = userType;
    this.authorities = authorities;
}



    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.userType = authorities.get(0).toString();
    }


    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType(){
        return this.userType;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    @Override
    public boolean isEnabled() {
       return enabled;
    }


}
