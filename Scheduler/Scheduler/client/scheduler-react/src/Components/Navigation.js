import { useContext } from "react";
import { Link } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";
import { useEffect, useState } from "react";
import EmployeeNav from "./EmployeeNav";
import CustomerNav from "./CustomerNav"
import AdminNav from "./AdminNav";
import Greeting from "./Greeting";
import { useHistory} from "react-router-dom";
import {findByUserId} from "../Services/GeneralService.js"

function Navigation()
{
    const { user, logout } = useContext(AuthContext);

    const [currentUser, setCurrentUser] = useState({
     
        firstName: "",
       
    });

    // const [userId, setUserId] = useState(user.appUserId);

    const history = useHistory();

    useEffect(() => {
        if (user)
        {
            findByUserId(user.appUserId)
            .then(setCurrentUser)
            .catch(() => history.push("/error"));
            // console.log()
            // setCurrentUser(user);

           
        }
     }, [user,history]);

       
    

    return (

        <div className="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-0 bg-white border-bottom box-shadow">
            <Link to="/" className="my-0 mr-md-auto font-weight-normal text-decoration-none text-dark larger-text">Scheduler</Link>
            {/* <h5 className="my-0 mr-md-auto font-weight-normal">Scheduler</h5> */}
            <nav className="my-2 my-md-0 mr-md-3">

                {user && <Link to="/profile" className="p-2 text-dark">View Profile</Link>}

                {(user && user.authorities === "employee") ?
                    <EmployeeNav/>:

                    <></> 
                }

                {(user && user.authorities === "customer") ?
                    <CustomerNav/>:
                    <></> 
                }

                {(user && user.authorities === "admin") ?
                    <AdminNav/>:
                    <></> 
                }

                <Greeting currentUser = {currentUser}/>
 
            </nav>

            {user ? <button className="btn btn-outline-primary" onClick={()=>logout(history)}>Logout</button>
                        : <Link to="/login" className="btn btn-success-new">Login</Link>
            }
        </div>

    )
}


export default Navigation;