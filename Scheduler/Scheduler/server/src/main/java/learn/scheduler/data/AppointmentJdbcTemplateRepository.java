package learn.scheduler.data;

import java.util.List;

import learn.scheduler.data.mapper.AppointmentMapper;
import learn.scheduler.model.Appointment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class AppointmentJdbcTemplateRepository implements AppointmentRepository{

    private final JdbcTemplate jdbcTemplate;

    public AppointmentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Appointment> findAll() {
        final String sql = "select appointment_id, start_time, end_time, employee_id, employee_name, customer_id, customer_name, service_id, service_name "
        + "from appointment limit 1000;";
        return jdbcTemplate.query(sql, new AppointmentMapper());
    }

    @Override
    public Appointment findById(int appointmentId) {
        final String sql = "select appointment_id, start_time, end_time, employee_id, employee_name, customer_id, customer_name, service_id, service_name "
        + "from appointment "
        + "where appointment_id = ?;";

        Appointment appointment = jdbcTemplate.query(sql, new AppointmentMapper(), appointmentId).stream()
        .findFirst().orElse(null);

        return appointment;
    }

    @Override
    public Appointment add(Appointment appointment) {
        final String sql = "insert into appointment (start_time, end_time, employee_id, employee_name, customer_id, customer_name, service_id, service_name) "
                + " values (?,?,?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, appointment.getStartTime());
            ps.setTimestamp(2, appointment.getEndTime());
            ps.setInt(3, appointment.getEmployeeId());
            ps.setString(4, appointment.getEmployeeName());
            ps.setInt(5, appointment.getCustomerId());
            ps.setString(6, appointment.getCustomerName());
            ps.setInt(7, appointment.getServiceId());
            ps.setString(8, appointment.getServiceName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        appointment.setAppointmentId(keyHolder.getKey().intValue());
        return appointment;

    }

    @Override
    public boolean update(Appointment appointment) {
        final String sql = "update appointment set "
        + "start_time = ?, "
        + "end_time = ?, "
        + "employee_id = ?, "
        + "employee_name = ?, "
        + "customer_id = ?, "
        + "customer_name = ?, "
        + "service_id = ?, "
        + "service_name = ?"
        + "where appointment_id = ?;";

        return jdbcTemplate.update(sql,
            appointment.getStartTime(),
            appointment.getEndTime(),
            appointment.getEmployeeId(),
            appointment.getEmployeeName(),
            appointment.getCustomerId(),
            appointment.getCustomerName(),
            appointment.getServiceId(),
            appointment.getServiceName(),
            appointment.getAppointmentId()) > 0;
               
    }

    @Override
    public boolean deleteById(int appointmentId) {
        return jdbcTemplate.update("delete from appointment where appointment_id = ?;", appointmentId) > 0;
    }

    @Override
    public boolean cancelCustomerAppointmentById(int appointmentId){
        return jdbcTemplate.update("update appointment set" +
                " customer_name = null," +
                " service_name = null," +
                " customer_id = 1," +
                " service_id = 1" +
                " where appointment_id = ?;", appointmentId) > 0;
    }

    public boolean customerExists(int customerId)
    {

        final String sql = "select count(email) "
                + "from app_user "
                + "where user_id = ? "
                + "AND "
                + "user_type = 'customer';";

                 int count = jdbcTemplate.queryForObject(sql, Integer.class, customerId);

        return count > 0;
    }

    public boolean serviceExists(int serviceId)
    {

        final String sql = "select count(service_id) "
                + "from service "
                + "where service_id = ?";

                 int count = jdbcTemplate.queryForObject(sql, Integer.class, serviceId);

        return count > 0;
    }

    public boolean employeeExists(int user_id)
    {

        final String sql = "select count(email) "
                + "from app_user "
                + "where user_id = ? "
                + "AND "
                + "user_type = 'employee'";

                 int count = jdbcTemplate.queryForObject(sql, Integer.class, user_id);

        return count > 0;
    }

    
}
