package learn.scheduler.domain;

import learn.scheduler.data.AppointmentRepository;
import learn.scheduler.data.mapper.AppointmentMapper;
import learn.scheduler.model.Appointment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {


    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public List<Appointment> findAll() {
        return repository.findAll();
    }

    public Appointment findById(int aId) {
        return repository.findById(aId);
    }

    public List<Appointment> findByEmployeeId(int employeeId)
    {
        return repository.findAll()
        .stream()
        .filter(i -> i.getEmployeeId()==employeeId)
        .collect(Collectors.toList());

    }

    public List<Appointment> findByCustomerId(int customerId)
    {
        return repository.findAll()
                .stream()
                .filter(i -> i.getCustomerId()==customerId)
                .collect(Collectors.toList());

    }

    public Result<Appointment> add(Appointment a) {
        Result<Appointment> result = validate(a, true);
        if (!result.isSuccess()) {
            return result;
        }

        if (a.getAppointmentId() != 0) {
            result.addMessage("appointmentId cannot be set for `add` operation", ResultType.INVALID);
            // System.out.println("bam");
            return result;
        }

        a = repository.add(a);
        result.setPayload(a);
        return result;
    }

    public Result<Appointment> update(Appointment a) {
        Result<Appointment> result = validate(a, false);
        if (!result.isSuccess()) {
            return result;
        }

        if (a.getAppointmentId() <= 0) {
            result.addMessage("appointmentId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(a)) {
            String msg = String.format("appointmentId: %s, not found", a.getEmployeeId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int aId) {
        return repository.deleteById(aId);
    }

    public boolean cancelCustomerAppointmentById(int appointmentId){
        return repository.cancelCustomerAppointmentById(appointmentId);
    }

    private Result<Appointment> validate(Appointment a, boolean isAdd) {
        Result<Appointment> result = new Result<>();
        if (a == null) {
            result.addMessage("appointment cannot be null", ResultType.INVALID);
            // System.out.println("bam1");
            return result;
        }

        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());

        if (a.getStartTime()==null)
        {
            result.addMessage("Invalid start time", ResultType.INVALID);
            // System.out.println("bam2");
            return result;  
        }

        if (a.getStartTime().before(now))
        {
            result.addMessage("Start time is before now", ResultType.INVALID);
            System.out.println("start time: " + a.getStartTime());
            return result; 
        }

        if (a.getEndTime()==null)
        {
            result.addMessage("Invalid end time", ResultType.INVALID);
            // System.out.println("bam3");
            return result;  
        }

        if (a.getEndTime().before(now))
        {
            result.addMessage("End time is before now", ResultType.INVALID);
            System.out.println("end time: " + a.getEndTime());
            return result; 
        }
        
        if (!a.getStartTime().before(a.getEndTime()))
        {
            result.addMessage("start time cannot be after/equal end time", ResultType.INVALID);
            // System.out.println("bam4");
            return result;  
        }


        boolean serviceExists = true;

        if (a.getServiceId() != 1)
        {
            repository.serviceExists(a.getServiceId());
        }
        
        
        if (!serviceExists)
        {
            result.addMessage("service does not exist", ResultType.INVALID);
            // System.out.println("bam5");
            return result;  
        }

        boolean customerExists = true;
        if (a.getCustomerId() != 1)
        {
            repository.customerExists(a.getCustomerId());
        }
       
        if (!customerExists)
        {
            result.addMessage("customer does not exist", ResultType.INVALID);
            // System.out.println("bam6");
            return result;  
        }

        boolean employeeExists = repository.employeeExists(a.getEmployeeId());
    
        if (!employeeExists)
        {
            result.addMessage("employee does not exist", ResultType.INVALID);
            // System.out.println("bam7");
            return result;  
        }

        List<Appointment> temp = repository.findAll();
        if (isAdd)
        {
            //no duplicate appointments being made by the same customer. 
            //(this is okay for update if nothing is meant to be updated)
            for (int i =0; i<temp.size();i++)
            {
                if (a.getCustomerId()==temp.get(i).getCustomerId() && 
                    a.getEmployeeId()==temp.get(i).getEmployeeId() &&
                    a.getServiceId()==temp.get(i).getServiceId() &&
                    a.getStartTime().equals(temp.get(i).getStartTime()) &&
                    a.getEndTime().equals(temp.get(i).getEndTime()))
                    {
                        result.addMessage("duplicate appointment.", ResultType.INVALID);
                        return result;
                    }
            }
    
        }

        //No different customers booking the same employee at the same time.

        for (int i =0; i<temp.size(); i++)
        {
            if (a.getEmployeeId() == temp.get(i).getEmployeeId())
            {
                if (isOverlap(a.getStartTime(), a.getEndTime(), temp.get(i).getStartTime(), temp.get(i).getEndTime()))
                {
                    if (isAdd)
                    {
                        result.addMessage("Employee is already booked", ResultType.INVALID);
                        System.out.println("Problem1");
                        return result;
                    }

                    // else
                    // {
                    //     if(!(temp.get(i).getCustomerId()==a.getCustomerId()))
                    //     {
                    //         result.addMessage("Employee is already booked", ResultType.INVALID);
                    //         return result;
                    //     }
                    // }


                }

            }
            
        }

        return result;
    }

    private boolean isOverlap(Timestamp start1, Timestamp end1, Timestamp start2, Timestamp end2)
    {
        
        if (start1.before(start2) && end1.before(end2)||
            start1.before(start2) && end1.after(end2) ||
            start1.after(start2) && end1.before(end2) ||
            start1.after(start2) && end1.after(end2) ||
            start1.equals(start2) || end1.equals(end2))
        {
            if (!(end1.before(start2) || start1.after(end2)))
            {
                return true;
            }

        }
        return false;
    }

    
}
