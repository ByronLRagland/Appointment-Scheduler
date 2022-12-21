package learn.scheduler.domain;

import org.junit.jupiter.api.Test;
import learn.scheduler.data.*;
import learn.scheduler.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;

import java.math.*;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

public class AppointmentServiceTest {
    @Autowired
    AppointmentService appointmentService;

    @MockBean
    AppointmentRepository repository;

    @Test
    void shouldNotAddEndBeforeStartTime() {

        Appointment appointment = makeAppointment();
        Timestamp end = new Timestamp(122, 11, 10, 10, 8, 7, 0);

        appointment.setEndTime(end);

        Result<Appointment> actual = appointmentService.add(appointment);
        assertEquals(ResultType.INVALID, actual.getType());

    }

    @Test
    void shouldNotAddPreviousDateTime() {

        Appointment appointment = makeAppointment();
        Timestamp end = new Timestamp(120, 2, 10, 10, 8, 7, 0);

        appointment.setEndTime(end);

        Result<Appointment> actual = appointmentService.add(appointment);
        assertEquals(ResultType.INVALID, actual.getType());

    }

    @Test
    void shouldNotUpdateNoId() {

        Appointment appointment = makeAppointment();
        Result<Appointment> actual = appointmentService.update(appointment);
        assertEquals(ResultType.INVALID, actual.getType());

    }



    Appointment makeAppointment() {
        Appointment appointment = new Appointment();
        Timestamp start = new Timestamp(122, 11, 12, 9, 8, 7, 0);
        Timestamp end = new Timestamp(122, 11, 12, 10, 8, 7, 0);

        appointment.setStartTime(start);
        appointment.setEndTime(end);
        appointment.setCustomerId(1);
        appointment.setEmployeeId(1);
        appointment.setServiceId(1);

        return appointment;
    }
}
