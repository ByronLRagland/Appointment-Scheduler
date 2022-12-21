import { useEffect, useState } from "react";
import { useHistory, Link, useParams } from "react-router-dom";
import AuthContext from "../../contexts/AuthContext";
import { useContext } from "react";
import { add } from "../../Services/Appointments.js";
import { findByUserId } from "../../Services/GeneralService";

// private int appointmentId;
// private Timestamp startTime;
// private Timestamp endTime;
// private int employeeId;
// private int customerId;
// private int serviceId;

var timestamp = new Date().getTime();

const APPOINTMENT_DEFAULT = {
    startTime: timestamp,
    endTime: timestamp,
    employeeId: -1,
    employeeName: "",
    customerId: 1,
    customerName: "",
    serviceId: 1,
    serviceName: ""
};

function AppointmentForm() {

   const { user, logout } = useContext(AuthContext);
   //appUserId
   const history = useHistory();
//    const endpoint = "http://localhost:8080/api/appointment";
   const [appointment, setAppointment] = useState(APPOINTMENT_DEFAULT);
   const [errs, setErrs] = useState([]);
   const [currentUser, setCurrentUser] = useState({
     
    firstName: "",
   
});

// const [userId, setUserId] = useState(user.appUserId);


useEffect(() => {
    if (user)
    {
        findByUserId(user.appUserId)
        .then(setCurrentUser)
        .catch(() => history.push("/error"));
    }
 }, [user, history]);

   const handleSubmit = (event) => {
    setErrs([]);
    console.log(appointment);
    event.preventDefault();
        add(appointment)
        .then(confirm)
        .then(() => history.push("/"))
        .catch(errs => {
            if (errs) {
                setErrs(errs);
                console.log(errs)
            } else {
                history.push("/error")
            }
        });
        
    };

    const confirm = () =>
    {
      window.confirm("Availability has been updated.");
    }

    const resetState = () => {
        setAppointment(APPOINTMENT_DEFAULT);
        setErrs([]);
      };

    const handleChange = (event) => {
        // make a copy of the object
        const newAppointment = { ...appointment };

            let rawTime = event.target.value;
            // console.log("Raw: " + rawTime);
            // // "2022-12-10T13:45"
            // // MM/DD/YYYY HH:MM:SS
            // let inputString = rawTime.substring(6,10) + "-" + rawTime.substring(0,2) +
            //  "-" + rawTime.substring(3,5) + "T" + rawTime.substring(11);
            //  console.log(inputString);
            newAppointment[event.target.name] = rawTime;
            newAppointment.employeeId = user.appUserId;
            newAppointment.employeeName = currentUser.firstName;

        setAppointment(newAppointment);
    };

    return(
        <div className="container">
          <h2 className="mt-5">Add Availability</h2>
          <div className="mt-5">
            {errs.length !== 0 && <div className="alert alert-danger">
              <ul>
                  {errs.map(err => <li key={err}>{err}</li>)}
              </ul>
            </div>}

            <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="startTime">Start Time: </label>
              <input
                id="startTime"
                name="startTime"
                type="datetime-local"
                className="form-control"
                onChange={handleChange}
                max="2025-12-31T19:30"
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="endTime">End Time: </label>
              <input
                id="endTime"
                name="endTime"
                type="datetime-local"
                className="form-control"
                onChange={handleChange}
                max="2025-12-31T20:30"
                required
              />
            </div>
            
            <div className="mt-5">
              <button className="btn bg-custom-purple mr-4 text-white" type="submit">
                Add Availability
              </button>
              <Link className="btn bg-cancel text-white" to="/">
                Cancel
              </Link>
            </div>
          </form>

          </div>
        </div>
    )
}

export default AppointmentForm;
  