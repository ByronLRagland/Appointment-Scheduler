package learn.scheduler.data;

import static org.junit.jupiter.api.Assertions.*;

import learn.scheduler.App;
import learn.scheduler.model.Appointment;
import learn.scheduler.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppointmentJdbcTemplateRepositoryTest
{
    @Autowired
    AppointmentJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Appointment> appointments = repository.findAll();
        assertNotNull(appointments);

        assertTrue(appointments.size() >= 2 && appointments.size() <= 4);
    }

    @Test
    void shouldFindById(){
        Appointment appointment = repository.findById(1);
        assertEquals("2022-11-11 10:00:00.0", appointment.getEndTime().toString());
        assertNotNull(appointment);
    }

    @Test
    void shouldAdd()
    {
        Timestamp start = new Timestamp(122, 11, 12, 9, 8, 7, 0);
        Timestamp end = new Timestamp(122, 11, 12, 9, 8, 7, 0);
        Appointment appointment =
                new Appointment(-1,start, end, 2, "Adam", 8,
                        "Joe" ,2,"haircut");

        assertNotNull(repository.add(appointment));
        assertEquals(repository.findById(4).getEndTime(), end);
    }

    @Test
    void shouldUpdate()
    {
        Timestamp start = new Timestamp(122, 12, 12, 9, 8, 7, 0);
        Appointment a = repository.findById(2);
        a.setStartTime(start);
        repository.update(a);
        assertEquals(repository.update(a), true);
        assertEquals(repository.findById(2).getStartTime(), start);
    }

    @Test
    void shouldDelete()
    {
        assertTrue(repository.deleteById(3));
        assertNull(repository.findById(3));
//         boolean found = false;
//         List<Appointment> appointments = repository.findAll();
//         for (int i =0; i < appointments.size(); i++)
//         {
//             if (appointments.get(i).getServiceId()==1)
//             {
//                 found = true;
//                 break;
//             }
//         }
//         assertFalse(found);
    }

}