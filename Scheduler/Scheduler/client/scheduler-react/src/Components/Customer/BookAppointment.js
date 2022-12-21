import { useEffect, useState } from "react";
import { useHistory, Link, useParams } from "react-router-dom";
import { findById } from "../../Services/Appointments";
import { findAll } from "../../Services/Services";
import { save } from "../../Services/Appointments";
import { findByUserId } from "../../Services/GeneralService";
import AuthContext from "../../contexts/AuthContext";
import { useContext } from "react";
// import Service from "../Service";

var timestamp = new Date().getTime();

const DEFAULT = {
  startTime: timestamp,
  endTime: timestamp,
  employeeId: -1,
  employeeName: "",
  customerId: 1,
  customerName: "",
  serviceId: 1,
  serviceName: "",
};

function BookAppointment() {
  const [appointment, setAppointment] = useState(DEFAULT);

  const [services, setServices] = useState([]);
  const [errors, setErrors] = useState([]);
  const { user, logout } = useContext(AuthContext);
  const [currentUser, setCurrentUser] = useState({
    firstName: "",
  });
  const history = useHistory();
  const { appointmentId } = useParams();
  //const { user, logout } = useContext(AuthContext);

  useEffect(() => {
    findById(appointmentId)
      .then(setAppointment)
      .catch(() => history.push("/error"));
  }, [history, appointmentId]);

  useEffect(() => {
    findAll("enabled")
      .then(setServices)
      .catch(() => history.push("/error"));
  }, [history, appointmentId]);

  useEffect(() => {
    if (user) {
      findByUserId(user.appUserId)
        .then(setCurrentUser)
        .catch(() => history.push("/error"));
    }
  }, [user, history]);

  function handleChange(event) {
    const newAppointment = { ...appointment };

    // if (event.target.name === "service") {
    //   const serviceId = parseInt(event.target.value, 10);
    //   if (event.target.checked) {
    //     APPOINTMENT_DEFAULT.services.push(
    //       services.find((s) => s.serviceId === serviceId)
    //     );
    //   } else {
    //     APPOINTMENT_DEFAULT.services = APPOINTMENT_DEFAULT.services.filter(
    //       (s) => s.serviceId !== serviceId
    //     );
    //   }
    // } else {
    newAppointment[event.target.name] = event.target.value;
    // }
    setAppointment(newAppointment);
    console.log(newAppointment);
  }

  const handleSubmit = (event) => {
    event.preventDefault();

    const selectedServiceId = services.find(
      (service) => service.serviceName === appointment.serviceName
    ).serviceId;

    console.log(user.appUserId);

    save({
      ...appointment,
      serviceId: selectedServiceId,
      customerId: user.appUserId,
      customerName: currentUser.firstName,
    })
      .then(() => history.push("/customer/appointments"))
      .catch((errors) => {
        if (errors) {
          setErrors(errors);
        } else {
          history.push("/error");
        }
      });
  };

  return (
    <>
      {errors.length > 0 && (
        <div>
          <h3>The following errors occured: </h3>
          <ul>
            {errors.map((error) => {
              return <li>{error}</li>;
            })}
          </ul>
        </div>
      )}

      <div className="container">
        <h2 className="mb-2">Book Appointment</h2>
        <h3>Services</h3>
        <form onSubmit={handleSubmit}>
          <div className="mb-3 form-check">
            {services.map((service) => (
              <div>
                <input
                  type="radio"
                  name="serviceName"
                  value={service.serviceName}
                  id={service.serviceName}
                  onChange={handleChange}
                />
                <label htmlFor={service.serviceName}>
                  ${service.price} {service.serviceName}{" "}
                  {service.serviceDescription}
                </label>
              </div>
            ))}
          </div>
          <div className="mt-4">
            <button
              style={{
                background: "rgb(150,125,250)",
                borderColor: "grey",
              }}
              className="btn btn-success-new mr-4"
              type="submit"
            >
              Confirm
            </button>
            <Link
              style={{
                background: "rgb(186,25,69)",
                borderColor: "grey",
                color: "white",
              }}
              className="btn btn-warning mb-0"
              to="/employee"
            >
              Cancel
            </Link>
          </div>
        </form>
      </div>
    </>
  );
}

export default BookAppointment;
