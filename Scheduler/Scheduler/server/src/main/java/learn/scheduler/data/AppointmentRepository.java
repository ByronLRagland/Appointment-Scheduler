package learn.scheduler.data;

import learn.scheduler.model.Appointment;
import learn.scheduler.model.Employee;

import java.util.List;

public interface AppointmentRepository {
    List<Appointment> findAll();

    Appointment findById(int appointmentId);

    Appointment add(Appointment appointment);

    boolean update(Appointment appointment);

    boolean deleteById(int appointmentId);

    boolean serviceExists(int serviceId);

    boolean cancelCustomerAppointmentById(int appointmentId);

    boolean customerExists(int customerId);

    boolean employeeExists(int employeeId);

}
