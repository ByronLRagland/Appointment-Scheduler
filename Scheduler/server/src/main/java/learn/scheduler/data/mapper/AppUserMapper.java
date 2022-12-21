package learn.scheduler.data.mapper;
import learn.scheduler.model.AppUser;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppUserMapper implements RowMapper<AppUser> {

    @Override
    public AppUser mapRow(ResultSet resultSet, int i) throws SQLException {


        AppUser appUser = new AppUser();
        appUser.setUserId(resultSet.getInt("user_id"));
        appUser.setFirstName(resultSet.getString("first_name"));
        appUser.setLastName(resultSet.getString("last_name"));
        appUser.setBio(resultSet.getString("bio"));
        appUser.setImageUrl(resultSet.getString("url_image"));
        appUser.setPhone(resultSet.getString("phone"));
        appUser.setEmail(resultSet.getString("email"));
        appUser.setPassword(resultSet.getString("password"));
        appUser.setEnabled(resultSet.getBoolean("enabled"));

        String userType = resultSet.getString("user_type");
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userType);
        authorities.add(authority);
        
        appUser.setAuthorities(authorities);

        return appUser;
    }
    
}
