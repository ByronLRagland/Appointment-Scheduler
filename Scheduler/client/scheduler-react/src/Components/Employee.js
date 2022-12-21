// import { useContext } from "react";
import { Link } from "react-router-dom";

function Employee({ employee }) {
  // const { user } = useContext(AuthContext);

  return (
    <div className="col">
      <div className="card" style={{ marginBottom: "30px" }}>
        <img
          src={employee.imageUrl}
          className="card-img-top"
          alt={employee.firstName}
        />
        <div className="card-body">
          <h5 className="card-title">
            <p style={{ textAlign: "center" }}>
              {employee.firstName} {employee.lastName}
            </p>
            <p style={{ textAlign: "center" }}>
              <Link
                style={{
                  background: "rgb(13,89,243)",
                  borderColor: "grey",
                }}
                to={`/schedule/${employee.userId}`}
                className="btn btn-primary"
              >
                View Schedule
              </Link>
            </p>
          </h5>
        </div>
        <div style={{ textAlign: "center" }} className="card-footer">
          {employee.bio}
        </div>
      </div>
    </div>
  );
}

export default Employee;
