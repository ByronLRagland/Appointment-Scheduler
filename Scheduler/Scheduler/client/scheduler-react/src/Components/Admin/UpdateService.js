import {useState, useEffect} from "react";
import { useHistory, Link, useParams } from "react-router-dom";

import ServiceForm from "./ServiceForm";

const SERVICE_DEFAULT = {
    serviceName: "",
    serviceDescription: "",
    duration: 0,
    price: 0, 
  };

function UpdateService(){
    const endpoint = "http://localhost:8080/api/services";
    const [service, setService] = useState(SERVICE_DEFAULT);
    const [errors, setErrors] = useState([]);
    const history = useHistory();
    const { id } = useParams();

    useEffect(() => {
        fetch(`${endpoint}/${id}`)
        .then((res) => res.json())
        .then((data) => setService(data))
        .catch((errors) => history.push("/notfound")) ;
        }
      );

    const processService = (event, newService) =>{
        event.preventDefault();
        const init = {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
              "Authorization": `Bearer ${localStorage.getItem("jwt")}`
              
            },
            body: JSON.stringify(newService),
          };
          fetch(`${endpoint}/${id}`, init)
          .then((response) => {
              if (response.status === 204) {
                  return null;
              } else if (response.status === 400) {
                return response.json();
              } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
              }
           })
        .then((data) => {
            if (!data) {
            resetState();
            history.push("/services/enabled");
            } else {
            setErrors(data); 
            }
        })
        .catch((error) => console.log(error)); 
       
    }

    
    const resetState = () => {
        setService(SERVICE_DEFAULT);
        setErrors([]);
      };

    return(

        <>
             <h2  className="title-list-form my-4">Update a Service</h2>
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
            { service.serviceId && (
            <ServiceForm  processService={processService} service={service} /> 
            )}  
            </div>         
        </>
    );


}

export default UpdateService;