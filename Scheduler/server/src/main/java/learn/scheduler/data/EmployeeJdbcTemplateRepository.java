package learn.scheduler.data;

import java.util.List;

import learn.scheduler.data.mapper.EmployeeMapper;
import learn.scheduler.model.Employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class EmployeeJdbcTemplateRepository implements EmployeeRepository{

    private final JdbcTemplate jdbcTemplate;

    public EmployeeJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> findAll() {

        final String sql = "select employee_id, first_name, last_name, bio, url_image "
        + "from employee limit 1000;";
        return jdbcTemplate.query(sql, new EmployeeMapper());
    }

    @Override
    public Employee findById(int employeeId) {
        final String sql = "select employee_id, first_name, last_name, bio, url_image "
        + "from employee "
        + "where employee_id = ?;";

        Employee employee = jdbcTemplate.query(sql, new EmployeeMapper(), employeeId).stream()
        .findFirst().orElse(null);

        return employee;
    }

    @Override
    public Employee add(Employee employee) {

        final String sql = "insert into employee (first_name, last_name, bio, url_image) "
                + " values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getBio());
            ps.setString(4, employee.getImageUrl());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        employee.setEmployeeId(keyHolder.getKey().intValue());
        return employee;

    }

    @Override
    public boolean update(Employee employee) {

        final String sql = "update employee set "
                + "first_name = ?, "
                + "last_name = ?, "
                + "bio = ?, "
                + "url_image = ? "
                + "where employee_id = ?;";

        return jdbcTemplate.update(sql,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getBio(),
                employee.getImageUrl(),
                employee.getEmployeeId())> 0;
    }

    @Override
    public boolean deleteById(int employeeId) {
        jdbcTemplate.update("delete from appointment where employee_id = ?;", employeeId);
        return jdbcTemplate.update("delete from employee where employee_id = ?;", employeeId) > 0;
    }

    
    
}
