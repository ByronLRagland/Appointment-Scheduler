import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router-dom";
import { Link } from "react-router-dom";
import { findAllCustomers } from "../../Services/Customers";


function CustomerList() {
  const [customers, setCustomers] = useState([]);

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
    findAllCustomers(activeStatus)
      .then(setCustomers)
      .catch(() => history.push("/error"));
  },[activeStatus]);


  const enable = (id)=>{
    fetch(`http://localhost:8080/api/appuser/enable/${id}`, init)
    .then((response) => {
        if (response.status==204) {
          history.push("/customers/enabled");
        } else {
            history.push("/error");
        }
      });
  }

  const disable = (id)=>{
    fetch(`http://localhost:8080/api/appuser/disable/${id}`, init)
    .then((response) => {
        if (response.status==204) {
          history.push("/customers/disabled");
        } else {
            history.push("/error");
        }
      });
  }


  return (
    <>
     {
        (activeStatus==="enabled" ) ? <h2 className="title-list-form my-4">Active Customers</h2> : 
        <h2 className="title-list-form my-4">Inactive Customers</h2>
      }
      <table className="table table-striped">
        <thead>
       { (customers.length===0)? <h6>No customers to show</h6> :
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>&nbsp;</th> 
          </tr> }
        </thead>
        <tbody>
          { customers.map((c) => (
             <tr key={c.userId}>
             <td >{c.firstName}</td>
             <td>{c.lastName}</td>
             <td>{c.email}</td>
             <td className="buttonContainer">
                {activeStatus=="enabled" ? (<button
                  className="btn btn-danger"
                  onClick={() => disable(c.userId)} 
                >
                  Deactivate
                </button>) : <button
                  className="btn btn-danger"
                  onClick={() => enable(c.userId)} 
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
        <Link className="btn btn-primary" to="/customers/disabled">View Inactive Customers</Link>
              
      </div>

      }

    </>
  );
}

export default CustomerList;