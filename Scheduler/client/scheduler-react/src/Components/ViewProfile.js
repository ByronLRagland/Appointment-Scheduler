import { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";
import { useContext } from "react";
import AuthContext from "../contexts/AuthContext";
import Profile from "./Profile";
import { findByUserId } from "../Services/GeneralService";

const PROFILE_DEFAULT = {
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    phone: "",
    bio: "",
    imageUrl: ""
  }

  

function ViewProfile() {
    
   const { user} = useContext(AuthContext);
   const [profile, setProfile] = useState(PROFILE_DEFAULT);

   const history = useHistory();

  
  useEffect(() => {
      if (user){
    
    findByUserId(user.appUserId)
      .then((data) => setProfile(data))
      .catch(() => history.push("/error"));
      }
  }, [user]);

  return (
    <>
      {/* <div className="row row-cols-3 g-2" id="viewProfile">  */}
      <h2 className="title-list-form my-4">Profile Details</h2>
          <Profile
            key={profile.userId}
            profile={profile}
          />
      {/* </div> */}
    </>
  );
}

export default ViewProfile;