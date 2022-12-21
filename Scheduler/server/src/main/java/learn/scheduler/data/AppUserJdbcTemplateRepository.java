package learn.scheduler.data;

import learn.scheduler.data.mapper.AppUserMapper;
import learn.scheduler.model.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository{


    private final JdbcTemplate jdbcTemplate;

    RowMapper<AppUser> mapper = (ResultSet rs, int rowIndex) -> {
        AppUser user = new AppUser();
        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setBio(rs.getString("bio"));
        user.setImageUrl(rs.getString("url_image"));
        user.setPhone(rs.getString("phone"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setEnabled(rs.getBoolean("enabled"));
        return user;
    };

    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AppUser> findAll() {


        final String sql = "select user_id, first_name, last_name, bio, url_image, phone, email, `password`, user_type, enabled from app_user;";

        return jdbcTemplate.query(sql, new AppUserMapper());
    }

    @Override
    public List<AppUser> findAllEnabledEmployees() {


        final String sql = "select user_id, first_name, last_name, bio, url_image, phone, email, `password`, user_type, enabled from app_user "
                + "where user_type= ? and enabled= true;";

        return jdbcTemplate.query(sql, new AppUserMapper(),"employee");
    }

    @Override
    public List<AppUser> findAllDisabledEmployees() {


        final String sql = "select user_id, first_name, last_name, bio, url_image, phone, email, `password`, user_type, enabled from app_user "
                + "where user_type= ? and enabled= false;";

        return jdbcTemplate.query(sql, new AppUserMapper(),"employee");
    }

    @Override
    public List<AppUser> findAllEnabledCustomers() {


        final String sql = "select user_id, first_name, last_name, bio, url_image, phone, email, `password`, user_type, enabled from app_user "
                + "where user_type= ? and enabled= true;";

        return jdbcTemplate.query(sql, new AppUserMapper(),"customer");
    }

    @Override
    public List<AppUser> findAllDisabledCustomers() {


        final String sql = "select user_id, first_name, last_name, bio, url_image, phone, email, `password`, user_type, enabled from app_user "
                + "where user_type= ? and enabled= false;";

        return jdbcTemplate.query(sql, new AppUserMapper(),"customer");
    }



    @Override
    public List<AppUser> findByUserType(String userType) {

        final String sql = "select user_id, first_name, last_name, bio, " + 
        "url_image, phone, email, `password`, user_type, enabled from app_user where user_type = ?;";

        return jdbcTemplate.query(sql, new AppUserMapper(), userType);

    }

    @Override
    public AppUser findById(int userId) {
        
        
        final String sql = "select user_id, first_name, last_name, bio, " + 
        "url_image, phone, email, `password`, user_type, enabled from app_user where user_id = ?;";

        return jdbcTemplate.query(sql, new AppUserMapper(), userId).stream()
        .findFirst().orElse(null);
    }

    @Override
    public AppUser add(AppUser appUser) {
        final String sql = "insert into app_user (first_name, last_name, bio, " + 
        "url_image, phone, email, `password`, user_type) "
                + " values (?,?,?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, appUser.getFirstName());
            ps.setString(2, appUser.getLastName());
            ps.setString(3, appUser.getBio());
            ps.setString(4, appUser.getImageUrl());
            ps.setString(5, appUser.getPhone());
            ps.setString(6, appUser.getEmail());
            ps.setString(7, appUser.getPassword());
            ps.setString(8, appUser.getUserType());

            String userType = appUser.getUserType();
            List<GrantedAuthority> authorities = new ArrayList<>();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userType);
            authorities.add(authority);
            appUser.setAuthorities(authorities);

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        appUser.setUserId(keyHolder.getKey().intValue());
        return appUser;
    }

    @Override
    public boolean update(AppUser appUser) {

        // "select user_id, first_name, last_name, bio, " + 
        // "url_image, phone, email, `password`, user_type from app_user where user_id = ?;";
        final String sql = "update app_user set "
        + "first_name = ?, "
        + "last_name = ?, "
        + "bio = ?, "
        + "url_image = ?, "
        + "phone = ?, "
        + "email = ?, "
        + "password = ?, "
        + "user_type = ? "
        + "where user_id = ?;";

    return jdbcTemplate.update(sql,
        appUser.getFirstName(),
        appUser.getLastName(),
        appUser.getBio(),
        appUser.getImageUrl(),
        appUser.getPhone(),
        appUser.getEmail(),
        appUser.getPassword(),
        appUser.getUserType(),
        appUser.getUserId())>0;

    }

    @Override
    public boolean enableUser(int userId) {
        final String sql = "update app_user set "
                + "enabled = ? "
                + "where user_id = ?;";

        return jdbcTemplate.update(sql,
                true,
                userId) > 0;
    }

    @Override
    public boolean disableUser(int userId) {
        final String sql = "update app_user set "
                + "enabled = ? "
                + "where user_id = ?;";

        return jdbcTemplate.update(sql,
                false,
                userId) > 0;
    }

    @Transactional
    @Override
    public boolean delete(int userId) {

        jdbcTemplate.update("delete from appointment where customer_id = ?;", userId);
        jdbcTemplate.update("delete from appointment where employee_id = ?;", userId);
     
        return jdbcTemplate.update("delete from app_user where user_id = ?;", userId) > 0;
    }
    
    public AppUser findByUsername(String username) {

        String sql = "select user_id, first_name, last_name, bio, url_image, phone, email, password, enabled "
                + "from app_user "
                + "where email = ?;";

        AppUser user = jdbcTemplate.query(sql, mapper, username).stream()
                .findFirst().orElse(null);

        if (user != null) {
            attachAuthorities(user);
        }

        return user;
    }

    private void attachAuthorities(AppUser user) {

        String sql = "select user_type "
                + "from app_user "
                + "where user_id = ?;";

        List<GrantedAuthority> authorities = jdbcTemplate.query(sql, (ResultSet rs, int index) -> {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(rs.getString("user_type"));
            return authority;
        }, user.getUserId());

        user.setAuthorities(authorities);
    }


}
