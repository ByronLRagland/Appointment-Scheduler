package learn.scheduler.data;

import learn.scheduler.data.mapper.CustomerMapper;
import learn.scheduler.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CustomerJdbcTemplateRepository implements CustomerRepository  {
    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        final String sql = "select customer_id, customer_name, phone_number, email from customer;";
        return jdbcTemplate.query(sql, new CustomerMapper());
    }

    @Override
    public Customer findById(int customerId) {
        final String sql = "select customer_id, customer_name, phone_number, email "
                + "from customer "
                + "where customer_id = ?;";

        return jdbcTemplate.query(sql, new CustomerMapper(), customerId).stream()
                .findFirst().orElse(null);

    }

    @Override
    public Customer add(Customer customer) {
        final String sql = "insert into customer (customer_name, phone_number, email) "
                + " values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getPhoneNumber());
            ps.setString(3, customer.getEmail());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        customer.setCustomerId(keyHolder.getKey().intValue());
        return customer;
    }

    @Override
    public boolean update(Customer customer) {
        final String sql = "update customer set "
                + "customer_name = ?, "
                + "phone_number = ?, "
                + "email = ? "
                + "where customer_id = ?;";

        return jdbcTemplate.update(sql,
                customer.getCustomerName(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                customer.getCustomerId())>0;
    }


    @Override
    @Transactional
    public boolean deleteById(int customerId) {
        jdbcTemplate.update("delete from appointment where customer_id = ?;", customerId);
        return jdbcTemplate.update("delete from customer where customer_id = ?;", customerId) > 0;
    }
}
