package learn.scheduler.data;

import learn.scheduler.data.mapper.*;
import learn.scheduler.model.Services;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.sql.*;
import java.util.*;

@Repository
public class ServicesJdbcTemplateRepository implements ServicesRepository {

    private final JdbcTemplate jdbcTemplate;

    public ServicesJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Services> findAll() {
        final String sql = "select service_id, service_name, service_description, duration, price "
                + "from service limit 1000;";
        return jdbcTemplate.query(sql, new ServiceMapper());
    }

    @Override
    public Services findById(int serviceId) {
        final String sql = "select service_id, service_name, service_description, duration, price "
                + "from service "
                + "where service_id = ?;";

        return jdbcTemplate.query(sql, new ServiceMapper(), serviceId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Services> findAllEnabled() {
        final String sql = "select service_id, service_name, service_description, duration, price "
                + "from service where enabled=true limit 1000;";
        return jdbcTemplate.query(sql, new ServiceMapper());
    }

    @Override
    public List<Services> findAllDisabled() {
        final String sql = "select service_id, service_name, service_description, duration, price "
                + "from service where enabled=false limit 1000;";
        return jdbcTemplate.query(sql, new ServiceMapper());
    }


    @Override
    public Services add(Services service) {
        final String sql = "insert into service (service_name, service_description, duration, price) "
                + " values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, service.getServiceName());
            ps.setString(2, service.getServiceDescription());
            ps.setInt(3, service.getDuration());
            ps.setBigDecimal(4, service.getPrice());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        service.setServiceId(keyHolder.getKey().intValue());
        return service;
    }

    @Override
    public boolean update(Services service) {
        final String sql = "update service set "
                + "service_name = ?, "
                + "service_description = ?, "
                + "duration = ?, "
                + "price = ? "
                + "where service_id = ?;";

        return jdbcTemplate.update(sql,
                service.getServiceName(),
                service.getServiceDescription(),
                service.getDuration(),
                service.getPrice(),
                service.getServiceId()) > 0;
    }

    @Override
    public boolean enableService(int serviceId) {
        final String sql = "update service set "
                + "enabled = ? "
                + "where service_id = ?;";

        return jdbcTemplate.update(sql,
                true,
                serviceId) > 0;
    }

    @Override
    public boolean disableService(int serviceId) {
        final String sql = "update service set "
                + "enabled = ? "
                + "where service_id = ?;";

        return jdbcTemplate.update(sql,
                false,
                serviceId) > 0;
    }


    @Transactional
    @Override
    public boolean deleteById(int serviceId) {
        jdbcTemplate.update("delete from appointment where service_id = ?;", serviceId);
        return jdbcTemplate.update("delete from service where service_id = ?;", serviceId) > 0;
    }
}
