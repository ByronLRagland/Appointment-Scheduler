import {useState, useEffect} from "react";
import { useHistory, Link, useParams } from "react-router-dom";

import ServiceForm from "./ServiceForm";

const SERVICE_DEFAULT = {
    serviceName: "",
    serviceDescription: "",
    duration: 0,
    price: 0, 
  };

function AddService(){
    const endpoint = "http://localhost:8080/api/services";
    const [service, setService] = useState(SERVICE_DEFAULT);
    const [errors, setErrors] = useState([]);
    const history = useHistory();

    const processService = (event, newService) =>{
        event.preventDefault();
        const init = {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "Authorization": `Bearer ${localStorage.getItem("jwt")}`
              
            },
            body: JSON.stringify(newService),
          };
          fetch(endpoint, init)
            .then((response) => {
              if (response.status === 201 || response.status === 400) {
                return response.json();
              } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
              }
            })
        .then((data) => {
            if (data.serviceId) {
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
          <h2  className="title-list-form my-4">Add a Service</h2>
            <div className="presentForm">
            {errors.length > 0 && (
            <div  className="alert alert-danger"> 
                <ul className="errors">
                {errors.map((e) => {
                 return <li key ={e.toString()}>
                 {e}
                 </li>;
                })}
                </ul>
            </div>
            )}   
            <ServiceForm  processService={processService} service={service} /> 
            </div>
        </>
    );


}

export default AddService;