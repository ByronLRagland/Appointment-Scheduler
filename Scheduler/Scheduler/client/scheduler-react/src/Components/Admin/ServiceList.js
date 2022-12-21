import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router-dom";
import { Link } from "react-router-dom";
import { findAll } from "../../Services/Services";


function ServiceList() {
  const [services, setServices] = useState([]);

  const history = useHistory();
  const { activeStatus } = useParams();

  const init = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${localStorage.getItem("jwt")}`
    }
};

  useEffect(() => {
    findAll(activeStatus)
      .then(setServices)
      .catch(() => history.push("/error"));
  },[activeStatus]);


  const enable = (id)=>{
    fetch(`http://localhost:8080/api/services/enable/${id}`, init)
    .then((response) => {
        if (response.status==204) {
          history.push("/services/enabled");
        } else {
            history.push("/error");
        }
      });
  }

  const disable = (id)=>{
    fetch(`http://localhost:8080/api/services/disable/${id}`, init)
    .then((response) => {
        if (response.status==204) {
          history.push("/services/disabled");
        } else {
            history.push("/error");
        }
      });
  }


  return (
    <>
      {
        (activeStatus==="enabled" ) ? <h2 className="title-list-form my-4">Active Services</h2> : 
        <h2 className="title-list-form my-4">Inactive Services</h2>
      }
      
      <table className="table table-striped">
        <thead>
        { (services.length===0)? <h6>No services to show</h6> :
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Estimated Duration</th>
            <th>Price</th>
            <th>&nbsp;</th> 
          </tr>}
        </thead>
        <tbody>
          {services.map((s) => (
             <tr key={s.serviceId}>
             <td >{s.serviceName}</td>
             <td>{s.serviceDescription}</td>
             <td>{s.duration}</td>
             <td>{s.price}</td>
             <td className="buttonContainer">
                <Link
                  className="btn btn-primary mr-2"
                  to={`/services/service/edit/${s.serviceId}`}
                >
                  Edit
                </Link>
                {activeStatus=="enabled" ? (<button
                  className="btn btn-danger"
                  onClick={() => disable(s.serviceId)} 
                >
                  Deactivate
                </button>) : <button
                  className="btn btn-danger"
                  onClick={() => enable(s.serviceId)} 
                >
                  Activate
                </button> }
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {(activeStatus==="enabled") &&
   
      <div className="buttonContainerLogin mt-4">
      <Link className="btn btn-success-new mr-2" to="/services/service/add">
        Add Service
      </Link>

      <Link className="btn btn-primary" to="/services/disabled">View Inactive Services</Link>
              
      </div>

     }
    </>
  );
}

export default ServiceList;