import { useHistory,  Link, useParams } from "react-router-dom";
import {useState} from "react";
 
function ServiceForm({processService, service }){

    const [newService, setNewService] = useState(service);

    const handleChange = (event) => {
        const newNewService = { ...newService };
        newNewService[event.target.name] = event.target.value;
        setNewService(newNewService);
    }
        
    const handleSubmit = (event) =>{
      //event.preventDefault();
      processService(event, newService);
    }

    return (
        <>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="serviceName">Service Name:</label>
              <input
                id="serviceName"
                name="serviceName"
                type="text"
                className="form-control"
                value={newService.serviceName}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="serviceDescription">Service Description:</label>
              <input
                id="serviceDescription"
                name="serviceDescription"
                type="text"
                className="form-control"
                value={newService.serviceDescription}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="duration">Estimated Duration:</label>
              <input
                id="duration"
                name="duration"
                type="number"
                className="form-control"
                value={newService.duration}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="price">Price:</label>
              <input
                id="price"
                name="price"
                type="number"
                className="form-control"
                value={newService.price}
                onChange={handleChange}
              />
            </div>
            <div className="mt-4">
              <button className="btn btn-success-new mr-4" type="submit">
                {newService.serviceId > 0 ? "Update Service" : "Add Service"}
              </button>
              <Link className="btn btn-danger" to="/services/enabled">
                Cancel
              </Link>
            </div>
          </form>
        </>
      );
}

export default ServiceForm;