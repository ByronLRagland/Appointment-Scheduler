import { Link } from "react-router-dom";
function EmployeeNav() {

    return (
        <>
            {/* <Link to="" className="p-2 text-dark">Your appointments</Link>
            <Link to="" className="p-2 text-dark">Availability</Link> */}
            {/* <a className="p-2 text-dark" href="#">Your appointments</a>  */}
            <Link to="/availability" className="p-2 text-dark">Availability</Link>
            <Link to="/appointments" className="p-2 text-dark">Your appointments</Link>
            {/* <a className="p-2 text-dark" href="#">Availability</a>  */}
        </>
      );

}

export default EmployeeNav