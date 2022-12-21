import { Link } from "react-router-dom";
import { useContext } from "react";
import AuthContext from "../contexts/AuthContext";

function Greeting({ currentUser }) {
  const { user } = useContext(AuthContext);
    if (user && currentUser.firstName)
    {
        return (
            <a className="p-2 text-dark" href="#">Hi, {currentUser.firstName}</a> 
          );
    }
 
}

export default Greeting;
