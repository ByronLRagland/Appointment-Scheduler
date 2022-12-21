import { Link } from "react-router-dom";
function AdminNav() {

    return (
        <>
            {/* <Link to="/services/enabled" className="p-2 text-dark">Active Services</Link>
            <Link to="/services/disabled" className="p-2 text-dark">Inactive Services</Link>
            <Link to="/employees/enabled" className="p-2 text-dark">Active Employees</Link>
            <Link to="/employees/disabled" className="p-2 text-dark">Inactive Employees</Link> 
            <Link to="/customers/enabled" className="p-2 text-dark">Active Customers</Link>
            <Link to="/customers/disabled" className="p-2 text-dark">Inactive Customers</Link>  */}

            <Link to="/services/enabled" className="p-2 text-dark">Manage Services</Link>
            <Link to="/employees/enabled" className="p-2 text-dark">Manage Employees</Link>
            <Link to="/customers/enabled" className="p-2 text-dark">Manage Customers</Link>

        </>
      );

}

export default AdminNav