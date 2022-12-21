import { Link } from "react-router-dom";

function SimpleNav()
{
   
    return (

        <div className="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-0 bg-white border-bottom box-shadow">
            <Link to="/" className="my-0 mr-md-auto font-weight-normal text-decoration-none text-dark larger-text">Scheduler</Link>
            {/* <h5 className="my-0 mr-md-auto font-weight-normal">Scheduler</h5> */}
    
        </div>

    )
}


export default SimpleNav;