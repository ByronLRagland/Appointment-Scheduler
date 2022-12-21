import { Link } from "react-router-dom";
function CustomerNav() {
  return (
    <>
      {/* <Link to="" className="p-2 text-dark">Your appointments</Link>
            <Link to="" className="p-2 text-dark">Book appointments</Link> */}
      <Link to="/employee" className="p-2 text-dark">
        Book appointments
      </Link>
      <Link to="/customer/appointments" className="p-2 text-dark">
        Your appointments
      </Link>
      {/* <a className="p-2 text-dark" href="#">Book appointments</a>  */}
    </>
  );
}

export default CustomerNav;
