import { useContext, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { add } from "../Services/addCustomer";

const endpoint = "http://localhost:8080/api/appuser/customer"
function SignUp() {


    const [errors, setErrors] = useState([]);

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [phone, setPhone] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [hasErr, setHasErr] = useState(false);


    
    const history = useHistory();

    const handleFirstNameChange = (evt) => setFirstName(evt.target.value);
    const handleLastNameChange = (evt) => setLastName(evt.target.value);
    const handlePhoneChange = (evt) => setPhone(evt.target.value);
    const handleEmailChange = (evt) => setEmail(evt.target.value);
    const handlePasswordChange = (evt) => setPassword(evt.target.value);

    function handleSubmit(evt) {
        evt.preventDefault();

        const user = {
            firstName,
            lastName,
            phone,
            email,
            password,
        };
        //console.log(user);
        // add(user)
        //     .then(u => {
        //         history.push("/");
        //     })
        //     .catch(() => setHasErr(true));
        const init = {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(user)
        };

        fetch(endpoint,init)
            .then((response) => {
              if (response.status === 201 || response.status === 400) {
                return response.json();
              } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
              }
            })
        .then((data) => {
            if (data.userId) {
            resetState();
            confirm();
            history.push("/");
            } else {
            setErrors(data); 
            }
        })
        .catch((error) => console.log(error)); 

        const resetState = () => {
            setErrors([]);
          };

        const confirm = () =>{
            window.confirm("Account has been created.");
        };
     
    }

    return (
        <>
        <div className="signUp mt-5">
        <h2 className="title-list-form mb-5">Account Sign Up</h2 >  
        {errors.length > 0 && (
        <div>
            <ul className="alert alert-danger">
            {errors.map((e) => {
             return <li key ={e.toString()}>
             {e}
             </li>;
            })}
            </ul>
        </div>
        )}   
        <form onSubmit={handleSubmit}>
            <div className="mb-2">
                <label htmlFor="firstName" className="form-label">First Name</label>
                <input type="text" id="firstName" name="firstName" className="form-control"
                    value={firstName} onChange={handleFirstNameChange}></input>
            </div>
            <div className="mb-2">
                <label htmlFor="lastName" className="form-label">Last Name</label>
                <input type="text" id="lastName" name="lastName" className="form-control"
                    value={lastName} onChange={handleLastNameChange}></input>
            </div>
            <div className="mb-2">
                <label htmlFor="phone" className="form-label">Phone Number</label>
                <input type="text" id="phone" name="phone" className="form-control"
                    value={phone} onChange={handlePhoneChange}></input>
            </div>
            <div className="mb-2">
                <label htmlFor="email" className="form-label">Email</label>
                <input type="text" id="email" name="email" className="form-control"
                    value={email} onChange={handleEmailChange}></input>
            </div>
            <div className="mb-2">
                <label htmlFor="password" className="form-label">Password</label>
                <input type="password" id="password" name="password" className="form-control"
                    value={password} onChange={handlePasswordChange}></input>
            </div>
            {hasErr && <div className="alert alert-danger">
                Invalid. :(
            </div>}
            <div className="buttonContainerLogin mt-4">
                <button className="btn btn-success-new mr-2" type="submit">Submit</button>
                <Link className="btn btn-danger" to="/">Cancel</Link>
            </div>
        </form>
        </div>
        </>
    );
}

export default SignUp;