import { useEffect, useState } from "react";
import { useHistory, Link, useParams } from "react-router-dom";
import AuthContext from "../../contexts/AuthContext";
import { useContext } from "react";
import { deleteById } from "../../Services/Appointments.js";
import { findByEmployee } from "../../Services/Appointments.js";
import { findByUserId } from "../../Services/GeneralService.js";

function ViewSchedule() {
  const { user, logout } = useContext(AuthContext);
  const [appointments, setAppointments] = useState([]);
  const history = useHistory();
  const [errs, setErrs] = useState([]);
  const [customer, setCustomer] = useState(null);

  const { userId } = useParams();

  useEffect(() => {
    findByEmployee(userId)
      .then(setAppointments)
      .catch(() => history.push("/error"));
  }, [history, userId]);

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

    if (tempHour > 12) {
      hour = tempHour - 12 + "";
      mark = "PM";
    }

    // formattedTime += year + "/" + month + "/" + day + " at " + hour + ":" + minute;
    let tempDate = new Date(timestamp);
    formattedTime = tempDate.toLocaleDateString("en-us", {
      weekday: "long",
      month: "short",
      day: "numeric",
    });
    formattedTime += " at " + hour + ":" + minute + mark;

    return formattedTime;
  }

  function formatName(name) {
    console.log(name);
    if (name === "" || name === "dummy") {
      return "TBD";
    } else {
      return name;
    }
  }

  const sortDates = {
    fn: (a, b) => new Date(a.startTime) - new Date(b.startTime),
  };

  return (
    <>
      <div className="container">
        <h2>Employee Schedule</h2>
        <table>
          <thead>
            <tr className="columns">
              <th scope="col">Status</th>
              <th scope="col">Start Time</th>
              <th scope="col">End Time</th>
              <th scope="col">&nbsp;</th>
            </tr>
          </thead>
          <tbody>
            {appointments.length < 1
              ? "No appointments available"
              : appointments.sort(sortDates["fn"]).map((appointment) => (
                  <tr className="rows" key={appointment.appointmentId}>
                    <td>{appointment.customerName ? "Booked" : "Open"}</td>
                    <td>{formatTimeStamp(appointment.startTime)}</td>
                    <td>{formatTimeStamp(appointment.endTime)}</td>
                    <td className="buttonContainer">
                      {appointment.customerName ? (
                        ""
                      ) : (
                        <Link
                          style={{
                            background: "rgb(13,89,243)",
                            borderColor: "grey",
                          }}
                          className="btn btn-secondary"
                          to={`/book/${appointment.appointmentId}`}
                        >
                          Book Appointment
                        </Link>
                      )}
                    </td>
                  </tr>
                ))}
          </tbody>
        </table>
      </div>
    </>
  );
}

export default ViewSchedule;
