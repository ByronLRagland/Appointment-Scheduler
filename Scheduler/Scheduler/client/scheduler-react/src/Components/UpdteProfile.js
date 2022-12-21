import {useState, useEffect} from "react";
import { useHistory, Link, useParams } from "react-router-dom";
import { useContext } from "react";
import AuthContext from "../contexts/AuthContext";

import ProfileForm from "./ProfileForm";
import { findByUserId } from "../Services/GeneralService";

const PROFILE_DEFAULT = {
  firstName: "",
  lastName: "",
  email: "",
  phone: "",
  bio: "",
  imageUrl: ""
}

function UpdateProfile(){
    const endpoint = "http://localhost:8080/api/appuser";
    const [profile, setProfile] = useState(PROFILE_DEFAULT);
    const [errors, setErrors] = useState([]);
    const history = useHistory();
    const { id } = useParams();
    const { user} = useContext(AuthContext);

    // useEffect(() => {
    //     fetch(`${endpoint}/${id}`)
    //     .then((res) => res.json())
    //     .then((data) => setProfile(data))
    //     .catch((errors) => history.push("/notfound")) ;
    //     }
    //   );

      useEffect(() => {
        console.log(user);
        if (user){
      
      findByUserId(user.appUserId)
        .then((data) => setProfile(data))
        .catch(() => history.push("/error"));
        }
    }, [user]);

    const processProfile = (event, newProfile) =>{
        event.preventDefault();

        const userUpdate={
          userId: newProfile.userId, 
          firstName: newProfile.firstName,
          lastName: newProfile.lastName,
          bio: newProfile.bio,
          imageUrl: newProfile.imageUrl,
          phone: newProfile.phone,
          email: newProfile.email,
          password: newProfile.password,
          userType: "admin"
      }
      console.log(JSON.stringify(userUpdate));
        const init = {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
              "Authorization": `Bearer ${localStorage.getItem("jwt")}`  
            },
            body: JSON.stringify(userUpdate),
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
            history.push("/profile");
            } else {
            setErrors(data); 
            }
        })
        .catch((error) => console.log(error)); 
       
    }

    
    const resetState = () => {
        setProfile(null);
        setErrors([]);
      };

    return(

        <> 
          <div className="container">
              <h2 className="my-4">Update Profile</h2>
              {errors.length > 0 && (
              <div>
                  <h3 className="errors">The following errors occured:</h3>
                  <ul className="errors">
                  {errors.map((e) => {
                  return <li key ={e.toString()}>
                  {e}
                  </li>;
                  })}
                  </ul>
              </div>
              )} 
              { profile.userId && (
              <ProfileForm  processProfile={processProfile} profile={profile} /> 
              )}               

          </div>
            
        </>
    );


}

export default UpdateProfile;