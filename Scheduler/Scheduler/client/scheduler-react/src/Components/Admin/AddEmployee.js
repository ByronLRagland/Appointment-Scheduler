import {useState, useEffect} from "react";
import { useHistory, Link, useParams } from "react-router-dom";

import EmployeeForm from "./EmployeeForm";
//import "./admin.css"


const EMPLOYEE_DEFAULT = {
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  };

function AddEmployee(){
    const endpoint = "http://localhost:8080/api/appuser/employee";
    const [employee, setEmployee] = useState(EMPLOYEE_DEFAULT);
    const [errors, setErrors] = useState([]);
    const history = useHistory();

    const processEmployee = (event, newEmployee) =>{
        event.preventDefault();
        const init = {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "Authorization": `Bearer ${localStorage.getItem("jwt")}`
              
            },
            body: JSON.stringify(newEmployee),
          };
          fetch(endpoint, init)
            .then((response) => {
              if (response.status === 201 || response.status === 400) {
                return response.json();
              } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
              }
            })
        .then((data) => {
            if (data.userId) {
            resetState();
            history.push("/employees/enabled");
            } else {
            setErrors(data); 
            }
        })
        .catch((error) => console.log(error)); 
      
    }

    
    const resetState = () => {
        setEmployee(EMPLOYEE_DEFAULT);
        setErrors([]);
      };

    return(

        <>
             <h2  className="title-list-form my-4">Add an Employee</h2>
             <div className="presentForm">
              {errors.length > 0 && (
              <div>
                  <ul  className="alert alert-danger">
                  {errors.map((e) => {
                  return <li key ={e.toString()}>
                  {e}
                  </li>;
                  })}
                  </ul>
              </div>
              )}   
              <EmployeeForm  processEmployee={processEmployee} employee={employee} /> 
            </div>
        </>
    );


}

export default AddEmployee;