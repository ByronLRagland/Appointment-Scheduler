import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router-dom";
import { Link } from "react-router-dom";
import { findAllEmployees } from "../../Services/Employees";
import "./admin.css"


function EmployeeList() {
  const [employees, setEmployees] = useState([]);

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
    findAllEmployees(activeStatus)
      .then(setEmployees)
      .catch(() => history.push("/error"));
  },[activeStatus]);


  const enable = (id)=>{
    fetch(`http://localhost:8080/api/appuser/enable/${id}`, init)
    .then((response) => {
        if (response.status==204) {
          history.push("/employees/enabled");
        } else {
            history.push("/error");
        }
      });
  }

  const disable = (id)=>{
    fetch(`http://localhost:8080/api/appuser/disable/${id}`, init)
    .then((response) => {
        if (response.status==204) {
          history.push("/employees/disabled");
        } else {
            history.push("/error");
        }
      });
  }


  return (
    <>
     {
        (activeStatus==="enabled" ) ? <h2 className="title-list-form mt-4 mb-4">Active Employees</h2> : 
        <h2 className="title-list-form mt-5 mb-4">Inactive Employees</h2>
      }
     
      <table className="table table-striped">
        <thead>
        { (employees.length===0)? <h6>No employees to show</h6> :
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>&nbsp;</th> 
          </tr> }
        </thead>
        <tbody>
          {employees.map((e) => (
             <tr key={e.userId}>
             <td >{e.firstName}</td>
             <td>{e.lastName}</td>
             <td>{e.email}</td>
             <td className="buttonContainer">
                {activeStatus=="enabled" ? (<button
                  className="btn btn-danger"
                  onClick={() => disable(e.userId)} 
                >
                  Deactivate
                </button>) : <button
                  className="btn btn-danger"
                  onClick={() => enable(e.userId)} 
                >
                  Activate
                </button> }
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {/* <div className="buttonContainerLogin mt-4">
        <Link className="btn btn-success-new mr-2" to="/employees/employee/add">
          Add Employee
        </Link>
               
        <Link className="btn btn-primary" to="/employees/disabled">View Inactive Employees</Link>
                 
      </div> */}
      {(activeStatus==="enabled") &&
   
      <div className="buttonContainerLogin mt-4">
      <Link className="btn btn-success-new mr-2" to="/employees/employee/add">
        Add Employee
      </Link>

      <Link className="btn btn-primary" to="/employees/disabled">View Inactive Employees</Link>
              
      </div>

    }
    </>
  );
}

export default EmployeeList;