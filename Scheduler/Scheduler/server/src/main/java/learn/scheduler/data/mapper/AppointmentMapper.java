package learn.scheduler.data.mapper;
import learn.scheduler.model.Appointment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// create table appointment (
// appointment_id int primary key auto_increment,
// start_time timestamp not null,
// end_time timestamp not null,
// employee_id int not null,
// employee_name varchar(20) not null,
// customer_id int,
// customer_name varchar(20),
// service_id int,
// service_name varchar(20),
// constraint fk_appointment_employee_id
// foreign key (employee_id)
// references app_user(user_id),
// constraint fk_appointment_customer_id
// foreign key (customer_id)
// references app_user(user_id),
// constraint fk_appointment_service_id
// foreign key (service_id)
// references service(service_id)
// );


public class AppointmentMapper implements RowMapper<Appointment> {

    @Override
    public Appointment mapRow(ResultSet resultSet, int i) throws SQLException {

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(resultSet.getInt("appointment_id"));
        appointment.setStartTime(resultSet.getTimestamp("start_time"));
        appointment.setEndTime(resultSet.getTimestamp("end_time"));
        appointment.setEmployeeId(resultSet.getInt("employee_id"));
        appointment.setEmployeeName(resultSet.getString("employee_name"));
        appointment.setCustomerId(resultSet.getInt("customer_id"));
        appointment.setCustomerName(resultSet.getString("customer_name"));
        appointment.setServiceId(resultSet.getInt("service_id"));
        appointment.setServiceName(resultSet.getString("service_name"));

        return appointment;
    }
}