import { useHistory,  Link, useParams } from "react-router-dom";
import {useState} from "react";
 
function EmployeeForm({processEmployee, employee }){

    const [newEmployee, setNewEmployee] = useState(employee);

    const handleChange = (event) => {
        const newNewEmployee = { ...newEmployee };
        newNewEmployee[event.target.name] = event.target.value;
        setNewEmployee(newNewEmployee);
    }
        
    const handleSubmit = (event) =>{
      //event.preventDefault();
      processEmployee(event, newEmployee);
    }

    return (
        <>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="firstName">First Name:</label>
              <input
                id="firstName"
                name="firstName"
                type="text"
                className="form-control"
                value={newEmployee.firstName}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="lastName">Last Name:</label>
              <input
                id="lastName"
                name="lastName"
                type="text"
                className="form-control"
                value={newEmployee.lastName}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="email">Email:</label>
              <input
                id="email"
                name="email"
                type="text"
                className="form-control"
                value={newEmployee.email}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="password">Password:</label>
              <input
                id="password"
                name="password"
                type="text"
                className="form-control"
                value={newEmployee.password}
                onChange={handleChange}
              />
            </div>
            <div className="mt-4">
              <button className="btn btn-success-new mr-4" type="submit">
                {newEmployee.userId > 0 ? "Update Employee" : "Add Employee"}
              </button>
              <Link className="btn btn-danger" to="/employees/enabled">
                Cancel
              </Link>
            </div>
          </form>
        </>
      );
}

export default EmployeeForm;