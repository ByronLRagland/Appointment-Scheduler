import { useEffect, useState } from "react";
import { useHistory, Link, useParams } from "react-router-dom";
import AuthContext from "../../contexts/AuthContext";
import { useContext } from "react";
import { deleteById } from "../../Services/Appointments.js";
import { findByEmployee } from "../../Services/Appointments.js";
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import './overwrite.css';

function AppointmentList() {
  const { user, logout } = useContext(AuthContext);
  const [appointments, setAppointments] = useState([]);
  const [selectedAppointments, setSelectedAppointments] = useState([]);
  const history = useHistory();
  const [errs, setErrs] = useState([]);
  const [customer, setCustomer] = useState(null);

  const [value, setValue] = useState(new Date());

  const getAppointments = () => {
    console.log("hi");
    findByEmployee(user.appUserId)
      .then((data) => {
        setAppointments(data);
      })
      .catch((errs) => {
        if (errs) {
          setErrs(errs);
          console.log(errs);
        } else {
          history.push("/error");
          console.log(errs);
        }
      });
  };

  function updateSelected(id) {
    let temp = selectedAppointments;
    for (let i = 0; i < temp.length; i++) {
      if (temp[i].appointmentId === id) {
        temp.splice(1, 0);
      }
    }
    setSelectedAppointments(temp);
  }

  useEffect(() => {
    if (user) {
      getAppointments();
    }
  }, [user]);

  const handleDeleteAppointment = (appointmentId) => {
    const appointment = appointments.find(
      (appointment) => appointment.appointmentId === appointmentId
    );

    if (
      window.confirm(
        `Delete Appointment at "${formatTimeStamp(appointment.startTime)}"?`
      )
    ) {
      deleteById(appointment.appointmentId)
        .then(getAppointments)
        .then(updateSelected(appointmentId))
        .catch((errs) => {
          if (errs) {
            setErrs(errs);
            console.log(errs);
          } else {
            history.push("/error");
          }
        });

      window.confirm("Appointment has been deleted.");
      window.location.reload(false);
    }
  };

  function formatTimeStamp(timestamp) {
    // // 2022-12-25T00:23:00.000+00:00
    let formattedTime = "";
    let mark = "AM";
    // let year = timestamp.substring(0, 4);
    // let month = timestamp.substring(5, 7);
    // let day = timestamp.substring(8, 10);

    let hour = timestamp.substring(11, 13);
    let minute = timestamp.substring(14, 16);

        let tempHour = parseInt(hour);

        if (tempHour >= 12)
        {
          mark = "PM"
        }
       
        if (tempHour > 12)
        {
          hour = (tempHour-12) + ""; 
        }


        // formattedTime += year + "/" + month + "/" + day + " at " + hour + ":" + minute; 
        let tempDate = new Date(timestamp);
        formattedTime = tempDate.toLocaleDateString('en-us', { weekday:"long", month:"short", day:"numeric"});
        formattedTime += " at " + hour + ":" + minute + mark;

    return formattedTime;
  }

  function formatName(name) {
    // console.log(name);
    if (name === "" || name === "terry") {
      return "TBD";
    } else {
      return name;
    }
  }

  const tileDisabled = ({ activeStartDate, date, view }) => {
    // console.log(appointments);
    let tempList = [];
    let raw, formattedTime, year, month, day;

    for (let i = 0; i < appointments.length; i++) {
      raw = appointments[i].startTime;
      formattedTime = "";
      year = raw.substring(0, 4);
      month = raw.substring(5, 7);
      day = raw.substring(8, 10);
      formattedTime += year + "-" + month + "-" + day;

      // let newDate = new Date(formattedTime);
      let newDate = new Date(raw);

      tempList.push(newDate);
    }

    for (let i = 0; i < tempList.length; i++) {
      if (
        date.getFullYear() === tempList[i].getFullYear() &&
        date.getMonth() === tempList[i].getMonth() &&
        date.getDate() === tempList[i].getDate()
      ) {
        return false;
      }
    }

    return true;
  };

  const onClickDay = (clickedDay) => {
    console.log(clickedDay);
    setValue(clickedDay);
    console.log(value);

    let raw, formattedTime, year, month, day;
    let tempList = [];

    for (let i = 0; i < appointments.length; i++) {
      raw = appointments[i].startTime;
      formattedTime = "";
      year = raw.substring(0, 4);
      month = raw.substring(5, 7);
      day = raw.substring(8, 10);
      formattedTime += year + "-" + month + "-" + day;

      let newDate = new Date(raw);

          if (clickedDay.getFullYear() === newDate.getFullYear() &&
              clickedDay.getMonth() === newDate.getMonth() &&
              clickedDay.getDate() == newDate.getDate())
              {
                tempList.push(appointments[i]);
              }
        }
        
        setSelectedAppointments(tempList);

     }
     const sortDates = {
      fn: (a, b) => new Date(a.startTime) - new Date(b.startTime),
    };

  return (
    <>
      <div className="container">
        <h2 className="mt-5">Your Appointments</h2>
        <div className="row mt-5">
          <div className="col-lg-4">
            <Calendar
              value={value}
              onClickDay={onClickDay}
              tileDisabled={tileDisabled}
            />
          </div>

              <div className="col-lg-8">
                <table className="table table-striped">
                <thead className="thead-dark">
                  <tr>
                    <th scope="col">Start Time</th>
                    <th scope="col">End Time</th>
                    <th scope="col">Customer</th>
                    <th scope="col">Service</th>
                    <th scope="col">&nbsp;</th>
                  </tr>
                </thead>
                <tbody>
                  {selectedAppointments.length < 1 ? 
                    <tr>
                      <td>"Select a date to view your appointments"</td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                    </tr>:
                    selectedAppointments.sort(sortDates["fn"]).map((appointment) => (
                      <tr key={appointment.appointmentId}>
                        <td>{formatTimeStamp(appointment.startTime)}</td>
                        <td>{formatTimeStamp(appointment.endTime)}</td>
                        {/* <td>{getCustomerName(appointment.customerId)}</td> */}
                        <td>{formatName(appointment.customerName)}</td>
                        <td>{formatName(appointment.serviceName)}</td> 
                        {/* <td>{appointment.employeeName}</td>  */}
                        <td className="buttonContainer">
                          <button
                            className="btn btn-danger"
                            onClick={() => handleDeleteAppointment(appointment.appointmentId)}
                          >
                            Cancel
                          </button>
                        </td>
                      </tr>
                    ))

                  }
                  
                </tbody>
                  </table>

            </div>
          </div>
      </div>
    </>
  );
}

export default AppointmentList;
