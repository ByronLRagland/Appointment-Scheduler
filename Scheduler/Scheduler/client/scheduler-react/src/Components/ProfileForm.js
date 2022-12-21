import { useHistory,  Link, useParams } from "react-router-dom";
import {useState} from "react";
 
function ProfileForm({processProfile, profile }){

    const [newProfile, setNewProfile] = useState(profile);

    const handleChange = (event) => {
        const newNewProfile = { ...newProfile };
        newNewProfile[event.target.name] = event.target.value;
        setNewProfile(newNewProfile);
    }
        
    const handleSubmit = (event) =>{
      //event.preventDefault();
      processProfile(event, newProfile);
    }

    return (
        <>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="firstName">First Name:</label>
              <input
                id="firstName"
                name="firstName"
                type="text"
                className="form-control"
                value={newProfile.firstName}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="lastName">Last Name:</label>
              <input
                id="lastName"
                name="lastName"
                type="text"
                className="form-control"
                value={newProfile.lastName}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="email">Email:</label>
              <input
                id="email"
                name="email"
                type="text"
                className="form-control"
                value={newProfile.email}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="password">Password:</label>
              <input
                id="password"
                name="password"
                type="text"
                className="form-control"
                value={newProfile.password}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="phone">Phone:</label>
              <input
                id="phone"
                name="phone"
                type="text"
                className="form-control"
                value={newProfile.phone}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="bio">Bio:</label>
              <input
                id="bio"
                name="bio"
                type="text"
                className="form-control"
                value={newProfile.bio}
                onChange={handleChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="url">Profile Picture:</label>
              <input
                id="imageUrl"
                name="imageUrl"
                type="text"
                className="form-control"
                value= {newProfile.imageUrl}
                onChange={handleChange}
              />
            </div>
            <div className="mt-4 pb-5">
              <button className="btn btn-success-new mr-4" type="submit">
                {newProfile.userId > 0 ? "Update Profile" : "Add Profile"}
              </button>
              <Link className="btn btn-danger" to="/profile">
                Cancel
              </Link>
            </div>
          </form>
        </>
      );
}

export default ProfileForm;