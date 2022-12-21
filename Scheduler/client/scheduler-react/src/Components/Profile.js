// import { useContext } from "react";
import { Link } from "react-router-dom";

function Profile({ profile }) {
  // const { user } = useContext(AuthContext);

  const PROFILE_DEFAULT = {
    firstName: "First name: TBD",
    lastName: "Last Name: TBD",
    email: "Email: TBD",
    phone: "Phone: TBD",
    bio: "Bio: TBD",
    imageUrl: "https://media.istockphoto.com/vectors/default-avatar-photo-placeholder-icon-grey-profile-picture-business-vector-id1327592449?k=20&m=1327592449&s=612x612&w=0&h=6yFQPGaxmMLgoEKibnVSRIEnnBgelAeIAf8FqpLBNww="
  }

  if (profile.firstName){
      PROFILE_DEFAULT.firstName=`First Name: ${profile.firstName}`;
  }

  if (profile.lastName){
    PROFILE_DEFAULT.lastName=`Last Name: ${profile.lastName}`;
  }

  if (profile.email){
    PROFILE_DEFAULT.email=`Email: ${profile.email}`;
  }

  if (profile.phone){
    PROFILE_DEFAULT.phone=`Phone: ${profile.phone}`
  }

  if (profile.bio){
    PROFILE_DEFAULT.bio=`Bio: ${profile.bio}`;
  }

  if (profile.imageUrl){
    PROFILE_DEFAULT.imageUrl=profile.imageUrl;
  }



  return (
    <>
    {/* <div className="col">
      <div className="card" id="card-profile">
        <img
          src={PROFILE_DEFAULT.imageUrl}
          className="card-img-top"
        />
        <div className="card-body">
          <h5 className="card-title">
            {PROFILE_DEFAULT.firstName}
          </h5>
          <h5>
          {PROFILE_DEFAULT.lastName} 
          </h5>
          <h5>
          {PROFILE_DEFAULT.email} 
          </h5>
          <h5>
          {PROFILE_DEFAULT.phone}
          </h5>
        </div>
        <div className="card-footer">
          {PROFILE_DEFAULT.bio}
        </div>
        <div className="buttonContainer" >
                <Link
                  className="btn btn-primary"
                  to={`/profile/edit/${profile.userId}`}
                >
                  Edit Profile
                </Link>
        </div>

      </div>
    </div> */}

    <div class="card w-50" id="card-profile">
    <div class="card-img-top d-flex align-items-center bg-light">
        <div>
            <img class="img-fluid"  src={PROFILE_DEFAULT.imageUrl} />
        </div>
        {/* <p class="card-body">Text next to the right of the image</p> */}
        <div className="card-body">
          <h5 className="card-title">
            {PROFILE_DEFAULT.firstName}
          </h5>
          <h5>
          {PROFILE_DEFAULT.lastName} 
          </h5>
          <h5>
          {PROFILE_DEFAULT.email} 
          </h5>
          <h5>
          {PROFILE_DEFAULT.phone}
          </h5>
        </div>
    </div>

    <div class="card-footer">
      {PROFILE_DEFAULT.bio}
    </div>
    <div >
    <Link
                  className="btn btn-primary"
                  to={`/profile/edit/${profile.userId}`}
                >
                  Edit Profile
                </Link>         
        </div>
</div> 






</>
  );
}

export default Profile;