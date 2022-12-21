import { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";
import Employee from "../Employee";
import { findAllEmployees } from "../../Services/Employees";

function ViewEmployees({ handleViewSchedule }) {
  const [employees, setEmployees] = useState([]);

  const history = useHistory();
  const { userType } = useParams();

  useEffect(() => {
    findAllEmployees("enabled")
      .then(setEmployees)
      .catch(() => history.push("/error"));
  }, [history, userType]);

  return (
    <>
      <div className="container mt-5" style={{ marginBottom: "30px" }}>
        <h2>Employees</h2>
        <div className="row row-cols-3 g-2 mt-5">
          {employees.map((e) => (
            <Employee
              key={e.userId}
              employee={e}
              handleViewSchedule={handleViewSchedule}
            />
          ))}
        </div>
      </div>
    </>
  );
}

export default ViewEmployees;
